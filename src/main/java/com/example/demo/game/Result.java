package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class Result {
    public void draw() {
        try {
            DrawUtils.draw(GetPath.PATH + "\\img\\win.png", 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
