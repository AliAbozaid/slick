package com.abozaid.cityguide.presenters;

import com.abozaid.cityguide.domain.callbacks.GetPlacesCallBack;
import com.abozaid.cityguide.domain.interactors.PlacesInteractor;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by aliabozaid on 7/16/17.
 */
@RunWith(MockitoJUnitRunner.class) public class PlacesPresenterTest {

  private static final String OUTPUT = "TEST_OUTPUT";
  private static final String LOCATION = "TEST_LOCATION";
  private static final int RADIUS = 123;
  private static final String TYPE = "TEST_TYPE";
  private static final String KEY = "TEST_KEY";
  //response
  final String STATUS = "OK";
  final String NAME = "NAME";
  final String ID = "ID";
  final String PLACE_ID = "PLACE_ID";
  private static final String THROWABLE_MESSAGE = "CUSTOM_ERROR";

  @Mock PlacesInteractor placesInteractor;
  @Mock private ListOfPlacesModel listOfPlacesModel;
  @Mock private Throwable throwable;

  @Before public void setUp() {

    throwable = new Throwable(THROWABLE_MESSAGE);
    listOfPlacesModel.status = STATUS;
    ListOfPlacesModel.Results result = new ListOfPlacesModel().new Results();
    result.name = NAME;
    result.id = ID;
    result.place_id = PLACE_ID;

    listOfPlacesModel.results = new ArrayList<>();
    listOfPlacesModel.results.add(result);
  }

  @Test public void testGetPlacesPresenterHappyCase() {

    doAnswer(new Answer<Void>() {
      public Void answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        listOfPlacesModel = (ListOfPlacesModel) args[0];
        getPlacesCallBack.success(listOfPlacesModel);
        return null;
      }
    }).when(placesInteractor).success(listOfPlacesModel);
    placesInteractor.success(listOfPlacesModel);
    verify(placesInteractor).success(listOfPlacesModel);
  }

  @Test public void testGetPlacesPresenterFailureCase() {

    Mockito.doAnswer(new Answer<Void>() {
      public Void answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        throwable = (Throwable) args[0];
        getPlacesCallBack.error(throwable);
        return null;
      }
    }).when(placesInteractor).error(throwable);
    placesInteractor.error(throwable);
    verify(placesInteractor).error(throwable);
  }

  GetPlacesCallBack getPlacesCallBack = new GetPlacesCallBack() {
    @Override public void success(ListOfPlacesModel data) {
      assertNotNull(data);
      assertEquals(listOfPlacesModel, data);
      listOfPlacesModel = data;
      assertEquals(listOfPlacesModel.status, STATUS);
      assertEquals(listOfPlacesModel.results.get(0).name, NAME);
      assertEquals(listOfPlacesModel.results.get(0).id, ID);
      assertEquals(listOfPlacesModel.results.get(0).place_id, PLACE_ID);
    }

    @Override public void error(Throwable throwable) {
      assertNotNull(throwable);
      assertEquals(throwable, throwable);
      assertEquals(throwable.getMessage(), THROWABLE_MESSAGE);
    }
  };
}
