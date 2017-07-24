package com.abozaid.cityguide.domain.callbacks;

import com.abozaid.cityguide.domain.models.ListOfPlacesModel;

/**
 * Created by aliabozaid on 7/12/17.
 */

public interface GetPlacesCallBack {
  void success(ListOfPlacesModel listOfPlacesModel);

  void error(Throwable throwable);
}
