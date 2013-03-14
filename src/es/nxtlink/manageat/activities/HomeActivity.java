package es.nxtlink.manageat.activities;

import roboguice.activity.RoboFragmentActivity;
import es.nxtlink.manageat.R;
import es.nxtlink.manageat.fragments.DishBigViewFragment;
import es.nxtlink.manageat.fragments.DishGridViewFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

public class HomeActivity extends RoboFragmentActivity implements DishGridViewFragment.OnDishSelectedListener{
	private static final String TAG = HomeActivity.class.getName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public void onDishSelected(String id) {
		//add new fragment in mobile on tablet change target for big view
		Log.d(TAG, "clicked on dish " + id);
		DishBigViewFragment viewFragment = DishBigViewFragment.newInstance(id);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	    ft.replace(R.id.fragmentContainer, viewFragment);
	    ft.addToBackStack(DishBigViewFragment.TAG);
	    ft.commit();
	}

}