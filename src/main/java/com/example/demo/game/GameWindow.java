package com.example.demo.game;

import com.example.demo.util.Window;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author dell
 * 6.写一个游戏窗体类去继承工具类里面Window抽象父类,继承里面非静态start方法去开启一个游戏窗体,开始游戏
 */
public class GameWindow extends Window {
    private Tank tank;//null
    private Tank2 tank2;//null
    Result result;
    //集合不用ArrayList否则出现并发修改异常,改用能解决这个异常集合跟ArrayList差不多,名字叫CopyOnWriterArrayList
    private CopyOnWriteArrayList<Bullet> blist = new CopyOnWriteArrayList<>();//子弹类集合blist

    private CopyOnWriteArrayList<Pictrue> list = new CopyOnWriteArrayList<>();//图片集合list
    private CopyOnWriteArrayList<Pictrue> list2 = new CopyOnWriteArrayList<>();//图片集合list
    private CopyOnWriteArrayList<TankFactory> tanks = new CopyOnWriteArrayList<>();//爆炸物集合list

    public GameWindow(String title, int width, int height, int fps) {
        //子类构造方法不写也是写,默认调用父类无参构造方法,现在父类没有无参,手动调用父类有参,你写按照你写的走
        super(title, width, height, fps);
        // TODO 自动生成的构造函数存根
    }

    @Override
    protected void onCreate() {

        tank = new Tank(64 * 8, 0);//坦克对象名tank

        tank2 = new Tank2(64 * 10, 0);  //第二辆坦克


        for (int i = 0; i < 6; i++) {//0-17
            Wall wall = new Wall(Config.SIZE * i, Config.SIZE * 1);
            Water water = new Water(Config.SIZE * i, Config.SIZE * 3);
            Grass grass = new Grass(Config.SIZE * i, Config.SIZE * 5);
            Steel steel = new Steel(Config.SIZE * i, Config.SIZE * 7);
            list.add(water);
            list.add(grass);
            list.add(steel);
            list.add(wall);
        }
        list.add(new Home(Config.WIDTH*2/3,Config.HEIGHT));
        list2.addAll(list);
        tanks.add(tank);
        tanks.add(tank2);

        //结果对象
         result=new Result();
    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {
        //每按下鼠标就调用一次,鼠标监听
//		System.out.println("onMouseEvent");
    }


    @Override
    protected void onKeyEvent(int key) {
        //每按下键盘的按键就调用一次,按键监听

        switch (key) {
            case Keyboard.KEY_UP://进来这里面说明我按下↑箭头按键,希望坦克移动,对应一个向上方向移动,过坦克对象名点调用,move方法
                tank.move(Direction.UP);
                break;

            case Keyboard.KEY_DOWN://进来这里面说明我按下↓箭头按键
                tank.move(Direction.DOWN);
                break;

            case Keyboard.KEY_LEFT://进来这里面说明我按下←箭头按键
                tank.move(Direction.LEFT);
                break;

            case Keyboard.KEY_RIGHT://进来这里面说明我按下→箭头按键
                tank.move(Direction.RIGH);
                break;

            case Keyboard.KEY_SPACE://进来这里面说明我按下空格键,坦克对象名调用shot方法得到子弹类对象名
                Bullet bullet = tank.shot();//bullet子弹类对象名,null;
                if (bullet != null) {
                    blist.add(bullet);
                }
                break;

            //坦克2
            case Keyboard.KEY_W://进来这里面说明我按下↑箭头按键,希望坦克移动,对应一个向上方向移动,过坦克对象名点调用,move方法
                tank2.move(Direction.UP);
                break;

            case Keyboard.KEY_S://进来这里面说明我按下↓箭头按键
                tank2.move(Direction.DOWN);
                break;

            case Keyboard.KEY_A://进来这里面说明我按下←箭头按键
                tank2.move(Direction.LEFT);
                break;

            case Keyboard.KEY_D://进来这里面说明我按下→箭头按键
                tank2.move(Direction.RIGH);
                break;

            case Keyboard.KEY_J://进来这里面说明我按下空格键,坦克对象名调用shot方法得到子弹类对象名
                Bullet bullet2 = tank2.shot();//bullet子弹类对象名,null;
                if (bullet2 != null) {
                    blist.add(bullet2);
                }
                break;

            default:
                break;
        }
    }

    static int count=1;

    @Override
    protected void onDisplayUpdate() {
        //第一个版本,内容包括:移动,碰撞,子弹销毁产生爆炸
        version_1();

        if(list.size()<=list2.size()-24){
//            count++;
//            list.addAll(list2);
            result.draw();
        }
    }

    private void version_1() {
        //子弹碰撞坦克,也会发生爆炸
        for (TankFactory tank : tanks) {
            tank.draw();
            for (Bullet bullet : blist) {
                if (bullet.checkHit(tank)) {
                    bullet.boom().draw();
                    blist.remove(bullet);
                }
            }
        }

        //坦克碰到坦克,也要停下来
        for (TankFactory tank1 : tanks) {
            for (TankFactory tank2 : tanks) {
                if (tank1.checkHit(tank2)) {
                    break;
                }
            }
        }

        //子弹撞到边界,或者 子弹碰到 图片,也会爆炸
        for (Bullet bullet : blist) {//bullet每一个子弹类对象名
            if (bullet.isDestroyed()) {
                bullet.boom().draw();
                blist.remove(bullet);//超出范围,就从集合里面移除这个子弹对象,集合里面就没有这个子弹对象了,就不会把它绘制出来了
            }
            for (Pictrue p : list) {
                //子弹碰到图片,就让他 爆炸
                if (bullet.checkHit(p)) {
                    if(p instanceof Home){
                        break;
                    }
                    bullet.boom().draw();
                    //子弹碰到图片,图片也消失
                    blist.remove(bullet);
                    list.remove(p);

                }
            }
            bullet.draw();
        }

        //图片把自己画出来
        for (Pictrue p : list) {//Pictrue p = 墙水草铁对象;多态
            p.draw();//父类引用执行子类对象,调用非静态方法,编译看左边父类,没有报错,真正的结果看指向的子类里面的draw方法,把不同子类图片绘制出来
        }

        //坦克碰到图片,就停下
        for (TankFactory tankFactory : tanks) {
            for (Pictrue p : list) {
                if (tankFactory.checkHit(p)) {
//                //如果检测到碰撞，一定要跳出循环,不跟其他铁比较,为什么???
                    break;
                }
            }
        }
    }
}
