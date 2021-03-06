package com.maveric;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class Webview extends MavericBaseActiity {
	WebView webView;
	final Activity activity = this;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.webview);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Apppref app = new Apppref(context);
		Bundle web = getIntent().getExtras();

		/* check the condition for askquestion from ExportZone only */

		try {
			if (web.getString("askquestion").equals("askquestion")) {
				RelativeLayout logo = (RelativeLayout)findViewById(R.id.logo);
				logo.setVisibility(View.GONE);
				if (!isNetworkAvailable()) {
					toast(getString(R.string.NO_INTERNET_CONNECTION));
					this.finish();
				}
			}

		} catch (Exception e) {
			Log.i("kumar", "error im webview" + e.getMessage());
		}

		loding(web.getString("title"), 3000);
		String url = web.getString("url");
		Log.i("manikk", url);
		try {
			webView = (WebView) findViewById(R.id.webview);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					activity.setTitle("Loading...");
					activity.setProgress(progress * 100);

					if (progress == 100)
						activity.setTitle(R.string.app_name);
				}
			});
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public void onReceivedHttpAuthRequest(WebView view,
						HttpAuthHandler handler, String host, String realm) {
					handler.proceed(app.getUserNameOnly(),
							app.getPasswordonly());
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			Log.i("manikk", url);
			webView.loadUrl(url);

		} catch (Exception e) {
			Log.i("manikk", url);

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
