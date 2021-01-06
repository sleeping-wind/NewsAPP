package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 内含小彩蛋，多次点击尝试哦 :)
 */
public class MineAboutActivity extends Activity {
	private TextView version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_about);
		version = (TextView) findViewById(R.id.mine_version);

		version.setOnClickListener(new OnClickListener() {
			long[] mHits = new long[5]; // 需要监听几次点击事件数组的长度就为几

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将mHints数组内的所有元素左移一个位置
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				// 获得当前系统已经启动的时间
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();
				// 点击的时间差小于500毫秒就认 为是连续点击
				if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
					startActivity(new Intent(MineAboutActivity.this,
							MineVersionActivity.class));
				}
			}
		});
	}
}
