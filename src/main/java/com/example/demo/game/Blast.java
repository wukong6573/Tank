package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class Blast{
    private int x;
    private int y;

    public Blast(Bullet bullet) {
            this.x = bullet.getX()-64;
            this.y = bullet.getY()-64;
    }

    public void draw(){
            try {
                DrawUtils.draw(GetPath.PATH+"\\img\\blast_8.gif",this.x, this.y);
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

