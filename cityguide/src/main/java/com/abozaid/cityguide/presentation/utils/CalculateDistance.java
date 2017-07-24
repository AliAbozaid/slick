package com.abozaid.cityguide.presentation.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by aliabozaid on 5/7/16.
 */
public class CalculateDistance {
  public static double distance(double lat1, double lon1, double lat2, double lon2) {

    int R = 3961; //in miles
    double dlon = lon2 - lon1;
    double dlat = lat1 - lat2;
    double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(
        Math.sin(dlon / 2.0), 2);
    double c = 2 * a * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
