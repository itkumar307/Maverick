package com.maveric.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.maveric.R;

public class MSWSClient {
	private Context ctx;

	private static final String DATA = "data";
	private static final String CONTENT_TYPE_JSON = "application/json";
	public Boolean isPostSuccessfully = false;

	public MSWSClient(String url, Context ctx, JSONArray jArray) {
		JSONObject jObject = new JSONObject();
		this.ctx = ctx;
		if (jArray != null) {
			try {
				HttpPost request = new HttpPost(url);
				jObject.put(DATA, jArray);
				StringEntity se = new StringEntity(jObject.toString(), "UTF-8");
				request.setEntity(se);
				request.setHeader("Accept", CONTENT_TYPE_JSON);
				request.setHeader("Content-type", CONTENT_TYPE_JSON);
				Log.d(ctx.getString(R.string.app_name),
						"success fully post to server" + jObject.toString());
				getHttpResponse(request);
			} catch (JSONException e) {
				Log.e(ctx.getString(R.string.app_name),
						"MSWSClient Json post failed " + e.getMessage(), e);
			} catch (UnsupportedEncodingException e) {
				Log.e(ctx.getString(R.string.app_name),
						"MSWSClient Json post failed UnsupportedEncodingException "
								+ e.getMessage(), e);
			}
		}
	}

	private void getHttpResponse(HttpPost request) {
		Log.d(ctx.getString(R.string.app_name), "getHttpResponseEnter"
				+ (request != null));
		InputStream is = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		try {
			Log.d(ctx.getString(R.string.app_name), "requet"
					+ (request != null));
			if (request != null) {
				response = httpclient.execute(request);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			Log.e(ctx.getString(R.string.app_name),
					"getResponse failure " + e.getMessage(), e);
			return;
		} catch (ConnectException e) {
			Log.e(ctx.getString(R.string.app_name),
					"getResponse read failure ConnectException "
							+ e.getMessage(), e);
			return;
		} catch (IOException e) {
			Log.e(ctx.getString(R.string.app_name),
					"getResponse read failure IOException " + e.getMessage(), e);
			return;
		}

		try {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			is.close();

			Log.d(ctx.getString(R.string.app_name),
					"API response : " + str.toString());
			if (str.toString().trim().equalsIgnoreCase("suecces")) {
				isPostSuccessfully = true;
			}
		} catch (Exception ex) {

			Log.e(ctx.getString(R.string.app_name),
					"API failure : " + ex.getMessage(), ex);
		}

	}
}
