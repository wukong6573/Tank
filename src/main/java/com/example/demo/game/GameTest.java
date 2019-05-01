package com.example.demo.game;

/**
 * @author dell
 * 5.写一个带有main方法的测试类,是所有程序的入口,所有程序都是从这里开始执行的
 */
public class GameTest{
	public static void main(String[] args) {
		GameWindow gw = new GameWindow(Config.TITLE, Config.WIDTH, Config.HEIGHT, Config.FPS);
		gw.start();
//		ArrayList<Integer> list=new ArrayList<>();
//		list.add(1);
//		list=new ArrayList<>();
//		list.add(3);
//
//		System.out.println(list);
	}
}
