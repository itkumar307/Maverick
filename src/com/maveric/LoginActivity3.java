package com.maveric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class LoginActivity3  extends MavericBaseActiity {
	
	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.login_screen3);

	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(context, LoginActivity4.class);
				startActivity(nextScreen);

			}
		});
    }
}
