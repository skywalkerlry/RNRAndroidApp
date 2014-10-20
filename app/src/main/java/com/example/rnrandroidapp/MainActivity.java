package com.example.rnrandroidapp;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView tv;

    String googleWeatherUrl1 = "http://www.weather.com.cn/data/sk/101220101.html";
    String googleWeatherUrl2 = "http://www.weather.com.cn/data/sk/101220101.html";

    /** Called when the activity is first created. */
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // 定义UI组件
        Button b1 = (Button) findViewById(R.id.Button01);
        Button b2 = (Button) findViewById(R.id.Button02);
        tv = (TextView) findViewById(R.id.TextView02);

        // 设置按钮单击监听器
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用URLConnection连接GoogleWeatherAPI
                urlConn();
            }
        });

        // 设置按钮单击监听器
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用HttpCient连接GoogleWeatherAPI
                httpClientConn();

            }
        });

    }

    // 使用URLConnection连接GoogleWeatherAPI
    protected void urlConn() {

        try {
            // URL
            URL url = new URL(googleWeatherUrl1);
            // HttpURLConnection
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Toast.makeText(getApplicationContext(), "连接Google Weather API成功!",
                        Toast.LENGTH_SHORT).show();
                // InputStreamReader
                InputStreamReader isr = new InputStreamReader(httpconn.getInputStream(), "utf-8");
                int i;
                String content = "";
                // read
                while ((i = isr.read()) != -1) {
                    content = content + (char) i;
                }
                isr.close();
                //设置TextView
                tv.setText(content);
            }
            //disconnect
            httpconn.disconnect();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "连接Google Weather API失败", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    // 使用HttpCient连接GoogleWeatherAPI
    protected void httpClientConn() {
        //DefaultHttpClient
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //HttpGet
        HttpGet httpget = new HttpGet(googleWeatherUrl2);
        //ResponseHandler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            String content = httpclient.execute(httpget, responseHandler);
            Toast.makeText(getApplicationContext(), "连接Google Weather API成功!",
                    Toast.LENGTH_SHORT).show();
            //设置TextView
            tv.setText(content);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "连接Google Weather API失败", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
        httpclient.getConnectionManager().shutdown();
    }
}