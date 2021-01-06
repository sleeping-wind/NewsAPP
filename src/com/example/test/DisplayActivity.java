package com.example.test;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 收藏页面，的展示
 */
public class DisplayActivity extends Activity {
	private List<News> mDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		initData();
		// 不好意思，虽然这样不厚道，但是，时间急迫，代码重用 :)
		startActivity(new Intent(DisplayActivity.this,
				MainActivity.class));
	}

	private void initData() {
		MyApplication myApp = (MyApplication) getApplication();
		mDataList = myApp.getList();
		Log.d("DisplayActivity", mDataList.toString());
	}
}
