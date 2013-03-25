package es.nxtlink.manageat.activities;

import com.google.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import es.nxtlink.manageat.R;
import es.nxtlink.manageat.api.ManagEatApiInterface;
import es.nxtlink.manageat.fragments.CategoriesListFragment;
import es.nxtlink.manageat.fragments.DishBigViewFragment;
import es.nxtlink.manageat.fragments.DishGridViewFragment;
import es.nxtlink.manageat.interfaces.ProgressInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class HomeActivity extends RoboFragmentActivity implements
		DishGridViewFragment.OnDishSelectedListener,
		CategoriesListFragment.onCategoryClickListener {
	private static final String TAG = HomeActivity.class.getName();
	@Inject
	private ManagEatApiInterface api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new DownloadMenu().execute();
	}

	private void loadDishGridFragment( String id ) {
		DishGridViewFragment gridFragment = DishGridViewFragment.newInstance(id);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.push_from_right_in,
				R.anim.push_to_left_out, R.anim.push_from_left_in,
				R.anim.push_to_right_out);
		ft.add(R.id.fragmentContainer, gridFragment, DishGridViewFragment.TAG);
		ft.addToBackStack(DishGridViewFragment.TAG);
		ft.commit();
	}

	private void loadCategoryFragment() {
		CategoriesListFragment categoryFragment = new CategoriesListFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.push_from_right_in,
				R.anim.push_to_left_out, R.anim.push_from_left_in,
				R.anim.push_to_right_out);
		ft.add(R.id.fragmentContainer, categoryFragment,
				CategoriesListFragment.TAG);
		ft.addToBackStack(CategoriesListFragment.TAG);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public void onDishSelected(String id) {
		// add new fragment in mobile on tablet change target for big view
		Log.d(TAG, "clicked on dish " + id);
		DishBigViewFragment viewFragment = DishBigViewFragment.newInstance(id);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.push_from_right_in,
				R.anim.push_to_left_out, R.anim.push_from_left_in,
				R.anim.push_to_right_out);
		ft.add(R.id.fragmentContainer, viewFragment, DishBigViewFragment.TAG);
		ft.addToBackStack(DishBigViewFragment.TAG);
		ft.commit();
	}

	@Override
	public void categoryClicked(String id) {
		Log.d(TAG, "clicked on " + id);
		loadDishGridFragment(id);
	}

	public class DownloadMenu extends AsyncTask<Void, Integer, Void> {

		private Progress progress;

		protected DownloadMenu() {
			this.progress = new Progress(this);
		}

		@Override
		protected Void doInBackground(Void... params) {
			if (api.checkForUpdates()) {
				api.updateCategories(progress);
				api.updateIngredients(progress);
				api.updateTags(progress);
				api.updateCurrentMenu(progress);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadCategoryFragment();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int total = values[1];
			int current = values[0];
			Log.d(TAG, "progresS " + current + " from " + total);
			Toast.makeText(HomeActivity.this, current + "/" + total,
					Toast.LENGTH_SHORT).show();
			super.onProgressUpdate(values);
		}

		public class Progress implements ProgressInterface {
			private DownloadMenu myTask;

			public Progress(DownloadMenu task) {
				this.myTask = task;
			}

			public void publish(Integer... values) {
				myTask.publishProgress(values);
			}
		}
	}
}