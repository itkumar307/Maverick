package com.maveric;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

enum Warm {

	STRESSTEXT1(1, R.string.warmup1), STRESSTEXT2(2, R.string.warmup2), STRESSTEXT3(
			3, R.string.warmup3), STRESSTEXT4(4, R.string.warmup4);

	Integer str;
	Integer index;

	Warm(Integer index, Integer str) {
		this.str = str;
		this.index = index;
	}

	public Integer getString() {
		return str;
	}

	public Integer getIndex() {
		return index;
	}

	public static Warm getStringByIndex(Integer index) {
		for (Warm s : Warm.values()) {
			if (s.getIndex().equals(index)) {
				return s;
			}
		}
		return null;
	}
}

public class StaticWarmUp extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.static_page);

	}

	ImageView image;
	ImageView next;
	TextView staticText;
	Context ctx;
	int i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = this.getApplicationContext();
		image = (ImageView) findViewById(R.id.topimage);
		next = (ImageView) findViewById(R.id.nextpg);
		staticText = (TextView) findViewById(R.id.loadstring);
		staticText.setText(""
				+ ctx.getResources().getString(Warm.STRESSTEXT1.getString()));
		image.setBackgroundDrawable(ctx.getResources().getDrawable(
				R.drawable.warmup));

		i = 2;
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (i < 5) {
						Warm s = Warm.getStringByIndex(i);
						staticText.setText(""
								+ ctx.getResources().getString(s.getString()));
						if (i == 4) {
							next.setVisibility(View.GONE);
						}
						i++;
					}

				} catch (Exception e) {
					Log.i("kumar", "errorinpage" + e.getMessage(), e);
				}

			}
		});

	}
}