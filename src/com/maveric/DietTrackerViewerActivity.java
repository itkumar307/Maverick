package com.maveric;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.maveric.contentprovider.FoodTrackerProvider;
import com.maveric.database.model.FoodTrackerTable;

public class DietTrackerViewerActivity extends MavericBaseActiity {

	TextView previous;
	TextView next;
	TextView date;
	Context ctx;
	Button close;
	Cursor cursorDate;
	Cursor cursorDetails;;
	ArrayList<String> allDate;
	int count;
	int constantCont;

	String noInput;
	TextView breakFastValue1, breakFastValue2, breakFastValue3;
	TextView lunchValue1, lunchValue2, lunchValue3;
	TextView dinnerValue1, dinnerValue2, dinnerValue3;
	TextView snacksValue1, snacksValue2, snacksValue3;

	TextView breakFastCount1, breakFastCount2, breakFastCount3;
	TextView lunchCount1, lunchCount2, lunchCount3;
	TextView dinnerCount1, dinnerCount2, dinnerCount3;
	TextView snacksCount1, snacksCount2, snacksCount3;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.dietviewcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();
		noInput = ctx.getResources().getString(R.string.NO_INPUT);
		previous = (TextView) findViewById(R.id.prev_date);
		next = (TextView) findViewById(R.id.next_date);
		next.setVisibility(View.INVISIBLE);
		date = (TextView) findViewById(R.id.date);

		/*
		 * Breakfast xml objects
		 */
		// selectDinner,selectLunch,selectSnack
		breakFastValue1 = (TextView) findViewById(R.id.breakfastselecteditem1);
		breakFastValue2 = (TextView) findViewById(R.id.breakfastselecteditem2);
		breakFastValue3 = (TextView) findViewById(R.id.breakfastselecteditem3);

		breakFastCount1 = (TextView) findViewById(R.id.breakfastselecteditemcount1);
		breakFastCount2 = (TextView) findViewById(R.id.breakfastselecteditemcount2);
		breakFastCount3 = (TextView) findViewById(R.id.breakfastselecteditemcount3);

		/*
		 * lunch xml objects
		 */

		lunchValue1 = (TextView) findViewById(R.id.lunchselecteditem1);
		lunchValue2 = (TextView) findViewById(R.id.lunchselecteditem2);
		lunchValue3 = (TextView) findViewById(R.id.lunchselecteditem3);

		lunchCount1 = (TextView) findViewById(R.id.lunchselecteditemcount1);
		lunchCount2 = (TextView) findViewById(R.id.lunchselecteditemcount2);
		lunchCount3 = (TextView) findViewById(R.id.lunchselecteditemcount3);

		/*
		 * dinner xml objects
		 */

		dinnerValue1 = (TextView) findViewById(R.id.dinnerselecteditem1);
		dinnerValue2 = (TextView) findViewById(R.id.dinnerselecteditem2);
		dinnerValue3 = (TextView) findViewById(R.id.dinnerselecteditem3);

		dinnerCount1 = (TextView) findViewById(R.id.dinnerselecteditemcount1);
		dinnerCount2 = (TextView) findViewById(R.id.dinnerselecteditemcount2);
		dinnerCount3 = (TextView) findViewById(R.id.dinnerselecteditemcount3);

		/*
		 * snacks xml objects
		 */
		snacksValue1 = (TextView) findViewById(R.id.snackselecteditem1);
		snacksValue2 = (TextView) findViewById(R.id.snackselecteditem2);
		snacksValue3 = (TextView) findViewById(R.id.snackselecteditem3);

		snacksCount1 = (TextView) findViewById(R.id.snackselecteditemcount1);
		snacksCount2 = (TextView) findViewById(R.id.snackselecteditemcount2);
		snacksCount3 = (TextView) findViewById(R.id.snackselecteditemcount3);

