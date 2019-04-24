package com.example.demo.test;

import com.example.demo.game.Config;
import com.example.demo.game.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class JFrameDemo extends JFrame {

    private JButton jb1, jb2;  //代表页面的按钮
    private JPanel jp1, jp2;  //有点像横幅
    private JLabel jl1;
    private static boolean live = true;
    private static int playTime = 0;
    private static JFrameDemo jFrameDemo;


    public static void main(String[] args) {
        jFrameDemo = new JFrameDemo();
    }

    public JFrameDemo() {
//        images=new Image[1];
//        images[0]=new ImageIcon(JFrameDemo.class.getResource("com/example/demo/res/img/enemy_1_d.gif")).getImage();
        jb1 = new JButton("开始游戏");
        jb2 = new JButton("迷男");

        jl1=new JLabel("这个是啥");
        jl1.setIcon(new ImageIcon(JFrameDemo.class.getResource("/res/img/blast_7.gif")));


        jp1 = new JPanel();
        jp2 = new JPanel();

        //把按钮放在横幅上
        jp1.add(jb1);
        jp2.add(jb2);

        //设置横幅的位置
        this.add(jp1, BorderLayout.WEST);  // 放在左边
        this.add(jp2, BorderLayout.EAST);  //放在右边
        this.add(jl1);
//        this.setTitle("李维迷男");
        this.setSize(400, 200);
        this.setResizable(true);  ////设置窗体是否可以调整大小，参数为布尔值
        this.setLocation(200, 200);   //离显示屏上边缘200像素，里显示屏左边缘200像素
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  ////用户单击窗口的关闭按钮时程序执行的操作
        this.setVisible(true);  //这个应该是可视化的设置

        this.addWindowFocusListener(new WindowFocusListener() {  //注册一个窗体焦点的监听器
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("获取焦点,游戏继续");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("失去焦点,游戏暂停");
            }
        });

        //给按钮设置点击事件
        jb1.addActionListener(new ActionListener() { //本来是内部类,监听点击事件,但是 "开始游戏" 可以设置为匿名内部类,一次有效
            @Override
            public void actionPerformed(ActionEvent e) {
                if(live) {
                    Config config=new Config();
                    GameWindow gw = new GameWindow(Config.TITLE, Config.WIDTH, Config.HEIGHT, Config.FPS);
                    gw.start();
                    live=false;
                }
            }
        });
        //给同一个按钮设置两个不同的点击事件
        jb1.addActionListener(new CheckLive());

    }

    //内部类点击监听
    class CheckLive implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(new CheckLive2()).start();
        }
    //内部类线程
        class CheckLive2 implements Runnable {
            @Override
            public void run() {
                while (live) {
                    jFrameDemo.setTitle("坦克大战开战:" + (playTime++) + "s");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
