package com.abozaid.cityguide.presentation.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abozaid.cityguide.BuildConfig;
import com.abozaid.cityguide.R;
import com.abozaid.cityguide.domain.callbacks.PlacesItemClicked;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import com.abozaid.cityguide.presentation.ui.fragments.PlacesFragment;
import com.abozaid.cityguide.presentation.utils.CalculateDistance;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Created by aliabozaid on 5/6/16.
 */
public class PlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  ArrayList<ListOfPlacesModel.Results> places;
  Gson gson;
  Resources resources;
  String type;
  //String loc;
  String latlng[];
  String distance;
  PlacesItemClicked placesItemClicked;

  //constructor to initialize
  public PlacesAdapter(PlacesItemClicked placesItemClicked) {
    this.context = ((PlacesFragment) placesItemClicked).getActivity();
    this.placesItemClicked = placesItemClicked;
    this.places = placesItemClicked.getPlaces();
    gson = new Gson();
    resources = context.getResources();
    this.type = placesItemClicked.getType();
    this.latlng = placesItemClicked.getLatLng();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_row, parent, false);
    return new Holder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

    final Holder holder = (Holder) viewHolder;
    holder.itemName.setText(places.get(position).name);
    holder.ratingBar.setRating(places.get(position).rating);

    final int resID = resources.getIdentifier(type, "drawable", BuildConfig.APPLICATION_ID);
    holder.itemImage.setImageResource(resID);
    final Location targetLocation = new Location("");
    targetLocation.setLatitude(places.get(position).geometry.location.lat);
    targetLocation.setLongitude(places.get(position).geometry.location.lng);
    distance = CalculateDistance.round(
        CalculateDistance.distance(places.get(position).geometry.location.lat,
            places.get(position).geometry.location.lng, Double.parseDouble(latlng[0]),
            Double.parseDouble(latlng[0])), 1) + " mi";

    holder.itemDistance.setText(distance);
    if (position == 0) {
      holder.separatorLine.setVisibility(View.GONE);
    } else {
      holder.separatorLine.setVisibility(View.VISIBLE);
    }

    holder.cardView.setOnClickListener(v -> {
      ListOfPlacesModel.Results result = places.get(position);

      placesItemClicked.itemClicked(Double.valueOf(latlng[0]), Double.valueOf(latlng[1]),
          result.geometry.location.lat, result.geometry.location.lng, result.name);
    });
  }

  @Override public int getItemCount() {
    return places.size();
  }

  //holder for item row
  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.card_view) CardView cardView;
    @BindView(R.id.item_image) ImageView itemImage;
    @BindView(R.id.item_name) TextView itemName;
    @BindView(R.id.item_distance) TextView itemDistance;
    @BindView(R.id.item_ratingBar) RatingBar ratingBar;
    @BindView(R.id.separator_line) View separatorLine;

    public Holder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
