package com.maveric;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.contentprovider.FoodTrackerProvider;
import com.maveric.database.model.FoodTable;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.obj.type.DietDataOrganize;

public class DietTrackerActivity extends MavericBaseActiity {

	RelativeLayout selectBreakFast;
	RelativeLayout selectDinner;
	RelativeLayout selectLunch;
	RelativeLayout selectSnack;
	Button previousLog, saveDietData;

	TextView breakFastValue;
	TextView lunchValue;
	TextView dinnerValue;
	TextView snacksValue;

	DietDataOrganize maverickData;
	private int bColories = 0, lColories = 0, dColories = 0, sColories = 0,
			bCorbos = 0, lCorbos = 0, dCorbos = 0, sCorbos = 0, bFat = 0,
			lFat = 0, dFat = 0, sFat = 0, bProtien = 0, lProtien = 0,
			dProtien = 0, sProtien = 0;
	int width;
	Context ctx;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.diet_tracker_container);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();

		ctx = getApplicationContext();

		maverickData = new DietDataOrganize(ctx);

		selectBreakFast = (RelativeLayout) findViewById(R.id.selectbreakfast);
		selectDinner = (RelativeLayout) findViewById(R.id.selectdinner);
		selectLunch = (RelativeLayout) findViewById(R.id.selectlunch);
		selectSnack = (RelativeLayout) findViewById(R.id.selectsnack);

		previousLog = (Button) findViewById(R.id.diet_date_logs);

		saveDietData = (Button) findViewById(R.id.savediet);

		lunchValue = (TextView) findViewById(R.id.lunch);
		breakFastValue = (TextView) findViewById(R.id.breakfast);
		dinnerValue = (TextView) findViewById(R.id.dinner);
		snacksValue = (TextView) findViewById(R.id.snack);

		saveDietData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final ProgressDialog progressDialog = ProgressDialog.show(
						DietTrackerActivity.this, "Saving...",
						"Wait a few sec your data is saving");

				new Thread() {
					public void run() {
						try {

							Calendar c = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat(
									"dd-MM-yyyy");
							String cureentDate = format.format(c.getTime());

							Log.i("kumar" + this.getClass(), "date"
									+ cureentDate);
							ContentValues values = new ContentValues();

							values.put(FoodTrackerTable.Column.DATE,
									cureentDate);
							values.put(FoodTrackerTable.Column.FOOD_BREAKFAST,
									maverickData.getBreakFast());
							values.put(FoodTrackerTable.Column.FOOD_LUNCH,
									maverickData.getLunch());
							values.put(FoodTrackerTable.Column.FOOD_DINNER,
									maverickData.getDinner());
							values.put(FoodTrackerTable.Column.FOOD_SNACK,
									maverickData.getSnacks());
							values.put(FoodTrackerTable.Column.CALORIES,
									bColories + lColories + dColories
											+ sColories);
							values.put(FoodTrackerTable.Column.CARBOS, bCorbos
									+ lCorbos + dCorbos + sCorbos);
							values.put(FoodTrackerTable.Column.FAT, bFat + lFat
									+ dFat + sFat);
							values.put(FoodTrackerTable.Column.PROTIN, bProtien
									+ lProtien + dProtien + sProtien);
							getContentResolver()
									.insert(FoodTrackerProvider.INSERT_FOOD_DETAILS_URI,
											values);
							handler.sendEmptyMessage(0);
						} catch (Exception e) {
							if (progressDialog != null) {
								progressDialog.dismiss();
							}
							Log.i("kumar:" + this.getClass(),
									"error in sve data into workout table"
											+ e.getMessage(), e);
							DietTrackerActivity.this.finish();
						}
						progressDialog.dismiss();
					}
				}.start();

			}
		});

		previousLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(DietTrackerActivity.this,
						DietTrackerViewerActivity.class));

			}
		});

		selectBreakFast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("kumar", "click");
				foodInput("Breakfast");

			}
		});
		selectLunch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Lunch");

			}
		});
		selectDinner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Dinner");
			}
		});
		selectSnack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Snacks");
			}
		});

	}

	protected void foodInput(final String HeadMsg) {
		try {
			final Cursor foodCursor = managedQuery(ExceriseProvider.FOOD_URI,
					null, null, null, null);
			Log.i("mohan", "food cur count" + foodCursor.getCount());
			foodCursor.moveToFirst();
			Log.i("mohan",
					"food colories no"
							+ foodCursor.getString(foodCursor
									.getColumnIndex(FoodTable.Column.CARBOS)));
			if (foodCursor.getCount() > 0) {
				Log.i("kumar" + this.getClass(),
						"foodcursor" + foodCursor.getCount());
				final Dialog listDialog = new Dialog(DietTrackerActivity.this,
						R.style.PauseDialog);

				listDialog.setTitle(HeadMsg);
				WindowManager.LayoutParams transparentView = listDialog
						.getWindow().getAttributes();
				transparentView.alpha = 0.7f;

				listDialog.getWindow().setAttributes(transparentView);

				LayoutInflater inflater = (LayoutInflater) DietTrackerActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.alertlist, null, false);
				listDialog.setContentView(view);
				LayoutParams params = listDialog.getWindow().getAttributes();
				params.width = (int) (width / 1.5);
				listDialog.getWindow().setAttributes(
						(android.view.WindowManager.LayoutParams) params);
				listDialog.setCanceledOnTouchOutside(true);

				ListView list = (ListView) listDialog
						.findViewById(R.id.alertlist);

				list.setAdapter(new SimpleCursorAdapter(
						DietTrackerActivity.this,
						R.layout.data_select_input_cardatat, foodCursor,
						new String[] { FoodTable.Column.NAME },
						new int[] { R.id.titlename }));

				listDialog.show();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int id, long position) {
						String foodValue = foodCursor.getString(foodCursor
								.getColumnIndex(FoodTable.Column.NAME));
						int selectedCalories = foodCursor.getInt(foodCursor
								.getColumnIndex(FoodTable.Column.CALORIES));
						int selectedCorbos = foodCursor.getInt(foodCursor
								.getColumnIndex(FoodTable.Column.CARBOS));
						int selectedFat = foodCursor.getInt(foodCursor
								.getColumnIndex(FoodTable.Column.FAT));
						int selectedProtien = foodCursor.getInt(foodCursor
								.getColumnIndex(FoodTable.Column.PROTIN));
						Log.i("kumar:" + this.getClass(), "Input of foodValue:"
								+ foodValue);
						if (HeadMsg.equalsIgnoreCase("Breakfast")) {
							maverickData.setBreakFast(foodValue);
							breakFastValue.setText(foodValue);
							bColories = selectedCalories;
							bCorbos = selectedCorbos;
							bFat = selectedFat;
							bProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Lunch")) {
							maverickData.setLunch(foodValue);
							lunchValue.setText(foodValue);
							lColories = selectedCalories;
							lCorbos = selectedCorbos;
							lFat = selectedFat;
							lProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Dinner")) {
							maverickData.setDinner(foodValue);
							dinnerValue.setText(foodValue);
							dColories = selectedCalories;
							dCorbos = selectedCorbos;
							dFat = selectedFat;
							dProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Snacks")) {
							maverickData.setSnacks(foodValue);
							snacksValue.setText(foodValue);
							sColories = selectedCalories;
							sCorbos = selectedCorbos;
							sFat = selectedFat;
							sProtien = selectedProtien;
						} else {
							// TODO
						}
						setGoal();
						listDialog.dismiss();
						overridePendingTransition(R.anim.prev_slidein,
								R.anim.prev_slideout);
					}
				});
			} else {
				// TODO
			}

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(), "excerise input function failed."
					+ e.getMessage(), e);
		}

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				DietTrackerActivity.this.finish();
				break;
			}
		}

	};

	private void setGoal() {
		TextView calories_answer = (TextView) findViewById(R.id.calories_answer);
		TextView carbos_answer = (TextView) findViewById(R.id.Carbos_answer);
		TextView fat_answer = (TextView) findViewById(R.id.fat_answer);
		TextView protein_answer = (TextView) findViewById(R.id.protein_answer);

		calories_answer.setText(""
				+ (bColories + lColories + dColories + sColories));
		Log.d(getString(R.string.app_name), "calories = "
				+ (bColories + lColories + dColories + sColories));
		carbos_answer.setText("" + (bCorbos + lCorbos + dCorbos + sCorbos));
		fat_answer.setText("" + (bFat + lFat + dFat + sFat));
		protein_answer
				.setText("" + (bProtien + lProtien + dProtien + sProtien));

	}
}
