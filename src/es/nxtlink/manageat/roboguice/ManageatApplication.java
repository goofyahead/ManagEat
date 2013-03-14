package es.nxtlink.manageat.roboguice;

import android.app.Application;
import android.content.Context;

public class ManageatApplication extends Application{
	private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
