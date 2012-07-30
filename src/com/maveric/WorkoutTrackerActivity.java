package com.maveric;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maveric.contentprovider.ExceriseProvider;
import com.maveric.database.model.ExceriseValue;
import com.maveric.obj.type.MaverickDataOrganize;

enum kumar {
	NORAML, SEARCH, FAVORITE;
}

public class WorkoutTrackerActivity extends MavericListBaseActiity {

	MaverickDataOrganize maverickData;
	Context ctx;
	Button showFavourite;
	Button result;
	Cursor workoutInfo;
	Cursor exceriseCursor;
	EditText searchText;
	TextView msg;

	@Override
	protected void setContentToLayout() {
		setContentView(R.layout.workoutcontainer);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = getApplicationContext();

		maverickData = new MaverickDataOrganize(ctx);
		msg = (TextView) findViewById(R.id.nolistmsg);

		showFavourite = (Button) findViewById(R.id.load_fav);

		result = (Button) findViewById(R.id.searchresult);

		searchText = (EditText) findViewById(R.id.excerisesearch);

		defaultLoadData();

		showFavourite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showFavourite.setVisibility(View.GONE);

				LinearLayout exceriseBlock = (LinearLayout) findViewById(R.id.exceriseblock);
				exceriseBlock.setVisibility(View.GONE);

				exceriseCursor = managedQuery(
						ExceriseProvider.WORKOUT_FAVOURITE_URI, null, null,
						null, null);

				exceriseTypeInput(exceriseCursor, kumar.FAVORITE);

			}
		});
		result.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String searchData = searchText.getText().toString();

				if (!TextUtils.isEmpty(searchData)) {

					Uri name = Uri.withAppendedPath(
							ExceriseProvider.EXCERISETYPE_SEARCH_URI,
							searchData);
					exceriseCursor = managedQuery(name, null, null, null, null);
					Log.i("kumar", "ha");
					exceriseTypeInput(exceriseCursor, kumar.SEARCH);
				} else {
					toast("Hey !! Enter text, Then go search");
				}
			}
		});

	}

	private void defaultLoadData() {
		exceriseCursor = managedQuery(ExceriseProvider.EXCERISETYPE_URI, null,
				null, null, null);
		exceriseTypeInput(exceriseCursor, kumar.NORAML);

	}

	private void exceriseTypeInput(Cursor exceriseCursor, kumar type) {
		try {
			if (exceriseCursor.getCount() > 0) {
				msg.setVisibility(View.GONE);
				getListView().setVisibility(View.VISIBLE);
				ListAdapter adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_1, exceriseCursor,
						new String[] { ExceriseValue.Column.EXCERISE_TYPE },
						new int[] { android.R.id.text1 });

				// Bind to our new adapter.
				setListAdapter(adapter);
				onClickList();
			} else {
				msg.setVisibility(View.VISIBLE);
				switch (type) {
				case FAVORITE:
					getListView().setVisibility(View.INVISIBLE);
					msg.setText("No favorite");
					break;
				case NORAML:
					msg.setText("No item");
					break;
				case SEARCH:
					getListView().setVisibility(View.INVISIBLE);
					msg.setText("please search once again");
					break;

				default:
					break;
				}

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

					int calories = exceriseCursor.getInt(exceriseCursor
							.getColumnIndex(ExceriseValue.Column.CALORIES));
					String type = exceriseCursor.getString(exceriseCursor
							.getColumnIndex(ExceriseValue.Column.EXCERISE_TYPE));
					String count = exceriseCursor.getString(exceriseCursor
							.getColumnIndex(ExceriseValue.Column.EXCERISE_COUNT));
					Intent s = new Intent(ctx, WorkoutTrackerSaveActivity.class);
					Log.i("kumar", "value" + calories + ":" + type + ":"
							+ count);
					s.putExtra("calories", calories);
					s.putExtra("type", type);
					s.putExtra("count", count);
					startActivity(s);

				}
			});
		} catch (Exception e) {
			Log.e("kumar" + this.getClass(), "List view" + e.getMessage(), e);
		}

	}
}
