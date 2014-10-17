package com.example.rnrandroidapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    //SQLiteDatabase对象
    SQLiteDatabase db;
    //数据库名
    public String db_name = "gallery.sqlite";
    //表名
    public String table_name = "pic";

    //辅助类名
    final DbHelper helper = new DbHelper(this, db_name, null, 1);

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI组件
        Button b1 = (Button) findViewById(R.id.Button01);
        Button b2 = (Button) findViewById(R.id.Button02);
        Button b3 = (Button) findViewById(R.id.Button03);
        Button b4 = (Button) findViewById(R.id.Button04);

        //从辅助类获得数据库对象
        db = helper.getWritableDatabase();

        //初始化数据
        initDatabase(db);
        //更新下拉列表中的数据
        updateSpinner();

        //定义按钮点击监听器
        OnClickListener ocl = new OnClickListener() {

            @Override
            public void onClick(View v) {

                //ContentValues对象
                ContentValues cv = new ContentValues();
                switch (v.getId()) {

                    //添加按钮
                    case R.id.Button01:

                        cv.put("fileName", "pic5.jpg");
                        cv.put("description", "图片5");
                        //添加方法
                        long long1 = db.insert("pic", "", cv);
                        //添加成功后返回行号，失败后返回-1
                        if (long1 == -1) {
                            Toast.makeText(MainActivity.this,
                                    "ID是" + long1 + "的图片添加失败！", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "ID是" + long1 + "的图片添加成功！", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        //更新下拉列表
                        updateSpinner();
                        break;

                    //删除描述是'图片5'的数据行
                    case R.id.Button02:
                        //删除方法
                        long long2 = db.delete("pic", "description='图片5'", null);
                        //删除失败返回0，成功则返回删除的条数
                        Toast.makeText(MainActivity.this, "删除了" + long2 + "条记录",
                                Toast.LENGTH_SHORT).show();
                        //更新下拉列表
                        updateSpinner();
                        break;

                    //更新文件名是'pic5.jpg'的数据行
                    case R.id.Button03:

                        cv.put("fileName", "pic0.jpg");
                        cv.put("description", "图片0");
                        //更新方法
                        int long3 = db.update("pic", cv, "fileName='pic5.jpg'", null);
                        //删除失败返回0，成功则返回删除的条数
                        Toast.makeText(MainActivity.this, "更新了" + long3 + "条记录",
                                Toast.LENGTH_SHORT).show();
                        //更新下拉列表
                        updateSpinner();
                        break;

                    //查询当前所有数据
                    case R.id.Button04:
                        Cursor c = db.query("pic", null, null, null, null,
                                null, null);
                        //cursor.getCount()是记录条数
                        Toast.makeText(MainActivity.this,
                                "当前共有" + c.getCount() + "条记录，下面一一显示：",
                                Toast.LENGTH_SHORT).show();
                        //循环显示
                        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                            Toast.makeText(MainActivity.this,
                                    "第"+ c.getInt(0)  +"条数据，文件名是" + c.getString(1) + "，描述是"+c.getString(2),
                                    Toast.LENGTH_SHORT).show();
                        }
                        //更新下拉列表
                        updateSpinner();
                        break;
                }
            }
        };

        //给按钮绑定监听器
        b1.setOnClickListener(ocl);
        b2.setOnClickListener(ocl);
        b3.setOnClickListener(ocl);
        b4.setOnClickListener(ocl);

    }

    //初始化表
    public void initDatabase(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put("fileName", "pic1.jpg");
        cv.put("description", "图片1");
        db.insert(table_name, "", cv);

        cv.put("fileName", "pic2.jpg");
        cv.put("description", "图片2");
        db.insert(table_name, "", cv);

        cv.put("fileName", "pic3.jpg");
        cv.put("description", "图片3");
        db.insert(table_name, "", cv);

        cv.put("fileName", "pic4.jpg");
        cv.put("description", "图片4");
        db.insert(table_name, "", cv);

    }

    //更新下拉列表
    public void updateSpinner() {

        //定义UI组件
        final TextView tv = (TextView) findViewById(R.id.TextView02);
        Spinner s = (Spinner) findViewById(R.id.Spinner01);

        //从数据库中获取数据放入游标Cursor对象
        final Cursor cursor = db.query("pic", null, null, null, null, null,
                null);

        //创建简单游标匹配器
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                cursor,
                new String[] {"fileName", "description" },
                new int[] {android.R.id.text1, android.R.id.text2 }
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //给下拉列表设置匹配器
        s.setAdapter(adapter);

        //定义子元素选择监听器
        OnItemSelectedListener oisl = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position, long id)
            {
                cursor.moveToPosition(position);
                tv.setText("当前pic的描述为：" + cursor.getString(2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        };

        //给下拉列表绑定子元素选择监听器
        s.setOnItemSelectedListener(oisl);
    }

    //窗口销毁时删除表中数据
    @Override
    public void onDestroy() {
        super.onDestroy();
        db.delete(table_name, null, null);
        updateSpinner();
    }
}
