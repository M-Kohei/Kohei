package com.example.matu.kobu_prototype.thread

import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import rx.Observable
import rx.Subscriber
import java.io.IOException
import java.util.*


/**
 * Created by riku on 16/12/04.
 */
class BluetoothServerThread(mContext: Context, btAdapter: BluetoothAdapter) : Observable.OnSubscribe<BluetoothSocket> {
	//サーバー側の処理
	//UUID：Bluetoothプロファイル毎に決められた値
	private val PROTOTYPE_BLUETOOTH_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

	private val serveSock: BluetoothServerSocket?
	private val myServerAdapter: BluetoothAdapter
	private val mContext: Context


	init {

		var tmpServeSock: BluetoothServerSocket? = null
		myServerAdapter = btAdapter
		try {
			//自デバイスのBluetoothサーバーソケットの取得
			tmpServeSock = myServerAdapter.listenUsingRfcommWithServiceRecord("BlueToothSample03", PROTOTYPE_BLUETOOTH_UUID)
		} catch (e: IOException) {
			e.printStackTrace()
		}

		serveSock = tmpServeSock
		this.mContext = mContext
	}

	override fun call(t: Subscriber<in BluetoothSocket>) {
		var receivedSocket: BluetoothSocket?
		while (true) {
			try {
				//クライアント側からの接続要求待ち。ソケットが返される。
				receivedSocket = serveSock?.accept()
			} catch (e: IOException) {
				t.onError(e)
				break
			}

			if (receivedSocket != null) {
				//ソケットを受け取れていた(接続完了時)の処理
				//RwClassにmanageSocketを移す
//				val rw = ReadWriteModel(mContext, receivedSocket, myNumber)
//				rw.start()

				try {
					//処理が完了したソケットは閉じる。
					serveSock?.close()
				} catch (e: IOException) {
					e.printStackTrace()
					t.onError(e)
				}

				t.onNext(receivedSocket)
				t.onCompleted()
				break
			}
		}
	}

	fun cancel() {
		try {
			serveSock?.close()
		} catch (e: IOException) {
		}

	}
}
