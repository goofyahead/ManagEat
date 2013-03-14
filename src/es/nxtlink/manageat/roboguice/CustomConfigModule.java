package es.nxtlink.manageat.roboguice;

import android.content.Context;

import com.google.inject.AbstractModule;

import es.nxtlink.manageat.api.ManagEatApi;
import es.nxtlink.manageat.api.ManagEatApiInterface;
import es.nxtlink.manageat.db.DataHelper;

public class CustomConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		Context context = ManageatApplication.getContext();
		ManagEatApiInterface manageatApi = new ManagEatApi();
		DataHelper dataHelper = new DataHelper(context);
		
		bind(ManagEatApiInterface.class).toInstance(manageatApi);
		bind(DataHelper.class).toInstance(dataHelper);
	}

}
