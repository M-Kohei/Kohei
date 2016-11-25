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
        mapset(1,1,0,68);
        mapset(1,1,1,51);
        mapset(0,1,1,34);
        mapset(0,0,1,17);
        mapset(0,1,0,0);
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
        sound[0]=soundpool.load(context,R.raw.a1f,1);
        sound[1]=soundpool.load(context,R.raw.a11,1);
        sound[2]=soundpool.load(context,R.raw.a12,1);
        sound[3]=soundpool.load(context,R.raw.a13,1);
        sound[4]=soundpool.load(context,R.raw.a14,1);
        sound[5]=soundpool.load(context,R.raw.a15,1);
        sound[6]=soundpool.load(context,R.raw.a16,1);
        sound[7]=soundpool.load(context,R.raw.a17,1);
        sound[8]=soundpool.load(context,R.raw.a18,1);
        sound[9]=soundpool.load(context,R.raw.a19,1);
        sound[10]=soundpool.load(context,R.raw.a110,1);
        sound[11]=soundpool.load(context,R.raw.a111,1);
        sound[12]=soundpool.load(context,R.raw.a112,1);
        sound[13]=soundpool.load(context,R.raw.a113,1);
        sound[14]=soundpool.load(context,R.raw.a114,1);
        sound[15]=soundpool.load(context,R.raw.a115,1);
        sound[16]=soundpool.load(context,R.raw.a116,1);
        sound[17]=soundpool.load(context,R.raw.b2f,1);
        sound[18]=soundpool.load(context,R.raw.b21,1);
        sound[19]=soundpool.load(context,R.raw.b22,1);
        sound[20]=soundpool.load(context,R.raw.b23,1);
        sound[21]=soundpool.load(context,R.raw.b24,1);
        sound[22]=soundpool.load(context,R.raw.b25,1);
        sound[23]=soundpool.load(context,R.raw.b26,1);
        sound[24]=soundpool.load(context,R.raw.b27,1);
        sound[25]=soundpool.load(context,R.raw.b28,1);
        sound[26]=soundpool.load(context,R.raw.b29,1);
        sound[27]=soundpool.load(context,R.raw.b210,1);
        sound[28]=soundpool.load(context,R.raw.b211,1);
        sound[29]=soundpool.load(context,R.raw.b212,1);
        sound[30]=soundpool.load(context,R.raw.b213,1);
        sound[31]=soundpool.load(context,R.raw.b214,1);
        sound[32]=soundpool.load(context,R.raw.b215,1);
        sound[33]=soundpool.load(context,R.raw.b216,1);
        sound[34]=soundpool.load(context,R.raw.c3f,1);
        sound[35]=soundpool.load(context,R.raw.c31,1);
        sound[36]=soundpool.load(context,R.raw.c32,1);
        sound[37]=soundpool.load(context,R.raw.c33,1);
        sound[38]=soundpool.load(context,R.raw.c34,1);
        sound[39]=soundpool.load(context,R.raw.c35,1);
        sound[40]=soundpool.load(context,R.raw.c36,1);
        sound[41]=soundpool.load(context,R.raw.c37,1);
        sound[42]=soundpool.load(context,R.raw.c38,1);
        sound[43]=soundpool.load(context,R.raw.c39,1);
        sound[44]=soundpool.load(context,R.raw.c310,1);
        sound[45]=soundpool.load(context,R.raw.c311,1);
        sound[46]=soundpool.load(context,R.raw.c312,1);
        sound[47]=soundpool.load(context,R.raw.c313,1);
        sound[48]=soundpool.load(context,R.raw.c314,1);
        sound[49]=soundpool.load(context,R.raw.c315,1);
        sound[50]=soundpool.load(context,R.raw.c316,1);
        sound[51]=soundpool.load(context,R.raw.d4f,1);
        sound[52]=soundpool.load(context,R.raw.d41,1);
        sound[53]=soundpool.load(context,R.raw.d42,1);
        sound[54]=soundpool.load(context,R.raw.d43,1);
        sound[55]=soundpool.load(context,R.raw.d44,1);
        sound[56]=soundpool.load(context,R.raw.d45,1);
        sound[57]=soundpool.load(context,R.raw.d46,1);
        sound[58]=soundpool.load(context,R.raw.d47,1);
        sound[59]=soundpool.load(context,R.raw.d48,1);
        sound[60]=soundpool.load(context,R.raw.d49,1);
        sound[61]=soundpool.load(context,R.raw.d410,1);
        sound[62]=soundpool.load(context,R.raw.d411,1);
        sound[63]=soundpool.load(context,R.raw.d412,1);
        sound[64]=soundpool.load(context,R.raw.d413,1);
        sound[65]=soundpool.load(context,R.raw.d414,1);
        sound[66]=soundpool.load(context,R.raw.d415,1);
        sound[67]=soundpool.load(context,R.raw.d416,1);
        sound[68]=soundpool.load(context,R.raw.e5f,1);
        sound[69]=soundpool.load(context,R.raw.e51,1);
        sound[70]=soundpool.load(context,R.raw.e52,1);
        sound[71]=soundpool.load(context,R.raw.e53,1);
        sound[72]=soundpool.load(context,R.raw.e54,1);
        sound[73]=soundpool.load(context,R.raw.e55,1);
        sound[74]=soundpool.load(context,R.raw.e56,1);
        sound[75]=soundpool.load(context,R.raw.e57,1);
        sound[76]=soundpool.load(context,R.raw.e58,1);
        sound[77]=soundpool.load(context,R.raw.e59,1);
        sound[78]=soundpool.load(context,R.raw.e510,1);
        sound[79]=soundpool.load(context,R.raw.e511,1);
        sound[80]=soundpool.load(context,R.raw.e512,1);
        sound[81]=soundpool.load(context,R.raw.e513,1);
        sound[82]=soundpool.load(context,R.raw.e514,1);
        sound[83]=soundpool.load(context,R.raw.e515,1);
        sound[84]=soundpool.load(context,R.raw.e516,1);
        sound[85]=soundpool.load(context,R.raw.f6f,1);
        sound[86]=soundpool.load(context,R.raw.f61,1);
        sound[87]=soundpool.load(context,R.raw.f62,1);
        sound[88]=soundpool.load(context,R.raw.f63,1);
        sound[89]=soundpool.load(context,R.raw.f64,1);
        sound[90]=soundpool.load(context,R.raw.f65,1);
        sound[91]=soundpool.load(context,R.raw.f66,1);
        sound[92]=soundpool.load(context,R.raw.f67,1);
        sound[93]=soundpool.load(context,R.raw.f68,1);
        sound[94]=soundpool.load(context,R.raw.f69,1);
        sound[95]=soundpool.load(context,R.raw.f610,1);
        sound[96]=soundpool.load(context,R.raw.f611,1);
        sound[97]=soundpool.load(context,R.raw.f612,1);
        sound[98]=soundpool.load(context,R.raw.f613,1);
        sound[99]=soundpool.load(context,R.raw.f614,1);
        sound[100]=soundpool.load(context,R.raw.f615,1);
        sound[101]=soundpool.load(context,R.raw.f616,1);
    }

}
