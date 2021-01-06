package com.example.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/**
 * @author Bruce.vvu
 * @date 2021-01-06
 * @Description 新闻显示页面 :)
 */
public class NewsActivity extends Activity {

	private WebView mWebView;

	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_webview);

		initView();
		showNews();
	}

	private void initView() {
		// 进度条
		mDialog = new ProgressDialog(this);
		mDialog.setMessage("正在加载中");

		mWebView = (WebView) findViewById(R.id.web_view);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				mDialog.show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if (mDialog.isShowing())
					mDialog.dismiss();
			}

		});
		mWebView.getSettings().setJavaScriptEnabled(true);

	}

	private void showNews() {
		// 接受上个页面传来的position,来获取url
		Intent intent = getIntent();
		int position = intent.getIntExtra("position", -1);
		if (position == -1)
			return;

		MyApplication myApp = (MyApplication) getApplication();
		String url = myApp.getList().get(position).getUrl();

		mWebView.loadUrl(url);
	}

}
