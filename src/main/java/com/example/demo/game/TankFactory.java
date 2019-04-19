package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.CollsionUtils;
import com.example.demo.util.DrawUtils;

import java.io.IOException;

public class TankFactory {
    Direction direction=Direction.UP;
     int x;//0
     int y;//0
     int width;//0
     int height;//0
     int speed = Config.SIZE / 2;//坦克的速度
     Direction badDirection;

    public void draw() {//画图要不断调用!!!
        try {
            switch (this.direction) {
                case UP:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(GetPath.PATH + "\\img\\tank_u.gif", x, y);//0,64

                    break;

                case DOWN:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(GetPath.PATH + "\\img\\tank_d.gif", x, y);//0,64
                    break;

                case LEFT:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(GetPath.PATH + "\\img\\tank_l.gif", x, y);//0,64
                    break;

                case RIGH:
                    //想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
                    DrawUtils.draw(GetPath.PATH + "\\img\\tank_r.gif", x, y);//0,64
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(Direction direction) {
        //为了提高效率,当成员方向this.direction跟按键方向direction不一致才赋值,其实这个时候就是调转方向的时候
        if (this.direction != direction) {
            this.direction = direction;
            return;
        }
        if (this.badDirection != null && this.badDirection == direction) {
            //检测到碰撞，不能继续往前走
            return;
        }

        //根据坦克不同的方向做不同坐标改变
        switch (direction) {//坐标改变的逻辑
            case UP:
                y -= speed;//y = y-32;
                break;

            case DOWN:
                y += speed;
                break;

            case LEFT:
                x -= speed;
                break;

            case RIGH:
                x += speed;
                break;

            default:
                break;
        }

        //坦克移动了,问题一:坦克可以超出屏幕范围,如果超过某个范围,我就让它等于那个范围,它就定在哪里,不能越界了,坦克要移动调用坦克move方法,在move方法里面做限制
        //越界处理,如果超过某个范围,我就让它等于那个范围,它就定在哪里,
        if (x <= 0) {
            x = 0;
        }

        if (y <= 0) {
            y = 0;
        }

        if (x >= Config.WIDTH - Config.SIZE) {
            x = Config.WIDTH - Config.SIZE;
        }

        if (y >= Config.HEIGHT - Config.SIZE) {
            y = Config.HEIGHT - Config.SIZE;
        }

    }

    //我定义一个成员变量记住它表示上一次发射子弹的时间
    private long last;//0,

    public Bullet shot() {//shot只能被坦克类的对象名调用,this谁来调用我我就代表谁
        long now = System.currentTimeMillis();//子弹发射当前时间,我定义一个成员变量记住它表示上一次发射子弹的时间
        //如果当前时间减去上一次发射时间小于某个值,我就返回null,方法都结束,就不走创建子类对象的逻辑,如果大于某个值我就把当前时间赋值给成员变量上一次时间
        if (now - last < 400) {
            return null;//我就返回null,方法都结束,就不走创建子类对象的逻辑,在shot方法调用处按键监听得到了null,就不添加到子弹集合,就不画,控制了
        } else {
            last = now;
        }

        Bullet bullet = new Bullet(this);//bullet;this坦克类对象名,this谁来调用我我就代表谁,this = tank对象名
        return bullet;//返回值这个方法调用完毕得到的结果
    }


    public Boolean checkHit(Steel p) {
        int x1 = p.x;
        int y1 = p.y;
        int w1 = p.width;
        int h1 = p.height;

        //坦克的坐标和宽高,this坦克对象名,谁来调用我就代表谁
        int x2 = this.x;//坦克的坐标值,包括刚好接触的时候
        int y2 = this.y;
        int w2 = this.width;
        int h2 = this.height;

        switch (this.direction) {//坐标改变的逻辑
            case UP:
                y2 -= 32;
                //根据工具类的碰撞逻辑，当到了接触面，仍然认为没有碰撞，明显不合理
                //我们规定：到了接触面就是碰撞，所以我们拉近（疏远）一点点距离 来达到 碰撞（不碰撞）的效果
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
        if (flag) {
            this.badDirection = this.direction;
        } else {
            //只要不是碰撞状态，就要清空标记，否者就算没碰撞也会停止移动
            this.badDirection = null;
        }
        return flag;
    }

}
