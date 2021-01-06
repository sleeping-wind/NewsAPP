package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 数据库助手类
 */
public class MyHelper extends SQLiteOpenHelper {
	private static final String TAG = "MyHelper";

	public MyHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
		Log.d(TAG, "MyHelper->Constructor");
	}

	/**
	 * 数据库创建的回调
	 * 
	 * @param db
	 *            新创建的数据库对象
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		// 创建一张表
		db.execSQL("CREATE TABLE fNews(id INTEGER PRIMARY KEY AUTOINCREMENT,image TEXT NOT NULL,title TEXT NOT NULL,author TEXT NOT NULL,date TEXT NOT NULL,url TEXT NOT NULL,uniquekey TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade：" + oldVersion + "->" + newVersion);
	}

}
