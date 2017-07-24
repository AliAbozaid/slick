package com.abozaid.slick;

import com.abozaid.slick.interactors.BaseInteractor;
import com.fernandocejas.arrow.checks.Preconditions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by aliabozaid on 7/18/17.
 */

@RunWith(MockitoJUnitRunner.class) public class BaseInteractorTest {

  private UseCaseTestClass useCase;
  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before public void setUp() {
    this.useCase = new UseCaseTestClass();
  }

  @Test public void testBuildUseCaseReturnCorrectResult() {
    useCase.execute(Params.EMPTY);
  }

  @Test public void testShouldFailWhenExecuteWithNullObserver() {
    expectedException.expect(NullPointerException.class);
    useCase.execute(null);
  }

  private class UseCaseTestClass extends BaseInteractor<Params> {

    UseCaseTestClass() {
      super();
    }

    @Override public void execute(Params params) {
      Preconditions.checkNotNull(params);
    }
  }

  private static class Params {
    private static BaseInteractorTest.Params EMPTY = new BaseInteractorTest.Params();

    private Params() {
    }
  }
}
