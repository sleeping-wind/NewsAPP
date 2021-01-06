package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 分类页面，继续看下去，很多有意思的自欺欺人的代码😳
 */
public class SelectActivity extends Activity implements OnClickListener {
	private ImageView img_news, img_select, img_favourite, img_mine;
	private ArrayList<String> mTextChann;
	private ArrayList<Integer> mImgChann;
	private ArrayList<HashMap<String, Object>> mDataChann;

	private GridView mChannGrid;

	private SimpleAdapter channAdapt;

	private int[] imgs = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);

		img_news = (ImageView) findViewById(R.id.img_xinwen);
		img_select = (ImageView) findViewById(R.id.img_fenlei);
		img_favourite = (ImageView) findViewById(R.id.img_shoucang);
		img_mine = (ImageView) findViewById(R.id.img_wode);
		img_news.setOnClickListener(this);
		img_select.setOnClickListener(this);
		img_favourite.setOnClickListener(this);
		img_mine.setOnClickListener(this);
		chanModel();
		channAdapt();
		channView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_xinwen:
			startActivity(new Intent(SelectActivity.this, MainActivity.class));
			break;
		case R.id.img_shoucang:
			startActivity(new Intent(SelectActivity.this,
					FavoriteActivity.class));
			break;
		case R.id.img_wode:
			startActivity(new Intent(SelectActivity.this, MineActivity.class));
			break;
		}

	}

	private void chanModel() {
		mTextChann = new ArrayList<String>();
		mImgChann = new ArrayList<Integer>();
		mDataChann = new ArrayList<HashMap<String, Object>>();

		mTextChann.add("头条");
		mTextChann.add("社会");
		mTextChann.add("国内");
		mTextChann.add("国际");
		mTextChann.add("娱乐");
		mTextChann.add("体育");
		mTextChann.add("军事");
		mTextChann.add("科技");
		mTextChann.add("财经");
		mTextChann.add("時尚");

		mImgChann.add(R.drawable.toutiao);
		mImgChann.add(R.drawable.shehui);
		mImgChann.add(R.drawable.guonei);
		mImgChann.add(R.drawable.guoji);
		mImgChann.add(R.drawable.yule);
		mImgChann.add(R.drawable.tiyu);
		mImgChann.add(R.drawable.junshi);
		mImgChann.add(R.drawable.keji);
		mImgChann.add(R.drawable.caijing);
		mImgChann.add(R.drawable.shishang);

		imgs = new int[] { R.drawable.toutiao, R.drawable.shehui,
				R.drawable.guonei, R.drawable.guoji, R.drawable.yule,
				R.drawable.tiyu, R.drawable.junshi, R.drawable.keji,
				R.drawable.caijing, R.drawable.shishang };

		for (int i = 0; i < mTextChann.size(); i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("img_chann", mImgChann.get(i));
			item.put("txt_chann", mTextChann.get(i));
			mDataChann.add(item);
		}

	}

	private void channAdapt() {

		String[] keys = new String[] { "img_chann", "txt_chann" };
		int[] ids = new int[] { R.id.img_channel, R.id.txt_channel };
		channAdapt = new SimpleAdapter(SelectActivity.this, mDataChann,
				R.layout.layout_select, keys, ids);

	}

	private void channView() {
		
		mChannGrid = (GridView) findViewById(R.id.gridview_channel);
		mChannGrid.setAdapter(channAdapt);

		mChannGrid.setOnItemClickListener(new OnItemClickListener() {
			String type = null;
			// News对象类型
			Vector<News> mDataList;
			@Override
			public void onItemClick(AdapterView<?> partent, View view,
					int position, long id) {
				MyApplication app = (MyApplication) getApplication();
				mDataList = app.getList();
				mDataList.clear();
				Data data = new Data();
				switch (position) {
				case 0:// toutiao
					data.setmDataList(mDataList);
					data.getDataFromServer("toutiao");
					Log.d("Get", mDataList.toString());
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 1:// shehui
					data.setmDataList(mDataList);
					data.getDataFromServer("shehui");
					Log.d("Get", mDataList.toString());
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 2:// guonei
					data.setmDataList(mDataList);
					data.getDataFromServer("guonei");
					Log.d("Get", mDataList.toString());
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 3:// guoji
					data.setmDataList(mDataList);
					data.getDataFromServer("guoji");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 4:// yule
					data.setmDataList(mDataList);
					data.getDataFromServer("yule");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 5:// tiyu
					data.setmDataList(mDataList);
					data.getDataFromServer("tiyu");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 6:// junshi
					data.setmDataList(mDataList);
					data.getDataFromServer("junshi");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 7:// keji
					data.setmDataList(mDataList);
					data.getDataFromServer("keji");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 8:// caijing
					data.setmDataList(mDataList);
					data.getDataFromServer("caijing");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				case 9:// shishang
					data.setmDataList(mDataList);
					data.getDataFromServer("shishang");
					startActivity(new Intent(SelectActivity.this,
							DisplayActivity.class));
					break;
				}
			}
		});
	}
}
