package com.example.test;

import java.util.Vector;

import android.app.Application;
import android.util.Log;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 所有数据，存到application
 */
public class MyApplication extends Application{
	
	//总数据源
	private static final Vector<News> appData = new Vector<News>();
	//返回数据源对象给每个Activity来使用
	public Vector<News> getList(){
		return appData;
	}

}
