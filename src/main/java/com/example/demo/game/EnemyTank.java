package com.example.demo.game;

import com.example.demo.util.DrawUtils;
import lombok.Data;

import java.io.IOException;
import java.util.Random;

@Data
public class EnemyTank extends TankFactory {
    private static int flag = 0;
    Boolean shotWall = false;
    private Random random = new Random();


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

    //敌方坦克追踪目标坦克
    public EnemyTank getDirection(TankFactory tank) {
        boolean go_up = false;
        boolean go_down = false;
        boolean go_left = false;
        boolean go_right = false;

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
            flag = 0;
            //移动前的坐标
            int mb_x = this.x;
            int mb_y = this.y;
            this.move(this.direction);

            boolean left_stop = (this.direction == Direction.LEFT) && mb_x == this.x;  //左右方向移动不了
            boolean right_stop = (this.direction == Direction.RIGH) && mb_x == this.x;  //左右方向移动不了
            boolean up_stop = (this.direction == Direction.UP) && mb_y == this.y;  //上下 移动不了
            boolean down_stop = (this.direction == Direction.DOWN) && mb_y == this.y;  //上下 移动不了

            if (left_stop) {//左边前进不了,并且忽略转向带来的错误"不可通过" 判定
                //如果追到目标坦克,不要移除方向
                if (!this.checkHit(tank)) {
//                    list.remove(Direction.LEFT);
                    System.out.println("left方向被移除");
                    if (tan_y > this.y) {
                        this.direction = Direction.DOWN;
                    } else if (tan_y < this.y) {
                        this.direction = Direction.UP;
                    } else if (tan_x > this.x) {
                        this.direction = Direction.RIGH;
                    }
                }
            } else {
                //否则就告诉AI坦克,这个方向可以走
                go_left = true;
            }

            if (right_stop) { //右边前进不了
                //如果追到目标坦克,不要移除方向
                if (!this.checkHit(tank)) {
                    //不再产生随机方向,根据目标坦克的位置,选择方向
                    if (tan_y > this.y) {
                        this.direction = Direction.DOWN;
                    } else if (tan_x < this.x) {
                        this.direction = Direction.LEFT;
                    } else if (tan_y < this.y) {
                        this.direction = Direction.UP;
                    }
                }
            } else {
                go_right = true;
            }
            if (up_stop) {
                //如果追到目标坦克,不要移除方向
                if (!this.checkHit(tank)) {
                    if (tan_y > this.y) {
                        this.direction = Direction.DOWN;
                    } else if (tan_x < this.x) {
                        this.direction = Direction.LEFT;
                    } else if (tan_x > this.x) {
                        this.direction = Direction.RIGH;
                    }
                }
            } else {
                go_up = true;
            }
            if (down_stop) {
                //如果追到目标坦克,不要移除方向
                if (!this.checkHit(tank)) {
                    if (tan_y < this.y) {
                        this.direction = Direction.UP;
                    } else if (tan_x < this.x) {
                        this.direction = Direction.LEFT;
                    } else if (tan_x > this.x) {
                        this.direction = Direction.RIGH;
                    }
                }
            } else {
                go_down = true;
            }

            if (go_up) {  //这里注意加else ,能往上走就尽量优先往上走,避免两次设置方向,优先选择当前能跑的方向
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
            if (go_down) {
                if (tan_y > this.y) {
                    this.direction = Direction.DOWN;
                } else if (tan_y < this.y) {
                    this.direction = Direction.UP;
                } else if (tan_x < this.x) {
                    this.direction = Direction.LEFT;
                } else if (tan_x > this.x) {
                    this.direction = Direction.RIGH;
                }
            }
            if (go_left) {
                if (tan_x < this.x) {
                    this.direction = Direction.LEFT;
                } else if (tan_y > this.y) {
                    this.direction = Direction.DOWN;
                } else if (tan_y < this.y) {
                    this.direction = Direction.UP;
                } else if (tan_x > this.x) {
                    this.direction = Direction.RIGH;
                }
            }
            if (go_right) {
                if (tan_x > this.x) {
                    this.direction = Direction.RIGH;
                } else if (tan_y > this.y) {
                    this.direction = Direction.DOWN;
                } else if (tan_y < this.y) {
                    this.direction = Direction.UP;
                } else if (tan_x < this.x) {
                    this.direction = Direction.LEFT;
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
