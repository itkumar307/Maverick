package com.maveric.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.maveric.database.MaverickHelper;
import com.maveric.database.model.FoodTrackerTable;
import com.maveric.database.model.FoodTable;
import com.maveric.database.model.WorkOutTrackerTable;

public class FoodProvider extends ContentProvider {
	private MaverickHelper database;
	private static final int INSERT_FOOD_DETAILS = 1;
	private static final int FOOD_BY_DATE_AND_TIMING = 2;
	private static final int FOOD_LIST_BY_SEARCH = 3;

	public static final String PROVIDER_NAME = "com.maveric.FoodTrackerProvider";
	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME);

	public static final Uri INSERT_FOOD_DETAILS_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/insertOrUpdatefood");

	public static final Uri FOOD_BY_DATE_TIMING_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/takevaluebydate");

	public static final Uri FOOD_LIST_BY_SEARCH_VALUE = Uri.parse("content://"
			+ PROVIDER_NAME + "/food_list");

	private static final UriMatcher sURIMatcher;

	static {
		sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sURIMatcher.addURI(PROVIDER_NAME, "insertOrUpdatefood",
				INSERT_FOOD_DETAILS);
		sURIMatcher.addURI(PROVIDER_NAME, "takevaluebydate/*/*",
				FOOD_BY_DATE_AND_TIMING);
		sURIMatcher.addURI(PROVIDER_NAME, "food_list/*", FOOD_LIST_BY_SEARCH);

	}

	@Override
	public boolean onCreate() {
		database = new MaverickHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		checkColumns(projection);
		int uriType = sURIMatcher.match(url);
		SQLiteDatabase db = database.getWritableDatabase();
		String groupBy = null;
		switch (uriType) {
		case FOOD_BY_DATE_AND_TIMING:
			queryBuilder.setTables(FoodTrackerTable.TABLE);
			sortOrder = FoodTrackerTable.Column._ID + " DESC ";
			queryBuilder.appendWhere( FoodTrackerTable.Column.FOOD_TYPE + " = "
					+ url.getPathSegments().get(2));
			break;
		case FOOD_LIST_BY_SEARCH:
			queryBuilder.setTables(FoodTable.TABLE);
			sortOrder = FoodTable.Column.NAME + " ASC ";
			queryBuilder.appendWhere(FoodTable.Column.NAME + " Like '%"
					+ url.getPathSegments().get(1) + "%'");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + url);
		}
		Log.d("kumar", "DB select query :" + url + "; projection :"
				+ projection + "; table :" + queryBuilder.getTables()
				+ "; selection :" + selection + "; sortOrder :" + sortOrder);
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, null, sortOrder);

		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), url);
		return cursor;
	}
	@Override
	public String getType(Uri uri) {
		return String.valueOf(sURIMatcher.match(uri));
	}

	@Override
	public synchronized Uri insert(Uri uri, ContentValues values) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case INSERT_FOOD_DETAILS:
			id = sqlDB.replaceOrThrow(FoodTrackerTable.TABLE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Insert Unknown URI: " + uri);
		}
		Log.d("kumar", "DB insert query :" + uri + "; generated Id :" + id
				+ "; values :" + values);
		return Uri.parse(BASE_URI + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		return 0;
	}

	@Override
	public synchronized int update(Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {

		return 0;
	}

	private void checkColumns(String[] projection) {
		String[] available = WorkOutTrackerTable.getColumns();
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}
}
