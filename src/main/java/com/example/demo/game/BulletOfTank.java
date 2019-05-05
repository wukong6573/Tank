package com.example.demo.game;

import com.example.demo.util.CollsionUtils;
import com.example.demo.util.DrawUtils;

import java.io.IOException;
import java.util.List;

public class BulletOfTank extends Bullet {
    int speed=Config.SIZE;
    public BulletOfTank(TankFactory tank) {
        super(tank);
        //子弹宽高
        //工具类绘制
        try {
            int[] size = DrawUtils.getSize(com.example.demo.game.Bullet.class.getResource("/res/img/water.gif").getPath());
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        //根据不同坦克方向(子弹方向)前提要把条件准备好,根据我发的图算出哪个坐标值给子弹类坐标进行赋值
        direction =tank.direction;//坦克的方向,子弹的方向
        //应该在子弹类draw方法要根据不同的子弹方向绘制不同的子弹图片,关键子弹方向来自坦克方向,已经拿到了,在子弹类构造方法里面已经拿到,如何在draw方法使用,定义成员变量接收坦克方向
        //成员变量可以在类不同方法里面使用,定义在方法外,所以我在方法内里面可以使用
        switch (direction) {//坐标改变的逻辑
            case UP:
                this.x = tank.x;
                this.y = tank.y-Config.SIZE;
                break;

            case DOWN:
                this.x = tank.x;
                this.y = tank.y+Config.SIZE;
                break;

            case LEFT:
                this.x = tank.x-Config.SIZE*2;
                this.y = tank.y;
                break;

            case RIGH:
                System.out.println("坦克x:"+tank.x);
                this.x = tank.x+Config.SIZE*2;
                this.y = tank.y;
                System.out.println("子弹x:"+this.x);
                break;

            default:
                break;
        }
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

//        switch (this.direction) {//坐标改变的逻辑
//            case UP:
//                y2 -= 32;
//                break;
//
//            case DOWN:
//                y2 += 32;
//                break;
//
//            case LEFT:
//                x2 -= 32;
//                break;
//
//            case RIGH:
//                x2 += 32;
//                break;
//
//            default:
//                break;
//        }
        boolean flag = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2);//true
        return flag;
    }

    public void autoFindHim(Tank2 tank2) {
        AutoFindHim autoFindHim=new AutoFindHim();
        List<FKPosition> wayList = autoFindHim.getWayLine(this, tank2);
        this.takeMove(wayList);
    }

    private void takeMove(List<FKPosition> wayList) {
        int i=wayList.size()-2;
        if(wayList.size()==0){
            System.out.println("无法到达终点");
            return;
        }
        if (i == 0) {
            System.out.println("到达终点");
        }

        System.out.println(wayList.size());
        FKPosition fk = wayList.get(i);
        System.out.println("子弹的x:"+this.x);
        System.out.println("子弹的y:"+this.y);
        //向上
        if (this.y / Config.SIZE > fk.getY()) {
            this.direction=Direction.UP;
        }

        //向下
        if (this.y / Config.SIZE < fk.getY()) {
            this.direction=Direction.DOWN;
        }

        //向左
        if (this.x / Config.SIZE > fk.getX()) {
            this.direction=Direction.LEFT;
        }

        //向右
        if (this.x / Config.SIZE < fk.getX()) {
            this.direction=Direction.RIGH;
        }
        this.draw();
    }

    public void draw(){//被不断调用!!!画不断画
        //应该在子弹类draw方法要根据不同的子弹方向绘制不同的子弹图片,关键子弹方向来自坦克方向,已经拿到了,在子弹类构造方法里面已经拿到,如何在draw方法使用,定义成员变量接收坦克方向
        //成员变量可以在类不同方法里面使用,定义在方法外,所以我在方法内里面可以使用
        switch (this.direction) {//坐标改变的逻辑,子弹方向
            case UP:
                //让子弹飞:在子弹类draw方法里面根据不同的方向做不同坐标的改变因为draw方法会不断调用坐标就不断改变就造成飞的效果不必要再写一个飞的方法
                y-=speed;//y = y-8;

                //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/water.gif").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case DOWN:
                y+=speed;
                //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                try {
//				DrawUtils.draw(GetPath.PATH+"\\img\\bullet_d.gif", x, y);//0,0
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/water.gif").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case LEFT:
                x-=speed;

                //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/water.gif").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            case RIGH:
                x+=speed;

                //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                try {
                    DrawUtils.draw(com.example.demo.game.Bullet.class.getResource("/res/img/water.gif").getPath(), x, y);//0,0
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }//画图要不断绘
                break;

            default:
                break;
        }
    }
}
