package es.nxtlink.manageat.api;

public interface ManagEatApiInterface {

	/**
	 * Updates current menu from server
	 */
	void updateCurrentMenu();
	
	void updateTags();
	
	void updateCategories();
	
	void updateIngredients();
	
	boolean checkForUpdates();
}
