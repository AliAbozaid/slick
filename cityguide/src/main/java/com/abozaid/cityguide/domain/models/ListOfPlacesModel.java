package com.abozaid.cityguide.domain.models;

import java.util.ArrayList;

/**
 * Created by aliabozaid on 5/6/16.
 */
public class ListOfPlacesModel {
  public ArrayList<Results> results;

  public class Results {
    public Geometry geometry;

    public class Geometry {
      public Location location;

      public class Location {
        public double lat;
        public double lng;
      }
    }

    public String icon;
    public String id;
    public String name;
    public ArrayList<Photos> photos;

    public class Photos {
      public int height;
      public int width;
      public ArrayList<String> html_attributions;
      public String photo_reference;
    }

    public String place_id;
    public float rating;
    public String reference;
    public String scope;
    public ArrayList<String> types;
    public String vicinity;
  }

  public String status;
}
