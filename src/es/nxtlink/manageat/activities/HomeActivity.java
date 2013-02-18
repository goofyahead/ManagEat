package es.nxtlink.manageat.activities;

import roboguice.activity.RoboActivity;

import com.google.inject.Inject;

import es.nxtlink.manageat.R;
import es.nxtlink.manageat.api.ManagEatApiInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class HomeActivity extends RoboActivity {
	@Inject
	private ManagEatApiInterface api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if (api.checkForUpdates()) {
			new DownloadMenu().execute();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	private class DownloadMenu extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			api.updateCategories();
			api.updateIngredients();
			api.updateTags();
			api.updateCurrentMenu();
			return null;
		}
	}
}
