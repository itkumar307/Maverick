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

public class ExceriseProvider extends ContentProvider {
	private MaverickHelper database;
	public static final String PROVIDER_NAME = "com.maveric.exceriseprovider";
	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME);
	public static final Uri EXCERISETYPE_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/excerisetype");
	private SQLiteDatabase db;
	private static final int EXCERISETYPE = 1;

	private static final UriMatcher MATCHER;

	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI(PROVIDER_NAME, "excerisetype", EXCERISETYPE);
	}

	@Override
	public boolean onCreate() {
		database = new MaverickHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		Log.d("kumar" + this.getClass(), "Query: " + url.getPath());
		checkColumns(projection);
		qb.setTables(ExceriseValue.TABLE);
		SQLiteDatabase db = database.getWritableDatabase();
		switch (MATCHER.match(url)) {

		case EXCERISETYPE:

			selection = "1) GROUP BY (" + ExceriseValue.Column._ID;
			sortOrder = ExceriseValue.Column._ID + " DESC ";
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
		int count = 0;

		// if (isCollectionUri(url)) {
		// count = db.update(ExceriseValue.TABLE, values, where, whereArgs);
		// } else {
		// String segment = url.getPathSegments().get(1);
		// count = db.update(
		// ExceriseValue.TABLE,
		// values,
		// getIdColumnName()
		// + "="
		// + segment
		// + (!TextUtils.isEmpty(where) ? " AND (" + where
		// + ')' : ""), whereArgs);
		// }
		//
		// getContext().getContentResolver().notifyChange(url, null);
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
}
