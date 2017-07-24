package com.abozaid.cityguide.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abozaid.cityguide.R;
import com.abozaid.cityguide.domain.callbacks.PlacesItemClicked;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import com.abozaid.cityguide.presentation.presenters.PlacesPresenter;
import com.abozaid.cityguide.presentation.ui.activities.MapActivity;
import com.abozaid.cityguide.presentation.ui.adapters.PlacesAdapter;
import com.abozaid.cityguide.presentation.ui.fragments.plugins.IPlacesFragment;
import com.abozaid.cityguide.presentation.utils.Constants;
import com.abozaid.slick.plugins.IPresenter;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by aliabozaid on 5/6/16.
 */
public class PlacesFragment extends MyBaseFragment implements IPlacesFragment, PlacesItemClicked {
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeRefreshLayout;


  PlacesAdapter placesAdapter;

  private static final String ARG_PARAM1 = "type";
  private static final String ARG_PARAM2 = "location";
  private String location, type;
  private RecyclerView.LayoutManager mLayoutManager;
  ArrayList<ListOfPlacesModel.Results> places;

  @Inject PlacesPresenter presenter;

  public PlacesFragment() {
    // Required empty public constructor
  }

  public static PlacesFragment newInstance(String param1, String param2) {
    PlacesFragment fragment = new PlacesFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public static PlacesFragment newInstance(String param1) {
    PlacesFragment fragment = new PlacesFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public IPresenter getPresenter() {
    return presenter;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      type = getArguments().getString(ARG_PARAM1);
      location = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_places, container, false);
    ButterKnife.bind(this, rootView);

    getFragmentComponent().inject(this);
    //this.getComponent()
    //this.getComponent(FragmentComponent.class).inject(this);

    places = new ArrayList<>();

    recyclerView.setHasFixedSize(true);

    mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);

    placesAdapter = new PlacesAdapter(PlacesFragment.this);
    recyclerView.setAdapter(placesAdapter);

    swipeRefreshLayout.setOnRefreshListener(() -> presenter.getPlaces(location, type));

    presenter.getPlaces(location, type);

    return rootView;
  }

  public void showLoading(boolean show) {

    if (show) {
      progressBar.setVisibility(View.VISIBLE);
      recyclerView.setVisibility(View.GONE);
    } else {
      progressBar.setVisibility(View.GONE);
      recyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showError(Throwable t) {
    //todo change this
    progressBar.setVisibility(View.GONE);
    recyclerView.setVisibility(View.GONE);
    showMessage("Please check your internet connection");
    swipeRefreshLayout.setRefreshing(false);
  }

  @Override public void updateView(ListOfPlacesModel listOfPlacesModel) {
    //todo change this
    recyclerView.setVisibility(View.VISIBLE);
    if (listOfPlacesModel != null && listOfPlacesModel.status.equals("OK")) {
      places.addAll(listOfPlacesModel.results);
      this.setPlaces(places);
      placesAdapter.notifyDataSetChanged();
    } else {
    }

    swipeRefreshLayout.setRefreshing(false);
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void itemClicked(double currentLat, double currentLng, double targetLat, double targerLng,
      String name) {
    Intent intent = new Intent(getActivity(), MapActivity.class);
    intent.putExtra(Constants.CURRENT_LAT, currentLat);
    intent.putExtra(Constants.CURRENT_LNG, currentLng);
    intent.putExtra(Constants.TARGET_LAT, targetLat);
    intent.putExtra(Constants.TARGET_LNG, targerLng);
    intent.putExtra(Constants.NAME, name);
    startActivity(intent);
  }

  @Override public void setPlaces(ArrayList<ListOfPlacesModel.Results> resultsArrayList) {
    this.places = resultsArrayList;
  }

  @Override public ArrayList<ListOfPlacesModel.Results> getPlaces() {
    return places;
  }

  @Override public String getType() {
    return type.toLowerCase();
  }

  @Override public String[] getLatLng() {
    if (location == null) return new String[] { "0", "0" };
    return location.split(",");
  }
}
