package com.example.demo.game;

import com.example.demo.util.DrawUtils;
import lombok.Data;

import java.io.IOException;

/**
 * @author dell
 * //万物皆对象,坦克图片事物,模拟坦克类,坦克对象,属性:图片坐标和宽高
 * //面向对象,把你当成那个对象,你作为一个坦克对象应该具有自己把自己绘制画在游戏窗体上的功能,方法draw,写在坦克类里面
 * //以后,把坦克图片,坦克类,坦克对象都看成同一个东西理解
 */
@Data
public class Tank2 extends TankFactory{
    Boolean shotWall = false;

    public Tank2(int x, int y) {
        this.x=x;
        this.y=y;
        try {
            int[] size = DrawUtils.getSize(Bullet.class.getResource("/res/img/tank_u.gif").getPath());
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void draw(){
        String up = "";
        String down = "";
        String left = "";
        String right = "";
            up = "/res/img/tank_u.gif";
            down="/res/img/tank_d.gif";
            left="/res/img/tank_l.gif";
            right="/res/img/tank_r.gif";
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

        Bullet bullet = new BulletOfTank2(this);//bullet;this坦克类对象名,this谁来调用我我就代表谁,this = tank对象名
        return bullet;
    }

    @Override
    public String toString() {
        return "Tank2{}";
    }
}

