package com.abozaid.cityguide.presentation.ui.activities.plugins;

import com.abozaid.slick.plugins.IBaseActivity;

/**
 * Created by aliabozaid on 7/13/17.
 */

public interface IMainActivity extends IBaseActivity {

  void isPermissionGranted(boolean isGranted);

  void showSnackBar(int resourceId);

  void setLocation(String location);
}
