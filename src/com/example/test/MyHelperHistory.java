package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 同样数据库助手，我觉得还能优化[ 留下了不会SQLite的眼泪 ): ]
 */
public class MyHelperHistory extends SQLiteOpenHelper{

	public MyHelperHistory(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE History(id INTEGER PRIMARY KEY AUTOINCREMENT,image TEXT NOT NULL,title TEXT NOT NULL,author TEXT NOT NULL,date TEXT NOT NULL,url TEXT NOT NULL,uniquekey TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

}
