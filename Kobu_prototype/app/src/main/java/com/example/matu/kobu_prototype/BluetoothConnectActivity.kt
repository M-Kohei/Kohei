package com.example.matu.kobu_prototype

import android.app.Activity


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import trikita.log.Log
import android.content.Intent
import android.content.IntentFilter
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_STARTED
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.bluetooth.BluetoothDevice.ACTION_NAME_CHANGED
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import com.example.matu.kobu_prototype.thread.BluetoothClientThread
import com.example.matu.kobu_prototype.thread.BluetoothServerThread


class BluetoothConnectActivity : AppCompatActivity() {
	private val REQUEST_ENABLE_BLUETOOTH = 1

	private var connectAbleListView:ListView? = null
	private var searchProgress:ProgressBar? = null
	private val mBtAdapter = BluetoothAdapter.getDefaultAdapter()
	private val foundDeviceList = mutableListOf<BluetoothDevice>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_bluetooth_connect)


		findViewById(R.id.start_search_button).setOnClickListener {v ->
			Log.d("button click")
			if (!mBtAdapter.enable()) {
				//OFFだった場合、ONにすることを促すダイアログを表示する画面に遷移
				val btOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
				startActivityForResult(btOn, REQUEST_ENABLE_BLUETOOTH)
			}
			//インテントフィルターとBroadcastReceiverの登録
			val filter = IntentFilter()
			filter.addAction(ACTION_DISCOVERY_STARTED)
			filter.addAction(ACTION_FOUND)
			filter.addAction(ACTION_NAME_CHANGED)
			filter.addAction(ACTION_DISCOVERY_FINISHED)
			registerReceiver(DeviceFoundReceiver, filter)

			//接続可能なデバイスを検出
			if (mBtAdapter.isDiscovering) {
				//検索中の場合は検出をキャンセルする
				mBtAdapter.cancelDiscovery()
			}
			//デバイスを検索する
			//一定時間の間検出を行う
			mBtAdapter.startDiscovery()
		}

		searchProgress = findViewById(R.id.search_progress) as? ProgressBar
		connectAbleListView = findViewById(R.id.connect_able_list_view) as? ListView
		connectAbleListView?.adapter = ArrayAdapter<String>(applicationContext, R.layout.list_view_black_text_layout)
		connectAbleListView?.setOnItemClickListener { adapterView, view, index, id ->
			val device = foundDeviceList[index]
			Log.d(device.name + "," + device.address)
			val btClientThread = BluetoothClientThread(applicationContext, device, mBtAdapter)
			btClientThread.start()
		}

		//BluetoothAdapter取得
		if (mBtAdapter != null) {
			//Bluetooth対応端末の場合の処理
			Log.d("Bluetoothがサポートされてます。")
		} else {
			//Bluetooth非対応端末の場合の処理
			Log.d("Bluetoothがサポートれていません。")
			finish()
		}
	}

	override fun onActivityResult(requestCode:Int,ResultCode :Int, date:Intent){
		//ダイアログ画面から結果を受けた後の処理を記述
		if(requestCode == REQUEST_ENABLE_BLUETOOTH){
			if(ResultCode == Activity.RESULT_OK){
				//BluetoothがONにされた場合の処理
				Log.d("BluetoothをONにしてもらえました。")
			}else{
				Log.d("BluetoothがONにしてもらえませんでした。")
				finish()
			}
		}
	}

	private val DeviceFoundReceiver = object: BroadcastReceiver(){
		//検出されたデバイスからのブロードキャストを受ける
		override fun onReceive(context: Context, intent:Intent){
			val action = intent.action
			val foundDevice: BluetoothDevice
			val connectAbleListAdapter = connectAbleListView?.adapter as? ArrayAdapter<String>
			if(ACTION_DISCOVERY_STARTED.equals(action)){
				Log.d("スキャン開始")
				connectAbleListAdapter?.clear()
				foundDeviceList.clear()
				searchProgress?.visibility = View.VISIBLE
			}
			if(ACTION_FOUND.equals(action)){
				//デバイスが検出された
				foundDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
				connectAbleListAdapter?.add(foundDevice.name + "\n" + foundDevice.address)
				foundDeviceList.add(foundDevice)
				Log.d("ACTION_FOUND", foundDevice.name)
			}
			if(ACTION_DISCOVERY_FINISHED.equals(action)){
				Log.d("スキャン終了")
				searchProgress?.visibility = View.GONE
			}
		}
	}

	override fun onResume() {
		super.onResume()
		//サーバースレッド起動、クライアントのからの要求待ちを開始
		Log.d("bluetooth server start")
		val btServerThread = BluetoothServerThread(this, mBtAdapter)
		btServerThread.start()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.bluetooth_activity_menu, menu)
		return true
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
}
