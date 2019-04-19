package com.example.demo.game;

import com.example.demo.config.GetPath;
import com.example.demo.util.DrawUtils;

import java.io.IOException;

/**
 * @author dell
 * //画一堆墙水草铁等图片出来,先画墙出来,不会,模仿子弹集合,搞个墙集合,添加墙对象,然后在不但刷新方法里面遍历这个墙集合拿到每一个墙对象点调用墙类里面的draw方法绘制出来
		//墙类不会写,模仿坦克类,图片,坐标和宽高,绘制自己方法,把墙图片,墙类,墙对象看成是一样的东西
		 *  代码复用性太差了,其实墙水草铁都是图片,抽取一个图片父类出来,让墙水草铁都去继承它,这样墙水草铁就成为图片,是图片就可以添加到图片集合统一存起来,
	那就不要搞墙水草铁等集合,然后在不但刷新方法里面遍历这个图片集合拿到每一个图片对象点调里面的draw方法绘制出来,把每一个图片画出来,墙水草铁都就绘制出来了
 */
public class Water extends Pictrue implements Obstructer{

	public Water(int x,int y){//x = 0,y = 64;
		super(x, y);
//		this.x = x;
//		this.y = y;
		//看我发的图,知道子弹坐标涉及到坦克坐标和宽高,子弹宽高,根据不同坦克方向(子弹方向)前提要把条件准备好,根据我发的图算出哪个坐标值给子弹类坐标进行赋值
		//刚刚调用坦克shot方法子弹类对象,调用到子弹类的构造方法,我就跑到子弹类够方法里面去了,在调用的时候我把坦克对象名this传入到子类类的构造方法里面,就可以拿到坦克对象名
		//工具类绘制
		try {
			int[] size = DrawUtils.getSize(GetPath.PATH+"\\img\\water.gif");
			this.width = size[0];
			this.height = size[1];
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void draw(){
		//想绘制画一张坦克图片在我游戏窗体上,很简单,调用我绘制工具类DrawUtils.draw(图片路径,图片坐标);
		try {
			DrawUtils.draw(GetPath.PATH+"\\img\\water.gif", x, y);//0,64
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}//画图要不断绘制画,要在不断刷新方法里面调用onDisplayUpdate,刷新,人的肉眼才能持续看到
	}
	
}
