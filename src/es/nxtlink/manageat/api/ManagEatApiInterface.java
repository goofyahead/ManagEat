package es.nxtlink.manageat.api;

import es.nxtlink.manageat.interfaces.ProgressInterface;

public interface ManagEatApiInterface {

	/**
	 * Updates current menu from server
	 * @param progress 
	 */
	void updateCurrentMenu(ProgressInterface progress);
	
	void updateTags(ProgressInterface progress);
	
	void updateCategories(ProgressInterface progress);
	
	void updateIngredients(ProgressInterface progress);
	
	boolean checkForUpdates();
}
