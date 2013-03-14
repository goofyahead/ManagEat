package es.nxtlink.manageat.fragments;

import com.google.inject.Inject;

import es.nxtlink.manageat.R;
import es.nxtlink.manageat.activities.HomeActivity;
import es.nxtlink.manageat.adapters.DishCursorAdapter;
import es.nxtlink.manageat.api.ManagEatApiInterface;
import es.nxtlink.manageat.db.DataHelper;
import es.nxtlink.manageat.interfaces.ProgressInterface;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class DishGridViewFragment extends RoboFragment {
	@Inject
	private ManagEatApiInterface api;
	@Inject
	private DataHelper dataHelper;
	@InjectView(R.id.dishGridView)
	private GridView dishGrid;
	private OnDishSelectedListener mCallback;
	private static final String TAG = HomeActivity.class.getName();

	// Container Activity must implement this interface
	public interface OnDishSelectedListener {
		public void onDishSelected(String id);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnDishSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dish_grid_view, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (api.checkForUpdates()) {
			new DownloadMenu().execute();
		}

		dishGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1,
					int position, long arg3) {
				String dishId = (String) adapter.getItemAtPosition(position);
				mCallback.onDishSelected(dishId);
			}
		});
	}

	public class DownloadMenu extends AsyncTask<Void, Integer, Void> {

		private Progress progress;

		protected DownloadMenu() {
			this.progress = new Progress(this);
		}

		@Override
		protected Void doInBackground(Void... params) {
			api.updateCategories(progress);
			api.updateIngredients(progress);
			api.updateTags(progress);
			api.updateCurrentMenu(progress);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dishGrid.setAdapter(new DishCursorAdapter(getActivity(), dataHelper.getDishCursor()));
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int total = values[1];
			int current = values[0];
			Log.d(TAG, "progresS " + current + " from " + total);
			Toast.makeText(getActivity(), current + "/" + total,
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
