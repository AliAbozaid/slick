package com.abozaid.cityguide.presentation.utils;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;

/**
 * Created by aliabozaid on 5/7/16.
 */
public class MapUtils {
  static LatLngBounds.Builder bc;

  public static void fixZoom(final GoogleMap map, ArrayList<LatLng> points) {

    bc = new LatLngBounds.Builder();
    for (LatLng item : points) {
      bc.include(item);
    }
    map.setOnMapLoadedCallback(() -> {
      CameraUpdate cameraViewOnMap = CameraUpdateFactory.newLatLngBounds(bc.build(), 50);
      map.animateCamera(cameraViewOnMap);
    });
  }
}
