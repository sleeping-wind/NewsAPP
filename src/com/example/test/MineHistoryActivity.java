package com.example.test;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 历史浏览记录。
 */
public class MineHistoryActivity extends Activity implements OnClickListener{
	private ImageView img_news, img_select, img_favourite, img_mine;
	private MyHelperHistory myHelperHistory;
	private TextView historyClear;
	
	// Model:模型层，即数据源（使用集合来存储）,内容为新闻的标题
	private ArrayList<String> mDataList;//存标题
	private ArrayList<String> mIdList;//存id
	private Vector<String> mUrlList; // 存储新闻连接
	private ListView mListView;
	
	// Model：模型层，即适配器
	private ArrayAdapter<String> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_history);
		Toast.makeText(MineHistoryActivity.this, "右上角有清空↗", Toast.LENGTH_SHORT).show();
		historyClear = (TextView) findViewById(R.id.history_clear);
		
		img_news = (ImageView) findViewById(R.id.img_xinwen);
		img_select = (ImageView) findViewById(R.id.img_fenlei);
		img_favourite = (ImageView) findViewById(R.id.img_shoucang);
		img_mine = (ImageView) findViewById(R.id.img_wode);
		img_news.setOnClickListener(this);
		img_select.setOnClickListener(this);
		img_favourite.setOnClickListener(this);
		img_mine.setOnClickListener(this);
		initData();
	}
	//最下方四个点击按钮
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_xinwen:
			startActivity(new Intent(MineHistoryActivity.this, MainActivity.class));
			break;
		case R.id.img_fenlei:
			startActivity(new Intent(MineHistoryActivity.this,
					SelectActivity.class));
			break;
		case R.id.img_wode:
			startActivity(new Intent(MineHistoryActivity.this, MineActivity.class));
			break;
		case R.id.img_shoucang:
			startActivity(new Intent(MineHistoryActivity.this, FavoriteActivity.class));
			break;
		}
	}
	
	private void initData() {
		new Thread() {
			public void run() {
				// 实例化数据源
				mDataList = new ArrayList<String>();
				mUrlList = new Vector<String>();
				mIdList = new ArrayList<String>();

				myHelperHistory = new MyHelperHistory(MineHistoryActivity.this,
						"userHistory.db", null, 2);
				// 获得可读的数据库对象
				SQLiteDatabase db = myHelperHistory.getReadableDatabase();
				// 调用查询的方法
				// 参数一：要查询的表，相当于FROM子句
				// 参数二：要查询的列，相当于SELECT子句
				// 参数三：查询的条件，相当于WHERE子句
				// 参数四：如果参数三种有?，表示参数三中的?数据
				// 参数五：相当于GROUP BY子句
				// 参数六：相当于HAVING子句
				// 参数七：相当于ORDER BY子句
				Cursor cursor = db.query("History", new String[] { "image",
						"title", "author", "date", "url", "uniquekey","id" },
						"id>=0", null, null, null, null);
				// 循环取出所有数据
				while (cursor.moveToNext() != false) {
					// 先把列名转换为列序号
					int indexImage = cursor.getColumnIndex("image");
					int indexTitle = cursor.getColumnIndex("title");
					int indexAuthor = cursor.getColumnIndex("author");
					int indexDate = cursor.getColumnIndex("date");
					int indexUrl = cursor.getColumnIndex("url");
					int indexUniqueKey = cursor.getColumnIndex("uniquekey");
					int indexId = cursor.getColumnIndex("id");
					
					// 通过序号获得值
					String image = cursor.getString(indexImage);
					String title = cursor.getString(indexTitle);
					String author = cursor.getString(indexAuthor);
					String date = cursor.getString(indexDate);
					String url = cursor.getString(indexUrl);
					String uniqueKey = cursor.getString(indexUniqueKey);
					String id = cursor.getString(indexId);

					mDataList.add(title);
					mUrlList.add(url);
					mIdList.add(id);
					runOnUiThread(new Runnable() {
						public void run() {
							initAdapter();
							initList();
						}
					});

				}
				// 关闭游标
				cursor.close();
				// 关闭数据库
				db.close();
			}
		}.start();
	}
	
	private void initAdapter() {
		// 参数一：当前的类名.this
		// 参数二：包含TextView的布局文件的ID
		// 参数三：数据源
		mAdapter = new ArrayAdapter<String>(MineHistoryActivity.this,
				R.layout.item_display, mDataList);
	}
	
	private void initList() {
		mListView = (ListView) findViewById(R.id.history_list);
		// 补充V和P的关联
		mListView.setAdapter(mAdapter);
		// 设置单个Item的点击事件监听器
		mListView.setOnItemClickListener(new OnItemClickListener() {

			// 参数一：点击的Item所在的容器控件对象
			// 参数二：单个Item布局的最外层的布局/控件
			// 参数三：点击的item的序号（重要）
			// 参数四：id，通常等同于参数三
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获得点击的这条新闻对应的连接
				String url = mUrlList.get(position);
				// 跳转传值
				Intent intent = new Intent(MineHistoryActivity.this,
						NewsActivity2.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});
		// 清空浏览记录
		historyClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteData();
				mDataList.clear();; // 删除数据
				mIdList.clear();
				mIdList.clear();
				Toast.makeText(MineHistoryActivity.this, "已清空", Toast.LENGTH_SHORT).show();
				mAdapter.notifyDataSetChanged(); // 刷新页面
			}
		});
	}
	
	// 数据库删除数据
		private void deleteData() {
	        myHelperHistory = new MyHelperHistory(MineHistoryActivity.this, "userHistory.db", null, 2);
	        //获得一个可写的数据库对象
	        SQLiteDatabase db = myHelperHistory.getWritableDatabase();
	        //删除数据
	        //参数一：要从哪个表中删除
	        //参数二：Where子句，即删除的条件
	        //参数三：如果参数二中有?，那么替换的值
	        db.delete("History", "id>=0", null);
	        
	        //关闭数据库
	        db.close();
	        /*// 展示消息
	        showToast("已执行删除操作！");*/
	    }
}
