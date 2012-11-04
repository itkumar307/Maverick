package com.maveric;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.database.model.ExceriseValue;

public class WorkoutTrackerCategoryActivity extends MavericListBaseActiity {

	Context ctx;
	Cursor exceriseCategoryCursor;
	TextView titleName;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = getApplicationContext();
		titleName = (TextView) findViewById(R.id.workout_tracker_side);
		titleName.setText("List of Exercise");
		defaultLoadData();

	}

	private void defaultLoadData() {
		exceriseCategoryCursor = managedQuery(
				ExceriseProvider.EXCERISETYPE_CATEGORY_URI, null, null, null,
				null);
		exceriseTypeInput(exceriseCategoryCursor);

	}

	private void exceriseTypeInput(Cursor exceriseCursor) {
		try {
			if (exceriseCursor.getCount() > 0) {

				getListView().setVisibility(View.VISIBLE);
				ListAdapter adapter = new SimpleCursorAdapter(this,
						R.layout.data_select_input_cardatat, exceriseCursor,
						new String[] { ExceriseValue.Column.EXCERISE_TYPE },
						new int[] { R.id.titlename });

				// Bind to our new adapter.
				setListAdapter(adapter);
				onClickList();
			}

		} catch (Exception e) {
			Log.e("kumar" + this.getClass(), "excerise input function failed."
					+ e.getMessage(), e);
		}

	}

	private void onClickList() {
		try {

			ListView list = getListView();
			list.setTextFilterEnabled(true);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String category = exceriseCategoryCursor.getString(exceriseCategoryCursor
							.getColumnIndex(ExceriseValue.Column.EXCERISE_TYPE));

					Intent s = new Intent(ctx, WorkoutTrackerActivity.class);
					s.putExtra("category", category);
					s.putExtra("date", getIntent().getExtras()
							.getString("date"));
					startActivity(s);
//					WorkoutTrackerCategoryActivity.this.finish();
				}
			});
		} catch (Exception e) {
			Log.e("kumar" + this.getClass(), "List view" + e.getMessage(), e);
		}

	}
}
