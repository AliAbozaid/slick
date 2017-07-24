package com.abozaid.cityguide.domain.interactors;

import com.abozaid.cityguide.domain.calls.DataCalls;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import com.abozaid.slick.interactors.RxBaseInteractor;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by aliabozaid on 7/16/17.
 */

public class PlacesRxInteractor
    extends RxBaseInteractor<ListOfPlacesModel, PlacesRxInteractor.Params> {
  DataCalls dataCalls;

  @Inject public PlacesRxInteractor(DataCalls dataCalls) {
    super();
    this.dataCalls = dataCalls;
  }

  @Override public Observable buildUseCaseObservable(Params params) {
    return dataCalls.getPlacesRx(params.output, params.location, params.radius, params.type,
        params.key);
  }

  public static final class Params {

    String output, location, type, key;
    int radius;

    private Params(String output, String location, int radius, String type, String key) {
      this.output = output;
      this.location = location;
      this.radius = radius;
      this.type = type;
      this.key = key;
    }

    public static Params getParams(String output, String location, int radius, String type,
        String key) {
      return new Params(output, location, radius, type, key);
    }
  }
}
