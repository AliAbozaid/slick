package com.abozaid.cityguide.presentation.ui.fragments.plugins;

import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import com.abozaid.slick.plugins.IBaseFragment;

/**
 * Created by aliabozaid on 7/12/17.
 */

public interface IPlacesFragment extends IBaseFragment {
  void updateView(ListOfPlacesModel data);

  void showError(Throwable t);
}
