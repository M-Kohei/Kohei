package com.example.matu.kobu_prototype.thread

import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothAdapter
import android.content.Context
import java.io.IOException
import java.util.*


/**
 * Created by riku on 16/12/04.
 */
class BluetoothClientThread (context: Context, device: BluetoothDevice, btAdapter: BluetoothAdapter) : Thread() {
	//クライアント側の処理
	private val clientSocket: BluetoothSocket?
	private val mDevice: BluetoothDevice
	private val mContext: Context
	//UUIDの生成
	private val PROTOTYPE_BLUETOOTH_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
	private val myClientAdapter: BluetoothAdapter

	//コンストラクタ定義
	init {
		//各種初期化
		mContext = context
		var tmpSock: BluetoothSocket? = null
		mDevice = device
		myClientAdapter = btAdapter

		try {
			//自デバイスのBluetoothクライアントソケットの取得
			tmpSock = device.createRfcommSocketToServiceRecord(PROTOTYPE_BLUETOOTH_UUID)
		} catch (e: IOException) {
			e.printStackTrace()
		}

		clientSocket = tmpSock
	}

	override fun run() {
		//接続要求を出す前に、検索処理を中断する。
		if (myClientAdapter.isDiscovering) {
			myClientAdapter.cancelDiscovery()
		}

		try {
			//サーバー側に接続要求
			clientSocket?.connect()
		} catch (e: IOException) {
			try {
				clientSocket?.close()
			} catch (closeException: IOException) {
				e.printStackTrace()
			}

			return
		}

		//接続完了時の処理
//		val rw = ReadWriteModel(mContext, clientSocket, myNumber)
//		rw.start()
	}

	fun cancel() {
		try {
			clientSocket?.close()
		} catch (e: IOException) {
			e.printStackTrace()
		}

	}
}