package com.example.demo.game;

import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class Result {
    public void drawWin() {
        try {
            DrawUtils.draw(Bullet.class.getResource("/res/img/win.png").getPath(), 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawFailue() {
        try {
            DrawUtils.draw(Bullet.class.getResource("/res/img/over.png").getPath(), 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
