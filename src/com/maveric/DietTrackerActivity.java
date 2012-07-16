package com.maveric;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
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

	RelativeLayout selectBreakFast1, selectBreakFast2, selectBreakFast3;
	RelativeLayout selectDinner1, selectDinner2, selectDinner3;
	RelativeLayout selectLunch1, selectLunch2, selectLunch3;
	RelativeLayout selectSnack1, selectSnack2, selectSnack3;
	Button previousLog, saveDietData;

	TextView breakFastValue1, breakFastValue2, breakFastValue3;
	TextView lunchValue1, lunchValue2, lunchValue3;
	TextView dinnerValue1, dinnerValue2, dinnerValue3;
	TextView snacksValue1, snacksValue2, snacksValue3;

	TextView breakFastCount1, breakFastCount2, breakFastCount3;
	TextView lunchCount1, lunchCount2, lunchCount3;
	TextView dinnerCount1, dinnerCount2, dinnerCount3;
	TextView snacksCount1, snacksCount2, snacksCount3;

	String cureentDate;

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

		previousLog = (Button) findViewById(R.id.diet_date_logs);
		saveDietData = (Button) findViewById(R.id.savediet);

		/*
		 * Breakfast xml objects
		 */
		// selectDinner,selectLunch,selectSnack

		selectBreakFast1 = (RelativeLayout) findViewById(R.id.selectbreakitem1);
		selectBreakFast2 = (RelativeLayout) findViewById(R.id.selectbreakitem2);
		selectBreakFast3 = (RelativeLayout) findViewById(R.id.selectbreakitem3);

		breakFastValue1 = (TextView) findViewById(R.id.breakfastselecteditem1);
		breakFastValue2 = (TextView) findViewById(R.id.breakfastselecteditem2);
		breakFastValue3 = (TextView) findViewById(R.id.breakfastselecteditem3);

		breakFastCount1 = (TextView) findViewById(R.id.breakfastselecteditemcount1);
		breakFastCount2 = (TextView) findViewById(R.id.breakfastselecteditemcount2);
		breakFastCount3 = (TextView) findViewById(R.id.breakfastselecteditemcount3);

		/*
		 * lunch xml objects
		 */

		selectLunch1 = (RelativeLayout) findViewById(R.id.selectlunchitem1);
		selectLunch2 = (RelativeLayout) findViewById(R.id.selectlunchitem2);
		selectLunch3 = (RelativeLayout) findViewById(R.id.selectlunchitem3);

		lunchValue1 = (TextView) findViewById(R.id.lunchselecteditem1);
		lunchValue2 = (TextView) findViewById(R.id.lunchselecteditem2);
		lunchValue3 = (TextView) findViewById(R.id.lunchselecteditem3);

		lunchCount1 = (TextView) findViewById(R.id.lunchselecteditemcount1);
		lunchCount2 = (TextView) findViewById(R.id.lunchselecteditemcount2);
		lunchCount3 = (TextView) findViewById(R.id.lunchselecteditemcount3);

		/*
		 * dinner xml objects
		 */

		selectDinner1 = (RelativeLayout) findViewById(R.id.selectdinneritem1);
		selectDinner2 = (RelativeLayout) findViewById(R.id.selectdinneritem2);
		selectDinner3 = (RelativeLayout) findViewById(R.id.selectdinneritem3);

		dinnerValue1 = (TextView) findViewById(R.id.dinnerselecteditem1);
		dinnerValue2 = (TextView) findViewById(R.id.dinnerselecteditem2);
		dinnerValue3 = (TextView) findViewById(R.id.dinnerselecteditem3);

		dinnerCount1 = (TextView) findViewById(R.id.dinnerselecteditemcount1);
		dinnerCount2 = (TextView) findViewById(R.id.dinnerselecteditemcount2);
		dinnerCount3 = (TextView) findViewById(R.id.dinnerselecteditemcount3);

		/*
		 * snacks xml objects
		 */

		selectSnack1 = (RelativeLayout) findViewById(R.id.selectsnackitem1);
		selectSnack2 = (RelativeLayout) findViewById(R.id.selectsnackitem2);
		selectSnack3 = (RelativeLayout) findViewById(R.id.selectsnackitem3);

		snacksValue1 = (TextView) findViewById(R.id.snackselecteditem1);
		snacksValue2 = (TextView) findViewById(R.id.snackselecteditem2);
		snacksValue3 = (TextView) findViewById(R.id.snackselecteditem3);

		snacksCount1 = (TextView) findViewById(R.id.snackselecteditemcount1);
		snacksCount2 = (TextView) findViewById(R.id.snackselecteditemcount2);
		snacksCount3 = (TextView) findViewById(R.id.snackselecteditemcount3);

		// get date
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			cureentDate = format.format(c.getTime());
			Log.i("kumar" + this.getClass(), "date" + cureentDate);
		} catch (Exception e1) {
			Log.i("kumar", "error in get date from calender" + e1.getMessage());
		}
		/*
		 * this for test condition to check next date
		 */
		// cureentDate="17-07-2012";

		getLastData();

		saveDietData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final ProgressDialog progressDialog = ProgressDialog.show(
						DietTrackerActivity.this, "Saving...",
						"Wait a few sec your data is saving");

				new Thread() {
					public void run() {
						try {

							Log.i("kumar", "bf" + maverickData.getBreakFast1()
									+ maverickData.getBreakFastCount1()
									+ maverickData.getBreakFast2()
									+ maverickData.getBreakFastCount2()
									+ maverickData.getBreakFast3()
									+ maverickData.getBreakFastCount3());

							Log.i("kumar",
									"lunch" + maverickData.getDinner1()
											+ maverickData.getLunchCount1()
											+ maverickData.getLunch2()
											+ maverickData.getLunchCount2()
											+ maverickData.getLunch3()
											+ maverickData.getLunchCount3());

							Log.i("kumar",
									"dinner" + maverickData.getDinner1()
											+ maverickData.getDinnerCount1()
											+ maverickData.getDinner2()
											+ maverickData.getDinnerCount2()
											+ maverickData.getDinner3()
											+ maverickData.getDinnerCount3());
							Log.i("kumar",
									"snack" + maverickData.getSnacks1()
											+ maverickData.getSnacksCount1()
											+ maverickData.getSnacks2()
											+ maverickData.getSnacksCount2()
											+ maverickData.getDinner3()
											+ maverickData.getSnacksCount3());

							String breakFastCollection = maverickData
									.getBreakFast1()
									+ ":"
									+ maverickData.getBreakFastCount1()
									+ ","
									+ maverickData.getBreakFast2()
									+ ":"
									+ maverickData.getBreakFastCount2()
									+ ","
									+ maverickData.getBreakFast3()
									+ ":"
									+ maverickData.getBreakFastCount3();

							String lunchCollection = maverickData.getLunch1()
									+ ":" + maverickData.getLunchCount1() + ","
									+ maverickData.getBreakFast2() + ":"
									+ maverickData.getLunchCount2() + ","
									+ maverickData.getLunch3() + ":"
									+ maverickData.getLunchCount3();

							String dinnerCollection = maverickData.getDinner1()
									+ ":" + maverickData.getDinnerCount1()
									+ "," + maverickData.getDinner2() + ":"
									+ maverickData.getDinnerCount2() + ","
									+ maverickData.getDinner3() + ":"
									+ maverickData.getDinnerCount3();

							String snacksCollection = maverickData.getSnacks1()
									+ ":" + maverickData.getSnacksCount1()
									+ "," + maverickData.getSnacks2() + ":"
									+ maverickData.getSnacksCount2() + ","
									+ maverickData.getSnacks3() + ":"
									+ maverickData.getSnacksCount3();

							ContentValues values = new ContentValues();

							values.put(FoodTrackerTable.Column.DATE,
									cureentDate);
							values.put(FoodTrackerTable.Column.FOOD_BREAKFAST,
									breakFastCollection);

							values.put(FoodTrackerTable.Column.FOOD_LUNCH,
									lunchCollection);
							values.put(FoodTrackerTable.Column.FOOD_DINNER,
									dinnerCollection);
							values.put(FoodTrackerTable.Column.FOOD_SNACK,
									snacksCollection);
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
		/*
		 * breakfast selector
		 */

		selectBreakFast1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("kumar", "click");
				foodInput("Breakfast1");

			}
		});
		selectBreakFast2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				foodInput("Breakfast2");

			}
		});
		selectBreakFast3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				foodInput("Breakfast3");

			}
		});

		/*
		 * Lunch selector
		 */

		selectLunch1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Lunch1");

			}
		});
		selectLunch2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Lunch2");

			}
		});
		selectLunch3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Lunch3");

			}
		});

		/*
		 * dinner selector
		 */
		selectDinner1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Dinner1");
			}
		});

		selectDinner2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Dinner2");
			}
		});

		selectDinner3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Dinner3");
			}
		});

		/*
		 * snacks selector
		 */
		selectSnack1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Snacks1");
			}
		});
		selectSnack2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Snacks2");
			}
		});
		selectSnack3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foodInput("Snacks3");
			}
		});

	}

	private void getLastData() {
		Uri name = Uri.withAppendedPath(FoodTrackerProvider.FOOD_BY_DATE_URI,
				cureentDate);
		Cursor cursorDetails = managedQuery(name, null, null, null, null);
		cursorDetails.moveToFirst();
		if (!(cursorDetails.getCount() > 0))
			return;
		else {
			String breakfast = cursorDetails.getString(cursorDetails
					.getColumnIndex(FoodTrackerTable.Column.FOOD_BREAKFAST));
			List<String> bfValue = splitValue(breakfast);
			setBreakFastValues(bfValue);
			String lunch = cursorDetails.getString(cursorDetails
					.getColumnIndex(FoodTrackerTable.Column.FOOD_LUNCH));
			List<String> lunchValue = splitValue(lunch);
			setLunchValues(lunchValue);
			String dinner = cursorDetails.getString(cursorDetails
					.getColumnIndex(FoodTrackerTable.Column.FOOD_DINNER));
			List<String> dinnerValue = splitValue(dinner);
			setDinnerValues(dinnerValue);
			String snacks = cursorDetails.getString(cursorDetails
					.getColumnIndex(FoodTrackerTable.Column.FOOD_SNACK));
			List<String> snacksValue = splitValue(snacks);
			setSnacksValues(snacksValue);
		}

	}

	private void setSnacksValues(List<String> snacksValue) {
		String a = snacksValue.get(0);
		String b = snacksValue.get(2);
		String c = snacksValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			maverickData.setSnacks1(a);
			maverickData.setSnacksCount1(Integer.parseInt(snacksValue.get(1)));
			snacksValue1.setText(a);
			snacksCount1.setText(snacksValue.get(1));
		}
		if (!b.equalsIgnoreCase("a")) {
			maverickData.setSnacks2(a);
			maverickData.setSnacksCount2(Integer.parseInt(snacksValue.get(3)));
			snacksValue2.setText(b);
			snacksCount2.setText(snacksValue.get(3));
		}
		if (!c.equalsIgnoreCase("a")) {
			maverickData.setSnacks3(a);
			maverickData.setSnacksCount3(Integer.parseInt(snacksValue.get(5)));
			snacksValue3.setText(c);
			snacksCount3.setText(snacksValue.get(5));
		}

	}

	private void setDinnerValues(List<String> dinnerValue) {
		String a = dinnerValue.get(0);
		String b = dinnerValue.get(2);
		String c = dinnerValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			maverickData.setDinner1(a);
			maverickData.setDinnerCount1(Integer.parseInt(dinnerValue.get(1)));
			dinnerValue1.setText(a);
			dinnerCount1.setText(dinnerValue.get(1));
		}
		if (!b.equalsIgnoreCase("a")) {
			maverickData.setDinner2(a);
			maverickData.setDinnerCount2(Integer.parseInt(dinnerValue.get(3)));
			dinnerValue2.setText(b);
			dinnerCount2.setText(dinnerValue.get(3));
		}
		if (!c.equalsIgnoreCase("a")) {
			maverickData.setDinner3(a);
			maverickData.setDinnerCount3(Integer.parseInt(dinnerValue.get(5)));
			dinnerValue3.setText(c);
			dinnerCount3.setText(dinnerValue.get(5));
		}

	}

	private void setLunchValues(List<String> lunchValue) {
		String a = lunchValue.get(0);
		String b = lunchValue.get(2);
		String c = lunchValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			maverickData.setLunch1(a);
			maverickData.setLunchCount1(Integer.parseInt(lunchValue.get(1)));
			lunchValue1.setText(a);
			lunchCount1.setText(lunchValue.get(1));
		}
		if (!b.equalsIgnoreCase("a")) {
			maverickData.setLunch2(a);
			maverickData.setLunchCount2(Integer.parseInt(lunchValue.get(3)));
			lunchValue2.setText(b);
			lunchCount2.setText(lunchValue.get(3));
		}
		if (!c.equalsIgnoreCase("a")) {
			maverickData.setLunch3(a);
			maverickData.setLunchCount3(Integer.parseInt(lunchValue.get(5)));
			lunchValue3.setText(c);
			lunchCount3.setText(lunchValue.get(5));
		}

	}

	private void setBreakFastValues(List<String> bfValue) {
		String a = bfValue.get(0);
		String b = bfValue.get(2);
		String c = bfValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			maverickData.setBreakFast1(a);
			maverickData.setBreakFastCount1(Integer.parseInt(bfValue.get(1)));
			breakFastValue1.setText(a);
			breakFastCount1.setText(bfValue.get(1));
		}
		if (!b.equalsIgnoreCase("a")) {
			maverickData.setBreakFast2(a);
			maverickData.setBreakFastCount2(Integer.parseInt(bfValue.get(3)));
			breakFastValue2.setText(b);
			breakFastCount2.setText(bfValue.get(3));
		}
		if (!c.equalsIgnoreCase("a")) {
			maverickData.setBreakFast3(a);
			maverickData.setBreakFastCount3(Integer.parseInt(bfValue.get(5)));
			breakFastValue3.setText(c);
			breakFastCount3.setText(bfValue.get(5));
		}

	}

	private List<String> splitValue(String value) {
		List<String> data;
		try {
			data = new ArrayList<String>();
			String[] separateValue = value.split("[,]");
			for (int c = 0; c < separateValue.length; c++) {
				String in = separateValue[c];
				String[] d = in.split("[:]");
				for (int m = 0; m < d.length; m++)
					data.add(d[m]);
			}
			return data;
		} catch (Exception e) {
			System.out.print("" + e + e.getMessage());
		}

		return null;
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
						if (HeadMsg.equalsIgnoreCase("Breakfast1")) {
							maverickData.setBreakFast1(foodValue);
							breakFastValue1.setText(foodValue);
							foodCount("Breakfast1");
							bColories = selectedCalories;
							bCorbos = selectedCorbos;
							bFat = selectedFat;
							bProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Breakfast2")) {
							maverickData.setBreakFast2(foodValue);
							breakFastValue2.setText(foodValue);
							foodCount("Breakfast2");
							bColories = selectedCalories;
							bCorbos = selectedCorbos;
							bFat = selectedFat;
							bProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Breakfast3")) {
							maverickData.setBreakFast3(foodValue);
							breakFastValue3.setText(foodValue);
							foodCount("Breakfast3");
							bColories = selectedCalories;
							bCorbos = selectedCorbos;
							bFat = selectedFat;
							bProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Lunch1")) {
							maverickData.setLunch1(foodValue);
							lunchValue1.setText(foodValue);
							foodCount("Lunch1");
							lColories = selectedCalories;
							lCorbos = selectedCorbos;
							lFat = selectedFat;
							lProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Lunch2")) {
							maverickData.setLunch2(foodValue);
							lunchValue2.setText(foodValue);
							foodCount("Lunch2");
							lColories = selectedCalories;
							lCorbos = selectedCorbos;
							lFat = selectedFat;
							lProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Lunch3")) {
							maverickData.setLunch3(foodValue);
							lunchValue3.setText(foodValue);
							foodCount("Lunch3");
							lColories = selectedCalories;
							lCorbos = selectedCorbos;
							lFat = selectedFat;
							lProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Dinner1")) {
							maverickData.setDinner1(foodValue);
							dinnerValue1.setText(foodValue);
							foodCount("Dinner1");
							dColories = selectedCalories;
							dCorbos = selectedCorbos;
							dFat = selectedFat;
							dProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Dinner2")) {
							maverickData.setDinner2(foodValue);
							dinnerValue2.setText(foodValue);
							foodCount("Dinner2");
							dColories = selectedCalories;
							dCorbos = selectedCorbos;
							dFat = selectedFat;
							dProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Dinner3")) {
							maverickData.setDinner3(foodValue);
							dinnerValue3.setText(foodValue);
							foodCount("Dinner3");
							dColories = selectedCalories;
							dCorbos = selectedCorbos;
							dFat = selectedFat;
							dProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Snacks1")) {
							maverickData.setSnacks1(foodValue);
							snacksValue1.setText(foodValue);
							foodCount("Snacks1");
							sColories = selectedCalories;
							sCorbos = selectedCorbos;
							sFat = selectedFat;
							sProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Snacks2")) {
							maverickData.setSnacks2(foodValue);
							snacksValue2.setText(foodValue);
							foodCount("Snacks2");
							sColories = selectedCalories;
							sCorbos = selectedCorbos;
							sFat = selectedFat;
							sProtien = selectedProtien;
						} else if (HeadMsg.equalsIgnoreCase("Snacks3")) {
							maverickData.setSnacks3(foodValue);
							snacksValue3.setText(foodValue);
							foodCount("Snacks3");
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

	private void foodCount(final String HeadMsg) {
		try {
			final String[] count = getResources().getStringArray(R.array.COUNT);

			final Dialog listDialog = new Dialog(DietTrackerActivity.this,
					R.style.PauseDialog);
			listDialog.setTitle("Count");
			WindowManager.LayoutParams transparentView = listDialog.getWindow()
					.getAttributes();
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

			ListView list = (ListView) listDialog.findViewById(R.id.alertlist);

			list.setAdapter(new ArrayAdapter<String>(DietTrackerActivity.this,
					R.layout.data_select_input_cardatat, count));
			listDialog.show();
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view,
						int id, long position) {
					String countValue = count[id];
					int values = Integer.parseInt(countValue);
					if (HeadMsg.equalsIgnoreCase("Breakfast1")) {
						maverickData.setBreakFastCount1(values);
						breakFastCount1.setText(""
								+ maverickData.getBreakFastCount1());
					} else if (HeadMsg.equalsIgnoreCase("Breakfast2")) {
						maverickData.setBreakFastCount2(values);
						breakFastCount2.setText(""
								+ maverickData.getBreakFastCount2());
					} else if (HeadMsg.equalsIgnoreCase("Breakfast3")) {
						maverickData.setBreakFastCount3(values);
						breakFastCount3.setText(""
								+ maverickData.getBreakFastCount3());
					} else if (HeadMsg.equalsIgnoreCase("Lunch1")) {
						maverickData.setLunchCount1(values);
						lunchCount1.setText("" + maverickData.getLunchCount1());
					} else if (HeadMsg.equalsIgnoreCase("Lunch2")) {
						maverickData.setLunchCount2(values);
						lunchCount2.setText("" + maverickData.getLunchCount2());
					} else if (HeadMsg.equalsIgnoreCase("Lunch3")) {
						maverickData.setLunchCount3(values);
						lunchCount3.setText("" + maverickData.getLunchCount3());
					} else if (HeadMsg.equalsIgnoreCase("Dinner1")) {
						maverickData.setDinnerCount1(values);
						dinnerCount1.setText(""
								+ maverickData.getDinnerCount1());
					} else if (HeadMsg.equalsIgnoreCase("Dinner2")) {
						maverickData.setDinnerCount2(values);
						dinnerCount2.setText(""
								+ maverickData.getDinnerCount2());
					} else if (HeadMsg.equalsIgnoreCase("Dinner3")) {
						maverickData.setDinnerCount3(values);
						dinnerCount3.setText(""
								+ maverickData.getDinnerCount3());
					} else if (HeadMsg.equalsIgnoreCase("Snacks1")) {
						maverickData.setSnacksCount1(values);
						snacksCount1.setText(""
								+ maverickData.getSnacksCount1());
					} else if (HeadMsg.equalsIgnoreCase("Snacks2")) {
						maverickData.setSnacksCount2(values);
						snacksCount2.setText(""
								+ maverickData.getSnacksCount2());
					} else if (HeadMsg.equalsIgnoreCase("Snacks3")) {
						maverickData.setSnacksCount3(values);
						snacksCount3.setText(""
								+ maverickData.getSnacksCount3());
					} else {
						// TODO
					}
					listDialog.dismiss();
					overridePendingTransition(R.anim.prev_slidein,
							R.anim.prev_slideout);
				}
			});
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
