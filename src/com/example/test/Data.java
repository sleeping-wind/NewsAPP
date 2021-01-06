package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 抽出来的 获取数据的类
 */
public class Data {
	private static final String TAG = "Data";
	Vector<News> mDataList;
	public void setmDataList(Vector<News> mDataList) {
		this.mDataList = mDataList;
	}
	public Vector<News> getmDataList() {
		return mDataList;
	}
	// 从服务器获得数据
	public void getDataFromServer(String type) {
		// 老师：http://v.juhe.cn/toutiao/index?&key=5465c4c5d60f72c3d756a9f1a9b8437d
		// 王帅："http://v.juhe.cn/toutiao/index?type=" + type+
		// "&key=86db400f09903e18b4ff6fca56b1ec96";
		// 辉：key=06da0d507b9d43ff0862c30e9040989a
		// 陈梓函 1d5a6b1e7337fe07800d341b040a1cc1
		// 我：441402163a553aef092fb1df564460c3
		// static final
		final String URL = "http://v.juhe.cn/toutiao/index?type=" + type
				+ "&key=86db400f09903e18b4ff6fca56b1ec96";
		new Thread() {

			public void run() {
				try {
					URL url = new URL(URL);
					// 通过url连接
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(10000);
					connection.setReadTimeout(10000);
					// 在服务器接收数据
					InputStream is = connection.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);

					StringBuilder sb = new StringBuilder();

					String buffer = new String();

					while ((buffer = br.readLine()) != null) {
						sb.append(buffer); // 此时sb中保存了所有来自服务器的数据。
					}

					br.close();
					// 解析json
					parseJSON(sb);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}.start();
	}

	// 解析json，data是从服务器获取的数据
	private void parseJSON(CharSequence data)
			throws JSONException {
		Log.d(TAG, "-->parseJSON");
		JSONArray content = new JSONObject(data.toString()).getJSONObject(
				"result").getJSONArray("data");

		// Log.d(TAG, content+"");

		News n = null;
		String title, author, date, image, uniqueKey, url;
		for (int i = 0; i < content.length(); i++) {
			JSONObject newsItem = content.getJSONObject(i);
			Log.d(TAG, newsItem + "");
			title = newsItem.getString("title");
			author = newsItem.getString("author_name");
			date = newsItem.getString("date");
			image = newsItem.getString("thumbnail_pic_s");
			uniqueKey = newsItem.getString("uniquekey");
			url = newsItem.getString("url");
			// 通过构造函数，保存对象类型数据
			n = new News(title, author, date, image, uniqueKey, url);
			mDataList.add(n);
		}
	}
}
