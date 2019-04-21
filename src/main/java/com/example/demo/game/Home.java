package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class Home extends Pictrue{
    public Home(int x,int y){//x = 0,y = 64;
        super(x, y);
        try {
            int[] size = DrawUtils.getSize(GetPath.PATH+"\\img\\01.jpg");
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
    @Override
    public void draw() {
        try {
            DrawUtils.draw(GetPath.PATH+"\\img\\01.jpg", x, y);//0,64
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
