package es.nxtlink.manageat.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import es.nxtlink.manageat.R;
import es.nxtlink.manageat.adapters.CategoryCursorAdapter;
import es.nxtlink.manageat.adapters.DishCursorAdapter;
import es.nxtlink.manageat.db.DbHelper;
import es.nxtlink.manageat.providers.DishProvider;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class CategoriesListFragment extends RoboFragment implements LoaderCallbacks<Cursor>{
	
	public static final String TAG = CategoriesListFragment.class.getName();
	private static final int URL_LOADER = 0;
	@InjectView(R.id.categories_view_list) ListView categoriesList;
	private onCategoryClickListener mCallback;
	private CategoryCursorAdapter mAdapter;

	public interface onCategoryClickListener {
		void categoryClicked (String id);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (onCategoryClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onCategoryClickListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.categories_view, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		categoriesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
				String categoryId = (String) adapter.getItemAtPosition(position);
				mCallback.categoryClicked(categoryId);
			}
		});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mAdapter = new CategoryCursorAdapter(getActivity());
		categoriesList.setAdapter(mAdapter);
		getLoaderManager().initLoader(URL_LOADER, null, this);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Uri contentUri = Uri.withAppendedPath(DishProvider.CONTENT_URI, DbHelper.TABLE_CATEGORIES_NAME);
		return new android.support.v4.content.CursorLoader(getActivity(), contentUri, null, null, null, DbHelper.CATEGORY_NAME);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		mAdapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.changeCursor(null);
	}
}
