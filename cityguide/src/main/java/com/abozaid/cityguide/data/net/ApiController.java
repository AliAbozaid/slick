package com.abozaid.cityguide.data.net;

import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aliabozaid on 5/6/16.
 */
public interface ApiController {

  @GET("nearbysearch/{output}") Observable<ListOfPlacesModel> getPlacesRx(
      @Path("output") String output, @Query("location") String location,
      @Query("radius") int radius, @Query("type") String type, @Query("key") String key);

  @GET("nearbysearch/{output}") Call<ListOfPlacesModel> getPlaces(@Path("output") String output,
      @Query("location") String location, @Query("radius") int radius, @Query("type") String type,
      @Query("key") String key);
}
