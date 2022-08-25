package com.rzm.testapplication.android_api.wms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.rzm.testapplication.R;

public class WindowManagerServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager_service);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void show(View view) {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(new Intent(WindowManagerServiceActivity.this, FloatingService.class));
        }
    }

    public void showApplicationDialog(View view) {
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(getApplicationContext());

        alterDiaglog.setIcon(R.drawable.ic_launcher_background);//图标
        alterDiaglog.setTitle("简单的dialog");//文字
        alterDiaglog.setMessage("生存还是死亡");//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton("生存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(WindowManagerServiceActivity.this,"点击了生存",Toast.LENGTH_SHORT).show();
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton("死亡", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(WindowManagerServiceActivity.this,"点击了死亡",Toast.LENGTH_SHORT).show();
            }
        });
        //中立的选择
        alterDiaglog.setNeutralButton("不生不死", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(WindowManagerServiceActivity.this,"点击了不生不死",Toast.LENGTH_SHORT).show();
            }
        });
        //显示
        AlertDialog alertDialog = alterDiaglog.create();

        //TODO 显示一个非activity dialog的关键设置
        alertDialog.getWindow().getAttributes().token = getWindow().getAttributes().token;
        alertDialog.show();
    }
}