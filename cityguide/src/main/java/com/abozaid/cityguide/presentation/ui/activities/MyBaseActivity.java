package com.abozaid.cityguide.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.Unbinder;
import com.abozaid.cityguide.presentation.dagger.components.ActivityComponent;
import com.abozaid.cityguide.presentation.dagger.components.DaggerActivityComponent;
import com.abozaid.cityguide.presentation.dagger.modules.ActivityModule;
import com.abozaid.slick.plugins.IBaseActivity;
import com.abozaid.slick.ui.BaseActivity;

/**
 * Created by aliabozaid on 7/13/17.
 */

public abstract class MyBaseActivity extends BaseActivity {

  ActivityComponent activityComponent;
  public Unbinder unbinder;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentBuild(this);
  }

  public void fragmentBuild(IBaseActivity iBaseActivity) {
    activityComponent =
        DaggerActivityComponent.builder().activityModule(new ActivityModule(iBaseActivity)).build();
  }

  public ActivityComponent getActivityComponent() {
    return activityComponent;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) unbinder.unbind();
  }
}
