package com.example.matu.kobu_prototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    //画面サイズ
    static int displayWidth;
    static int displayHeight;

    //プレイモード
    static int play_mode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //画面サイズを取得する
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics  = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        displayWidth=displayMetrics.widthPixels;
        displayHeight=displayMetrics.heightPixels;

        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        setContentView(l);

        l.addView(new GameSurfesView(this));
        AcSensor.Inst().onCreate(this);
    }

    @Override
    protected void onResume() { // アクティビティが動き始める時呼ばれる
        super.onResume();
        AcSensor.Inst().onResume();// 開始時にセンサーを動かし始める
    }

    @Override
    protected void onPause() { // アクティビティの動きが止まる時呼ばれる
        super.onPause();
        AcSensor.Inst().onPause();// 中断時にセンサーを止める
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.free:
                play_mode = 1;
                return true;
            case R.id.play:
                play_mode = 2;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
