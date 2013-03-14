package es.nxtlink.manageat.fragments;

import java.io.File;

import com.google.inject.Inject;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import es.nxtlink.manageat.R;
import es.nxtlink.manageat.db.DataHelper;
import es.nxtlink.manageat.models.Dish;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class DishBigViewFragment extends RoboFragment {

	@Inject
	private DataHelper dataHelper;
	@InjectView(R.id.videoViewer)
	private VideoView videoView;
	private static final String DISH_ID = "dish_id";
	public static final String TAG = DishBigViewFragment.class.getName();

	public static DishBigViewFragment newInstance(String dishId) {
		DishBigViewFragment myFragment = new DishBigViewFragment();

		Bundle args = new Bundle();
		args.putString(DISH_ID, dishId);
		args.putParcelable(key, value)
		myFragment.setArguments(args);
		return myFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dish_view_fragment, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		String dishId = getArguments().getString(DISH_ID);
		Log.d(TAG, "dish id " + dishId);
		
		Dish mDish = dataHelper.getDishById(dishId);
		Log.d(TAG,"dish retrieved with name " + mDish.getName());
		File path = getActivity().getFileStreamPath(mDish.getVideo());
		Log.d(TAG,"path is " + path.toString());

		MediaController mc = new MediaController(getActivity());
		

		videoView.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.setLooping(true);
			}
		});
		
		videoView.setMediaController(mc);
		videoView.setVideoPath(path.toString());
		videoView.start();
	}

}
