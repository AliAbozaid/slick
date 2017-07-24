package com.abozaid.slick.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.abozaid.slick.plugins.IBaseActivity;
import com.abozaid.slick.plugins.IBasePresenter;

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

  public SharedPreferences sharedPreferences;

  public abstract IBasePresenter getPresenter();

  @Override public void onCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    IBasePresenter presenter = getPresenter();
    if (presenter != null) {
      presenter.onSaveInstanceState(outState);
    }
  }

  protected void onResume() {
    super.onResume();
    if (getPresenter() != null) {
      getPresenter().resume();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    if (getPresenter() != null) {
      getPresenter().pause();
    }
  }

  @Override protected void onStart() {
    super.onStart();
    if (getPresenter() != null) {
      getPresenter().start();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (getPresenter() != null) {
      getPresenter().stop();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    IBasePresenter presenter = getPresenter();
    if (presenter != null) {
      presenter.activityResult(requestCode, resultCode, data);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (getPresenter() != null) {
      getPresenter().requestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override public void showMessage(int messageResourceId) {
    showStatusMessage(getString(messageResourceId));
  }

  @Override public void showMessage(String message) {
    showStatusMessage(message);
  }

  public void showStatusMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
    return new Intent(this, klass);
  }

  @Override public void finishActivity() {
    finish();
  }

  @Override public void finishActivity(int result) {
    if (getParent() == null) {
      setResult(result);
    } else {
      getParent().setResult(result);
    }
    finish();
  }

  @Override public void finishActivity(int result, Intent intent) {
    if (getParent() == null) {
      setResult(result, intent);
    } else {
      getParent().setResult(result, intent);
    }

    finish();
  }

  @Override public SharedPreferences getSharedPreferences() {
    return sharedPreferences;
  }

  @Override public void handleError(int resourceId) {
    showMessage(resourceId);
    finishActivity();
  }

  @Override public void requestPermission(String[] permissions, int requestCode) {
    ActivityCompat.requestPermissions(this, permissions, requestCode);
  }

  @Override public void showProgressBar(String message) {
  }

  @Override public void hideProgressBar() {

  }
}
