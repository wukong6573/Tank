package com.example.demo.game;

/**
 * @author dell
 * 常量类,//由于上面的游戏标题,宽高,帧率这些东西以后还要用到,我就把这些东西抽取到一个常量类里面存起来,以后想通过类名.调用,开发技巧,先使用后创建,选中字母按ctr shif x变成大写
 */
public class Config {

	public static final String TITLE = "坦克大战";
	public static final int SIZE = 64;
	public static final int WIDTH = SIZE*12;
	public static final int HEIGHT = SIZE*8;
	public static final int FPS = 50;
	
}
