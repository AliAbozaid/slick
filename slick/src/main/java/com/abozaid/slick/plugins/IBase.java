package com.abozaid.slick.plugins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by aliabozaid on 7/11/17.
 */

public interface IBase {
  void showMessage(int messageResourceId);

  void showMessage(String message);

  String getString(int id);

  String getString(int resId, Object... formatArgs);

  void startActivity(Intent intent);

  void startActivity(Class<?> klass);

  void startActivityForResult(Class<?> klass, int requestCode);

  void startActivityForResult(Intent intent, int requestCode);

  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults);

  Intent getIntent(Class<?> klass);

  void finishActivity();

  void finishActivity(int result);

  void finishActivity(int resultOk, Intent createIntent);

  void handleError(int resourceId);

  void requestPermission(String[] permissions, int requestCode);

  void showProgressBar(String message);

  void hideProgressBar();

  SharedPreferences getSharedPreferences();
}
