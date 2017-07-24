package com.abozaid.cityguide.presentation.presenters;

import com.abozaid.cityguide.BuildConfig;
import com.abozaid.cityguide.CityGuideApp;
import com.abozaid.cityguide.domain.callbacks.GetPlacesCallBack;
import com.abozaid.cityguide.domain.interactors.PlacesInteractor;
import com.abozaid.cityguide.domain.interactors.PlacesRxInteractor;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import com.abozaid.cityguide.presentation.dagger.components.ApplicationComponent;
import com.abozaid.cityguide.presentation.presenters.plugins.IPlacesPresenter;
import com.abozaid.cityguide.presentation.ui.fragments.plugins.IPlacesFragment;
import com.abozaid.slick.interactors.DefaultObserver;
import com.abozaid.slick.presenters.BasePresenter;
import javax.inject.Inject;

/**
 * Created by aliabozaid on 7/12/17.
 */

public class PlacesPresenter extends BasePresenter<IPlacesFragment> implements IPlacesPresenter {

  @Inject PlacesInteractor placesInteractor;
  @Inject PlacesRxInteractor placesRxInteractor;
  private static final String TAG = PlacesPresenter.class.getSimpleName();
  String type;
  ListOfPlacesModel listOfPlaces;

  @Inject public PlacesPresenter(IPlacesFragment view) {
    super(view);
    getApplicationComponent().inject(this);
    //init();
  }

  @Override public void getPlaces(String location, String type) {
    this.type = type;
    getView().showLoading(true);
    //without using rx java to
    /*placesInteractor.execute(
        PlacesInteractor.Params.getParams(BuildConfig.OUTPUT, location, BuildConfig.RADIUS, type,
            BuildConfig.API_KEY, getPlacesCallBack));*/
    // using rx java
    placesRxInteractor.execute(new GetPlacesObserver(),
        PlacesRxInteractor.Params.getParams(BuildConfig.OUTPUT, location, BuildConfig.RADIUS, type,
            BuildConfig.API_KEY));
  }

  GetPlacesCallBack getPlacesCallBack = new GetPlacesCallBack() {
    @Override public void success(ListOfPlacesModel listOfPlacesModel) {
      getView().showLoading(false);
      getView().updateView(listOfPlacesModel);
      listOfPlaces = listOfPlacesModel;
    }

    @Override public void error(Throwable throwable) {
      getView().showLoading(false);
      //todo handle with custom error class
      getView().showError(throwable);
    }
  };

  protected ApplicationComponent getApplicationComponent() {
    return ((CityGuideApp) getView().getActivity().getApplication()).getApiComponent();
  }

  private final class GetPlacesObserver extends DefaultObserver<ListOfPlacesModel> {

    @Override public void onComplete() {
      getView().showLoading(false);
    }

    @Override public void onError(Throwable e) {
      getView().showLoading(false);
      getView().showError(e);
    }

    @Override public void onNext(ListOfPlacesModel listOfPlacesModel) {
      getView().updateView(listOfPlacesModel);
    }
  }

  @Override public void destroy() {
    super.destroy();
    placesRxInteractor.dispose();
  }
}
