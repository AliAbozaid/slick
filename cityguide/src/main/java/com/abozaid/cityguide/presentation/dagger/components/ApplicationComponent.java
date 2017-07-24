package com.abozaid.cityguide.presentation.dagger.components;

import com.abozaid.cityguide.domain.calls.DataCalls;
import com.abozaid.cityguide.presentation.dagger.modules.ApiModule;
import com.abozaid.cityguide.presentation.presenters.PlacesPresenter;
import com.abozaid.cityguide.presentation.ui.fragments.PlacesFragment;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by aliabozaid on 5/6/16.
 */
@Singleton @Component(modules = { ApiModule.class }) public interface ApplicationComponent {
  void inject(PlacesPresenter placesPresenter);

  Retrofit retrofit();

  DataCalls loginCall();
}

