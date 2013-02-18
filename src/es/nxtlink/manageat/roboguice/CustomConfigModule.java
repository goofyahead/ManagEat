package es.nxtlink.manageat.roboguice;

import com.google.inject.AbstractModule;

import es.nxtlink.manageat.api.ManagEatApi;
import es.nxtlink.manageat.api.ManagEatApiInterface;

public class CustomConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		ManagEatApiInterface manageatApi = new ManagEatApi();
		
		bind(ManagEatApiInterface.class).toInstance(manageatApi);
	}

}
