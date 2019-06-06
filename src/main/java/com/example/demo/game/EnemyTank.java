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
    Boolean shotWall = false;
    private int speed= Config.SIZE;
    private Random random = new Random();
    private AutoFindHim autoFindHim;
    public List<FKPosition> wayList = new ArrayList<>();
    private static int i = 0;

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
    public com.example.demo.game.EnemyTank getDirection(Tank tank) {

        flag++;

        if (flag > 10) {
          /*  //创建自动寻路对象
            autoFindHim = new AutoFindHim();
            //把寻路的所有坐标集合拿出来
            wayList = autoFindHim.getWayLine(this, tank);
            //第一个是终点坐标，倒数第一个方块是 起点坐标，倒数第二个是与起点相邻的路线方块
            //坦克下一次走的坐标,对应集合中的索引,为了避免size=1时的,1-2=-1 造成的索引越界异常,这里做个三目运算
            i = wayList.size() - 2>=0 ? wayList.size() - 2 : 0;
            //通过flag,控制移动速度
            flag = 0;
            //根据下一个方块的位置,生成坦克的移动方向
            Direction direction = this.tankMove(wayList);
            if (direction != null) {
                this.move(direction);
            }*/
        }
        return this;
    }


    private Direction tankMove(List<FKPosition> wayList) {
        if (i == 0) {
            System.out.println("到达终点");
        }
        if (wayList == null || wayList.size() == 0) {
            System.out.println("无法 到达终点 ！");
            return null;
        }
        FKPosition fk = wayList.get(i);
        //向上
        if (this.y / Config.SIZE > fk.getY()) {
            return Direction.UP;
        }

        //向下
        if (this.y / Config.SIZE < fk.getY()) {
            return Direction.DOWN;
        }

        //向左
        if (this.x / Config.SIZE > fk.getX()) {
            return Direction.LEFT;
        }

        //向右
        if (this.x / Config.SIZE < fk.getX()) {
            return Direction.RIGH;
        }
        return null;
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
}
