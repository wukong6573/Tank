package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.DrawUtils;
import lombok.Data;

import java.io.IOException;

/**
 * @author dell
 * //万物皆对象,坦克图片事物,模拟坦克类,坦克对象,属性:图片坐标和宽高
 * //面向对象,把你当成那个对象,你作为一个坦克对象应该具有自己把自己绘制画在游戏窗体上的功能,方法draw,写在坦克类里面
 * //以后,把坦克图片,坦克类,坦克对象都看成同一个东西理解
 */
@Data
public class Tank extends TankFactory{
    public Tank(int x, int y) {//x = 0,y = 64;
        this.x=x;
        this.y=y;
        try {
            int[] size = DrawUtils.getSize(GetPath.PATH + "\\img\\tank_u.gif");
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}

