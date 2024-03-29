package es.nxtlink.manageat.fragments;

import es.nxtlink.manageat.R;
import es.nxtlink.manageat.activities.HomeActivity;
import es.nxtlink.manageat.adapters.DishCursorAdapter;
import es.nxtlink.manageat.db.DbHelper;
import es.nxtlink.manageat.providers.DishProvider;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class DishGridViewFragment extends RoboFragment implements LoaderCallbacks<Cursor>{

	@InjectView(R.id.dishGridView)
	private GridView dishGrid;
	private OnDishSelectedListener mCallback;
	private DishCursorAdapter dishCursorAdapter;
	private String categoryId;
	public static final String TAG = HomeActivity.class.getName();
	private static final int URL_LOADER = 0;
	private static final String CATEGORY_ID = "category_id";

	// Container Activity must implement this interface
	public interface OnDishSelectedListener {
		public void onDishSelected(String id);
	}
	
	public static DishGridViewFragment newInstance(String dishId) {
		DishGridViewFragment myFragment = new DishGridViewFragment();

		Bundle args = new Bundle();
		args.putString(CATEGORY_ID, dishId);
		myFragment.setArguments(args);
		return myFragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		dishCursorAdapter = new DishCursorAdapter(getActivity());
		dishGrid.setAdapter(dishCursorAdapter);
		getLoaderManager().initLoader(URL_LOADER, null, this);
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
		dishGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1,
					int position, long arg3) {
				String dishId = (String) adapter.getItemAtPosition(position);
				mCallback.onDishSelected(dishId);
			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		categoryId = getArguments().getString(CATEGORY_ID);
		Log.d(TAG, "category id " + categoryId);
		Uri contentUri = Uri.withAppendedPath(DishProvider.CONTENT_URI, DbHelper.TABLE_CATEGORIES_JOIN_DISHES);
		String[] args = { categoryId };
		return new android.support.v4.content.CursorLoader(getActivity(), contentUri, null, DbHelper.TABLE_NAME_RELATED_CATEGORIES + "." +
		DbHelper.RELATION_CAT_ID + "=?", args, DbHelper.DISH_NAME);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		dishCursorAdapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		dishCursorAdapter.changeCursor(null);
	}
}
