package com.abozaid.slick.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.abozaid.slick.plugins.IBaseFragment;
import com.abozaid.slick.plugins.IPresenter;
import java.lang.ref.WeakReference;

/**
 * Created by aliabozaid on 5/2/17.
 */

public abstract class BaseFragment extends Fragment implements IBaseFragment {
  WeakReference<Activity> activity;
  public SharedPreferences sharedPreferences;

  public abstract IPresenter getPresenter();

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = new WeakReference(getActivity());
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.get());
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (getPresenter() != null) getPresenter().onSaveInstanceState(outState);
  }

  @Override public void onStart() {
    super.onStart();
    if (getPresenter() != null) {
      getPresenter().start();
    }
  }

  @Override public void onPause() {
    super.onPause();
    if (getPresenter() != null) {
      getPresenter().pause();
    }
  }

  @Override public void onResume() {
    super.onResume();
    if (getPresenter() != null) {
      getPresenter().resume();
    }
  }

  @Override public void onStop() {
    super.onStop();
    if (getPresenter() != null) {
      getPresenter().stop();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (getPresenter() != null) {
      getPresenter().destroy();
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (getPresenter() != null) getPresenter().activityResult(requestCode, resultCode, data);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (getPresenter() != null) {
      getPresenter().requestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getPresenter() != null) {
      getPresenter().userVisibleHint(isVisibleToUser);
    }
  }

  @Override public void showMessage(int messageResourceId) {
    showStatusMessage(getString(messageResourceId));
  }

  @Override public void showMessage(String message) {
    showStatusMessage(message);
  }

  public void showStatusMessage(String message) {
    Toast.makeText(activity.get(), message, Toast.LENGTH_LONG).show();
  }

  @Override public void startActivity(Class<?> klass) {
    Intent intent = getIntent(klass);
    startActivity(intent);
  }

  @Override public void startActivityForResult(Class<?> klass, int requestCode) {
    Intent intent = getIntent(klass);
    startActivityForResult(intent, requestCode);
  }

  @Override public Intent getIntent(Class<?> klass) {
    return new Intent(activity.get(), klass);
  }

  @Override public void finishActivity() {
    activity.get().finish();
  }

  @Override public void finishActivity(int result) {
    if (activity.get().getParent() == null) {
      activity.get().setResult(result);
    } else {
      activity.get().getParent().setResult(result);
    }
    activity.get().finish();
  }

  @Override public void finishActivity(int resultOk, Intent createIntent) {
    if (activity.get().getParent() == null) {
      activity.get().setResult(resultOk, createIntent);
    } else {
      activity.get().getParent().setResult(resultOk, createIntent);
    }

    activity.get().finish();
  }

  @Override public SharedPreferences getSharedPreferences() {
    return sharedPreferences;
  }

  @Override public void handleError(int resourceId) {
    showMessage(resourceId);
    finishActivity();
  }

  @Override public void requestPermission(String[] permissions, int requestCode) {
    requestPermissions(permissions, requestCode);
  }

  @Override public void showProgressBar(String message) {

  }

  @Override public void hideProgressBar() {

  }

  @Override public void showLoading(boolean isLoading) {

  }
}
