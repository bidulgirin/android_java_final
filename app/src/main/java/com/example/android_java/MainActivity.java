package com.example.android_java;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.*;

import com.aykuttasil.callrecord.CallRecord;
import com.example.android_java.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 0;
    private DevicePolicyManager mDPM;
    private ComponentName mAdminName;


    //Call Recording varibales
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";

    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
            MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
            AUDIO_RECORDER_FILE_EXT_3GP };

    AudioManager audioManager;

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Toast.makeText(MainActivity.this,
                    "Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Toast.makeText(MainActivity.this,
                            "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
                    .show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TEST", "나와!!!!!");

        try {
            // Initiate DevicePolicyManager.
            DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName mAdminName = new ComponentName(this, DeviceAdminDemo.class);

            if (!mDPM.isAdminActive(mAdminName)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
                //startActivityForResult(intent, REQUEST_CODE);
                startActivity(intent);
            } else {
                // callRecord 클래스의 인스턴스를 저장
                CallRecord callRecord;

                callRecord = new CallRecord.Builder(this)
                        .setRecordFileName("test")
                        .setRecordDirName("Download")
                        .setRecordDirPath(Environment.getExternalStorageDirectory().getPath()) // optional & default value
                        .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                        .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB) // optional & default value
                        .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
                        .setShowSeed(false) // optional, default=true ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                        .build();

                callRecord.enableSaveFile();
                callRecord.startCallReceiver();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // callRecord.stopCallReceiver();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode) {
            Intent intent = new Intent(MainActivity.this, TService.class);
            startService(intent);
        }
    }

}