package com.android.demo.contacts.module3;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class Module3HappySensorService extends Service {
    private static final String TAG =Module3HappySensorService.class.getSimpleName();

    private SensorManager mSensorManager;
    private MySensorEventListener mListener;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mListener=new MySensorEventListener();
        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(mListener,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        mSensorManager=null;
        super.onDestroy();
    }

    private static class MySensorEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = sensorEvent.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];
                Log.d(TAG,String.format("Accelerometer values:x=%2f\ty=%2f\tz=%2f",x,y,z));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}

