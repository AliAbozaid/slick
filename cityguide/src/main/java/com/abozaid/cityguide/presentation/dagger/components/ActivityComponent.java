package com.abozaid.cityguide.presentation.dagger.components;

import com.abozaid.cityguide.presentation.dagger.modules.ActivityModule;
import com.abozaid.cityguide.presentation.presenters.MainPresenter;
import com.abozaid.cityguide.presentation.ui.activities.MainActivity;
import com.abozaid.slick.plugins.IBaseActivity;
import dagger.Component;

/**
 * Created by aliabozaid on 7/13/17.
 */

@PerView @Component(modules = ActivityModule.class) public interface ActivityComponent {
  void inject(MainActivity mainActivity);

  IBaseActivity fragment();

  MainPresenter mainPresenter();
}
