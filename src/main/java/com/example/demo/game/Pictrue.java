package com.example.demo.game;

public abstract class Pictrue {
	protected int x;//0
	protected int y;//64
	protected int width;//0
	protected int height;//0
	
	public Pictrue(int x,int y){//x = 0,y = 64;
		this.x = x;
		this.y = y;
	}
	
	public abstract void draw();// 绘制自己方法的方法,但是具体绘制什么东西不知道,就应该定义为抽象方法,抽象父类
		
	
}
