package com.abozaid.cityguide.presentation.presenters.plugins;

import com.abozaid.slick.plugins.IBasePresenter;

/**
 * Created by aliabozaid on 7/13/17.
 */

public interface IMainPresenter extends IBasePresenter {
  void requestLocationPermission();

  void getLocationPermission();
}
