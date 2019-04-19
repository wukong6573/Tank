package com.example.demo.game;

/**
 * @author dell
 * 5.写一个带有main方法的测试类,是所有程序的入口,所有程序都是从这里开始执行的
 */
public class GameTest {
	public static void main(String[] args) {
		//6.写一个游戏窗体类去继承工具类里面Window抽象父类,继承里面非静态start方法去开启一个游戏窗体,开始游戏
//		GameWindow gw = new GameWindow("坦克大战", 64*18, 64*10, 50);//50游戏每秒刷新频率,帧率,每秒刷新50次,gw
		//由于上面的游戏标题,宽高,帧率这些东西以后还要用到,我就把这些东西抽取到一个常量类里面存起来,以后想通过类名.调用,开发技巧,先使用后创建,选中字母按ctr shif x变成大写
		GameWindow gw = new GameWindow(Config.TITLE, Config.WIDTH, Config.HEIGHT, Config.FPS);
		gw.start();//调用start方法之后会调用游戏窗体类GameWindow重写抽象方法,比如onCreate()方法..等等
	}
}
