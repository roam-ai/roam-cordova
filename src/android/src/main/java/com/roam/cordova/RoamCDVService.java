package com.roam.cordova;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class RoamCDVService extends Service {

    private CDVRoam.RoamCordovaReceiver roamCordovaReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        register();
    }

    private void register() {
        roamCordovaReceiver = new CDVRoam.RoamCordovaReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.roam.android.RECEIVED");
        registerReceiver(roamCordovaReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void unRegister() {
        if (roamCordovaReceiver != null) {
            unregisterReceiver(roamCordovaReceiver);
        }
    }

    @Override
    public void onDestroy() {
        unRegister();
        super.onDestroy();
    }
}
