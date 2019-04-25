package com.example.demo.game;

import com.example.demo.util.DrawUtils;
import lombok.Data;

import java.io.IOException;

@Data
public class EnemyTank extends TankFactory {
    private static int flag = 0;

    private Direction lastDirection;
    Boolean shotWall = false;

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
    public EnemyTank getDirection(int key) {
        flag++;
        //每个方向进来,走两步再说
        if (flag > 50) {
            if (key == 1) {
                this.move(Direction.UP);
                flag = 0;
            } else if (key == 2) {
                this.move(Direction.DOWN);
                flag = 0;
            } else if (key == 3) {
                this.move(Direction.LEFT);
                flag = 0;
            } else if (key == 4) {
                this.move(Direction.RIGH);
                flag = 0;
            }
            lastDirection = this.direction;
        } else {
            if (lastDirection != null) {
                this.move(lastDirection);
            }
        }
        return this;
    }

    public void draw(){
        String up = "";
        String down = "";
        String left = "";
        String right = "";
            up = "/res/img/enemy_1_u.gif";
            down="/res/img/enemy_1_d.gif";
            left="/res/img/enemy_1_l.gif";
            right="/res/img/enemy_1_r.gif";
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
}
