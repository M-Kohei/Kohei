package com.example.matu.kobu_prototype;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by matu on 2016/11/19.
 */
public class Cord {
    public ArrayList<Circle> cord_list;
    public Circle play;

    public Cord(){
        cord_list = new ArrayList<Circle>();
        play = new Circle();
        play.color = Color.WHITE;
    }

    public int min_circle_color()
    {
        boolean renewal = false;
        Circle min = new Circle();
        for(int i=0;i<cord_list.size();i++){
            if(cord_list.get(i).cord_number == play.cord_number){
                if(!renewal){
                    min = cord_list.get(i);
                    renewal = true;
                }
                else{
                    if(min.y < cord_list.get(i).y)
                        min=cord_list.get(i);
                }
            }
        }

        if(!renewal)
            min = play;

        return min.color;
    }
}
