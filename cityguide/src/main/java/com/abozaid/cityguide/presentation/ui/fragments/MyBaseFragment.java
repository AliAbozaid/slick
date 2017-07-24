package com.abozaid.cityguide.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.Unbinder;
import com.abozaid.cityguide.presentation.dagger.components.DaggerFragmentComponent;
import com.abozaid.cityguide.presentation.dagger.components.FragmentComponent;
import com.abozaid.cityguide.presentation.dagger.modules.FragmentModule;
import com.abozaid.slick.plugins.IBaseFragment;
import com.abozaid.slick.ui.BaseFragment;

/**
 * Created by aliabozaid on 7/12/17.
 */

public abstract class MyBaseFragment extends BaseFragment {

  FragmentComponent fragmentComponent;
  public Unbinder unbinder;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeInjector(this);
  }

  protected void initializeInjector(IBaseFragment iBaseFragment) {
    fragmentComponent =
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule(iBaseFragment)).build();
    getFragmentComponent();
  }

  public FragmentComponent getFragmentComponent() {
    return fragmentComponent;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) unbinder.unbind();
  }
}
