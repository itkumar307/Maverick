package com.maveric;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.maveric.util.MUpdateClient;

public class MavericHomeActivity extends MavericBaseActiity {

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.home);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context ctx;
		ctx = this.getApplicationContext();
		TextView bmiText = (TextView) findViewById(R.id.calories_born_answer);
		// TextView waterReq =(TextView) findViewById(R.id.diet_answer);
		Apppref appPref = new Apppref(context);
		bmiText.setText(appPref.getRecWeight() + " kg");
		// waterReq.setText(appPref.getRecWater()+" L");

		/*
		 * check new updates
		 */
		try {
			MUpdateClient mc = new MUpdateClient();
			mc.insertNewDataIfAvailabe(ctx);
		} catch (Exception e) {
			Log.i("MavericHomeActivity", "Error in update app");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (exitOnBackButton() && keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			displayExitAlert();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected boolean exitOnBackButton() {
		return true;
	}

	private void displayExitAlert() {
		AlertDialog dialog = new AlertDialog.Builder(MavericHomeActivity.this)
				.create();
		dialog.setTitle(R.string.DIALOGMESSAGE);
		dialog.setButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		dialog.setButton2("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				MavericHomeActivity.this.closeOptionsMenu();
			}
		});
		dialog.show();
	}
	
}