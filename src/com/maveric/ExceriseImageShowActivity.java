package com.maveric;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class ExceriseImageShowActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.imagelist);

	}

	Context ctx;
	Spinner exceriseSpinner;
	WebView exceriseImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		exceriseSpinner = (Spinner) findViewById(R.id.excerisespinner);
		exceriseImage = (WebView) findViewById(R.id.exceriseimag);
		exceriseImage.getSettings().setJavaScriptEnabled(true);
		exceriseImage.getSettings().setBuiltInZoomControls(true);
		
		ctx=getApplicationContext();
		
		// exceriseImage.getSettings().setJavaScriptEnabled(true);
		exceriseSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> list, View arg1, int pos,
					long arg3) {
				try {
					String data = list.getItemAtPosition(pos).toString();
					Resources r = getResources();
					int stringId = r.getIdentifier(data, "string", ctx.getPackageName());
					String des=r.getString(stringId).replaceAll("break", "<br>");

					Log.i("kumar","NEW"+data);					
					if (!data.equalsIgnoreCase("-- Please select --")) {
						
						if(!IsExitsFile(data+".gif")){
							data="Image-unavailable";
							
						}

						Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
								.getDefaultDisplay();
						
//						int width = display.getWidth()+50;
//						int height = display.getHeight()+50;

//						String temp = "<html>"
//								+ "<body style=\"text-align: center;  vertical-align: center;\">"
//								+ " <div >"
//								+ "<img src='file:///android_asset/excerisegif/"
//								+ data + ".gif' +  align='middle' />"+"<H1 align='left'>" + des + "</H1>"
//								+ "</div></body></html>";
						
						String temp = "<html>"
								+ "<body>"
								+ " <div  style=\"text-align: center;  vertical-align: center;\" >"
								+ "<img src='file:///android_asset/excerisegif/"
								+ data + ".gif' +  align='middle' /> </div>"+"<div> <p style='padding:5px' align='left' > <b>" 
								+ des + "</b> </p>"+" <div style='height:70px'> </div>"
								+ "</div></body></html>";

						exceriseImage.loadDataWithBaseURL(null, temp,
								"text/html", "UTF-8", null);
					}
				} catch (Exception e) {
					Log.e("kumar", "error in webview" + e.getMessage());
				}

			}

			private boolean  IsExitsFile(String filename) {
				
				
				  boolean bAssetOk = false;
				    try {
				        InputStream stream = context.getAssets().open("excerisegif/"+filename);
				        stream.close();
				        bAssetOk = true;
				    } catch (FileNotFoundException e) {
				    	bAssetOk = false;
				        Log.w("IOUtilities", "assetExists failed: "+e.toString());
				    } catch (IOException e) {
				    	bAssetOk = false;
				        Log.w("IOUtilities", "assetExists failed: "+e.toString());
				    }
				    return bAssetOk;		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}
}
