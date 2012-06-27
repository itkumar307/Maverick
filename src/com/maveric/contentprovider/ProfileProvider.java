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
import com.maveric.database.model.ProfileTable;

public class ProfileProvider extends ContentProvider {

	private MaverickHelper database;

	private static final int INSERT = 1;
	private static final int GET_ALL = 2;

	public static final String PROVIDER_NAME = "com.maveric.contentprovider.ProfileProvider";
	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME);

	public static final Uri INSERT_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/insertProfile");
	public static final Uri SEARCH_BY_EMAIL_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/search_by_email");

	private static final UriMatcher sURIMatcher;
	static {
		sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sURIMatcher.addURI(PROVIDER_NAME, "insertProfile", INSERT);
		sURIMatcher.addURI(PROVIDER_NAME, "search_by_email", GET_ALL);
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
		case INSERT:
			id = sqlDB.replaceOrThrow(ProfileTable.TABLE, null, values);
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
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		checkColumns(projection);
		int uriType = sURIMatcher.match(url);
		SQLiteDatabase db = database.getWritableDatabase();
		String groupBy = null;
		switch (uriType) {

		case GET_ALL:
			queryBuilder.setTables(ProfileTable.TABLE);
			
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + url);
		}
		Log.d("Maveric", "DB select query :" + url + "; projection :"
				+ projection + "; table :" + queryBuilder.getTables()
				+ "; selection :" + selection + "; sortOrder :" + sortOrder);

		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, null, sortOrder);

		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), url);
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
