package com.abozaid.cityguide.presentation.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abozaid.cityguide.R;
import com.abozaid.cityguide.presentation.utils.Constants;
import com.abozaid.cityguide.presentation.utils.MapUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

/**
 * Created by aliabozaid on 5/7/16.
 */
public class MapActivity extends FragmentActivity
    implements OnMapReadyCallback, View.OnClickListener {
  private GoogleMap mMap;
  double currentLat, currentLng, targetLat, targetLng;
  String name;
  ArrayList<LatLng> locations;

  @BindView(R.id.fab) FloatingActionButton fab;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    ButterKnife.bind(this);

    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

    currentLat = getIntent().getExtras().getDouble(Constants.CURRENT_LAT);
    currentLng = getIntent().getExtras().getDouble(Constants.CURRENT_LNG);
    targetLat = getIntent().getExtras().getDouble(Constants.TARGET_LAT);
    targetLng = getIntent().getExtras().getDouble(Constants.TARGET_LNG);
    name = getIntent().getExtras().getString(Constants.NAME);
    fab.setOnClickListener(this);
    mapFragment.getMapAsync(this);
  }

  @Override public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    locations = new ArrayList<>();

    LatLng currentLatLng = new LatLng(currentLat, currentLng);
    mMap.addMarker(new MarkerOptions().position(currentLatLng).title("My Location"));
    locations.add(currentLatLng);

    LatLng targetLatLng = new LatLng(targetLat, targetLng);
    locations.add(targetLatLng);
    mMap.addMarker(new MarkerOptions().position(targetLatLng).title(name));

    MapUtils.fixZoom(googleMap, locations);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fab:
        openGoogleMaps();
        break;
    }
  }

  private void openGoogleMaps() {
    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + targetLat + "," + targetLng + "&mode=d");
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    if (mapIntent.resolveActivity(getPackageManager()) != null) {
      try {
        startActivity(mapIntent);
      } catch (ActivityNotFoundException e) {
        // TODO Auto-generated catch block
        Toast.makeText(MapActivity.this, "Something went wrong please try again",
            Toast.LENGTH_SHORT).show();
      }
    }
  }
}
