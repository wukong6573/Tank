package com.example.demo.game;

import com.example.demo.util.DrawUtils;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class EnemyTank extends TankFactory {
    private static int flag = 0;
    Boolean shotWall = false;
    private Random random = new Random();
    private  ArrayList<Direction> dirs=new ArrayList<>();

    public EnemyTank(int x, int y) {
        dirs.add(Direction.UP);
        dirs.add(Direction.DOWN);
        dirs.add(Direction.LEFT);
        dirs.add(Direction.RIGH);
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

    //敌方坦克追踪目标坦克
    public EnemyTank getDirection(TankFactory tank,CopyOnWriteArrayList<Pictrue> list) {
        if(dirs.size()==0){
            dirs.add(Direction.UP);
            dirs.add(Direction.DOWN);
            dirs.add(Direction.LEFT);
            dirs.add(Direction.RIGH);
        }
        int tan_x = 0;
        int tan_y = 0;

        //实现AI坦克自动寻找目标的功能
        if (tank instanceof Tank) {
            tan_x = tank.x;
            tan_y = tank.y;
        }
        flag++;
        //每个方向进来,走两步再说
        if (flag > 10) {
            System.out.println(dirs.size());
            flag = 0;
            System.out.println(this.direction);
            this.move(this.direction);


            //如果碰到图片,就要转变方向
            for (Pictrue p : list) {
                if (this.checkHit(p)) {
                    dirs.remove(this.direction);
                    this.direction = dirs.get(random.nextInt(dirs.size()));
                    break;
                    //这里一定要加一个break,不再和其他的图片比较,因为和其他图片肯定是不会碰撞的,所以会走到else..语句里,,造成bug
                }else {
                    if (tan_y < this.y) {
                        this.direction = Direction.UP;
                    } else if (tan_y > this.y) {
                        this.direction = Direction.DOWN;
                    } else if (tan_x < this.x) {
                        this.direction = Direction.LEFT;
                    } else if (tan_x > this.x) {
                        this.direction = Direction.RIGH;
                    }
                }

            }

        }
        return this;
    }

    public void draw() {
        String up = "";
        String down = "";
        String left = "";
        String right = "";
        up = "/res/img/enemy_1_u.gif";
        down = "/res/img/enemy_1_d.gif";
        left = "/res/img/enemy_1_l.gif";
        right = "/res/img/enemy_1_r.gif";
        try {
            switch (this.direction) {
                case UP:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(Bullet.class.getResource(up).getPath(), x, y);
                    break;

                case DOWN:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(Bullet.class.getResource(down).getPath(), x, y);//0,64
                    break;

                case LEFT:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(Bullet.class.getResource(left).getPath(), x, y);//0,64
                    break;

                case RIGH:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(Bullet.class.getResource(right).getPath(), x, y);//0,64
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long last;

    public Bullet shot() {//shot只能被坦克类的对象名调用,this谁来调用我我就代表谁
        long now = System.currentTimeMillis();//子弹发射当前时间,我定义一个成员变量记住它表示上一次发射子弹的时间
        //如果当前时间减去上一次发射时间小于某个值,我就返回null,方法都结束,就不走创建子类对象的逻辑,如果大于某个值我就把当前时间赋值给成员变量上一次时间
        if (now - last < 400) {
            return null;//我就返回null,方法都结束,就不走创建子类对象的逻辑,在shot方法调用处按键监听得到了null,就不添加到子弹集合,就不画,控制了
        } else {
            last = now;
        }

        Bullet bullet = new BulletOfEnemyTank(this);//bullet;this坦克类对象名,this谁来调用我我就代表谁,this = tank对象名
        return bullet;
    }

    @Override
    public String toString() {
        return "EnemyTank{}";
    }
}
