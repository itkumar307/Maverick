package com.maveric.contentprovider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class ExceriseProvider extends ContentProvider {

	private static final String DATABASE_NAME = "exceriseDB";
	public static final String PROVIDER_NAME = "com.maveric.exceriseprovider";
	public static final Uri EXCERISETYPE_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/excerisetype");

	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase db;

	private static final int EXCERISETYPE = 1;

	private static final UriMatcher MATCHER;

	public static final class Columns implements BaseColumns {
		public static final String _ID = "_id";
		public static final String EXCERISE_TYPE = "exceriseType";
		public static final String CALORIES = "calories";
	}

	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI(PROVIDER_NAME, "excerisetype", EXCERISETYPE);
	}

	public int getDbVersion() {
		return (DATABASE_VERSION);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		private final Context ctx;

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			ctx = context;

			// checking database and copy Data from asset folder only if it
			// doesn't exists
			if (!isExceriseDBExists()) {
				try {
					copyDataBase();
				} catch (IOException e) {
					Log.e("kumar" + this.getClass(),
							"Error copying model database", e);
					throw new RuntimeException("Error copying model database",
							e);
				}
				Log.d("kumar" + this.getClass(),
						"Initial model database is created");
			}
		}

		private void copyDataBase() throws IOException {
			InputStream myInput = ctx.getAssets().open(DATABASE_NAME);
			File db = ctx.getDatabasePath(DATABASE_NAME);
			if (!db.exists()) {
				String dbDir = db.getAbsolutePath().substring(0,
						db.getAbsolutePath().lastIndexOf("/"));
				File dbDirectory = new File(dbDir);
				dbDirectory.mkdir();
			}
			OutputStream myOutput = new FileOutputStream(db.getAbsolutePath());

			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			myOutput.flush();
			myOutput.close();
			myInput.close();
		}

		private boolean isExceriseDBExists() {

			SQLiteDatabase checkDB = null;
			boolean exist = false;
			try {
				File db = ctx.getDatabasePath(DATABASE_NAME);
				String dbPath = db.getAbsolutePath();
				if (db == null || !db.exists()) {
					exist = false;
				} else {
					checkDB = SQLiteDatabase.openDatabase(dbPath, null,
							SQLiteDatabase.OPEN_READONLY
									| SQLiteDatabase.NO_LOCALIZED_COLLATORS);
				}
			} catch (Exception e) {
				Log.e("kumar" + this.getClass(), "database " + DATABASE_NAME
						+ " does't exist", e);
			}

			if (checkDB != null) {
				exist = true;
				if (checkDB.isOpen())
					checkDB.close();
			}
			return exist;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	@Override
	public boolean onCreate() {
		db = (new DatabaseHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		Log.d("kumar" + this.getClass(), "Query: " + url.getPath());

		qb.setTables(getTableName());

		switch (MATCHER.match(url)) {

		case EXCERISETYPE:
			projection = new String[] { Columns._ID, Columns.EXCERISE_TYPE,
					Columns.CALORIES };

			selection = "1) GROUP BY (" + Columns._ID;
			if (TextUtils.isEmpty(selection))
				;

			sortOrder = Columns._ID + " ASC ";
			if (TextUtils.isEmpty(sortOrder))
				;

			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + url);

		}

		if (TextUtils.isEmpty(sortOrder)) {
			sortOrder = getDefaultSortOrder();
		}

		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), url);

		Log.d("kumar" + this.getClass(), "ModelProvider returns the columns:"
				+ c.getColumnCount() + ", Count:" + c.getCount()
				+ ", Column Names:" + Arrays.toString(c.getColumnNames()));
		return c;
	}

	@Override
	public String getType(Uri url) {
		if (isCollectionUri(url)) {
			return (getCollectionType());
		}

		return (getSingleType());
	}

	@Override
	public Uri insert(Uri url, ContentValues initialValues) {
		long rowID;
		ContentValues values;

		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		if (!isCollectionUri(url)) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		for (String colName : getRequiredColumns()) {
			if (values.containsKey(colName) == false) {
				throw new IllegalArgumentException("Missing column: " + colName);
			}
		}

		populateDefaultValues(values);

		rowID = db.insert(getTableName(), getNullColumnHack(), values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(EXCERISETYPE_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}

		throw new SQLException("Failed to insert row into " + url);
	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		int count;

		if (isCollectionUri(url)) {
			count = db.delete(getTableName(), where, whereArgs);
		} else {
			String segment = url.getPathSegments().get(1);
			count = db.delete(
					getTableName(),
					getIdColumnName()
							+ "="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
		}

		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int count;

		if (isCollectionUri(url)) {
			count = db.update(getTableName(), values, where, whereArgs);
		} else {
			String segment = url.getPathSegments().get(1);
			count = db.update(
					getTableName(),
					values,
					getIdColumnName()
							+ "="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
		}

		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	private boolean isCollectionUri(Uri url) {
		return (MATCHER.match(url) == EXCERISETYPE);
	}

	private String getTableName() {
		return ("excerise");
	}

	private String getIdColumnName() {
		return (Columns._ID);
	}

	private String getDefaultSortOrder() {
		return (Columns._ID);
	}

	private String getCollectionType() {
		return ("vnd.android.cursor.dir/vnd.maverick");
	}

	private String getSingleType() {
		return ("vnd.android.cursor.item/vnd.maverick");
	}

	private String[] getRequiredColumns() {
		return (new String[] { Columns.EXCERISE_TYPE });
	}

	private void populateDefaultValues(ContentValues values) {

		if (values.containsKey(ExceriseProvider.Columns.EXCERISE_TYPE) == false) {
			values.put(ExceriseProvider.Columns.EXCERISE_TYPE, 0.0f);
		}
		if (values.containsKey(ExceriseProvider.Columns.CALORIES) == false) {
			values.put(ExceriseProvider.Columns.CALORIES, 0.0f);
		}
	}

	private String getNullColumnHack() {
		return ("_id");
	}
}
