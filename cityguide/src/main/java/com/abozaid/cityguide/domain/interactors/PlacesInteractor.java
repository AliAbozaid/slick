package com.abozaid.cityguide.domain.interactors;

import com.abozaid.cityguide.domain.callbacks.GetPlacesCallBack;
import com.abozaid.cityguide.domain.callbacks.InteractorCallback;
import com.abozaid.cityguide.domain.calls.DataCalls;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import com.abozaid.slick.interactors.BaseInteractor;
import javax.inject.Inject;

/**
 * Created by aliabozaid on 7/12/17.
 */

public class PlacesInteractor extends BaseInteractor<PlacesInteractor.Params>
    implements InteractorCallback<ListOfPlacesModel> {

  DataCalls dataCalls;
  GetPlacesCallBack getPlacesCallBack;

  @Inject public PlacesInteractor(DataCalls dataCalls) {
    this.dataCalls = dataCalls;
  }

  @Override public void execute(Params params) {
    getPlacesCallBack = params.getPlacesCallBack;
    dataCalls.getPlaces(params.output, params.location, params.radius, params.type, params.key,
        this);
  }

  @Override public void success(ListOfPlacesModel listOfPlacesModel) {
    getPlacesCallBack.success(listOfPlacesModel);
  }

  @Override public void error(Throwable throwable) {
    getPlacesCallBack.error(throwable);
  }

  public static final class Params {

    private GetPlacesCallBack getPlacesCallBack;
    String output, location, type, key;
    int radius;

    private Params(String output, String location, int radius, String type, String key,
        GetPlacesCallBack getPlacesCallBack) {
      this.getPlacesCallBack = getPlacesCallBack;
      this.output = output;
      this.location = location;
      this.radius = radius;
      this.type = type;
      this.key = key;
    }

    public static Params getParams(String output, String location, int radius, String type,
        String key, GetPlacesCallBack getPlacesCallBack) {
      return new Params(output, location, radius, type, key, getPlacesCallBack);
    }
  }
}
