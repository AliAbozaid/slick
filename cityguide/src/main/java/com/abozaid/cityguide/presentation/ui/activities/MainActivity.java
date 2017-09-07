package com.abozaid.cityguide.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abozaid.cityguide.R;
import com.abozaid.cityguide.presentation.presenters.plugins.IMainPresenter;
import com.abozaid.cityguide.presentation.ui.activities.plugins.IMainActivity;
import com.abozaid.cityguide.presentation.ui.adapters.TabAdapters;
import com.abozaid.cityguide.presentation.ui.fragments.PlacesFragment;
import com.abozaid.slick.plugins.IBasePresenter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import javax.inject.Inject;

public class MainActivity extends MyBaseActivity implements IMainActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.main_layout) RelativeLayout mainLayout;
  @BindView(R.id.viewpagertab) SmartTabLayout viewPagerTab;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.location_permission) TextView locationPermission;

  @BindArray(R.array.types) String[] titles;
  TabAdapters adapter;

  @Inject IMainPresenter presenter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getActivityComponent().inject(this);
    setSupportActionBar(toolbar);

    viewPagerTab.setVisibility(View.VISIBLE);
    viewPager.setVisibility(View.VISIBLE);

    fab.setOnClickListener(view -> presenter.getLocationPermission());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_about) {
      Intent intent = new Intent(MainActivity.this, AboutActivity.class);
      startActivity(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void setUpTab(String location) {

    adapter = new TabAdapters(getSupportFragmentManager());
    for (String title : titles) {
      adapter.addFrag(PlacesFragment.newInstance(title, location), title);
    }
    viewPager.setAdapter(adapter);
    viewPagerTab.setViewPager(viewPager);
    viewPager.setOffscreenPageLimit(3);
  }

  @Override public void isPermissionGranted(boolean isGranted) {
    if (isGranted) {
      locationPermission.setVisibility(View.GONE);
      fab.setVisibility(View.GONE);
    } else {

      locationPermission.setVisibility(View.VISIBLE);
      fab.setVisibility(View.VISIBLE);
      locationPermission.setText(R.string.enable_location);
    }
  }

  @Override public void showSnackBar(int resourceId) {
    if (resourceId == R.string.permission_location) {
      Snackbar.make(mainLayout, R.string.permission_location, Snackbar.LENGTH_INDEFINITE)
          .setAction(R.string.ok, view -> presenter.requestLocationPermission())
          .show();
    } else {
      Snackbar.make(mainLayout, resourceId, Snackbar.LENGTH_SHORT).show();
    }
  }

  @Override public void setLocation(String location) {
    setUpTab(location);
  }

  @Override public IBasePresenter getPresenter() {
    return presenter;
  }
}
