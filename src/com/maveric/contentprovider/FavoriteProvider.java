package com.maveric.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import com.maveric.database.MaverickHelper;
import com.maveric.database.model.FavWorkoutTracterTable;
import com.maveric.database.model.ProfileTable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class FavoriteProvider extends ContentProvider {
	private MaverickHelper database;
	private static final int workoutTrackerInsert = 1;
	private static final int workoutTrackerValueGet = 2;

	public static final String PROVIDER_NAME = "com.maveric.contentprovider.FavoriteProvider";
	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME);

	public static final Uri WORKTRACKER_FAV_INSERT = Uri.parse("content://"
			+ PROVIDER_NAME + "/insertWorkoutFav");
	public static final Uri  WORKTRACKER_FAV = Uri.parse("content://"
			+ PROVIDER_NAME + "/getWorkoutTrackerFav");
	private static final UriMatcher sURIMatcher;
	static {
		sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sURIMatcher.addURI(PROVIDER_NAME, "insertWorkoutFav",
				workoutTrackerInsert);
		sURIMatcher.addURI(PROVIDER_NAME, "getWorkoutTrackerFav/*",
				workoutTrackerValueGet);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case workoutTrackerInsert:
			id = sqlDB.replaceOrThrow(FavWorkoutTracterTable.TABLE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		Log.d("Maveric", "DB insert query :" + uri + "; generated Id :" + id
				+ "; values :" + values);
		return Uri.parse(BASE_URI + "/" + id);
	}

	@Override
	public boolean onCreate() {
		database = new MaverickHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		checkColumns(projection);
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase db = database.getWritableDatabase();
		String groupBy = null;
		switch (uriType) {

		case workoutTrackerValueGet:
			queryBuilder.setTables(FavWorkoutTracterTable.TABLE);
			queryBuilder.appendWhere(FavWorkoutTracterTable.Column.NAME + " = "
					+ "'" + uri.getPathSegments().get(1) + "'");
			Log.i("manikk", FavWorkoutTracterTable.Column.NAME + " = "
					+ "' " + uri.getPathSegments().get(1) + " '");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, null, sortOrder);
		Log.d("Maveric", "DB select query :" + uri + "; projection :"
				+ projection + "; table :" + queryBuilder.getTables()
				+ "; selection :" + selection + "; sortOrder :" + sortOrder);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void checkColumns(String[] projection) {
		String[] available = ProfileTable.getColumns();
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
