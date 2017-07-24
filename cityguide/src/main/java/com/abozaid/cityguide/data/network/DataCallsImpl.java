package com.abozaid.cityguide.data.network;

import android.util.Log;
import com.abozaid.cityguide.data.net.ApiController;
import com.abozaid.cityguide.domain.callbacks.InteractorCallback;
import com.abozaid.cityguide.domain.calls.DataCalls;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import io.reactivex.Observable;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by aliabozaid on 7/12/17.
 */

public class DataCallsImpl implements DataCalls {

  Retrofit retrofit;
  ApiController apiController;

  @Inject public DataCallsImpl(Retrofit retrofit) {
    this.retrofit = retrofit;
    apiController = retrofit.create(ApiController.class);
  }

  @Override
  public void getPlaces(String output, String location, int radius, String type, String key,
      final InteractorCallback interactorCallback) {
    Log.d("TYPE", type + ":" + location);
    Call<ListOfPlacesModel> call = apiController.getPlaces(output, location, radius, type, key);
    call.enqueue(new Callback<ListOfPlacesModel>() {
      @Override
      public void onResponse(Call<ListOfPlacesModel> call, Response<ListOfPlacesModel> response) {
        if (response.code() == 200) interactorCallback.success(response.body());
      }

      @Override public void onFailure(Call<ListOfPlacesModel> call, Throwable t) {
        interactorCallback.error(t);
      }
    });
  }

  public Observable<ListOfPlacesModel> getPlacesRx(String output, String location, int radius,
      String type, String key) {
    return apiController.getPlacesRx(output, location, radius, type, key);
  }
}
