package com.newer.caculater;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.BLUE;

public class MainActivity extends AppCompatActivity {


    private Button button_clean_all;
    private Button button_equal;
    private Button button_clean;
    private Button button_back;
    private TextView editText;
    private TextView textView;
    private TextView textViewHis;
    private Button[] buttons = new Button[10];
    private Button[] marks = new Button[5];
    int[] num_ids ={R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,
            R.id.button8,R.id.button9,R.id.button0};
    int[] sign_ids = {R.id.button_add,R.id.button_minus,R.id.button_muilt,R.id.button_divide,R.id.button_dian};
    private static final Object TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        for(int i = 0 ;i<buttons.length;i++){
            buttons[i] = (Button)findViewById(num_ids[i]);
        }
        for(int i = 0 ;i<marks.length;i++){
            marks[i] = (Button)findViewById(sign_ids[i]);
        }
        button_clean = (Button) findViewById(R.id.button_clean);
        button_back = (Button) findViewById(R.id.button_back);
        button_equal = (Button) findViewById(R.id.button_equal);
        button_clean_all = (Button) findViewById(R.id.button_clean_all);
        editText = (TextView) findViewById(R.id.editText_input);
        textView = (TextView) findViewById(R.id.textView_show);
        textViewHis = (TextView) findViewById(R.id.textView_His);
//        editText.setBackground();
        btnListener listener = new btnListener();
        marksListener marksListener = new marksListener();
        for (Button button : buttons) {
            button.setOnClickListener(listener);
        }
        for (Button button : marks) {
            button.setOnClickListener(marksListener);
        }
        //等于
        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s != null) {
                    Util util = new Util();
                    String str = util.Caculator(s);
                    if (str.equals("false")) {
                        Toast.makeText(MainActivity.this, "请按正确格式输入", Toast.LENGTH_SHORT).show();
                    } else if (!TextUtils.isEmpty(editText.getText())) {
                        int j = str.indexOf(".");
                        if (j != -1) {
                            for (int i = 0; i < 20; i++) {
                                if (str.substring(str.length() - 1, str.length()).equals("0")) {
                                    str = str.substring(0, str.length() - 1);
                                } else {
                                    break;
                                }
                            }
                        }
                        textView.setText(String.valueOf(str));
                    } else {
                        return;
                    }
                }
            }
        });
        //清除监听器

        button_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = editText.getText().toString();
                String s2 = textView.getText().toString();
                if (!TextUtils.isEmpty(editText.getText()) && !TextUtils.isEmpty(textView.getText())) {
                    textViewHis.append(s1 + "=" + s2 + " ");
                }
                textView.setText(null);
                editText.setText(null);
            }
        });
        //回退按钮监听器
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                int length = str.length();
                if (length < 2) {
                    editText.setText(null);
                } else {
                    //如果editText长度超过1则截取前一段
                    editText.setText(str.substring(0, length - 1));
                }
            }
        });
        button_clean_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHis.setText(null);
            }
        });
    }

    //数字按钮监听器，把数字显示在editText
    class btnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String str = editText.getText().toString();
            Button btn = (Button) v;
            String input = btn.getText().toString();
            editText.append(input);
        }
    }

    class marksListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
//            String str = editText.getText().toString();
            Button btn = (Button) v;
//            int length = str.length();
            String input = btn.getText().toString();
            editText.append(input);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }
}
