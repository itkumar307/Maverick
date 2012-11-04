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
import com.maveric.database.model.ExceriseValue;
import com.maveric.database.model.FoodTable;
import com.maveric.database.model.FoodTrackerTable;

public class ExceriseProvider extends ContentProvider {
	public static final String WORKOUT_DB = "WorkoutDB";
	private MaverickHelper database;
	public static final String PROVIDER_NAME = "com.maveric.exceriseprovider";
	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME);
	public static final Uri EXCERISETYPE_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/excerisetype");
	public static final Uri FOOD_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/food");

	public static final Uri WORKOUT_FAVOURITE_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/takefavourite");

	public static final Uri ADD_FAVOURITE_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/addfavourite");
	public static final Uri EXCERISETYPE_SEARCH_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/selectexceriseserach");

	public static final Uri EXCERISETYPE_CATEGORY_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/excersiecategory");

	public static final Uri EXCERISETYPE_SEARCH_SUB_URI = Uri
			.parse("content://" + PROVIDER_NAME + "/excerisesub");

	public static final Uri EXCERISETYPE_CATOGRY_SEARCH_URI = Uri
			.parse("content://" + PROVIDER_NAME + "/excerisesearchbycategory");

	// private SQLiteDatabase db;
	private static final int EXCERISETYPE = 1;
	private static final int FOODTYPE = 2;
	private static final int WORKOUT_FAVOURITE = 3;
	private static final int ADD_WORKOUT_FAVOURITE = 4;
	private static final int EXCERISETYPE_SEARCH = 5;
	private static final int EXCERISETYPE_CATEGORY = 6;
	private static final int EXCERISETYPE_SEARCH_SUB = 7;
	private static final int EXCERISETYPE_CATOGRY_SEARCH = 8;

	private static final UriMatcher MATCHER;

	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI(PROVIDER_NAME, "excerisetype", EXCERISETYPE);
		MATCHER.addURI(PROVIDER_NAME, "food", FOODTYPE);
		MATCHER.addURI(PROVIDER_NAME, "takefavourite", WORKOUT_FAVOURITE);
		MATCHER.addURI(PROVIDER_NAME, "addfavourite", ADD_WORKOUT_FAVOURITE);
		MATCHER.addURI(PROVIDER_NAME, "selectexceriseserach/*",
				EXCERISETYPE_SEARCH);
		MATCHER.addURI(PROVIDER_NAME, "excersiecategory", EXCERISETYPE_CATEGORY);
		MATCHER.addURI(PROVIDER_NAME, "excerisesub/*", EXCERISETYPE_SEARCH_SUB);
		MATCHER.addURI(PROVIDER_NAME, "excerisesearchbycategory/*",
				EXCERISETYPE_CATOGRY_SEARCH);
	}

	@Override
	public boolean onCreate() {
		database = new MaverickHelper(getContext(), WORKOUT_DB);
		return true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		Log.d("kumar" + this.getClass(), "Query: " + url.getPath());

		SQLiteDatabase db = database.getWritableDatabase();
		switch (MATCHER.match(url)) {

		case EXCERISETYPE:
			qb.setTables(ExceriseValue.TABLE);
			checkColumns(projection);
			selection = "1) GROUP BY (" + ExceriseValue.Column._ID;
			sortOrder = ExceriseValue.Column._ID + " DESC ";
			break;

		case FOODTYPE:
			qb.setTables(FoodTrackerTable.TABLE);
			checkFoodColumns(projection);
			selection = "1) GROUP BY (" + FoodTrackerTable.Column._ID;
			sortOrder = FoodTrackerTable.Column._ID + " DESC ";
			break;

		case WORKOUT_FAVOURITE:
			qb.setTables(ExceriseValue.TABLE);
			sortOrder = ExceriseValue.Column._ID + " DESC ";
			qb.appendWhere(ExceriseValue.Column.FAVOURITE_STATUS + "= '1'");
			break;

		case EXCERISETYPE_SEARCH:
			String columname = url.getPathSegments().get(1);
			qb.setTables(ExceriseValue.TABLE);
			checkColumns(projection);
			sortOrder = ExceriseValue.Column._ID + " DESC ";
			qb.appendWhere(ExceriseValue.Column.EXCERISE_NAME + " like '%"
					+ columname + "%'");
			break;

		case EXCERISETYPE_CATEGORY:
			qb.setTables(ExceriseValue.TABLE);
			checkColumns(projection);
			selection = "1) GROUP BY (" + ExceriseValue.Column.EXCERISE_TYPE;
			sortOrder = ExceriseValue.Column._ID + " DESC ";
			break;

		case EXCERISETYPE_SEARCH_SUB:

			String categoryName = url.getPathSegments().get(1);
			qb.setTables(ExceriseValue.TABLE);
			checkColumns(projection);
			sortOrder = ExceriseValue.Column._ID + " DESC ";
			qb.appendWhere(ExceriseValue.Column.EXCERISE_NAME + " like '%"
					+ categoryName + "%'" + " AND "
					+ ExceriseValue.Column.INTENSITY_SELECT + " != 'NA' ");
			break;
		case EXCERISETYPE_CATOGRY_SEARCH:

			String subName = url.getPathSegments().get(1);
			qb.setTables(ExceriseValue.TABLE);
			checkColumns(projection);
			sortOrder = ExceriseValue.Column._ID + " DESC ";
			qb.appendWhere(ExceriseValue.Column.EXCERISE_TYPE + " like '%"
					+ subName + "%'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + url);

		}
		Log.d("kumar", "DB select query :" + url + "; projection :"
				+ projection + "; table :" + qb.getTables() + "; selection :"
				+ selection + "; sortOrder :" + sortOrder);

		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), url);

		return c;
	}

	@Override
	public String getType(Uri uri) {
		return String.valueOf(MATCHER.match(uri));
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {

		int uriType = MATCHER.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case EXCERISETYPE:
			id = sqlDB.replaceOrThrow(ExceriseValue.TABLE, null, initialValues);
			break;
		case FOODTYPE:
			id = sqlDB.replaceOrThrow(FoodTable.TABLE, null, initialValues);
			break;

		default:
			throw new IllegalArgumentException("Insert Unknown URI: " + uri);
		}
		Log.d("kumar", "DB insert query :" + uri + "; generated Id :" + id
				+ "; values :" + initialValues);
		return Uri.parse(BASE_URI + "/" + id);

	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		int count = 0;
		// TODO
		return count;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int uriType = MATCHER.match(url);
		SQLiteDatabase db = database.getWritableDatabase();
		int count = 0;
		switch (uriType) {
		case ADD_WORKOUT_FAVOURITE:
			count = db.update(ExceriseValue.TABLE, values,
					ExceriseValue.Column.EXCERISE_NAME + "= '" + where + "'",
					whereArgs);
			// count = db.update(
			// ExceriseValue.TABLE,
			// values,
			// getIdColumnName()
			// + "="
			// + segment
			// + (!TextUtils.isEmpty(where) ? " AND (" + where
			// + ')' : ""), whereArgs);
			break;
		}
		return count;
	}

	private void checkColumns(String[] projection) {
		String[] available = ExceriseValue.getColumns();
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

	private void checkFoodColumns(String[] projection) {
		String[] available = FoodTrackerTable.getColumns();
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
