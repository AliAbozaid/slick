/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abozaid.cityguide.interactors;

import com.abozaid.cityguide.domain.calls.DataCalls;
import com.abozaid.cityguide.domain.interactors.PlacesRxInteractor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class) public class PlacesRxInteractorTest {

  private static final String OUTPUT = "TEST_OUTPUT";
  private static final String LOCATION = "TEST_LOCATION";
  private static final int RADIUS = 123;
  private static final String TYPE = "TEST_TYPE";
  private static final String KEY = "TEST_KEY";

  private PlacesRxInteractor placesRxInteractor;

  @Mock private DataCalls dataCalls;

  @Before public void setUp() {
    placesRxInteractor = new PlacesRxInteractor(dataCalls);
  }

  @Test public void testGetPlacesInteractorObservableHappyCase() {
    placesRxInteractor.buildUseCaseObservable(
        PlacesRxInteractor.Params.getParams(OUTPUT, LOCATION, RADIUS, TYPE, KEY));

    verify(dataCalls).getPlacesRx(OUTPUT, LOCATION, RADIUS, TYPE, KEY);
    verifyNoMoreInteractions(dataCalls);
  }
}
