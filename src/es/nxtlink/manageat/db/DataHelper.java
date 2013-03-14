package es.nxtlink.manageat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.nxtlink.manageat.models.*;

public class DataHelper {

	private static final String TAG = DataHelper.class.getName();

	private SQLiteDatabase db;

	public DataHelper(Context context) {
		DbHelper openHelper = new DbHelper(context);
		db = openHelper.getWritableDatabase();
	}

	public void saveDish(Dish dish) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.DISH_ID, dish.getId());
		values.put(DbHelper.DISH_NAME, dish.getName());
		values.put(DbHelper.DISH_DESCRIPTION, dish.getDescription());
		values.put(DbHelper.DISH_IMAGE, dish.getImage());
		values.put(DbHelper.DISH_VIDEO, dish.getVideo());
		values.put(DbHelper.DISH_PRICE, dish.getPrice());
		values.put(DbHelper.DISH_DEMO, dish.isDemo());
		db.insert(DbHelper.TABLE_NAME_DISHES, null, values);
	}

	public void saveTag(Tag currentTag) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.TAG_ID, currentTag.getId());
		values.put(DbHelper.TAG_NAME, currentTag.getName());
		values.put(DbHelper.TAG_DESCRIPTION, currentTag.getDescription());
		db.insert(DbHelper.TABLE_TAGS_NAME, null, values);
	}

	public void saveCategory(Category currentCategory) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.CATEGORY_ID, currentCategory.getId());
		values.put(DbHelper.CATEGORY_NAME, currentCategory.getName());
		values.put(DbHelper.CATEGORY_DESCRIPTION,
				currentCategory.getDescription());
		db.insert(DbHelper.TABLE_CATEGORIES_NAME, null, values);
	}

	public void saveIngredient(Ingredient currentIngredient) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.INGREDIENT_ID, currentIngredient.getId());
		values.put(DbHelper.INGREDIENT_NAME, currentIngredient.getName());
		values.put(DbHelper.INGREDIENT_DESCRIPTION,
				currentIngredient.getDescription());
		db.insert(DbHelper.TABLE_INGREDIENTS_NAME, null, values);
	}

	public void saveRelation(String id, String related) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.RELATION_ID_A, id);
		values.put(DbHelper.RELATION_ID_B, related);
		db.insert(DbHelper.TABLE_NAME_RELATIONS, null, values);		
	}

	public void saveRelatedCategory(String id, String categoryId) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.RELATION_CAT_DISH_ID, id);
		values.put(DbHelper.RELATION_CAT_ID, categoryId);
		db.insert(DbHelper.TABLE_NAME_RELATED_CATEGORIES, null, values);
	}

	public void saveRelatedTag(String id, String tagId) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.RELATION_TAG_DISH_ID, id);
		values.put(DbHelper.RELATION_TAG_ID, tagId);
		db.insert(DbHelper.TABLE_NAME_RELATED_TAGS, null, values);
	}

	public void saveRelatedIngredient(String id, String ingredientId) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.RELATION_INGREDIENT_DISH_ID, id);
		values.put(DbHelper.RELATION_INGREDIENT_ID, ingredientId);
		db.insert(DbHelper.TABLE_NAME_RELATED_INGREDIENTS, null, values);
	}
	
	public Cursor getDishCursor () {
		return db.query(DbHelper.TABLE_NAME_DISHES, null, null, null, null, null, DbHelper.DISH_NAME + " ASC");
	}
	
	public Cursor getDishCursor (String selection, String args) {
		final String SELECTION = selection + "=?";
	    final String[] SELECTION_ARGS = {args};
		return db.query(DbHelper.TABLE_NAME_DISHES, null, SELECTION, SELECTION_ARGS, null, null, DbHelper.DISH_NAME + " ASC");
	}
	
	public Dish getDishById (String id) {
		return getDishFromCursor(getDishCursor(DbHelper.DISH_ID , id));
	}
	
	private Dish getDishFromCursor (Cursor cursor) {
		Dish searchedDish = null;
		 if (cursor.moveToNext()) {
			 String video = cursor.getString(cursor.getColumnIndex(DbHelper.DISH_VIDEO));
			 String name = cursor.getString(cursor.getColumnIndex(DbHelper.DISH_NAME));
			 float price = cursor.getFloat(cursor.getColumnIndex(DbHelper.DISH_PRICE));
			 String description = cursor.getString(cursor.getColumnIndex(DbHelper.DISH_DESCRIPTION));
			 String id = cursor.getString(cursor.getColumnIndex(DbHelper.DISH_ID));
			 String image = cursor.getString(cursor.getColumnIndex(DbHelper.DISH_IMAGE));
			 boolean demo = cursor.getInt(cursor.getColumnIndex(DbHelper.DISH_DEMO)) == 0 ? false : true;
			 
			 searchedDish = new Dish(id, name, description, price, image, video, demo);
		 }
		
		cursor.close();
		return searchedDish;
	}
}
