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
import android.widget.ArrayAdapter
import android.widget.ListView

class BluetoothConnectActivity : AppCompatActivity() {
	private val REQUEST_ENABLE_BLUETOOTH = 1

	private var connectAbleListView:ListView? = null
	private val mBtAdapter = BluetoothAdapter.getDefaultAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_bluetooth_connect)


		findViewById(R.id.start_search_button).setOnClickListener({v ->
			Log.d("button click")
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
		})

		connectAbleListView = findViewById(R.id.connect_able_list_view) as? ListView
		connectAbleListView?.adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1)

		//BluetoothAdapter取得
		if (mBtAdapter != null) {
			//Bluetooth対応端末の場合の処理
			Log.d("Bluetoothがサポートされてます。")
		} else {
			//Bluetooth非対応端末の場合の処理
			Log.d("Bluetoothがサポートれていません。")
			finish()
		}

		val btEnable = mBtAdapter.isEnabled
		if (btEnable == true) {
			//BluetoothがONだった場合の処理
		} else {
			//OFFだった場合、ONにすることを促すダイアログを表示する画面に遷移
			val btOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
			startActivityForResult(btOn, REQUEST_ENABLE_BLUETOOTH)
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
			}
			if(ACTION_FOUND.equals(action)){
				//デバイスが検出された
				foundDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
				connectAbleListAdapter?.add(foundDevice.name + "\n" + foundDevice.address)
				Log.d("ACTION_FOUND", foundDevice.name)
			}


			if(ACTION_DISCOVERY_FINISHED.equals(action)){
				Log.d("スキャン終了");
			}
		}
	};

}
