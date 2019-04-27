package com.example.demo.game;

import com.example.demo.util.DrawUtils;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class EnemyTank extends TankFactory {
    private static int flag = 0;
    private int step;  //定义一个计算移动步数的变量

    private Direction lastDirection;
    private static List<Direction> list;

    Boolean shotWall = false;
    private Random random = new Random();

    static {
        list = new ArrayList<>();
        list.add(Direction.UP);
        list.add(Direction.DOWN);
        list.add(Direction.LEFT);
        list.add(Direction.RIGH);
    }

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
    public EnemyTank getDirection() {
        boolean go_up = false;
        boolean go_down = false;
        boolean go_left = false;
        boolean go_right = false;
        if(this.step>=3){  //一个方向走了3步,就考虑换个方向巡逻
            this.step=0;
            this.lastDirection = list.get(random.nextInt(list.size()));
        }

        flag++;
        //每个方向进来,走两步再说
        if (flag > 10) {
            flag = 0;
            //移动前的坐标
            int mb_x = this.x;
            int mb_y = this.y;
            if (this.lastDirection != null) {
                //移动两次的原因是为了避开 转向的时候,如果跑一次,坐标没改变,就会认为是不可通过的bug
                this.move(lastDirection);
            } else {
                this.move(this.direction);
            }

            boolean left_stop = (this.direction == Direction.LEFT) && mb_x == this.x;  //左右方向移动不了
            boolean right_stop = (this.direction == Direction.RIGH) && mb_x == this.x;  //左右方向移动不了
            boolean up_stop = (this.direction == Direction.UP) && mb_y == this.y;  //上下 移动不了
            boolean down_stop = (this.direction == Direction.DOWN) && mb_y == this.y;  //上下 移动不了
            if (left_stop && this.lastDirection==this.direction) {//左边前进不了,并且忽略转向带来的错误"不可通过" 判定
                list.remove(Direction.LEFT);
                if (list.size() > 0) {
                    //从其他方向,随机取一个方向
                    this.lastDirection = list.get(random.nextInt(list.size()));
                }
            } else if (this.direction == Direction.LEFT) {
                //否则就告诉AI坦克,这个方向可以走
                go_left = true;
            }

            if (right_stop && this.lastDirection==this.direction) { //右边前进不了
                list.remove(Direction.RIGH);
                if (list.size() > 0) {
                    this.lastDirection = list.get(random.nextInt(list.size()));
                }
            } else if (this.direction == Direction.RIGH) {
                go_right = true;
            }
            if (up_stop && this.lastDirection==this.direction) {
                list.remove(Direction.UP);
                if (list.size() > 0) {
                    this.lastDirection = list.get(random.nextInt(list.size()));
                }
            } else if (this.direction == Direction.UP) {
                go_up = true;
            }
            if (down_stop && this.lastDirection==this.direction) {
                list.remove(Direction.DOWN);
                if (list.size() > 0) {
                    this.lastDirection = list.get(random.nextInt(list.size()));
                }
            } else if (this.direction == Direction.DOWN) {
                go_down = true;
            }

            if (list.size() > 0) {//如果某个方向能跑,就一直跑下去
                if (go_up) {
                    this.lastDirection = Direction.UP;
                    this.step++;
                }
                if (go_down) {
                    this.lastDirection = Direction.DOWN;
                    this.step++;
                }
                if (go_left) {
                    this.lastDirection = Direction.LEFT;
                    this.step++;
                }
                if (go_right) {
                    this.lastDirection = Direction.RIGH;
                    this.step++;
                }
            } else {
                //当所有方向都被移除,重新复原所有方向
                list.add(Direction.UP);
                list.add(Direction.DOWN);
                list.add(Direction.LEFT);
                list.add(Direction.RIGH);
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
}
