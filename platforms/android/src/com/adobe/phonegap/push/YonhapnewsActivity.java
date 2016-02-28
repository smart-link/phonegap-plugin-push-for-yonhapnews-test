package com.adobe.phonegap.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

public class YonhapnewsActivity extends Activity implements PushConstants {

    private static final String LOG_TAG = "PushPlugin_YonhapnewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String message = extras.getString("message");
        boolean isActive = PushPlugin.isActive();
        boolean isInForeground = PushPlugin.isInForeground();
        Log.d(LOG_TAG, "isActive = " + isActive);
        Log.d(LOG_TAG, "isInForeground = " + isInForeground);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setMessage(message).setTitle("연합뉴스TV");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                processPushBundle();
                finish();
                reloadMainActivity();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void processPushBundle() {
        boolean isActive = PushPlugin.isActive();
        boolean isInForeground = PushPlugin.isInForeground();
        Bundle extras = getIntent().getExtras();
        extras.putBoolean(FOREGROUND, isInForeground);
        extras.putBoolean(COLDSTART, !isActive);
        PushPlugin.sendExtras(extras);
    }

    private void reloadMainActivity() {
        boolean isActive = PushPlugin.isActive();
        if (!isActive) {
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
            startActivity(intent);
        }
    }

    private int parseInt(String value, Bundle extras) {
        int retval = 0;

        try {
            retval = Integer.parseInt(extras.getString(value));
        }
        catch(NumberFormatException e) {
            Log.e(LOG_TAG, "Number format exception - Error parsing " + value + ": " + e.getMessage());
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "Number format exception - Error parsing " + value + ": " + e.getMessage());
        }

        return retval;
    }

}
