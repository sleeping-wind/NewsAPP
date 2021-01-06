package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 我的页面，代码很冗余，前端师傅太给力，后期一定优化
 */
public class MineActivity extends Activity implements OnClickListener {
	private ImageView img_news, img_select, img_favourite, img_mine;
	private TextView setting1,setting2,mine_fav1,
	mine_fav2,mine_history1,mine_history2,mine_about1,mine_about2,mine_login;
	private ImageView setting0,mine_fav0,mine_history0,mine_about0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
		setting1 = (TextView) findViewById(R.id.mine_setting1);
		setting1.setOnClickListener(this);
		setting2 = (TextView) findViewById(R.id.mine_setting2);
		setting2.setOnClickListener(this);
		setting0 = (ImageView) findViewById(R.id.mine_setting0);
		setting0.setOnClickListener(this);
		mine_fav0 = (ImageView) findViewById(R.id.mine_fav0);
		mine_fav1 = (TextView) findViewById(R.id.mine_fav1);
		mine_fav2 = (TextView) findViewById(R.id.mine_fav2);
		mine_fav0.setOnClickListener(this);
		mine_fav1.setOnClickListener(this);
		mine_fav2.setOnClickListener(this);
		mine_history0 = (ImageView) findViewById(R.id.mine_history0);
		mine_history1 = (TextView) findViewById(R.id.mine_history1);
		mine_history2 = (TextView) findViewById(R.id.mine_history2);
		mine_history0.setOnClickListener(this);
		mine_history1.setOnClickListener(this);
		mine_history2.setOnClickListener(this);
		mine_about0 = (ImageView) findViewById(R.id.mine_about0);
		mine_about1 = (TextView) findViewById(R.id.mine_about1);
		mine_about2 = (TextView) findViewById(R.id.mine_about2);
		mine_about0.setOnClickListener(this);
		mine_about1.setOnClickListener(this);
		mine_about2.setOnClickListener(this);
		
		mine_login = (TextView) findViewById(R.id.mine_login);
		mine_login.setOnClickListener(this);
		
		
		img_news = (ImageView) findViewById(R.id.img_xinwen);
		img_select = (ImageView) findViewById(R.id.img_fenlei);
		img_favourite = (ImageView) findViewById(R.id.img_shoucang);
		img_mine = (ImageView) findViewById(R.id.img_wode);
		
		img_news.setOnClickListener(this);
		img_select.setOnClickListener(this);
		img_favourite.setOnClickListener(this);
		img_mine.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_xinwen:
			startActivity(new Intent(MineActivity.this, MainActivity.class));
			break;
		case R.id.img_fenlei:
			startActivity(new Intent(MineActivity.this, SelectActivity.class));
			break;
		case R.id.img_shoucang:
			startActivity(new Intent(MineActivity.this, FavoriteActivity.class));
			break;
		case R.id.mine_setting1:
		case R.id.mine_setting2:
		case R.id.mine_setting0:
			startActivity(new Intent(MineActivity.this, MineSettingActivity.class));
			break;
		case R.id.mine_fav0:
		case R.id.mine_fav1:
		case R.id.mine_fav2:
			startActivity(new Intent(MineActivity.this, FavoriteActivity.class));
			break;
		case R.id.mine_history0:
		case R.id.mine_history1:
		case R.id.mine_history2:
			startActivity(new Intent(MineActivity.this, MineHistoryActivity.class));
			break;
		case R.id.mine_about0:
		case R.id.mine_about1:
		case R.id.mine_about2:
			startActivity(new Intent(MineActivity.this, MineAboutActivity.class));
			break;
		case R.id.mine_login:
			startActivity(new Intent(MineActivity.this, MineLoginActivity.class));
			break;
		}

	}
}
