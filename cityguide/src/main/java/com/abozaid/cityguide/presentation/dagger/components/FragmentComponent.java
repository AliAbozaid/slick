package com.abozaid.cityguide.presentation.dagger.components;

import com.abozaid.cityguide.presentation.dagger.modules.FragmentModule;
import com.abozaid.cityguide.presentation.ui.fragments.PlacesFragment;
import com.abozaid.slick.plugins.IBaseFragment;
import dagger.Component;

@PerView @Component(modules = FragmentModule.class) public interface FragmentComponent {
  void inject(PlacesFragment placesFragment);

  IBaseFragment fragment();

  //PlacesPresenter placesPresenter();
}