		try {
			cursorDate = managedQuery(FoodTrackerProvider.GET_FOOD_DETAILS_URI,
					null, null, null, null);
			constantCont = cursorDate.getCount();
			Log.i("kumar" + this.getClass(), "constantCont:" + constantCont);
			count = 0;
			cursorDate.moveToFirst();
			if (constantCont > 0) {
				allDate = new ArrayList<String>();
				do {

					allDate.add(cursorDate.getString(cursorDate
							.getColumnIndex(FoodTrackerTable.Column.DATE)));

				} while (cursorDate.moveToNext());

				date.setText(allDate.get(0));
				if (constantCont == 1) {
					next.setVisibility(View.INVISIBLE);
					previous.setVisibility(View.INVISIBLE);
				}
				Log.i("kumar" + this.getClass(), "array count" + allDate.size());
				listViewRefresh(allDate.get(0));
			} else {

			}
		} catch (Exception e1) {
			Log.e("kumar" + this.getClass(),
					"cursor error load date" + e1.getMessage(), e1);
		}

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if (count <= constantCont) {
						next.setVisibility(View.VISIBLE);
						Log.i("kumar" + this.getClass(),
								"previous start count:" + count);
						count = count + 1;
						String presentDate = allDate.get(count);
						date.setText(presentDate);
						listViewRefresh(presentDate);
						if (count == constantCont - 1) {
							previous.setVisibility(View.INVISIBLE);
						}
						Log.i("kumar" + this.getClass(), "previous end count:"
								+ count);

					}
				} catch (Exception e) {
					Log.e("kumar" + this.getClass(),
							"previous" + e.getMessage(), e);
				}

			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					if (count <= constantCont) {
						Log.i("kumar" + this.getClass(), "next start count:"
								+ count);
						count = count - 1;
						String presentDate = allDate.get(count);
						date.setText(presentDate);
						listViewRefresh(presentDate);
						previous.setVisibility(View.VISIBLE);
						if (count == 0) {
							next.setVisibility(View.INVISIBLE);
						}
						Log.i("kumar" + this.getClass(), "next previous count:"
								+ count);
					}
				} catch (Exception e) {
					Log.e("kumar" + this.getClass(), "next" + e.getMessage(), e);

				}

			}
		});

	}

	protected void listViewRefresh(String presentDate) {
		try {
			Log.i("kumar" + this.getClass(), "date" + presentDate);
			Uri name = Uri.withAppendedPath(
					FoodTrackerProvider.FOOD_BY_DATE_URI, presentDate);
			Cursor cursorDetails = managedQuery(name, null, null, null, null);

			Log.i("kumar" + this.getClass(), "count of cursor list view"
					+ cursorDetails.getCount());
			cursorDetails.moveToFirst();
			if (!(cursorDetails.getCount() > 0))
				return;
			else {
				String breakfast = cursorDetails
						.getString(cursorDetails
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
		} catch (Exception e1) {
			Log.e("kumar" + this.getClass(), "cursor error" + e1.getMessage(),
					e1);

		}
	}

	private void setSnacksValues(List<String> snacksValue) {
		String a = snacksValue.get(0);
		String b = snacksValue.get(2);
		String c = snacksValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			snacksValue1.setText(a);
			snacksCount1.setText(snacksValue.get(1));
		} else
			snacksValue1.setText(noInput);

		if (!b.equalsIgnoreCase("a")) {
			snacksValue2.setText(b);
			snacksCount2.setText(snacksValue.get(3));
		} else
			snacksValue2.setText(noInput);

		if (!c.equalsIgnoreCase("a")) {
			snacksValue3.setText(c);
			snacksCount3.setText(snacksValue.get(5));
		} else
			snacksValue3.setText(noInput);

	}

	private void setDinnerValues(List<String> dinnerValue) {
		String a = dinnerValue.get(0);
		String b = dinnerValue.get(2);
		String c = dinnerValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			dinnerValue1.setText(a);
			dinnerCount1.setText(dinnerValue.get(1));
		} else
			dinnerValue1.setText(noInput);

		if (!b.equalsIgnoreCase("a")) {
			dinnerValue2.setText(b);
			dinnerCount2.setText(dinnerValue.get(3));
		} else
			dinnerValue2.setText(noInput);

		if (!c.equalsIgnoreCase("a")) {
			dinnerValue3.setText(c);
			dinnerCount3.setText(dinnerValue.get(5));
		} else
			dinnerValue3.setText(noInput);

	}

	private void setLunchValues(List<String> lunchValue) {
		String a = lunchValue.get(0);
		String b = lunchValue.get(2);
		String c = lunchValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			lunchValue1.setText(a);
			lunchCount1.setText(lunchValue.get(1));
		} else
			lunchValue1.setText(noInput);

		if (!b.equalsIgnoreCase("a")) {
			lunchValue2.setText(b);
			lunchCount2.setText(lunchValue.get(3));
		} else
			lunchValue2.setText(noInput);
		if (!c.equalsIgnoreCase("a")) {
			lunchValue3.setText(c);
			lunchCount3.setText(lunchValue.get(5));
		} else
			lunchValue3.setText(noInput);

	}

	private void setBreakFastValues(List<String> bfValue) {
		String a = bfValue.get(0);
		String b = bfValue.get(2);
		String c = bfValue.get(4);
		if (!a.equalsIgnoreCase("a")) {
			breakFastValue1.setText(a);
			breakFastCount1.setText(bfValue.get(1));
		} else
			breakFastValue1.setText(noInput);
		if (!b.equalsIgnoreCase("a")) {
			breakFastValue2.setText(b);
			breakFastCount2.setText(bfValue.get(3));
		} else
			breakFastValue2.setText(noInput);
		if (!c.equalsIgnoreCase("a")) {
			breakFastValue3.setText(c);
			breakFastCount3.setText(bfValue.get(5));
		} else
			breakFastValue3.setText(noInput);

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
}
