package com.abozaid.cityguide.domain.calls;

import com.abozaid.cityguide.domain.callbacks.InteractorCallback;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import io.reactivex.Observable;

/**
 * Created by aliabozaid on 7/12/17.
 */

public interface DataCalls {
  void getPlaces(String output, String location, int radius, String type, String key,
      InteractorCallback interactorCallback);

  Observable<ListOfPlacesModel> getPlacesRx(String output, String location, int radius, String type,
      String key);
}
