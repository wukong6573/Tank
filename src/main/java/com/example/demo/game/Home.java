package com.example.demo.game;

import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class Home extends Pictrue {
    public Home(int x,int y){//x = 0,y = 64;
        super(x, y);
        try {
            int[] size = DrawUtils.getSize(Bullet.class.getResource("/res/img/grass.gif").getPath());
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
            DrawUtils.draw(Bullet.class.getResource("/res/img/grass.gif").getPath(), x, y);//0,64
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
