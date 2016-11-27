package com.example.matu.kobu_prototype;

import android.content.Context;
import android.graphics.Color;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by matu on 2016/11/19.
 */
public class Sound_Map {
    HashMap<Integer,Integer> map = new HashMap<Integer ,Integer>();
    HashMap<Integer,Float> x_map =new HashMap<Integer,Float>();
    HashMap<Integer,Float> y_map =new HashMap<Integer,Float>();
    SoundPool soundpool;
    int sound[];

    public Sound_Map(){
    }

    public Sound_Map(Context context){
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(12);						//音を同時に鳴らす数を設定
        soundpool = builder.build();

        sound=new int[102];

        soundCreate(context);
        mapset(1,0,0,85);
        mapset(1, 1,0,68);
        mapset(1, 1, 1,51);
        mapset(0, 1, 1,34);
        mapset(0,0, 1, 17);
        mapset(0, 1,0,0);
        x_set();
        y_set();
    }

    public void mapset(int r,int g,int b,int n)
    {
        for(int i=1;i<17;i++)
            map.put(Color.rgb(i*r*10, i*g*10, i*b*10),sound[n+i]);
        map.put(Color.rgb(r*200, g*200, b*200),sound[n]);
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

        sound[0] = soundpool.load(context.getExternalFilesDir(null) + "/a1f.wav", 1);
        sound[1]=soundpool.load(context.getExternalFilesDir(null) + "/a11.wav", 1);
        sound[2]=soundpool.load(context.getExternalFilesDir(null) + "/a12.wav", 1);
        sound[3]=soundpool.load(context.getExternalFilesDir(null) + "/a13.wav", 1);
        sound[4]=soundpool.load(context.getExternalFilesDir(null) + "/a14.wav", 1);
        sound[5]=soundpool.load(context.getExternalFilesDir(null) + "/a15.wav", 1);
        sound[6]=soundpool.load(context.getExternalFilesDir(null) + "/a16.wav", 1);
        sound[7]=soundpool.load(context.getExternalFilesDir(null) + "/a17.wav", 1);
        sound[8]=soundpool.load(context.getExternalFilesDir(null) + "/a18.wav", 1);
        sound[9]=soundpool.load(context.getExternalFilesDir(null) + "/a19.wav", 1);
        sound[10]=soundpool.load(context.getExternalFilesDir(null) + "/a110.wav", 1);
        sound[11]=soundpool.load(context.getExternalFilesDir(null) + "/a111.wav", 1);
        sound[12]=soundpool.load(context.getExternalFilesDir(null) + "/a112.wav", 1);
        sound[13]=soundpool.load(context.getExternalFilesDir(null) + "/a113.wav", 1);
        sound[14]=soundpool.load(context.getExternalFilesDir(null) + "/a114.wav", 1);
        sound[15]=soundpool.load(context.getExternalFilesDir(null) + "/a115.wav", 1);
        sound[16]=soundpool.load(context.getExternalFilesDir(null) + "/a116.wav", 1);
        sound[17]=soundpool.load(context.getExternalFilesDir(null) + "/b2f.wav", 1);
        sound[18]=soundpool.load(context.getExternalFilesDir(null) + "/b21.wav", 1);
        sound[19]=soundpool.load(context.getExternalFilesDir(null) + "/b22.wav", 1);
        sound[20]=soundpool.load(context.getExternalFilesDir(null) + "/b23.wav", 1);
        sound[21]=soundpool.load(context.getExternalFilesDir(null) + "/b24.wav", 1);
        sound[22]=soundpool.load(context.getExternalFilesDir(null) + "/b25.wav", 1);
        sound[23]=soundpool.load(context.getExternalFilesDir(null) + "/b26.wav", 1);
        sound[24]=soundpool.load(context.getExternalFilesDir(null) + "/b27.wav", 1);
        sound[25]=soundpool.load(context.getExternalFilesDir(null) + "/b28.wav", 1);
        sound[26]=soundpool.load(context.getExternalFilesDir(null) + "/b29.wav", 1);
        sound[27]=soundpool.load(context.getExternalFilesDir(null) + "/b210.wav", 1);
        sound[28]=soundpool.load(context.getExternalFilesDir(null) + "/b211.wav", 1);
        sound[29]=soundpool.load(context.getExternalFilesDir(null) + "/b212.wav", 1);
        sound[30]=soundpool.load(context.getExternalFilesDir(null) + "/b213.wav", 1);
        sound[31]=soundpool.load(context.getExternalFilesDir(null) + "/b214.wav", 1);
        sound[32]=soundpool.load(context.getExternalFilesDir(null) + "/b215.wav", 1);
        sound[33]=soundpool.load(context.getExternalFilesDir(null) + "/b216.wav", 1);
        sound[34]=soundpool.load(context.getExternalFilesDir(null) + "/c3f.wav", 1);
        sound[35]=soundpool.load(context.getExternalFilesDir(null) + "/c31.wav", 1);
        sound[36]=soundpool.load(context.getExternalFilesDir(null) + "/c32.wav", 1);
        sound[37]=soundpool.load(context.getExternalFilesDir(null) + "/c33.wav", 1);
        sound[38]=soundpool.load(context.getExternalFilesDir(null) + "/c34.wav", 1);
        sound[39]=soundpool.load(context.getExternalFilesDir(null) + "/c35.wav", 1);
        sound[40]=soundpool.load(context.getExternalFilesDir(null) + "/c36.wav", 1);
        sound[41]=soundpool.load(context.getExternalFilesDir(null) + "/c37.wav", 1);
        sound[42]=soundpool.load(context.getExternalFilesDir(null) + "/c38.wav", 1);
        sound[43]=soundpool.load(context.getExternalFilesDir(null) + "/c39.wav", 1);
        sound[44]=soundpool.load(context.getExternalFilesDir(null) + "/c310.wav", 1);
        sound[45]=soundpool.load(context.getExternalFilesDir(null) + "/c311.wav", 1);
        sound[46]=soundpool.load(context.getExternalFilesDir(null) + "/c312.wav", 1);
        sound[47]=soundpool.load(context.getExternalFilesDir(null) + "/c313.wav", 1);
        sound[48]=soundpool.load(context.getExternalFilesDir(null) + "/c314.wav", 1);
        sound[49]=soundpool.load(context.getExternalFilesDir(null) + "/c315.wav", 1);
        sound[50]=soundpool.load(context.getExternalFilesDir(null) + "/c316.wav", 1);
        sound[51]=soundpool.load(context.getExternalFilesDir(null) + "/d4f.wav", 1);
        sound[52]=soundpool.load(context.getExternalFilesDir(null) + "/d41.wav", 1);
        sound[53]=soundpool.load(context.getExternalFilesDir(null) + "/d42.wav", 1);
        sound[54]=soundpool.load(context.getExternalFilesDir(null) + "/d43.wav", 1);
        sound[55]=soundpool.load(context.getExternalFilesDir(null) + "/d44.wav", 1);
        sound[56]=soundpool.load(context.getExternalFilesDir(null) + "/d45.wav", 1);
        sound[57]=soundpool.load(context.getExternalFilesDir(null) + "/d46.wav", 1);
        sound[58]=soundpool.load(context.getExternalFilesDir(null) + "/d47.wav", 1);
        sound[59]=soundpool.load(context.getExternalFilesDir(null) + "/d48.wav", 1);
        sound[60]=soundpool.load(context.getExternalFilesDir(null) + "/d49.wav", 1);
        sound[61]=soundpool.load(context.getExternalFilesDir(null) + "/d410.wav", 1);
        sound[62]=soundpool.load(context.getExternalFilesDir(null) + "/d411.wav", 1);
        sound[63]=soundpool.load(context.getExternalFilesDir(null) + "/d412.wav", 1);
        sound[64]=soundpool.load(context.getExternalFilesDir(null) + "/d413.wav", 1);
        sound[65]=soundpool.load(context.getExternalFilesDir(null) + "/d414.wav", 1);
        sound[66]=soundpool.load(context.getExternalFilesDir(null) + "/d415.wav", 1);
        sound[67]=soundpool.load(context.getExternalFilesDir(null) + "/d416.wav", 1);
        sound[68]=soundpool.load(context.getExternalFilesDir(null) + "/e5f.wav", 1);
        sound[69]=soundpool.load(context.getExternalFilesDir(null) + "/e51.wav", 1);
        sound[70]=soundpool.load(context.getExternalFilesDir(null) + "/e52.wav", 1);
        sound[71]=soundpool.load(context.getExternalFilesDir(null) + "/e53.wav", 1);
        sound[72]=soundpool.load(context.getExternalFilesDir(null) + "/e54.wav", 1);
        sound[73]=soundpool.load(context.getExternalFilesDir(null) + "/e55.wav", 1);
        sound[74]=soundpool.load(context.getExternalFilesDir(null) + "/e56.wav", 1);
        sound[75]=soundpool.load(context.getExternalFilesDir(null) + "/e57.wav", 1);
        sound[76]=soundpool.load(context.getExternalFilesDir(null) + "/e58.wav", 1);
        sound[77]=soundpool.load(context.getExternalFilesDir(null) + "/e59.wav", 1);
        sound[78]=soundpool.load(context.getExternalFilesDir(null) + "/e510.wav", 1);
        sound[79]=soundpool.load(context.getExternalFilesDir(null) + "/e511.wav", 1);
        sound[80]=soundpool.load(context.getExternalFilesDir(null) + "/e512.wav", 1);
        sound[81]=soundpool.load(context.getExternalFilesDir(null) + "/e513.wav", 1);
        sound[82]=soundpool.load(context.getExternalFilesDir(null) + "/e514.wav", 1);
        sound[83]=soundpool.load(context.getExternalFilesDir(null) + "/e515.wav", 1);
        sound[84]=soundpool.load(context.getExternalFilesDir(null) + "/e516.wav", 1);
        sound[85]=soundpool.load(context.getExternalFilesDir(null) + "/f6f.wav", 1);
        sound[86]=soundpool.load(context.getExternalFilesDir(null) + "/f61.wav", 1);
        sound[87]=soundpool.load(context.getExternalFilesDir(null) + "/f62.wav", 1);
        sound[88]=soundpool.load(context.getExternalFilesDir(null) + "/f63.wav", 1);
        sound[89]=soundpool.load(context.getExternalFilesDir(null) + "/f64.wav", 1);
        sound[90]=soundpool.load(context.getExternalFilesDir(null) + "/f65.wav", 1);
        sound[91]=soundpool.load(context.getExternalFilesDir(null) + "/f66.wav", 1);
        sound[92]=soundpool.load(context.getExternalFilesDir(null) + "/f67.wav", 1);
        sound[93]=soundpool.load(context.getExternalFilesDir(null) + "/f68.wav", 1);
        sound[94]=soundpool.load(context.getExternalFilesDir(null) + "/f69.wav", 1);
        sound[95]=soundpool.load(context.getExternalFilesDir(null) + "/f610.wav", 1);
        sound[96]=soundpool.load(context.getExternalFilesDir(null) + "/f611.wav", 1);
        sound[97]=soundpool.load(context.getExternalFilesDir(null) + "/f612.wav", 1);
        sound[98]=soundpool.load(context.getExternalFilesDir(null) + "/f613.wav", 1);
        sound[99]=soundpool.load(context.getExternalFilesDir(null) + "/f614.wav", 1);
        sound[100]=soundpool.load(context.getExternalFilesDir(null) + "/f615.wav", 1);
        sound[101]=soundpool.load(context.getExternalFilesDir(null) + "/f616.wav", 1);
    }

}
