package com.abozaid.cityguide.presentation.presenters.plugins;

import com.abozaid.slick.plugins.IPresenter;

/**
 * Created by aliabozaid on 7/12/17.
 */

public interface IPlacesPresenter extends IPresenter {
  void getPlaces(String location, String type);
}
