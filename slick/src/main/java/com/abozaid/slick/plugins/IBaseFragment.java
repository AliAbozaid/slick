package com.abozaid.slick.plugins;

import android.app.Activity;

/**
 * Created by aliabozaid on 5/2/17.
 */

public interface IBaseFragment extends IBase {

  Activity getActivity();

  void setUserVisibleHint(boolean isVisibleToUser);

  void showLoading(boolean isLoading);
}
