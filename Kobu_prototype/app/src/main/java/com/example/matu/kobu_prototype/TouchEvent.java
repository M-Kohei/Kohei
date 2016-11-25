package com.example.matu.kobu_prototype;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/**
 * Created by matu on 2016/11/19.
 */
public class TouchEvent {
    private Cord cord;
    Bitmap img1,img2;
    int cord_number[] = {-1,1,0,2,5,-1,4,3};
    int count;
    float first_y;

    public Cord get_cord(){ return cord; }
    public int get_touch_count(){ return count; }

    public TouchEvent()
    {
        cord = new Cord();
    }

    public void set(Bitmap p1, Bitmap p2) {
        img1 = p1;
        img2 = p2;
    }

    public int get_touch_cordnumber(int c)
    {
        int bit=0;

        if( c == Color.WHITE )
            return -1;

        if( Color.red(c) > 0 )
            bit += 4;
        if( Color.green(c) > 0 )
            bit += 2;
        if( Color.blue(c) > 0 )
            bit += 1;

        return cord_number[bit];
    }

    public int get_touch_cordnumber_y(int c)
    {
        if( Color.red(c) > 0)
            return Color.red(c);
        else if( Color.green(c) > 0)
            return Color.green(c);
        else if( Color.blue(c) > 0)
            return Color.blue(c);
        else
            return 0;
    }

    public boolean TouchEvent(MotionEvent event, float posY) {

        int action = MotionEventCompat.getActionMasked(event);//アクションタイプの取得
        count = MotionEventCompat.getPointerCount(event);//含まれる点の個数を取得
        //Log.d("TEST",""+count);
        for(int i=0;i<count;i++){
            int id = MotionEventCompat.getPointerId(event,i);//i番目ののpointIdを取得
            float x = MotionEventCompat.getX(event,i);//i番目のx座標を取得
            float y = MotionEventCompat.getY(event,i);//i番目のy座標を取得
            if(x<0 && y<0)
                continue;
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    //タッチした1点めの処理 countは必ず1のはず
                    Circle c=new Circle(id);
                    if(y<0.7*MainActivity.displayHeight){
                        c.setPos((int)x,(int)y-(int)posY);
                        c.setAction(MotionEvent.ACTION_DOWN);
                        c.setcolor(img1.getPixel((int)x,(int)y-(int)posY));
                        c.set_cord_number(get_touch_cordnumber(c.color));
                        cord.cord_list.add(c);
                    }
                    else{
                        c.setcolor(img2.getPixel((int)x,(int)y-(int)0.7*MainActivity.displayHeight));
                        c.set_cord_number(get_touch_cordnumber(c.color));
                        c.sound_play = true;
                        cord.play = c;
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if(i == MotionEventCompat.getActionIndex(event)){
                        //はじめて画面にタッチした点の処理
                        Circle c1=new Circle(id);
                        if(y<0.7*MainActivity.displayHeight){
                            c1.setPos((int)x,(int)y-(int)posY);
                            c1.setAction(MotionEvent.ACTION_DOWN);
                            c1.setcolor(img1.getPixel((int)x,(int)y-(int)posY));
                            c1.set_cord_number(get_touch_cordnumber(c1.color));
                            cord.cord_list.add(c1);
                        }
                        else{
                            c1.setcolor(img2.getPixel((int)x,(int)y-(int)0.7*MainActivity.displayHeight));
                            c1.set_cord_number(get_touch_cordnumber(c1.color));
                            c1.sound_play = true;
                            cord.play = c1;
                        }

                    }
                    else{
                        //すでに画面にタッチしている点の処理
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //指を離した最後の点の処理 countは必ず1のはず
                    for(int k=0;k<cord.cord_list.size();k++)
                    {
                        if(cord.cord_list.get(k).id == id){
                            cord.cord_list.get(k).setAction(MotionEvent.ACTION_UP);
                            cord.cord_list.remove(k);
                        }
                    }
                    if(cord.play.id == id){
                        cord.play.setAction(MotionEvent.ACTION_UP);
                        cord.play.sound_play = false;
                    }
                    count--;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if(i == MotionEventCompat.getActionIndex(event)){
                        //指を離した点の処理
                        for(int k=0;k<cord.cord_list.size();k++){
                            if(cord.cord_list.get(k).id == id){
                                cord.cord_list.get(k).setAction(MotionEvent.ACTION_UP);
                                cord.cord_list.remove(k);
                            }
                        }
                        if(cord.play.id == id){
                            cord.play.setAction(MotionEvent.ACTION_UP);
                            cord.play.sound_play = false;
                        }
                    }
                    else{
                        //すでに画面にタッチしている点の処理
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(y < 0.7*MainActivity.displayHeight){
                        for(int k=0;k<cord.cord_list.size();k++){
                            if(cord.cord_list.get(k).id == id){
                                cord.cord_list.get(k).setAction(MotionEvent.ACTION_MOVE);
                                cord.cord_list.get(k).setPos((int)x,(int)y-(int)posY);
                                cord.cord_list.get(k).setcolor(img1.getPixel((int)x,(int)y-(int)posY));
                                cord.cord_list.get(k).set_cord_number(get_touch_cordnumber(cord.cord_list.get(k).color));
                            }
                        }
                    }
                    else{
                        int pixel_color = img2.getPixel((int)x,(int)y-(int)0.7*MainActivity.displayHeight);
                        if(cord.play.get_color() != pixel_color && pixel_color != Color.WHITE){
                            cord.play.setcolor(pixel_color);
                            cord.play.set_cord_number(get_touch_cordnumber(cord.play.color));
                            cord.play.sound_play = true;
                        }
                        else{
                            cord.play.setcolor(pixel_color);
                            cord.play.set_cord_number(get_touch_cordnumber(cord.play.color));
                        }
                    }
                    break;
            }
        }
        return true;
    }
}
