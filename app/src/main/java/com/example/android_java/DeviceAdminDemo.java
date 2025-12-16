package com.example.android_java;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceAdminDemo extends DeviceAdminReceiver {

    // 기기를 재시작하면 앱이 작동을 멈추고 녹화도 중단되는 오류 수정
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        context.stopService(new Intent(context, TService.class));
        Intent myIntent = new Intent(context, TService.class);
        context.startService(myIntent);

    }
    public void onEnabled(Context context, Intent intent) {
    };

    public void onDisabled(Context context, Intent intent) {
    };
}