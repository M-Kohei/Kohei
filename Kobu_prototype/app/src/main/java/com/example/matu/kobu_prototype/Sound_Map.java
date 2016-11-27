package com.example.matu.kobu_prototype;

import android.content.Context;
import android.graphics.Color;
import android.media.SoundPool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trikita.log.Log;

/**
 * Created by matu on 2016/11/19.
 */
public class Sound_Map {
    HashMap<Integer,Integer> map = new HashMap<Integer ,Integer>();
    HashMap<Integer,Float> x_map =new HashMap<Integer,Float>();
    HashMap<Integer,Float> y_map =new HashMap<Integer,Float>();
    SoundPool soundpool;
    ArrayList<File> wavList = new ArrayList<>();

    public Sound_Map(Context context){
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(12);						//音を同時に鳴らす数を設定
        soundpool = builder.build();

        soundCreate(context);
        mapset(context);
        x_set();
        y_set();
    }

    public void mapset(Context context)
    {
        int rgbUnit[][] = {
                {0, 1, 0},      // a
                {0, 0, 1},      // b
                {0, 1, 1},      // c
                {1, 1, 1},      // d
                {1, 1, 0},      // e
                {1, 0, 0}       // f
        };
        for (File file : wavList) {
            int colorIndex;

            String regex = "([a-f])[1-6]([0-9]+)";
            Pattern p = Pattern.compile(regex);

            Matcher m = p.matcher(file.getName());

            int suffixNum;
            int prefixChar;
            if (m.find()){
                prefixChar = m.group(1).charAt(0);
                Log.d(m.group(1));
                Log.d(prefixChar);
                suffixNum = Integer.parseInt(m.group(2));
                Log.d(m.group() + ":" + suffixNum);
            } else {
                continue;
            }

            colorIndex = prefixChar - 'a';
            if (suffixNum == 1) {
                map.put(
                        Color.rgb(
                                rgbUnit[colorIndex][0] * 200,
                                rgbUnit[colorIndex][1] * 200,
                                rgbUnit[colorIndex][2] * 200
                        ),
                        soundpool.load(file.getAbsolutePath(), 1)
                );
            } else {
                map.put(
                        Color.rgb(
                                rgbUnit[colorIndex][0] * 10 * (suffixNum - 1),
                                rgbUnit[colorIndex][1] * 10 * (suffixNum - 1),
                                rgbUnit[colorIndex][2] * 10 * (suffixNum - 1)
                        ),
                        soundpool.load(file.getAbsolutePath(), 1)
                );
            }
        }
    }

    public void x_set()
    {
        x_map.put(5,(float) 8.5);
        x_map.put(4,(float) 61);
        x_map.put(3,(float) 122.5);
        x_map.put(2,(float) 185.5);
        x_map.put(1,(float) 243.5);
        x_map.put(0,(float) 302.5);
    }

    public void y_set()
    {
        y_map.put(10,(float) 75);
        y_map.put(20,(float) 167);
        y_map.put(30,(float) 257);
        y_map.put(40,(float) 347);
        y_map.put(50,(float) 441);
        y_map.put(60,(float) 531.5);
        y_map.put(70,(float) 612.5);
        y_map.put(80,(float) 693.5);
        y_map.put(90,(float) 773.5);
        y_map.put(100,(float) 856);
        y_map.put(110,(float) 944.5);
        y_map.put(120,(float) 1036.5);
        y_map.put(130,(float) 1127);
        y_map.put(140,(float) 1218);
        y_map.put(150,(float) 1313.5);
        y_map.put(160,(float) 1425.5);
    }

    public void sound(Cord cord){
        if(map.get(cord.min_circle_color())!=null && cord.play.sound_play && cord.play.cord_number!=-1){
            soundpool.play(map.get(cord.min_circle_color()), 1.0f, 1.0f, 0, 0, 2);
            //print(cord.min_circle_color(),c);
            cord.play.sound_play = false;
        }
    }

    public void soundCreate(Context context)
    {
        for (File file : context.getExternalFilesDir(null).listFiles()) {
            if (!file.getName().contains("wav"))
                continue;

            wavList.add(file);
        }
    }
}
