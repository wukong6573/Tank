package com.example.demo.game;

import com.example.demo.util.CollsionUtils;
import com.example.demo.util.DrawUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author dell
 * //坦克发射子弹,你作为坦克对象应该具有发射子弹方法shot,写在坦克类,这个方法的返回值拿到子弹类对象名
 * //只要我拿到子弹对象名就调用子弹类里面draw方法绘制子弹图片,子弹类,图片,坐标宽高,绘制自己的方法draw方法,不会写,完全模仿坦克类
 */
public abstract class Bullet {
    public int x;//0
    public int y;//0
    public int width;//0
    public int height;//0

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction direction;//null,成员变量,子弹方向,赋值来自坦克方向
    public int speed = Config.SIZE / 8;

    public Bullet(TankFactory tank) {//tank = tank对象名;坦克类对象名拿到了
        //子弹宽高
        //工具类绘制
        try {
            int[] size = DrawUtils.getSize(com.example.demo.game.Bullet.class.getResource("/res/img/bullet_u.gif").getPath());
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
                this.x = tank.x + (tank.width / 2 - this.width / 2);
                this.y = tank.y - this.height / 2;
                break;

            case DOWN:
                this.x = tank.x + (tank.width / 2 - this.width / 2);
                this.y = tank.y + (tank.height) / 2;
                break;

            case LEFT:
                this.x = tank.x - this.width / 2;
                this.y = tank.y + tank.height / 2 - this.height / 2;
                break;

            case RIGH:
                this.x = tank.x + tank.width - this.width / 2;
                this.y = tank.y + tank.height / 2 - this.height / 2;
                break;

            default:
                break;
        }

    }


    public void draw() {//被不断调用!!!画不断画
        //应该在子弹类draw方法要根据不同的子弹方向绘制不同的子弹图片,关键子弹方向来自坦克方向,已经拿到了,在子弹类构造方法里面已经拿到,如何在draw方法使用,定义成员变量接收坦克方向
        //成员变量可以在类不同方法里面使用,定义在方法外,所以我在方法内里面可以使用
        switch (this.direction) {//坐标改变的逻辑,子弹方向
            case UP:

                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/bullet_u.gif").getPath(), x, y);//0,0
                    y -= speed;//y = y-8;
                } catch (IOException e) {
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case DOWN:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/bullet_d.gif").getPath(), x, y);//0,0
                    y += speed;
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case LEFT:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/bullet_l.gif").getPath(), x, y);//0,0
                    x -= speed;
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case RIGH:
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/bullet_r.gif").getPath(), x, y);//0,0
                    x += speed;
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            default:
                break;
        }
    }

    //你作为一个子弹对象,应该具有一个判断自己是否超过屏幕范围的方法1,如果是返回true,在不断刷新方法里面遍历子弹集合拿到每一个子弹类对象调用方法1如果返回true,就从集合里面移除
    //判断自己是否超过屏幕范围的方法1,如果是返回true,
    public boolean isDestroyed() {//如何调用呢,只要拿到对象名可以调用了,不断刷新方法里面遍历子弹集合拿到每一个子弹类对象
        if (x < 0 || y < 0 || x > Config.WIDTH || y > Config.HEIGHT) {
            return true;
        }
        return false;
    }

    public Blast boom() {
        Blast blast = new Blast(this);
        return blast;
    }

    public abstract Boolean checkHit(Pictrue p);

    public Boolean checkHit(TankFactory p) {
        //子弹不和自家的坦克产生碰撞
//        if(this instanceof BulletOfTank && p instanceof Tank){
//            return false;
//        }
        if(this instanceof BulletOfEnemyTank && p instanceof EnemyTank){
            return false;
        }
        if(this instanceof BulletOfTank2 && p instanceof Tank2){
            return false;
        }
        int x1 = p.x;
        int y1 = p.y;
        int w1 = p.width;
        int h1 = p.height;

        //坦克的坐标和宽高,this坦克对象名,谁来调用我就代表谁
        int x2 = this.x;
        int y2 = this.y;
        int w2 = this.width;
        int h2 = this.height;
//
        switch (this.direction) {//坐标改变的逻辑
            case UP:
                y2 -= 10;
                break;

            case DOWN:
                y2 += 10;
                break;

            case LEFT:
                x2 -= 10;
                break;

            case RIGH:
                x2 += 10;
                break;

            default:
                break;
        }
        boolean flag = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2);//true
        return flag;
    }

    public Bullet autoFindHim(Tank2 tank2) {
        return null;
    }

}
