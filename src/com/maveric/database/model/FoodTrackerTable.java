package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;

public class FoodTrackerTable {

	public static final String TABLE = "foodtracker";

	public static class Column {
		public static final String _ID = "_id";
		public static final String DATE = "date";
		public static final String NAME = "name";
		public static final String PROTIN = "protin";
		public static final String CARBOS = "carbos";
		public static final String FAT = "fat";
		public static final String FAV_STATE = "fav_state";
		public static final String FOOD_TYPE = "food_type";
		public static final String CALORIES = "calories";
		public static final String SERVE = "serve";
		public static final String USERSERVE ="userserve";
		public static final String UNIT = "unit";
		public static final String UPDATED = "updated";
		public static final String CREATED = "created";
	}

	public static String[] getColumns() {
		String[] columns = { Column._ID, Column.NAME, Column.PROTIN,
				Column.CARBOS, Column.FAT, Column.CALORIES, Column.SERVE,
				Column.FAV_STATE,Column.USERSERVE };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + Column.NAME
			+ " TEXT," + Column.PROTIN + " TEXT," + Column.CARBOS + " TEXT,"
			+ Column.FAV_STATE + " TEXT, " + Column.FAT + " TEXT, "
			+ Column.CREATED + " TEXT," + Column.UPDATED + " TEXT,"
			+ Column.CALORIES + " TEXT," + Column.FOOD_TYPE + " TEXT,"
			+ Column.UNIT + " TEXT," + Column.DATE + " TEXT, " + Column.SERVE
			+ " TEXT, "+ Column.USERSERVE + " TEXT ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		// TODO Version check has to done here to avoid old version database
		// crack while upgrading new version database
	}
}