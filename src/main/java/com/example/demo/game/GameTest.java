package com.example.demo.game;

import com.example.demo.util.PdfUtil;
import com.example.demo.util.XDocService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 * 5.写一个带有main方法的测试类,是所有程序的入口,所有程序都是从这里开始执行的
 */
public class GameTest{
	public static void main(String[] args) {
//		GameWindow gw = new GameWindow(Config.TITLE, Config.WIDTH, Config.HEIGHT, Config.FPS);
//		gw.start();

		Map<String,Object> map=new HashMap<>();
		map.put("echostr",666);
		System.out.println(map.toString());
	}
}
