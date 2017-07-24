package com.abozaid.cityguide.interactors;

import com.abozaid.cityguide.domain.callbacks.InteractorCallback;
import com.abozaid.cityguide.domain.calls.DataCalls;
import com.abozaid.cityguide.domain.models.ListOfPlacesModel;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by aliabozaid on 7/16/17.
 */

@RunWith(MockitoJUnitRunner.class) public class PlacesInteractorTest {
  private static final String OUTPUT = "TEST_OUTPUT";
  private static final String LOCATION = "TEST_LOCATION";
  private static final int RADIUS = 123;
  private static final String TYPE = "TEST_TYPE";
  private static final String KEY = "TEST_KEY";

  @Mock private DataCalls dataCalls;
  @Mock private ListOfPlacesModel listOfPlacesModel;
  @Mock private Throwable throwable;

  final String STATUS = "OK";
  final String NAME = "NAME";
  final String ID = "ID";
  final String PLACE_ID = "PLACE_ID";
  private static final String THROWABLE_MESSAGE = "CUSTOM_ERROR";

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

  @Test public void testGetPlacesInteractorHappyCase() {
    doAnswer(new Answer<Void>() {
      public Void answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        interactorCallback = (InteractorCallback) args[5];
        interactorCallback.success(listOfPlacesModel);
        return null;
      }
    }).when(dataCalls).getPlaces(OUTPUT, LOCATION, RADIUS, TYPE, KEY, interactorCallback);
    dataCalls.getPlaces(OUTPUT, LOCATION, RADIUS, TYPE, KEY, interactorCallback);
    verify(dataCalls).getPlaces(OUTPUT, LOCATION, RADIUS, TYPE, KEY, interactorCallback);
    verifyNoMoreInteractions(dataCalls);
  }

  @Test public void testGetPlacesInteractorFailureCase() {

    doAnswer(new Answer<Void>() {
      public Void answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        interactorCallback = (InteractorCallback) args[5];
        interactorCallback.error(throwable);
        return null;
      }
    }).when(dataCalls).getPlaces(OUTPUT, LOCATION, RADIUS, TYPE, KEY, interactorCallback);
    dataCalls.getPlaces(OUTPUT, LOCATION, RADIUS, TYPE, KEY, interactorCallback);
    verify(dataCalls).getPlaces(OUTPUT, LOCATION, RADIUS, TYPE, KEY, interactorCallback);
    verifyNoMoreInteractions(dataCalls);
  }

  InteractorCallback interactorCallback = new InteractorCallback<ListOfPlacesModel>() {
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
