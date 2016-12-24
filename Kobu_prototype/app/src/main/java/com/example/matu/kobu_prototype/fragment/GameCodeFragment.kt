package com.example.matu.kobu_prototype.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.matu.kobu_prototype.AcSensor
import com.example.matu.kobu_prototype.GameSurfaceView
import com.example.matu.kobu_prototype.R

/**
 * Created by riku on 16/12/17.
 */
class GameCodeFragment : Fragment() {
	private var playMode = 1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		super.onCreateView(inflater, container, savedInstanceState)
		setHasOptionsMenu(true)

		AcSensor.Inst().onCreate(activity)

		val view = inflater?.inflate(R.layout.fragment_game_code, container, false)
		val gameSurfaceView = view?.findViewById(R.id.game_surface_view) as GameSurfaceView?

		gameSurfaceView?.setPlayMode(playMode)
		return view
	}

	override fun onResume() { // アクティビティが動き始める時呼ばれる
		super.onResume()
		AcSensor.Inst().onResume()	// 開始時にセンサーを動かし始める
	}

	override fun onPause() { // アクティビティの動きが止まる時呼ばれる
		super.onPause()
		AcSensor.Inst().onPause()	// 中断時にセンサーを止める
	}


	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.main, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		val id = item.itemId
		val gameSurfaceView = activity.findViewById(R.id.game_surface_view) as GameSurfaceView?
		when(id) {
			R.id.free -> {
				playMode = 1
				gameSurfaceView?.setPlayMode(playMode)
				return true
			}
			R.id.play -> {
				playMode = 2
				gameSurfaceView?.setPlayMode(playMode)
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}