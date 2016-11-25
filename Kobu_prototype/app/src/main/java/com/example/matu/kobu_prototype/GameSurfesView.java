package com.example.matu.kobu_prototype;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by matu on 2016/11/19.
 */
public class GameSurfesView extends SurfaceView implements SurfaceHolder.Callback ,Runnable{

    private float limitY,PosY;

    Paint p = new Paint();
    Bitmap img0,img1,img2;
    Sound_Map sound_map;

    SurfaceHolder mHolder;
    Thread sled ;
    Context context;
    TouchEvent touchevent;
    float target_position = 0,step = 0,radius=50;
    boolean set_T=true,set_P=false;
    int step_count=0;
    float Radius=50,rx,ry,sx;

    int pictureWidth,h;

    long time1=0;
    Random r;

    public GameSurfesView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        sound_map = new Sound_Map(context);
        touchevent = new TouchEvent();

        r = new Random();

        this.context=context;
    }

    public boolean onTouchEvent(MotionEvent event) {
        touchevent.TouchEvent(event,PosY);
        return true;
    }

    public int get_move_mode(float Acy){
        if( Acy > 7 )
            return 3;
        else if( Acy > 5)
            return  2;
        else if( Acy > 3 )
            return  1;
        else if( Acy > -3 )
            return  0;
        else if( Acy > -5)
            return  -1;
        else if( Acy > -7)
            return  -2;
        else
            return  -3;
    }

    public void set_positionY(int mode){
        switch(mode){
            case -3:	PosY+=15;	break;
            case -2:	PosY+=10;	break;
            case -1:	PosY+=5;	break;
            case 0:		break;
            case 1:		PosY-=5;	break;
            case 2:		PosY-=10;	break;
            case 3:		PosY-=15;	break;
        }

        if(PosY > 0)
            PosY = 0;
        if( PosY < -limitY)
            PosY = -limitY;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Resources res = this.getContext().getResources();
        img0 = BitmapFactory.decodeResource(res, R.mipmap.guita_flet2);
        img1 = BitmapFactory.decodeResource(res, R.mipmap.guita_flet2_coloar_map);
        img2 = BitmapFactory.decodeResource(res, R.mipmap.guita_zyaro_n);
        pictureWidth=img0.getWidth();
        h = img0.getHeight();
        sx=(float)MainActivity.displayWidth/(float)pictureWidth;
        limitY=h*sx-(float )0.7*MainActivity.displayHeight;

        img0=Bitmap.createScaledBitmap(img0,MainActivity.displayWidth,(int) (h*sx),true);
        img1=Bitmap.createScaledBitmap(img1,MainActivity.displayWidth,(int) (h*sx),true);
        img2=Bitmap.createScaledBitmap(img2,MainActivity.displayWidth,(int) (h*sx),true);

        PosY =  -1 * limitY/2;

        touchevent.set(img1,img2);

        sled = new Thread(this);
        sled.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        sled = null;

        if(img0 != null)
            img0.recycle();
        if(img1 != null)
            img1.recycle();
        if(img2 != null)
            img2.recycle();
    }

    @Override
    public void run() {
        while( sled != null )
        {

            Canvas c=mHolder.lockCanvas();
            if(c==null)
                break;
            p.setARGB(255,255,255,255);
            c.drawRect(0,0,1200,1800,p);

            p.setColor(Color.RED);
            p.setTextSize(60);

            if(MainActivity.play_mode==1){
                if(touchevent.get_touch_count() == 0){
                    int move_mode = get_move_mode(AcSensor.Inst().getY());
                    set_positionY(move_mode);
                }
                else if(touchevent.get_touch_count() == 1 && touchevent.get_cord().cord_list.size() > 0 ){
                    int rcolor = touchevent.get_cord().cord_list.get(0).color;
                    if(sound_map.x_map.get(touchevent.get_touch_cordnumber(rcolor))!=null
                            && sound_map.y_map.get(touchevent.get_touch_cordnumber_y(rcolor))!=null){
                        rx = (float) (sound_map.x_map.get(touchevent.get_touch_cordnumber(rcolor))*sx*1.33);
                        ry = (float) (sound_map.y_map.get(touchevent.get_touch_cordnumber_y(rcolor))*sx*1.33);
                    }
                }



                c.drawBitmap(img1,0,PosY,p);
                c.drawBitmap(img2,0,(float) (0.7*MainActivity.displayHeight),p);

                //testtext
                //c.drawText("AcX : "+AcSensor.Inst().getX(), 50, 100, p);
                p.setAntiAlias(true);
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(4);
                c.drawCircle(rx,ry+PosY,radius,p);
                c.drawText("rx:" +rx,50, 100, p);
                c.drawText("ry:" +ry,50, 200, p);
                c.drawText("radius: "+radius*sx, 50, 300, p);
                c.drawText("AcY : "+AcSensor.Inst().getY(), 50, 400, p);
                c.drawText("count : "+touchevent.get_touch_count(), 50, 500, p);
                c.drawText("test mode", 50, 600, p);
                //c.drawText("AcZ : "+AcSensor.Inst().getZ(), 50, 500, p);
                //c.drawText("AcY * 134 : "+AcSensor.Inst().getY() * 134, 50, 600, p);
                c.drawText("limitY" +limitY,50,700, p);
                c.drawText("ph:"+img1.getHeight()+" pw:"+img1.getWidth()+" sx:"+sx,50, 800, p);
                c.drawText("PosY" +PosY,50,900, p);
                c.drawText("dW:"+MainActivity.displayWidth+" pW:"+pictureWidth,50,1000,p);
                //testtext
            }
            else if(MainActivity.play_mode==2){
                if(touchevent.get_touch_count() == 1 && set_T){
                    target_position = -1 * (r.nextInt(2686));
                    step = (target_position - PosY)/30;
                    step_count=0;
                    set_T=false;
                    set_P=true;
                }

                else if(set_P){
                    step_count++;
                    if(step_count==30){
                        set_T=true;
                        set_P=false;
                    }
                    else{
                        PosY=PosY+step;
                    }
                }

                c.drawBitmap(img1,0,PosY,p);
                c.drawBitmap(img2,0,(float) (0.7*MainActivity.displayHeight),p);

                long time2=System.nanoTime();
                c.drawText("time1 :"+time1, 50, 100, p);
                c.drawText("time2 :"+time2, 50, 200, p);
                c.drawText("time2-time1 :"+(time2-time1), 50, 300, p);
                c.drawText("test play mode", 50, 400, p);
                c.drawText("PosY : "+PosY, 50, 500, p);
                c.drawText("radius : "+radius, 50, 600, p);
                c.drawText("step : "+step, 50, 700, p);
                c.drawText("target position : "+target_position, 50, 800, p);
                time1=time2;
            }


            sound_map.sound(touchevent.get_cord());

            mHolder.unlockCanvasAndPost(c);
        }
    }
}
