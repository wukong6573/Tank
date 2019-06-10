package com.example.demo.game;

import com.example.demo.util.CollsionUtils;
import com.example.demo.util.DrawUtils;

import java.io.IOException;
import java.util.List;

public class BulletOfTank extends Bullet {

    public BulletOfTank(TankFactory tank) {
        super(tank);
        //子弹宽高
        //工具类绘制
        try {
            int[] size = DrawUtils.getSize(com.example.demo.game.Bullet.class.getResource("/res/img/pq.png").getPath());
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        //根据不同坦克方向(子弹方向)前提要把条件准备好,根据我发的图算出哪个坐标值给子弹类坐标进行赋值
        direction = tank.direction;//坦克的方向,子弹的方向
        //应该在子弹类draw方法要根据不同的子弹方向绘制不同的子弹图片,关键子弹方向来自坦克方向,已经拿到了,在子弹类构造方法里面已经拿到,如何在draw方法使用,定义成员变量接收坦克方向
        //成员变量可以在类不同方法里面使用,定义在方法外,所以我在方法内里面可以使用
        switch (direction) {//坐标改变的逻辑
            case UP:
                this.x = tank.x;
                this.y = tank.y - Config.SIZE;
                break;

            case DOWN:
                this.x = tank.x;
                this.y = tank.y + Config.SIZE;
                break;

            case LEFT:
                this.x = tank.x - Config.SIZE;
                this.y = tank.y;
                break;

            case RIGH:
                this.x = tank.x + Config.SIZE;
                this.y = tank.y;
                break;

            default:
                break;
        }
    }

    public Boolean checkHit(Pictrue p) {
        int x1 = p.x;
        int y1 = p.y;
        int w1 = p.width;
        int h1 = p.height;

        //坦克的坐标和宽高,this坦克对象名,谁来调用我就代表谁
        int x2 = this.x;
        int y2 = this.y;
        int w2 = this.width;
        int h2 = this.height;

        boolean flag = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2);//true
        return flag;
    }

    public Bullet autoFindHim(Tank2 tank2) {
        BulletOfTankAutoFindHim autoFindHim = new BulletOfTankAutoFindHim();
        List<FKPosition> wayList = autoFindHim.getWayLine(this, tank2);
        this.takeMove(wayList);
        return this;
    }

    private void takeMove(List<FKPosition> wayList) {
        int i = 0;
        i = wayList.size() - 2 >= 0 ? wayList.size() - 2 : 0;
        if (wayList.size() == 0) {
            System.out.println("无法到达终点");
            return;
        }
        if (i == 0) {
            System.out.println("到达终点");
        }

        FKPosition fk = wayList.get(i);
        //向上
        if (this.y> fk.getY()*Config.SIZE) {
            this.y =fk.getY()*Config.SIZE;
        }

        //向下
        if (this.y / Config.SIZE < fk.getY()) {
            this.y =fk.getY()*Config.SIZE;
        }

        //向左
        if (this.x> fk.getX()*Config.SIZE) {
            this.x=fk.getX()*Config.SIZE;
        }

        //向右
        if (this.x< fk.getX()*Config.SIZE) {
            this.x =fk.getX()* Config.SIZE;
        }
    }

    public void draw() {//被不断调用!!!画不断画
        switch (this.direction) {//坐标改变的逻辑,子弹方向
            case UP:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/pq.png").getPath(), x, y);//0,0
                } catch (IOException e) {
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case DOWN:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/pq.png").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case LEFT:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/pq.png").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case RIGH:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/pq.png").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            default:
                break;
        }
    }

    public boolean isDestroyed() {//如何调用呢,只要拿到对象名可以调用了,不断刷新方法里面遍历子弹集合拿到每一个子弹类对象
        if (x < 0 || y < 0 || x >= Config.WIDTH || y >= Config.HEIGHT) {
            return true;
        }
        return false;
    }

}
