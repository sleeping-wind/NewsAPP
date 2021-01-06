package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 主页，显示新闻
 */
public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";

	// Model
	private List<News> mDataList;
	// View
	private ListView mListView;

	private MyAdapter mAdapter;

	// 自定义数据库助手类对象
	private MyHelperHistory mHelperHistory;
	private MyHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Data data = new Data();
		// data.getDataFromServer();
		initData();
		initAdapter();
		initView();
		mAdapter.notifyDataSetChanged();

	}

	private void initData() {
		MyApplication myApp = (MyApplication) getApplication();
		mDataList = myApp.getList();
	}

	private void initView() {
		img_news = (ImageView) findViewById(R.id.img_xinwen);
		img_select = (ImageView) findViewById(R.id.img_fenlei);
		img_favourite = (ImageView) findViewById(R.id.img_shoucang);
		img_mine = (ImageView) findViewById(R.id.img_wode);
		img_news.setOnClickListener(this);
		img_select.setOnClickListener(this);
		img_favourite.setOnClickListener(this);
		img_mine.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.news_list);
		mListView.setAdapter(mAdapter);
		// 点击看新闻 + 浏览记录保存
		mListView.setOnItemClickListener(new OnItemClickListener() {
			// 类似onClick
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String img = mDataList.get(position).getImage();
				String title = mDataList.get(position).getTitle();
				String author = mDataList.get(position).getAuthor();
				String date = mDataList.get(position).getDate();
				String url = mDataList.get(position).getUrl();
				String uniqueKey = mDataList.get(position).getUniqueKey();
				insertDataHis(img, title, author, date, url, uniqueKey);

				Intent intent = new Intent(MainActivity.this,
						NewsActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
		// 长按收藏
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(MainActivity.this, "收藏成功！", Toast.LENGTH_SHORT)
						.show();
				String img = mDataList.get(position).getImage();
				String title = mDataList.get(position).getTitle();
				String author = mDataList.get(position).getAuthor();
				String date = mDataList.get(position).getDate();
				String url = mDataList.get(position).getUrl();
				String uniqueKey = mDataList.get(position).getUniqueKey();
				insertData(img, title, author, date, url, uniqueKey);
				return true;
			}
		});
	}

	private void initAdapter() {
		mAdapter = new MyAdapter();
	}

	public class MyAdapter extends BaseAdapter {
		// mDataList的长度
		@Override
		public int getCount() {
			return mDataList.size();
		}

		// 取滑动列表某行的数据
		@Override
		public Object getItem(int position) {
			return mDataList.get(position);
		}

		// 返回滑动列表的id
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 一个滑动列表包含，一个图片 标题 作者 时间
			View layout = null;
			ImageView image = null;
			TextView textTitle = null;
			TextView textAuthor = null;
			TextView textDate = null;
			// listview滚动的时候快速设置值，重用，提高效率
			ViewHolder holder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(MainActivity.this);
				layout = inflater.inflate(R.layout.item_layout, parent, false);
				image = (ImageView) layout.findViewById(R.id.img1);
				textTitle = (TextView) layout.findViewById(R.id.text_title);
				textAuthor = (TextView) layout.findViewById(R.id.text_author);
				textDate = (TextView) layout.findViewById(R.id.text_date);
				holder = new ViewHolder(image, textTitle, textAuthor, textDate);
				layout.setTag(holder);
			} else {
				layout = convertView;
				// 标签赋给holder
				holder = (ViewHolder) layout.getTag();
				image = holder.vImage;
				textTitle = holder.vTitle;
				textAuthor = holder.vAuthor;
				textDate = holder.vDate;
			}
			// news中获取数据
			News news = mDataList.get(position);
			textTitle.setText(news.getTitle());
			textAuthor.setText(news.getAuthor());
			textDate.setText(news.getDate());
			// 显示图
			showImage(position, image);

			return layout;
		}

		// bean
		class ViewHolder {
			public ImageView vImage;
			public TextView vTitle;
			public TextView vAuthor;
			public TextView vDate;

			public ViewHolder(ImageView image, TextView title, TextView author,
					TextView date) {
				vImage = image;
				vTitle = title;
				vAuthor = author;
				vDate = date;
			}
		}

	}

	private void showImage(final int position, final ImageView imageView) {
		new Thread() {
			@Override
			public void run() {

				try {
					// 得到图像链接
					URL url = new URL(mDataList.get(position).getImage());
					Log.d(TAG, "URL url = " + url.toString());

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(10000);
					conn.setReadTimeout(10000);
					InputStream is = conn.getInputStream();
					Log.d(TAG, "InputStream is =" + is);
					// 位图转化
					final Bitmap bitmap = BitmapFactory.decodeStream(is);
					Log.d(TAG, "Bitmap bitmap = " + bitmap);

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							imageView.setImageBitmap(bitmap);
						}
					});

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private ImageView img_news, img_select, img_favourite, img_mine;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_fenlei:
			startActivity(new Intent(MainActivity.this, SelectActivity.class));
			break;
		case R.id.img_xinwen:
			startActivity(new Intent(MainActivity.this, MainActivity.class));
			break;
		case R.id.img_shoucang:
			startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
			break;
		case R.id.img_wode:
			startActivity(new Intent(MainActivity.this, MineActivity.class));
			break;
		}

	}

	// 收藏
	private void insertData(String image, String title, String author,
			String date, String url, String uniqueKey) {
		mHelper = new MyHelper(MainActivity.this, "userFavoriteNews.db", null,
				2);
		// 获得一个可写的数据库对象
		SQLiteDatabase db = mHelper.getWritableDatabase();
		// 准备一个要插入的数据对象
		ContentValues cv = new ContentValues();
		// 添加要插入的数据（如果有多列就多次添加）
		// 参数一：列名；参数二：数值
		cv.put("image", image);
		cv.put("title", title);
		cv.put("author", author);
		cv.put("date", date);
		cv.put("url", url);
		cv.put("uniquekey", uniqueKey);
		// 参数一：要插入的表名
		// 参数三：要插入的数据
		db.insert("fNews", null, cv);
		// 关闭数据库
		db.close();
	}

	// 历史记录
	private void insertDataHis(String image, String title, String author,
			String date, String url, String uniqueKey) {
		mHelperHistory = new MyHelperHistory(MainActivity.this,
				"userHistory.db", null, 2);
		// 获得一个可写的数据库对象
		SQLiteDatabase db = mHelperHistory.getWritableDatabase();
		// 准备一个要插入的数据对象
		ContentValues cv = new ContentValues();
		// 添加要插入的数据（如果有多列就多次添加）
		// 参数一：列名；参数二：数值
		cv.put("image", image);
		cv.put("title", title);
		cv.put("author", author);
		cv.put("date", date);
		cv.put("url", url);
		cv.put("uniquekey", uniqueKey);
		// 参数一：要插入的表名
		// 参数三：要插入的数据
		db.insert("History", null, cv);
		// 关闭数据库
		db.close();
	}
}
