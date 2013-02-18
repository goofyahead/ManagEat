package es.nxtlink.manageat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "ManagEat";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME_DISHES = "DISHES_TABLE";
	public static final String DISH_ID = "DISH_ID";
	public static final String DISH_NAME = "DISH_NAME";
	public static final String DISH_DESCRIPTION = "DISH_DESCRIPTION";
	public static final String DISH_IMAGE = "DISH_IMAGE";
	public static final String DISH_VIDEO = "DISH_VIDEO";
	public static final String DISH_DEMO = "DISH_DEMO";
	public static final String DISH_PRICE = "DISH_PRICE";
	
	private static final String TABLE_NAME_RELATIONS = "RELATIONS_TABLE";
	private static final String RELATION_ID_A = "RELATION_ITEM_A";
	private static final String RELATION_ID_B = "RELATION_ITEM_B";

	private static final String TABLE_CATEGPORIES_NAME = "CATEGORIES_TABLE";
	private static final String CATEGORY_ID = "CATEGORY_ID";
	private static final String CATEGORY_NAME = "CATEGORY_NAME";

	private static final String TABLE_INGREDIENTS_NAME = "INGREDIENTS_TABLE";
	private static final String INGREDIENT_ID = "INGREDIENT_ID";
	private static final String INGREDIENT_NAME = "INGREDIENT_NAME";

	private static final String TABLE_TAGS_NAME = "TAGS_TABLE";
	private static final String TAG_ID = "TAG_ID";
	private static final String TAG_NAME = "TAG_NAME";

	public static final String CREATE_DISHES = "CREATE TABLE "
            + TABLE_NAME_DISHES + " ("
            + DISH_ID + " TEXT PRIMARY KEY, "
            + DISH_NAME + " TEXT, "
            + DISH_DESCRIPTION + " TEXT, "
            + DISH_IMAGE + " TEXT, "
            + DISH_VIDEO + " TEXT, "
            + DISH_DEMO + " INTEGER "
            + DISH_PRICE + " REAL )";

	
	public static final String CREATE_RELATIONS = "CREATE TABLE "
			+ TABLE_NAME_RELATIONS + " ("
			+ RELATION_ID_A + " TEXT, "
			+ RELATION_ID_B + " TEXT) ";
	
	public static final String CREATE_CATEGORIES = "CREATE TABLE "
			+ TABLE_CATEGPORIES_NAME + " ("
			+ CATEGORY_ID + " TEXT PRIMARY KEY, "
			+ CATEGORY_NAME + " TEXT)";
	
	public static final String CREATE_TAGS = "CREATE TABLE "
			+ TABLE_TAGS_NAME + " ("
			+ TAG_ID + " TEXT PRIMARY KEY, "
			+ TAG_NAME + " TEXT)";
;
	
	public static final String CREATE_INGREDIENTS = "CREATE TABLE "
			+ TABLE_INGREDIENTS_NAME + " ("
			+ INGREDIENT_ID + " TEXT PRIMARY KEY, "
			+ INGREDIENT_NAME + " TEXT)";
	
	private static final String TAG = DbHelper.class.getName();
		

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "creating database");
		db.execSQL(DbHelper.CREATE_CATEGORIES);
		db.execSQL(DbHelper.CREATE_DISHES);
		db.execSQL(DbHelper.CREATE_INGREDIENTS);
		db.execSQL(DbHelper.CREATE_RELATIONS);
		db.execSQL(DbHelper.CREATE_TAGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading database, this will drop tables and recreate");
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.CREATE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.CREATE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.CREATE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.CREATE_RELATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.CREATE_TAGS);
        onCreate(db);
	}
}
