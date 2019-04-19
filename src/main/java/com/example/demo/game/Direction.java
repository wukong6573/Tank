package com.example.demo.game;

/**
 * @author dell
 * //坦克移动,你作为一个坦克对象应该具有一个移动自己方法,move方法写在坦克类里面,
		//坦克图片移动,根据坦克不同的方向做不同坐标改变,关键是方向来自哪里?来自按键监听对应其方向,用枚举类表示方向
		//move方法已经写好了,在按键监听处通过坦克对象名点调用,move方法里面定义一个方向类型变量来接收,
		 枚举类每一个成员变量都是一个枚举类对象,方向类,方向类对象就是具体一个方向
 */
public enum Direction {
	UP,//static Direction UP = new Direction();
	DOWN,
	LEFT,
	RIGH
}
