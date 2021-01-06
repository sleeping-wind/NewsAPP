package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 彩蛋即将出现了，嘿嘿 :)
 */
public class MineVersionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		Toast.makeText(MineVersionActivity.this, "祝大家新年快乐😀", Toast.LENGTH_LONG).show();
	}
}
