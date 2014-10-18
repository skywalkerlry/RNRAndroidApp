package com.example.rnrandroidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainHelloAlertDialog extends Activity {

    //定义一个对话框的ID
    int Edward_Movie_Dialog = 1;

    // 对话框按钮点击事件监听器
    OnClickListener ocl = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_NEGATIVE:
                    Toast.makeText(MainHelloAlertDialog.this, "我不喜欢他的电影。",
                            Toast.LENGTH_LONG).show();
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    Toast.makeText(MainHelloAlertDialog.this, "说不上喜欢不喜欢。",
                            Toast.LENGTH_LONG).show();
                    break;
                case Dialog.BUTTON_POSITIVE:
                    Toast.makeText(MainHelloAlertDialog.this, "我很喜欢他的电影。",
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 定义对话框对象
        Dialog dialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star).setTitle("喜好调查")
                .setMessage("你喜欢看爱德华.诺顿Edward Norton的电影吗？")
                .setNegativeButton("不喜欢", ocl).setNeutralButton("一般般", ocl)
                .setPositiveButton("很喜欢", ocl).create();

        //显示对话框
        dialog.show();

        //定义 按钮 UI组件
        Button b1 = (Button) findViewById(R.id.Button01);
        Button b2 = (Button) findViewById(R.id.Button02);
        Button b3 = (Button) findViewById(R.id.Button03);

        //定义按钮的单击事件监听器
        View.OnClickListener b_ocl= new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.Button01:
                        //显示对话框
                        showDialog(Edward_Movie_Dialog);
                        break;
                    case R.id.Button02:
                        //关闭对话框 这个功能好傻X，根本点不到的按钮
                        dismissDialog(Edward_Movie_Dialog);
                        break;
                    case R.id.Button03:
                        //移除对话框 这个功能好傻X，根本点不到的按钮
                        removeDialog(Edward_Movie_Dialog);
                        break;
                }
            }
        };

        //绑定按钮监听器
        b1.setOnClickListener(b_ocl);
        b2.setOnClickListener(b_ocl);
        b3.setOnClickListener(b_ocl);

    }

    // 创建会话框时调用
    @Override
    public Dialog onCreateDialog(int id) {
        Toast.makeText(this, "onCreateDialog方法被调用", Toast.LENGTH_LONG).show();

        return new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star).setTitle("喜好调查")
                .setMessage("你喜欢看爱德华.诺顿Edward Norton的电影吗？")
                .setNegativeButton("不喜欢", ocl).setNeutralButton("一般般", ocl)
                .setPositiveButton("很喜欢", ocl).create();
    }

    // 每次显示对话框之前会被调用
    @Override
    public void onPrepareDialog(int id, Dialog dialog){
        Toast.makeText(this, "onPrepareDialog方法被调用", Toast.LENGTH_LONG).show();
        super.onPrepareDialog(id, dialog);
    }

}
