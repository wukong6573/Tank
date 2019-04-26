package com.example.demo.game;

import com.example.demo.util.CollsionUtils;

public abstract class TankFactory {
    Direction direction = Direction.UP;
    int x;//0
    int y;//0
    int width;//0
    int height;//0
    int speed = Config.SIZE / 2;//坦克的速度
    Direction badDirection;
    Direction badDirection2;

    int step=0;

    public abstract void draw();

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

        if (this.badDirection2 != null && this.badDirection2 == direction) {
            //检测到碰撞，不能继续往前走
            return;
        }
        step++;
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


    public abstract Bullet shot();


    public Boolean checkHit(Pictrue p) {
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

    public Boolean checkHit(TankFactory p) {
        if (this.equals(p)) {
            //不能和自己碰撞
            return false;
        }
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
            this.badDirection2 = this.direction;
        } else {
            //只要不是碰撞状态，就要清空标记，否者就算没碰撞也会停止移动
            this.badDirection2 = null;
        }
        return flag;
    }

}
