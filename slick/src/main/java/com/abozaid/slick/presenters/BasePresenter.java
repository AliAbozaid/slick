package com.abozaid.slick.presenters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.abozaid.slick.plugins.IBaseFragment;
import com.abozaid.slick.plugins.IPresenter;
import java.lang.ref.WeakReference;

/**
 * Created by aliabozaid on 5/2/17.
 */

public class BasePresenter<T extends IBaseFragment> implements IPresenter {

  private WeakReference<T> _viewReference;

  private ProgressDialog mProgressDialog;

  public BasePresenter(@NonNull T view) {
    super();
    _viewReference = new WeakReference<T>(view);
  }

  protected Intent getIntent(Class<?> classType) {
    return getView().getIntent(classType);
  }

  protected void finishActivity(int result) {
    getView().finishActivity(result);
  }

  protected void finish() {
    getView().finishActivity();
  }

  protected void showMessage(int messageResourceId) {
    getView().showMessage(messageResourceId);
  }

  protected void showMessage(String message) {
    getView().showMessage(message);
  }

  protected String getString(int resourceId) {
    return getView().getString(resourceId);
  }

  protected String getString(int resourceId, Object... formatArgs) {
    return getView().getString(resourceId, formatArgs);
  }

  protected void startActivity(Class<?> classType) {
    getView().startActivity(classType);
  }

  protected void startActivity(@NonNull Intent intent) {
    getView().startActivity(intent);
  }

  protected void startActivityForResult(Class<?> classType, int requestCode) {
    Intent intent = getIntent(classType);
    getView().startActivityForResult(intent, requestCode);
  }

  protected void startActivityForResult(@NonNull Intent intent, int requestCode) {
    getView().startActivityForResult(intent, requestCode);
  }

  protected SharedPreferences getSharedPreferences() {
    return getView().getSharedPreferences();
  }

  @Override public void activityResult(int requestCode, int resultCode, Intent data) {
  }

  @Override public void onSaveInstanceState(Bundle outState) {
  }

  @Override public void requestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
  }

  @Override public void userVisibleHint(boolean isVisibleToUser) {
    return;
  }

  @Override public void pause() {
  }

  @Override public void start() {
  }

  @Override public void stop() {
  }

  @Override public void resume() {
  }

  @Override public void destroy() {
  }

  protected T getView() {
    return _viewReference.get();
  }

  public void showProgressDialog(String title, String body) {
    if (mProgressDialog == null) {
      mProgressDialog = ProgressDialog.show(getView().getActivity(), title, body, true, false);
    }
    mProgressDialog.show();
  }

  public void hideProgressDialog() {
    try {
      if (mProgressDialog != null && mProgressDialog.isShowing()) {
        mProgressDialog.dismiss();
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
