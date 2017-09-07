package com.abozaid.cityguide.presentation.dagger.modules;

import com.abozaid.cityguide.presentation.dagger.components.PerView;
import com.abozaid.cityguide.presentation.presenters.MainPresenter;
import com.abozaid.cityguide.presentation.presenters.plugins.IMainPresenter;
import com.abozaid.cityguide.presentation.ui.activities.plugins.IMainActivity;
import com.abozaid.slick.plugins.IBaseActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by aliabozaid on 7/13/17.
 */
@Module public class ActivityModule {
  IBaseActivity iBaseActivity;

  public ActivityModule(IBaseActivity iBaseActivity) {
    this.iBaseActivity = iBaseActivity;
  }

  @Provides @PerView public IBaseActivity provideFragment() {
    return iBaseActivity;
  }

  @Provides @PerView IMainPresenter provideNetworkCalls(IBaseActivity iBaseActivity) {
    return new MainPresenter((IMainActivity) iBaseActivity);
  }
}
