package com.abozaid.cityguide;

import android.app.Application;
import com.abozaid.cityguide.presentation.dagger.components.ApplicationComponent;
import com.abozaid.cityguide.presentation.dagger.components.DaggerApplicationComponent;
import com.abozaid.cityguide.presentation.dagger.modules.ApiModule;

/**
 * Created by aliabozaid on 5/6/16.
 */
public class CityGuideApp extends Application {

  ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();

    initBuild();
  }

  public void initBuild() {
    applicationComponent = DaggerApplicationComponent.builder().apiModule(new ApiModule()).build();
  }

  public ApplicationComponent getApiComponent() {
    return applicationComponent;
  }
}
