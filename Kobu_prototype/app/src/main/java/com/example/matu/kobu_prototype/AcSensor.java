package com.example.matu.kobu_prototype;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by matu on 2016/11/19.
 */
public class AcSensor implements SensorEventListener {

    private SensorManager _sensorManager = null;
    private float _x, _y, _z;

    public void onCreate(Context c) {
        _sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
        onResume();
    }

    public void onResume() {
        List<Sensor> sensorList = _sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList != null && !sensorList.isEmpty()) {
            Sensor sensor = sensorList.get(0);
            _sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void onPause() {
        if( _sensorManager == null ){
            return;
        }
        _sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            _x = event.values[0]; // X
            _y = event.values[1]; // Y
            _z = event.values[2]; // Z
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float getX(){
        return _x;
    }

    public float getY(){
        return _y;
    }

    public float getZ(){
        return _z;
    }

    private static AcSensor _instance = new AcSensor();
    private AcSensor() {
        _x = _y = _z = 0;
    }
    public static AcSensor Inst() {
        return _instance;
    }
}
