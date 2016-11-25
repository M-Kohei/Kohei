package com.example.matu.kobu_prototype;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by matu on 2016/11/19.
 */
public class Circle {
    public int id;
    public int x;
    public int y;
    public int cord_number;
    public int action;
    public int color;
    public boolean sound_play;
    Circle(int id){this.id=id;}

    public Circle() {
    }

    public void setPos(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void setAction(int action){
        switch(action){
            case MotionEvent.ACTION_DOWN:
                this.action=0;
                //this.color=Color.BLUE;
                break;
            case MotionEvent.ACTION_MOVE:
                this.action=1;
                //this.color=Color.GREEN;
                break;
            case MotionEvent.ACTION_UP:
                this.action=2;
                //this.color=Color.RED;
                break;
        }
    }

    public void setcolor(int c)
    {
        this.color = c;
    }

    public void set_cord_number(int n)
    {
        this.cord_number = n;
    }

    public int get_color() {
        return this.color;

    }

    public int get_action()
    {
        return this.action;
    }


    public void draw(Canvas c){
        Paint p=new Paint();
        p.setColor(color);
        c.drawCircle(x, y, 30, p);
        p.setColor(Color.BLACK);
        p.setTextSize(50);
        //c.drawText(action, x, y, p);
        c.drawText(""+color,50,500,p);
        c.drawText("red"+Color.red(this.color), 100, 600, p);
        c.drawText("green"+Color.green(this.color), 100, 800, p);
        c.drawText("blue"+Color.blue(this.color), 100, 1000, p);

    }
}
