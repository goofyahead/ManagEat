package es.nxtlink.manageat.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;

import es.nxtlink.manageat.utils.Connectivity;

public class ManagEatApi implements ManagEatApiInterface {

	private static final String TAG = ManagEatApi.class.getName();

	private static String API_URL = "http://54.246.122.90/api/";
	private static String CURRENT_MENU = "currentMenu";
	private static String GET_MENU_URL = API_URL + CURRENT_MENU;
	private static String CATEGORIES = "categories";
	private static String GET_CATEGORIES_URL = API_URL + CATEGORIES;
	private static String TAGS = "tags";
	private static String GET_TAGS_URL = API_URL + TAGS;
	private static String INGREDIENTS = "ingredients";
	private static String GET_INGREDIENTS_URL = API_URL + INGREDIENTS;

	@Override
	public void updateCurrentMenu() {
		String menuList = null;
		JSONArray dishes = null;
		try {
			menuList = Connectivity.getDataFromUrl(GET_MENU_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			dishes = new JSONArray(menuList);
			for (int x = 0; x < dishes.length(); x++) {
				Log.d(TAG, "current dish: "
						+ dishes.getJSONObject(x).toString());
				// save to BD
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "error generatin JSONarray: " + e.getMessage());
		}
	}

	@Override
	public void updateTags() {
		String tagList = null;
		JSONArray tags = null;
		try {
			tagList = Connectivity.getDataFromUrl(GET_TAGS_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			tags = new JSONArray(tagList);
			for (int x = 0; x < tags.length(); x++) {
				Log.d(TAG, "current tag: " + tags.getJSONObject(x).toString());
				// save to BD
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "error generatin JSONarray: " + e.getMessage());
		}

	}

	@Override
	public void updateCategories() {
		String categoriesList = null;
		JSONArray categories = null;
		try {
			categoriesList = Connectivity.getDataFromUrl(GET_CATEGORIES_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			categories = new JSONArray(categoriesList);
			for (int x = 0; x < categories.length(); x++) {
				Log.d(TAG, "current category: "
						+ categories.getJSONObject(x).toString());
				// save to BD
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "error generatin JSONarray: " + e.getMessage());
		}
	}

	@Override
	public void updateIngredients() {
		String ingredientsList = null;
		JSONArray ingredients = null;
		try {
			ingredientsList = Connectivity.getDataFromUrl(GET_INGREDIENTS_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ingredients = new JSONArray(ingredientsList);
			for (int x = 0; x < ingredients.length(); x++) {
				Log.d(TAG, "current dish: "
						+ ingredients.getJSONObject(x).toString());
				// save to BD
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "error generatin JSONarray: " + e.getMessage());
		}
	}

	@Override
	public boolean checkForUpdates() {
		return true;
	}
}
