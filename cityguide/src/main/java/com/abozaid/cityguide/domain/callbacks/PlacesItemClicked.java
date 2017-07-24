package com.abozaid.cityguide.domain.callbacks;

import android.support.annotation.Nullable;

import com.abozaid.cityguide.domain.models.ListOfPlacesModel;

import java.util.ArrayList;

/**
 * Created by aliabozaid on 5/7/16.
 */
public interface PlacesItemClicked {
  void itemClicked(double currentLat, double currentLng, double targetLat, double targerLng,
      String name);

  void setPlaces(ArrayList<ListOfPlacesModel.Results> resultsArrayList);

  ArrayList<ListOfPlacesModel.Results> getPlaces();

  String getType();

  @Nullable String[] getLatLng();
}
