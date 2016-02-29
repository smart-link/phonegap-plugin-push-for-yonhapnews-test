package com.adobe.phonegap.push;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class YonhapnewsActivity extends Activity implements PushConstants {

    private static final String LOG_TAG = "PushPlugin_YonhapnewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String message = extras.getString("message");
        boolean isActive = PushPlugin.isActive();
        boolean isInForeground = PushPlugin.isInForeground();
        Log.d(LOG_TAG, "isActive = " + isActive);
        Log.d(LOG_TAG, "isInForeground = " + isInForeground);

        String packageName = getPackageName();
        Resources resources = getResources();
        setContentView(resources.getIdentifier("yonhapnews_layout", "layout", packageName));

        TextView content = (TextView) findViewById(resources.getIdentifier("yonhapnews_content", "id", packageName));
        content.setText(message);

        LinearLayout layout = (LinearLayout) findViewById(resources.getIdentifier("yonhapnews_layout", "id", packageName));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                processPushBundle();
                finish();
                reloadMainActivity();
            }
        });

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.y = 0; params.x = 0;
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
    }
    protected void onCreate_Backup(Bundle savedInstanceState) {
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
