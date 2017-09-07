package com.abozaid.cityguide.presentation.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.abozaid.cityguide.CityGuideApp;
import com.abozaid.cityguide.R;
import com.abozaid.cityguide.presentation.dagger.components.ApplicationComponent;
import com.abozaid.cityguide.presentation.presenters.plugins.IMainPresenter;
import com.abozaid.cityguide.presentation.ui.activities.plugins.IMainActivity;
import com.abozaid.cityguide.presentation.utils.PermissionUtil;
import com.abozaid.slick.presenters.BaseActivityPresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import javax.inject.Inject;

/**
 * Created by aliabozaid on 7/13/17.
 */

public class MainPresenter extends BaseActivityPresenter<IMainActivity>
    implements IMainPresenter, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener,
    ResultCallback<LocationSettingsResult> {

  final String TAG = MainPresenter.class.getSimpleName();

  private static String[] PERMISSIONS_LOCATION =
      { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION };
  protected GoogleApiClient mGoogleApiClient;
  protected LocationRequest mLocationRequest;
  protected LocationSettingsRequest mLocationSettingsRequest;
  protected Location mCurrentLocation;
  protected static final int REQUEST_CHECK_SETTINGS = 0x1;
  public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
  public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
      UPDATE_INTERVAL_IN_MILLISECONDS / 2;
  String locationStr;

  @Inject public MainPresenter(IMainActivity view) {
    super(view);
    init();
  }

  private void init() {
    buildGoogleApiClient();
    createLocationRequest();
    buildLocationSettingsRequest();
    getLocationPermission();
  }

  protected synchronized void buildGoogleApiClient() {
    Log.i(TAG, "Building GoogleApiClient");
    mGoogleApiClient =
        new GoogleApiClient.Builder((Activity) getView()).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
  }

  protected void createLocationRequest() {
    mLocationRequest = new LocationRequest();

    // Sets the desired interval for active location updates. This interval is
    // inexact. You may not receive updates at all if no location sources are available, or
    // you may receive them slower than requested. You may also receive updates faster than
    // requested if other applications are requesting location at a faster interval.
    mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

    // Sets the fastest rate for active location updates. This interval is exact, and your
    // application will never receive updates faster than this value.
    mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  protected ApplicationComponent getApplicationComponent() {
    return ((CityGuideApp) ((Activity) getView()).getApplication()).getApiComponent();
  }

  @Override public void getLocationPermission() {
    if (Build.VERSION.SDK_INT >= 23) {
      if (!PermissionUtil.verifyPermissionsGranted(PERMISSIONS_LOCATION, (Activity) getView())) {
        // location permissions have not been granted.
        requestLocationPermissions();
      } else {
        checkLocationSettings();
      }
    }
  }

  private void requestLocationPermissions() {
    if (!PermissionUtil.verifyPermissionsRationale(PERMISSIONS_LOCATION, (Activity) getView())) {

      // Display a SnackBar with an explanation and a button to trigger the request.
      getView().isPermissionGranted(false);
      getView().showSnackBar(R.string.permission_location);
    } else {
      // request location permissions have not been granted yet. Request them directly.
      getView().requestPermission(PERMISSIONS_LOCATION, 0);
    }
  }

  @Override public void requestLocationPermission() {
    getView().requestPermission(PERMISSIONS_LOCATION, 0);
  }

  protected void checkLocationSettings() {
    PendingResult<LocationSettingsResult> result =
        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
            mLocationSettingsRequest);
    result.setResultCallback(this);
  }

  @Override public void start() {
    super.start();
    mGoogleApiClient.connect();
  }

  @Override public void resume() {
    super.resume();
        /*if (!isEnableGps && listOfPlaces == null && PermissionUtil.verifyPermissionsGranted(PERMISSIONS_LOCATION, getView().getActivity()))
            startLocationUpdates();*/

  }

  @Override public void pause() {
    super.pause();
    // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
    if (mGoogleApiClient.isConnected()) {
      stopLocationUpdates();
    }
  }

  @Override public void stop() {
    super.stop();
    mGoogleApiClient.disconnect();
  }

  protected void stopLocationUpdates() {
    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        .setResultCallback(status -> {
        });
  }

  @Override public void onResult(LocationSettingsResult locationSettingsResult) {
    final Status status = locationSettingsResult.getStatus();
    switch (status.getStatusCode()) {
      case LocationSettingsStatusCodes.SUCCESS:
        Log.i(TAG, "All location settings are satisfied.");
        startLocationUpdates();

        break;
      case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to"
            + "upgrade location settings ");

        try {
          // Show the dialog by calling startResolutionForResult(), and check the result
          // in onActivityResult().
          status.startResolutionForResult((Activity) getView(), REQUEST_CHECK_SETTINGS);
        } catch (IntentSender.SendIntentException e) {
          Log.i(TAG, "PendingIntent unable to execute request.");
        }
        break;
      case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
        Log.i(TAG,
            "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
        break;
    }
  }

  @SuppressWarnings("MissingPermission") protected void startLocationUpdates() {
    //// TODO: permission already granted to be fixed
    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
        this).setResultCallback(status -> Log.d(TAG, status.toString()));
  }

  protected void buildLocationSettingsRequest() {
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    builder.addLocationRequest(mLocationRequest);
    mLocationSettingsRequest = builder.build();
  }

  @Override public void activityResult(int requestCode, int resultCode, Intent data) {
    //super.activityResult(requestCode, resultCode, data);

    switch (requestCode) {
      // Check for the integer request code originally supplied to startResolutionForResult().
      case REQUEST_CHECK_SETTINGS:
        switch (resultCode) {
          case Activity.RESULT_OK:
            getView().isPermissionGranted(true);
            Log.i(TAG, "User agreed to make required location settings changes.");
            startLocationUpdates();
            break;
          case Activity.RESULT_CANCELED:
            getView().isPermissionGranted(false);
            getView().showSnackBar(R.string.enable_location);
            Log.i(TAG, "User chose not to make required location settings changes.");
            break;
        }
        break;
    }
  }

  @Override public void requestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    //super.requestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == 0) {
      // We have requested multiple permissions for location, so all of them need to be
      // checked.
      if (PermissionUtil.verifyPermissions(grantResults)) {
        // All required permissions have been granted, display location fragment.
        checkLocationSettings();
        getView().isPermissionGranted(true);
      } else {
        getView().isPermissionGranted(false);
        getView().showSnackBar(R.string.permissions_not_granted);
      }
    } else {
      // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override public void onConnected(Bundle bundle) {

  }

  @Override public void onConnectionSuspended(int i) {

  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {

  }

  @Override public void onLocationChanged(Location location) {
    stopLocationUpdates();
    locationStr = location.getLatitude() + "," + location.getLongitude();
    getView().isPermissionGranted(true);
    getView().setLocation(locationStr);
  }
}
