package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 这就是首页了，欢迎页面，Welcome！ :)
 */
public class WelcomeActivity extends Activity {
	private int flag = 0; // 判断跳转
	private static final String TAG = "WelcomeActivity";
	private String type = "top";
	// 倒计时文本
	private TextView text;
	private ImageView imgWelcome;
	// 跳过
	private TextView textJump;
	// News对象类型
	private Vector<News> mDataList;
	// 自定义数据库助手类对象
	private MyHelper mHelper;
	private MyHelperHistory mHelperHistory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		// 绑定id
		text = (TextView) findViewById(R.id.text_count_down);
		imgWelcome = (ImageView) findViewById(R.id.img_welcome);
		textJump = (TextView) findViewById(R.id.btn_jump);
		Log.d(TAG, "-->onCreate");
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		String flag = sp.getString("flag", "flag");
		// 倒计时
		countDown(flag);
		// 跳过广告
		jump();

		MyApplication myApp = (MyApplication) getApplication();
		mDataList = myApp.getList();
		Data data = new Data();
		data.setmDataList(mDataList);
		data.getDataFromServer("top");

	}

	// 倒计时，创建数据库
	private void countDown(final String s) {
		new Thread() {
			public void run() {
				if ("flag".equals(s)) {
					// SQLite数据库会生成一个数据库文件，扩展名是.db或.db3
					mHelper = new MyHelper(WelcomeActivity.this,
							"userFavoriteNews.db", null, 1);
					mHelperHistory = new MyHelperHistory(WelcomeActivity.this,
							"userHistory.db", null, 1);
					// 目的只是为了创建出数据库文件和表，所以获得一个只读的数据库对象后立刻关闭
					mHelper.getReadableDatabase().close();
					mHelperHistory.getReadableDatabase().close();
					SharedPreferences sp = getPreferences(MODE_PRIVATE);
					// 进入编辑状态，获得编辑器对象
					Editor editor = sp.edit();
					// 添加数据存储（键值对）
					editor.putString("flag", "a");
					// 提交
					editor.commit();
				}

				// 倒计时
				for (int i = 10; i >= 0; i--) {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// 创建一个专门用于赋值的变量
					final int count = i;
					// 切换到主线程中执行
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							text.setText("广告还剩：" + count + "s");
							if (count == 0 && flag == 0) {
								Intent intent = new Intent(
										WelcomeActivity.this,
										MainActivity.class); // 页面跳转
																// MainActivity.java
								startActivity(intent);
								Toast.makeText(WelcomeActivity.this, "长按新闻可收藏哦: )", Toast.LENGTH_SHORT).show();
								finish();
							}
						}
					});
				}
			};
		}.start();
	}

	// 跳过广告
	public void jump() {
		textJump.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				flag++;
				finish();
				Toast.makeText(WelcomeActivity.this, "长按新闻可收藏哦: )", Toast.LENGTH_SHORT).show();
			}
		});
		imgWelcome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri.parse("http://taxq.sdust.edu.cn/"));
				startActivity(intent);
			}
		});
	}
}
