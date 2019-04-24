package com.example.demo.game;

import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class EnemyTank extends TankFactory {
    public EnemyTank(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            int[] size = DrawUtils.getSize(Bullet.class.getResource("/res/img/enemy_1_d.gif").getPath());
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    //敌方坦克随机方向
    public void getDirection(int key) {
        //每个方向进来,走两步再说
            if (key % 2==0) {
                this.move(Direction.UP);
            }
            if (key % 3 == 0) {
                this.move(Direction.DOWN);
            }
            if (key % 5 == 0) {
                this.move(Direction.LEFT);
            }
            if (key % 7 ==0) {
                this.move(Direction.RIGH);
            }


    }
}
