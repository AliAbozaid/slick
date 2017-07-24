package com.abozaid.slick.plugins;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by aliabozaid on 7/13/17.
 */

public interface IBasePresenter {
  void activityResult(int requestCode, int resultCode, Intent data);

  void onSaveInstanceState(Bundle outState);

  void requestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults);

  void pause();

  void start();

  void stop();

  void resume();

  void destroy();

  void showProgressDialog(String title, String body);

  void hideProgressDialog();
}
