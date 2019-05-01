package com.example.demo.game;

import com.example.demo.util.CollsionUtils;

public class BulletOfTank2 extends Bullet {
    public BulletOfTank2(TankFactory tank) {
        super(tank);
    }

    public Boolean checkHit(Pictrue p){
        int x1 = p.x;
        int y1 = p.y;
        int w1 = p.width;
        int h1 = p.height;

        //坦克的坐标和宽高,this坦克对象名,谁来调用我就代表谁
        int x2 = this.x;
        int y2 = this.y;
        int w2 = this.width;
        int h2 = this.height;

        switch (this.direction) {//坐标改变的逻辑
            case UP:
                y2 -= 32;
                break;

            case DOWN:
                y2 += 32;
                break;

            case LEFT:
                x2 -= 32;
                break;

            case RIGH:
                x2 += 32;
                break;

            default:
                break;
        }
        boolean flag = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2);//true
        return flag;
    }
}
