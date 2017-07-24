package com.abozaid.cityguide.presentation.dagger.modules;

import com.abozaid.cityguide.presentation.dagger.components.PerView;
import com.abozaid.cityguide.presentation.presenters.PlacesPresenter;
import com.abozaid.cityguide.presentation.ui.fragments.plugins.IPlacesFragment;
import com.abozaid.slick.plugins.IBaseFragment;
import dagger.Module;
import dagger.Provides;

@Module public class FragmentModule {

  IBaseFragment iBaseFragment;

  public FragmentModule(IBaseFragment iBaseFragment) {
    this.iBaseFragment = iBaseFragment;
  }

  @Provides @PerView public IBaseFragment provideFragment() {
    return iBaseFragment;
  }

  @Provides @PerView PlacesPresenter provideNetworkCalls(IBaseFragment iBaseFragment) {
    return new PlacesPresenter((IPlacesFragment) iBaseFragment);
  }
}