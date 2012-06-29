package com.maveric.database.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FoodTrackerTable {
	public static final String TABLE = "foodtracker";

	public static class Column {
		public static final String ID = "_id";
		public static final String DATE = "date";
		public static final String FOOD_BREAKFAST = "foodbf";
		public static final String FOOD_LUNCH = "foodlunch";
		public static final String FOOD_DINNER = "fooddinner";
		public static final String FOOD_SNACK = "foodsnack";
		public static final String FAT = "fat";
		public static final String PROTIN = "protin";
		public static final String CALORIES = "calories";
		public static final String CARBOS = "carbos";

	}

	public static String[] getColumns() {
		String[] columns = { Column.ID, Column.DATE, Column.FOOD_BREAKFAST,
				Column.FOOD_LUNCH, Column.FOOD_DINNER, Column.FOOD_SNACK,
				Column.FAT, Column.PROTIN, Column.CALORIES, Column.CARBOS };
		return columns;
	}

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE
			+ " ( " + Column.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Column.DATE + " TEXT, " + Column.FOOD_BREAKFAST + " TEXT,"
			+ Column.FOOD_LUNCH + " TEXT, " + Column.FOOD_DINNER + " TEXT, "
			+ Column.FOOD_SNACK + " TEXT, " + Column.FAT + " INTEGER, "
			+ Column.PROTIN + " INTEGER, " + Column.CALORIES + " INTEGER, "
			+ Column.CARBOS + " INTEGER ); ";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.i("kumar", "Upgrading database from version " + oldVersion + " to "
				+ newVersion
				+ ", which will destroy all old data in the table " + TABLE);
		// TODO Version check has to done here to avoid old version database
		// crack while upgrading new version database
	}
}