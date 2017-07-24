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
package com.abozaid.slick;

import com.abozaid.slick.interactors.RxBaseInteractor;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class) public class RxBaseInteractorTest {

  private UseCaseTestClass useCase;

  private TestDisposableObserver<Object> testObserver;
  @Mock Observable<Object> observable;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before public void setUp() {
    this.useCase = new UseCaseTestClass();
    this.testObserver = new TestDisposableObserver<>();
  }

  @Test public void testBuildUseCaseObservableReturnCorrectResult() {
    useCase.execute(testObserver, Params.EMPTY);

    assertThat(testObserver.valuesCount).isZero();
  }

  @Test public void testSubscriptionWhenExecutingUseCase() {
    useCase.execute(testObserver, Params.EMPTY);
    useCase.dispose();

    assertThat(testObserver.isDisposed()).isTrue();
  }

  @Test public void testShouldFailWhenExecuteWithNullObserver() {
    expectedException.expect(NullPointerException.class);
    useCase.execute(null, Params.EMPTY);
  }

  private class UseCaseTestClass extends RxBaseInteractor<Object, Params> {

    UseCaseTestClass() {
      super();
    }

    @Override public Observable<Object> buildUseCaseObservable(Params params) {
      return Observable.empty();
    }

    @Override public void execute(DisposableObserver<Object> observer, Params params) {
      Preconditions.checkNotNull(observer);
      super.addDisposable(observable.subscribeWith(observer));
    }
  }

  private static class TestDisposableObserver<T> extends DisposableObserver<T> {
    private int valuesCount = 0;

    @Override public void onNext(T value) {
      valuesCount++;
    }

    @Override public void onError(Throwable e) {
      // no-op by default.
    }

    @Override public void onComplete() {
      // no-op by default.
    }
  }

  private static class Params {
    private static Params EMPTY = new Params();

    private Params() {
    }
  }
}
