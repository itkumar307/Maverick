package com.maveric.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MaverickProvider extends ContentProvider {

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
}

//	private  database;
//
//	public static final String CALLER_IS_SYNCADAPTER = "CALLER_IS_SYNCADAPTER";
//
//	private static final int GET_DIRTY_PRODUCTS = 1;
//	private static final int INSERT_OR_UPDATE_PRODUCTS = 2;
//	private static final int PRODUCT_BY_ID = 3;
//	private static final int INSERT_OR_UPDATE_IMAGES = 4;
//	private static final int SEARCH_PRODUCTS = 5;
//	private static final int IMAGES_BY_PRODUCT_ID = 6;
//	private static final int ALL_IMAGES = 7;
//	private static final int ALL_PRODUCTS = 8;
//	private static final int GET_DIRTY_IMAGES = 9;
//	private static final int IMAGE_BY_ID = 10;
//	private static final int IMAGES_BY_PRODUCT_ID_IGNORE_UPLOAD_TRUE = 11;
//	private static final int PRODUCT_DASHBOARD = 12;
//	private static final int GET_PARTNERS_OLD_PRODUCTS = 13;
//	private static final int PARTNER_OWNER_CARS_COUNT = 14;
//
//	public static final String PROVIDER_NAME = "com.vskein.vcars.ProductProvider";
//	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME);
//
//	public static final Uri INSERT_OR_UPDATE_PRODUCTS_URI = Uri
//			.parse("content://" + PROVIDER_NAME + "/insertOrUpdateProducts");
//	public static final Uri GET_DIRTY_PRODUCTS_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/getDirtyProducts");
//	public static final Uri GET_DIRTY_IMAGES_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/getDirtyImages");
//	public static final Uri INSERT_OR_UPDATE_IMAGES_URI = Uri
//			.parse("content://" + PROVIDER_NAME + "/insertOrUpdateImages");
//	public static final Uri PRODUCT_BY_ID_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/productById");
//	public static final Uri PRODUCT_SEARCH_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/productoveralllist");
//	public static final Uri IMAGES_BY_PRODUCT_ID_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/imagesByProductId");
//	public static final Uri IMAGES_BY_PRODUCT_ID_IGNORE_UPLOAD_TRUE_URI = Uri
//			.parse("content://" + PROVIDER_NAME
//					+ "/imagesByProductIdIgnoreUploadTrue");
//	public static final Uri ALL_PRODUCTS_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/allProducts");
//	public static final Uri ALL_IMAGES_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/allImages");
//	public static final Uri IMAGE_BY_ID_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/imageById");
//	public static final Uri PRODUCT_DASHBOARD_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/dashboardlist");
//	public static final Uri GET_PARTNERS_OLD_PRODUCTS_URI = Uri
//			.parse("content://" + PROVIDER_NAME + "/time");
//	public static final Uri PARTNER_OWNER_CARS_COUNT_URI = Uri
//			.parse("content://" + PROVIDER_NAME + "/partnerOwnerCarsCount");
//	private static final UriMatcher sURIMatcher;
//
//	static {
//		sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//		sURIMatcher.addURI(PROVIDER_NAME, "allProducts", ALL_PRODUCTS);
//		sURIMatcher.addURI(PROVIDER_NAME, "allImages", ALL_IMAGES);
//		sURIMatcher.addURI(PROVIDER_NAME, "getDirtyImages", GET_DIRTY_IMAGES);
//		sURIMatcher.addURI(PROVIDER_NAME, "getDirtyProducts",
//				GET_DIRTY_PRODUCTS);
//		sURIMatcher.addURI(PROVIDER_NAME, "insertOrUpdateProducts",
//				INSERT_OR_UPDATE_PRODUCTS);
//		sURIMatcher.addURI(PROVIDER_NAME, "productById/*", PRODUCT_BY_ID);
//		sURIMatcher.addURI(PROVIDER_NAME, "insertOrUpdateImages",
//				INSERT_OR_UPDATE_IMAGES);
//		sURIMatcher.addURI(PROVIDER_NAME,
//				"productoveralllist/*/*/*/*/*/*/*/*/*/*/*", SEARCH_PRODUCTS);
//		sURIMatcher.addURI(PROVIDER_NAME, "imagesByProductId/*",
//				IMAGES_BY_PRODUCT_ID);
//		sURIMatcher.addURI(PROVIDER_NAME, "imageById/*", IMAGE_BY_ID);
//		sURIMatcher.addURI(PROVIDER_NAME,
//				"imagesByProductIdIgnoreUploadTrue/*",
//				IMAGES_BY_PRODUCT_ID_IGNORE_UPLOAD_TRUE);
//		sURIMatcher.addURI(PROVIDER_NAME, "dashboardlist/*/*/*",
//				PRODUCT_DASHBOARD);
//		sURIMatcher.addURI(PROVIDER_NAME, "time/*", GET_PARTNERS_OLD_PRODUCTS);
//		sURIMatcher.addURI(PROVIDER_NAME,
//				"partnerOwnerCarsCount/*/*/*/*/*/*/*/*/*/*/*",
//				PARTNER_OWNER_CARS_COUNT);
//	}
//
//	@Override
//	public boolean onCreate() {
//		database = new ProductDBHelper(getContext());
//		return true;
//	}
//
//	@Override
//	public Cursor query(Uri url, String[] projection, String selection,
//			String[] selectionArgs, String sortOrder) {
//
//		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//		checkColumns(projection);
//		int uriType = sURIMatcher.match(url);
//		SQLiteDatabase db = database.getWritableDatabase();
//		String groupBy = null;
//
//		switch (uriType) {
//
//		case GET_DIRTY_PRODUCTS:
//			queryBuilder.setTables(WaterTracker.TABLE);
//			sortOrder = WaterTracker.Column.UPDATED + " DESC ";
//			queryBuilder.appendWhere(WaterTracker.Column.DIRTY + "="
//					+ Product.Dirty.TRUE.getDirty());
//			break;
//
//		case GET_DIRTY_IMAGES:
//			queryBuilder.setTables(DietTracker.TABLE);
//			sortOrder = DietTracker.Column.UPDATED + " DESC ";
//			queryBuilder.appendWhere(WaterTracker.Column.DIRTY + "!="
//					+ Image.Dirty.FALSE.getDirty());
//			break;
//
//		case PRODUCT_BY_ID:
//			String id = url.getPathSegments().get(1);
//			queryBuilder.setTables(WaterTracker.TABLE);
//			sortOrder = WaterTracker.Column.UPDATED + " DESC ";
//			queryBuilder.appendWhere(WaterTracker.Column.PRODUCT_ID + "='" + id
//					+ "'");
//			break;
//
//		case IMAGES_BY_PRODUCT_ID:
//			String productId = url.getPathSegments().get(1);
//			queryBuilder.setTables(DietTracker.TABLE);
//			sortOrder = DietTracker.Column.UPDATED + " DESC ";
//			queryBuilder.appendWhere(DietTracker.Column.PRODUCT_ID + "='"
//					+ productId + "'" + " AND " + DietTracker.Column.STATUS
//					+ "=" + Image.ImageStatus.NEW.getImageStatus());
//			break;
//
//		case SEARCH_PRODUCTS: {
//			queryBuilder.setTables(WaterTracker.TABLE + " LEFT OUTER JOIN "
//					+ DietTracker.TABLE + " ON (" + WaterTracker.TABLE + "."
//					+ WaterTracker.Column.PRODUCT_ID + " = " + DietTracker.TABLE
//					+ "." + DietTracker.Column.PRODUCT_ID + " AND "
//					+ DietTracker.TABLE + "." + DietTracker.Column.STATUS + "="
//					+ Image.ImageStatus.NEW.getImageStatus() + ")");
//			sortOrder = WaterTracker.TABLE + "." + WaterTracker.Column.UPDATED
//					+ " DESC ";
//			groupBy = WaterTracker.TABLE + "." + WaterTracker.Column.PRODUCT_ID;
//
//			projection = new String[] {
//					WaterTracker.TABLE + "._id",
//					WaterTracker.TABLE + "." + WaterTracker.Column.PRODUCT_ID,
//					WaterTracker.TABLE + "." + WaterTracker.Column.COLOR,
//					WaterTracker.TABLE + "." + WaterTracker.Column.FUELTYPE,
//					WaterTracker.TABLE + "." + WaterTracker.Column.INSEXPIRY,
//					WaterTracker.TABLE + "." + WaterTracker.Column.INSPROVIDER,
//					WaterTracker.TABLE + "." + WaterTracker.Column.KMS,
//					WaterTracker.TABLE + "." + WaterTracker.Column.MAKE,
//					WaterTracker.TABLE + "." + WaterTracker.Column.MAKE_ID,
//					WaterTracker.TABLE + "." + WaterTracker.Column.MODEL,
//					WaterTracker.TABLE + "." + WaterTracker.Column.MODEL_ID,
//					WaterTracker.TABLE + "." + WaterTracker.Column.VERSION,
//					WaterTracker.TABLE + "." + WaterTracker.Column.VERSION_ID,
//					WaterTracker.TABLE + "." + WaterTracker.Column.YEAR,
//					WaterTracker.TABLE + "." + WaterTracker.Column.NOTES,
//					WaterTracker.TABLE + "." + WaterTracker.Column.OPRICE,
//					WaterTracker.TABLE + "." + WaterTracker.Column.OWNERS,
//					WaterTracker.TABLE + "."
//							+ WaterTracker.Column.PRODUCTSTATUS,
//					WaterTracker.TABLE + "." + WaterTracker.Column.REGNO,
//					WaterTracker.TABLE + "." + WaterTracker.Column.SOLDDATE,
//					WaterTracker.TABLE + "." + WaterTracker.Column.SPRICE,
//					WaterTracker.TABLE + "." + WaterTracker.Column.UPDATED,
//					WaterTracker.TABLE + "."
//							+ WaterTracker.Column.UPDATED_BY_USER_NAME,
//					WaterTracker.TABLE + "." + WaterTracker.Column.USER_NAME,
//					WaterTracker.TABLE + "." + WaterTracker.Column.CREATED,
//					WaterTracker.TABLE + "."
//							+ WaterTracker.Column.LOCAL_UPDATED,
//					WaterTracker.TABLE + "." + WaterTracker.Column.SELLER,
//					WaterTracker.TABLE + "."
//							+ WaterTracker.Column.SELLER_COMPANY,
//					WaterTracker.TABLE + "."
//							+ WaterTracker.Column.INVENTORY_OWNER,
//					DietTracker.TABLE + "." + DietTracker.Column.DIRTY,
//					DietTracker.TABLE + "." + DietTracker.Column.PATH };
//
//			String color = checkIsNotNULL(url.getPathSegments().get(4));
//			String makeId = url.getPathSegments().get(8);
//			String modelId = url.getPathSegments().get(1);
//			String fuelType = url.getPathSegments().get(10);
//			String completeUrl = WaterTracker.TABLE + "."
//					+ WaterTracker.Column.MODEL_ID + " " + modelId + " and "
//					+ WaterTracker.TABLE + "." + WaterTracker.Column.MAKE_ID
//					+ " " + makeId + " and " + WaterTracker.TABLE + "."
//					+ WaterTracker.Column.PRODUCTSTATUS + "<="
//					+ url.getPathSegments().get(9) + " and "
//					+ WaterTracker.TABLE + "." + WaterTracker.Column.FUELTYPE
//					+ " " + fuelType + " and "
//					+ WaterTracker.Column.INVENTORY_OWNER
//					+ url.getPathSegments().get(11);
//			if (!isNotValue(url.getPathSegments().get(2), url.getPathSegments()
//					.get(3), "1000000")) {
//				completeUrl += " and " + WaterTracker.Column.KMS + " >="
//						+ url.getPathSegments().get(2) + " and "
//						+ WaterTracker.Column.KMS + " <="
//						+ url.getPathSegments().get(3);
//			}
//			if (!isNotNULL(color)) {
//				completeUrl += " and " + WaterTracker.Column.COLOR + " "
//						+ color;
//			}
//			if (!isNotNULL(url.getPathSegments().get(5))) {
//				completeUrl += " and " + WaterTracker.Column.OWNERS
//						+ url.getPathSegments().get(5);
//			}
//
//			if (!isNotValue(url.getPathSegments().get(6), url.getPathSegments()
//					.get(7), "10000000")) {
//				completeUrl += " and " + WaterTracker.Column.OPRICE + " >="
//						+ url.getPathSegments().get(6) + " and "
//						+ WaterTracker.Column.OPRICE + " <"
//						+ url.getPathSegments().get(7);
//			}
//
//			queryBuilder.appendWhere(completeUrl);
//			VSLog.debug(this.getClass(), "DB Search query :" + url
//					+ "; completeUrl :" + completeUrl);
//		}
//			break;
//
//		case PARTNER_OWNER_CARS_COUNT:
//			queryBuilder.setTables(WaterTracker.TABLE);
//
//			projection = new String[] { "count(*) as " + DashBoardHelper.COUNT,
//					WaterTracker.Column.INVENTORY_OWNER };
//			groupBy = WaterTracker.Column.INVENTORY_OWNER;
//
//			String color = checkIsNotNULL(url.getPathSegments().get(4));
//			String makeId = url.getPathSegments().get(8);
//			String modelId = url.getPathSegments().get(1);
//			String fuelType = url.getPathSegments().get(10);
//			String completeUrl = WaterTracker.TABLE + "."
//					+ WaterTracker.Column.MODEL_ID + " " + modelId + " and "
//					+ WaterTracker.TABLE + "." + WaterTracker.Column.MAKE_ID
//					+ " " + makeId + " and " + WaterTracker.TABLE + "."
//					+ WaterTracker.Column.PRODUCTSTATUS + "<="
//					+ url.getPathSegments().get(9) + " and "
//					+ WaterTracker.TABLE + "." + WaterTracker.Column.FUELTYPE
//					+ " " + fuelType + " and "
//					+ WaterTracker.Column.INVENTORY_OWNER
//					+ url.getPathSegments().get(11);
//			if (!isNotValue(url.getPathSegments().get(2), url.getPathSegments()
//					.get(3), "1000000")) {
//				completeUrl += " and " + WaterTracker.Column.KMS + " >="
//						+ url.getPathSegments().get(2) + " and "
//						+ WaterTracker.Column.KMS + " <="
//						+ url.getPathSegments().get(3);
//			}
//			if (!isNotNULL(color)) {
//				completeUrl += " and " + WaterTracker.Column.COLOR + " "
//						+ color;
//			}
//			if (!isNotNULL(url.getPathSegments().get(5))) {
//				completeUrl += " and " + WaterTracker.Column.OWNERS
//						+ url.getPathSegments().get(5);
//			}
//
//			if (!isNotValue(url.getPathSegments().get(6), url.getPathSegments()
//					.get(7), "10000000")) {
//				completeUrl += " and " + WaterTracker.Column.OPRICE + " >="
//						+ url.getPathSegments().get(6) + " and "
//						+ WaterTracker.Column.OPRICE + " <"
//						+ url.getPathSegments().get(7);
//			}
//			queryBuilder.appendWhere(completeUrl);
//			VSLog.debug(this.getClass(), "DB Search query :" + url
//					+ "; completeUrl :" + completeUrl);
//			break;
//
//		case PRODUCT_DASHBOARD:
//			queryBuilder.setTables(WaterTracker.TABLE);
//			String columname = url.getPathSegments().get(1);
//			String count = DashBoardHelper.COUNT;
//			String listGroup = DashBoardHelper.LIST_GROUP;
//			projection = new String[] { "count(*) as " + count,
//					columname + " as " + listGroup };
//			sortOrder = count + " DESC " + " LIMIT 3 ";
//			queryBuilder.appendWhere(url.getPathSegments().get(2));
//			if (url.getPathSegments().get(3).equalsIgnoreCase("true"))
//				groupBy = listGroup;
//			break;
//
//		case GET_PARTNERS_OLD_PRODUCTS:
//			queryBuilder.setTables(WaterTracker.TABLE);
//			String time = url.getPathSegments().get(1);
//			queryBuilder.setTables(WaterTracker.TABLE);
//			sortOrder = WaterTracker.Column.UPDATED + " DESC ";
//			queryBuilder.appendWhere(WaterTracker.Column.INVENTORY_OWNER + "="
//					+ InventoryType.PARTNER.getValue() + " and "
//					+ WaterTracker.Column.UPDATED + " <" + time);
//			break;
//
//		default:
//			throw new IllegalArgumentException("Unknown URI: " + url);
//		}
//		VSLog.debug(this.getClass(),
//				"DB select query :" + url + "; projection :" + projection
//						+ "; table :" + queryBuilder.getTables()
//						+ "; selection :" + selection + "; sortOrder :"
//						+ sortOrder);
//		Cursor cursor = queryBuilder.query(db, projection, selection,
//				selectionArgs, groupBy, null, sortOrder);
//
//		// Make sure that potential listeners are getting notified
//		cursor.setNotificationUri(getContext().getContentResolver(), url);
//		return cursor;
//	}
//
//	private boolean isNotValue(String initial, String last, String uptoLast) {
//		if (initial.equals("0") && last.equals(uptoLast)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	private boolean isNotNULL(String input) {
//		if (input != null && input.equals("IS NOT NULL"))
//			return true;
//
//		return false;
//	}
//
//	private String checkIsNotNULL(String i) {
//		if (i.equals("IS NOT NULL"))
//			return i;
//		else
//			return "=" + "'" + i + "'";
//	}
//
//	@Override
//	public String getType(Uri uri) {
//		return String.valueOf(sURIMatcher.match(uri));
//	}
//
//	@Override
//	public synchronized Uri insert(Uri uri, ContentValues values) {
//
//		boolean isFromSyncAdaptor = Boolean.valueOf(uri
//				.getQueryParameter(CALLER_IS_SYNCADAPTER));
//
//		int uriType = sURIMatcher.match(uri);
//		SQLiteDatabase sqlDB = database.getWritableDatabase();
//		long id = 0;
//		switch (uriType) {
//		case INSERT_OR_UPDATE_PRODUCTS:
//			id = sqlDB.replaceOrThrow(WaterTracker.TABLE, null, values);
//			break;
//		case INSERT_OR_UPDATE_IMAGES:
//			id = sqlDB.replaceOrThrow(DietTracker.TABLE, null, values);
//			break;
//		default:
//			throw new IllegalArgumentException("Unknown URI: " + uri);
//		}
//		VSLog.debug(this.getClass(), "DB insert query :" + uri
//				+ "; generated Id :" + id + "; values :" + values);
//
//		if (!isFromSyncAdaptor)
//			getContext().getContentResolver().notifyChange(uri, null);
//		return Uri.parse(BASE_URI + "/" + id);
//	}
//
//	@Override
//	public int delete(Uri uri, String selection, String[] selectionArgs) {
//		VSLog.debug(this.getClass(), "DB delete query :" + uri
//				+ "; selection :" + selection);
//
//		boolean isFromSyncAdaptor = Boolean.valueOf(uri
//				.getQueryParameter(CALLER_IS_SYNCADAPTER));
//
//		int uriType = sURIMatcher.match(uri);
//		SQLiteDatabase sqlDB = database.getWritableDatabase();
//		int count = 0;
//		switch (uriType) {
//		case PRODUCT_BY_ID:
//			String productId = uri.getPathSegments().get(1);
//			count = sqlDB.delete(WaterTracker.TABLE,
//					WaterTracker.Column.PRODUCT_ID + "=" + "'" + productId
//							+ "'", null);
//			break;
//		case IMAGE_BY_ID:
//			String imageId = uri.getPathSegments().get(1);
//			count = sqlDB.delete(DietTracker.TABLE, DietTracker.Column.IMAGE_ID
//					+ "=" + "'" + imageId + "'", null);
//			break;
//		case ALL_PRODUCTS:
//			count = sqlDB.delete(WaterTracker.TABLE, null, null);
//			break;
//		case ALL_IMAGES:
//			count = sqlDB.delete(DietTracker.TABLE, null, null);
//			break;
//		default:
//			throw new IllegalArgumentException("Unknown URI: " + uri);
//		}
//		VSLog.debug(this.getClass(), "DB delete query record count " + count);
//		if (!isFromSyncAdaptor)
//			getContext().getContentResolver().notifyChange(uri, null);
//		return count;
//	}
//
//	@Override
//	public synchronized int update(Uri uri, ContentValues values,
//			String selection, String[] selectionArgs) {
//		VSLog.debug(this.getClass(), "DB update query :" + uri
//				+ "; selection :" + selection + "; values :" + values);
//
//		boolean isFromSyncAdaptor = Boolean.valueOf(uri
//				.getQueryParameter(CALLER_IS_SYNCADAPTER));
//
//		int uriType = sURIMatcher.match(uri);
//		SQLiteDatabase sqlDB = database.getWritableDatabase();
//		int rowsUpdated = 0;
//		switch (uriType) {
//		case PRODUCT_BY_ID:
//			String id = uri.getLastPathSegment();
//			rowsUpdated = sqlDB.update(WaterTracker.TABLE, values,
//					WaterTracker.Column.PRODUCT_ID + "='" + id + "'",
//					selectionArgs);
//			break;
//		case IMAGE_BY_ID:
//			String imageId = uri.getLastPathSegment();
//			rowsUpdated = sqlDB.update(DietTracker.TABLE, values,
//					DietTracker.Column.IMAGE_ID + "=" + "'" + imageId + "'",
//					selectionArgs);
//			break;
//		case IMAGES_BY_PRODUCT_ID:
//			rowsUpdated = sqlDB.update(
//					DietTracker.TABLE,
//					values,
//					DietTracker.Column.PRODUCT_ID + "=" + "'"
//							+ uri.getLastPathSegment() + "'", selectionArgs);
//			break;
//		case IMAGES_BY_PRODUCT_ID_IGNORE_UPLOAD_TRUE:
//			rowsUpdated = sqlDB
//					.update(DietTracker.TABLE,
//							values,
//							DietTracker.Column.PRODUCT_ID + " = " + "'"
//									+ uri.getLastPathSegment() + "' and "
//									+ DietTracker.Column.DIRTY + " != "
//									+ Image.Dirty.UPLOAD_TRUE.getDirty(),
//							selectionArgs);
//			break;
//		default:
//			throw new IllegalArgumentException("Unknown URI: " + uri);
//		}
//		VSLog.debug(this.getClass(), "DB update query record count "
//				+ rowsUpdated);
//		if (!isFromSyncAdaptor)
//			getContext().getContentResolver().notifyChange(uri, null);
//		return rowsUpdated;
//	}
//
//	private void checkColumns(String[] projection) {
//		String[] available = WaterTracker.getColumns();
//		if (projection != null) {
//			HashSet<String> requestedColumns = new HashSet<String>(
//					Arrays.asList(projection));
//			HashSet<String> availableColumns = new HashSet<String>(
//					Arrays.asList(available));
//			// Check if all columns which are requested are available
//			if (!availableColumns.containsAll(requestedColumns)) {
//				throw new IllegalArgumentException(
//						"Unknown columns in projection");
//			}
//		}
//	}

