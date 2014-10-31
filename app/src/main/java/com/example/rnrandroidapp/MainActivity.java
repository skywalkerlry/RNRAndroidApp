package com.example.rnrandroidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Button;
import android.util.DisplayMetrics;

public class MainActivity extends Activity {

    //定义私有控件对象
    private TextView textView01;
    private ProgressBar progressBar01;
    private Button button01;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.textView01=(TextView)super.findViewById(R.id.TextView01);
        this.button01 = (Button)findViewById(R.id.Button01);
        this.progressBar01 = (ProgressBar)findViewById(R.id.ProgressBar01);

        //获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        super.getWindowManager().getDefaultDisplay().getMetrics(dm);
        String strOpt = "屏幕分辨率：" + dm.widthPixels + "*" + dm.heightPixels
                + "\n屏幕密度：" + dm.density
                + "\n屏幕密度DPI：" + dm.densityDpi;
        this.textView01.setText(strOpt);

        //设置button监听行为
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar01.setVisibility(View.VISIBLE);
            }
        });
    }
}