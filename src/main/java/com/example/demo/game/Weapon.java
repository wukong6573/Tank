package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class Weapon extends Pictrue{
    public Weapon(int x, int y) {
        super(x, y);
        try {
            int[] size = DrawUtils.getSize(GetPath.PATH+"\\img\\short_gun.jpg");
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Override
    public void draw() {
        //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
        try {
            DrawUtils.draw(GetPath.PATH+"\\img\\short_gun.jpg", x, y);//0,64
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
