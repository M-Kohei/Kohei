package com.example.matu.kobu_prototype.fragment

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import com.example.matu.kobu_prototype.R
import com.example.matu.kobu_prototype.thread.BluetoothClientThread
import com.example.matu.kobu_prototype.thread.BluetoothServerThread
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import trikita.log.Log
import android.app.Activity.RESULT_OK
import android.app.Activity
import android.content.ContentValues.TAG
import android.widget.Toast



/**
 * Created by riku on 16/12/17.
 */
class BluetoothConnectFragment : Fragment() {
	private val REQUEST_ENABLE_BLUETOOTH = 1

	private var connectAbleListView: ListView? = null
	private var searchProgress: ProgressBar? = null
	private val mBtAdapter = BluetoothAdapter.getDefaultAdapter()
	private val foundDeviceList = mutableListOf<BluetoothDevice>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		super.onCreateView(inflater, container, savedInstanceState)
		setHasOptionsMenu(true)

		val view = inflater?.inflate(R.layout.fragment_bluetooth_connect, container, false)
		view?.findViewById(R.id.start_search_button)?.setOnClickListener { v ->
			Log.d("button click")
			if (!mBtAdapter.isEnabled) {
				//OFFだった場合、ONにすることを促すダイアログを表示する画面に遷移
				val btOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
				startActivityForResult(btOn, REQUEST_ENABLE_BLUETOOTH)
			}
			//インテントフィルターとBroadcastReceiverの登録
			val filter = IntentFilter()
			filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
			filter.addAction(BluetoothDevice.ACTION_FOUND)
			filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED)
			filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
			activity.registerReceiver(DeviceFoundReceiver, filter)

			//接続可能なデバイスを検出
			if (mBtAdapter.isDiscovering) {
				//検索中の場合は検出をキャンセルする
				mBtAdapter.cancelDiscovery()
			}
			//デバイスを検索する
			//一定時間の間検出を行う
			mBtAdapter.startDiscovery()
		}

		searchProgress = view?.findViewById(R.id.search_progress) as? ProgressBar
		connectAbleListView = view?.findViewById(R.id.connect_able_list_view) as? ListView
		connectAbleListView?.adapter = ArrayAdapter<String>(activity.applicationContext, R.layout.list_view_black_text_layout)
		connectAbleListView?.setOnItemClickListener { adapterView, view, index, id ->
			val device = foundDeviceList[index]
			Log.d(device.name + "," + device.address)

			Observable.create(BluetoothClientThread(activity.applicationContext, device, mBtAdapter))
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(object: Observer<BluetoothSocket> {
						var socket: BluetoothSocket? = null
						override fun onCompleted() {
							Log.d("completed")
							val gameActivityIntent = Intent()
							gameActivityIntent.setClassName("com.example.matu.kobu_prototype","com.example.matu.kobu_prototype.MainActivity")
//							gameActivityIntent.putExtra(BLUETOOTH_SOCKET, socket)
							startActivity(gameActivityIntent)
						}

						override fun onError(e: Throwable) {
							Log.d("error")
						}

						override fun onNext(progress: BluetoothSocket) {
							Log.d(progress)
							socket = progress
						}
					})
		}

		//BluetoothAdapter取得
		if (mBtAdapter != null) {
			//Bluetooth対応端末の場合の処理
			Log.d("Bluetoothがサポートされてます。")
		} else {
			//Bluetooth非対応端末の場合の処理
			Log.d("Bluetoothがサポートれていません。")
			activity.finish()
		}

		return view
	}

	private val DeviceFoundReceiver = object: BroadcastReceiver(){
		//検出されたデバイスからのブロードキャストを受ける
		override fun onReceive(context: Context, intent:Intent){
			val action = intent.action
			val foundDevice: BluetoothDevice
			val connectAbleListAdapter = connectAbleListView?.adapter as? ArrayAdapter<String>
			if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
				Log.d("スキャン開始")
				connectAbleListAdapter?.clear()
				foundDeviceList.clear()
				searchProgress?.visibility = View.VISIBLE
			}
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				//デバイスが検出された
				foundDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
				connectAbleListAdapter?.add(foundDevice.name + "\n" + foundDevice.address)
				foundDeviceList.add(foundDevice)
				Log.d("ACTION_FOUND", foundDevice.name)
			}
			if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
				Log.d("スキャン終了")
				searchProgress?.visibility = View.GONE
			}
		}
	}

	override fun onResume() {
		super.onResume()
		//サーバースレッド起動、クライアントのからの要求待ちを開始
		Log.d("bluetooth server start")

		Observable.create(BluetoothServerThread(activity.applicationContext, mBtAdapter))
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object: Observer<BluetoothSocket> {
					var socket: BluetoothSocket? = null
					override fun onCompleted() {
						Log.d("completed")
						val gameActivityIntent = Intent()
						gameActivityIntent.setClassName("com.example.matu.kobu_prototype","com.example.matu.kobu_prototype.MainActivity")
//						gameActivityIntent.putExtra(BLUETOOTH_SOCKET, socket?.inputStream)
						startActivity(gameActivityIntent)
					}

					override fun onError(e: Throwable) {
						Log.d("error")
					}

					override fun onNext(progress: BluetoothSocket) {
						Log.d(progress)
						socket = progress
					}
				})
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.bluetooth_activity_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle item selection
		when (item.itemId) {
			R.id.do_search_able -> {
				val discoverableOn = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
				discoverableOn.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
				startActivity(discoverableOn)
				return true
			}
			else -> return super.onOptionsItemSelected(item)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode != REQUEST_ENABLE_BLUETOOTH)
			return
		if (resultCode == Activity.RESULT_OK) {
			// When the request to enable Bluetooth returns
			// Bluetooth is now enabled, so set up a chat session
		} else {
			// User did not enable Bluetooth or an error occured
			Log.d(TAG, "BT not enabled")
			activity.finish()
		}
	}
}