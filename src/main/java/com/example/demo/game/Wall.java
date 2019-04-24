package com.example.demo.game;

import com.example.demo.util.DrawUtils;

import java.io.IOException;

/**
 * @author dell
 * //画一堆墙水草铁等图片出来,先画墙出来,不会,模仿子弹集合,搞个墙集合,添加墙对象,然后在不但刷新方法里面遍历这个墙集合拿到每一个墙对象点调用墙类里面的draw方法绘制出来
		//墙类不会写,模仿坦克类,图片,坐标和宽高,绘制自己方法,把墙图片,墙类,墙对象看成是一样的东西
		 *  代码复用性太差了,其实墙水草铁都是图片,抽取一个图片父类出来,让墙水草铁都去继承它,这样墙水草铁就成为图片,是图片就可以添加到图片集合统一存起来,
	那就不要搞墙水草铁等集合,然后在不但刷新方法里面遍历这个图片集合拿到每一个图片对象点调里面的draw方法绘制出来,把每一个图片画出来,墙水草铁都就绘制出来了
 */
public class Wall extends Pictrue {

	public Wall(int x,int y){//x = 0,y = 64;
		super(x,y);
		//工具类绘制
		try {
			int[] size = DrawUtils.getSize(Bullet.class.getResource("/res/img/wall.gif").getPath());
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
			DrawUtils.draw(Bullet.class.getResource("/res/img/wall.gif").getPath(), x, y);//0,64
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}//画图要不断绘制画,要在不断刷新方法里面调用onDisplayUpdate,刷新,人的肉眼才能持续看到
	}
	
}
