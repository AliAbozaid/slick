package com.abozaid.slick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.abozaid.slick.plugins.IBaseActivity;
import com.abozaid.slick.presenters.BaseActivityPresenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by aliabozaid on 7/19/17.
 */

@RunWith(MockitoJUnitRunner.class) public class BaseActivityPresenterTest { /* extends BasePresenter<IBaseFragment> */

  @Mock BaseActivityPresenterImplTest presenter;
  @Mock Intent intent;
  @Mock Bundle bundle;
  String[] permissions;
  int[] permissionsGranted;
  Object[] formatArgs;

  @Before public void setUp() {
    presenter.start();
    presenter.resume();
    presenter.pause();
    presenter.stop();
    presenter.destroy();
    permissionsGranted = new int[2];
    permissions = new String[2];
    formatArgs = new Object[2];
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertNotNull(presenter);
  }

  @Test public void shouldLifeCycleCalled() throws Exception {

    verify(presenter, times(1)).start();
    verify(presenter, times(1)).resume();
    verify(presenter, times(1)).pause();
    verify(presenter, times(1)).stop();
    verify(presenter, times(1)).destroy();
    verifyNoMoreInteractions(presenter);
  }

  @Test public void shouldRequestRunTimePermissionCalled() throws Exception {

    presenter.requestPermissionsResult(0, permissions, permissionsGranted);

    verify(presenter, times(1)).requestPermissionsResult(0, permissions, permissionsGranted);
  }

  @Test public void shouldActivityResultCalled() throws Exception {

    presenter.activityResult(0, 0, intent);

    verify(presenter, times(1)).activityResult(0, 0, intent);
  }

  @Test public void shouldSaveStateCalled() throws Exception {

    presenter.onSaveInstanceState(bundle);

    verify(presenter, times(1)).onSaveInstanceState(bundle);
  }

  @Test public void shouldShowProgressDialogCalled() throws Exception {

    presenter.showProgressDialog("", "");

    verify(presenter, times(1)).showProgressDialog("", "");
  }

  @Test public void shouldHideProgressDialogCalled() throws Exception {

    presenter.hideProgressDialog();

    verify(presenter, times(1)).hideProgressDialog();
  }

  @Test public void shouldFinishCalled() throws Exception {

    presenter.finishCalled();

    verify(presenter, times(1)).finishCalled();
  }

  @Test public void shouldFinishWithResultCalled() throws Exception {

    presenter.finishWithResultCalled(0);

    verify(presenter, times(1)).finishWithResultCalled(0);
  }

  @Test public void shouldShowMessageCalled() throws Exception {

    presenter.showMessageCalled(0);

    verify(presenter, times(1)).showMessageCalled(0);
  }

  @Test public void shouldShowMessageStringCalled() throws Exception {

    presenter.showMessageCalled("");

    verify(presenter, times(1)).showMessageCalled("");
  }

  @Test public void shouldGetStringCalled() throws Exception {

    presenter.getStringCalled(0);

    verify(presenter, times(1)).getStringCalled(0);
  }

  @Test public void shouldGetStringWithFormatCalled() throws Exception {

    presenter.getStringCalled(0, formatArgs);

    verify(presenter, times(1)).getStringCalled(0, formatArgs);
  }

  @Test public void shouldStartActivityCalled() throws Exception {

    presenter.startActivityCalled();

    verify(presenter, times(1)).startActivityCalled();
  }

  @Test public void shouldStartActivityForResultCalled() throws Exception {

    presenter.startActivityForResultCalled(0);

    verify(presenter, times(1)).startActivityForResultCalled(0);
  }

  @Test public void shouldGetSharedPreferencesCalled() throws Exception {

    presenter.getSharedPreferencesCalled();

    verify(presenter, times(1)).getSharedPreferencesCalled();
    ;
  }

  public class BaseActivityPresenterImplTest extends BaseActivityPresenter<IBaseActivity> {
    public BaseActivityPresenterImplTest(IBaseActivity view) {
      super(view);
    }

    public void finishCalled() throws Exception {

      finish();
    }

    public void finishWithResultCalled(int result) throws Exception {

      finishActivity(result);
    }

    public void showMessageCalled(int result) throws Exception {

      showMessage(result);
    }

    public void showMessageCalled(String key) throws Exception {

      showMessage(key);
    }

    public void getStringCalled(int resourceId) throws Exception {

      getString(resourceId);
    }

    public void getStringCalled(int resourceId, Object... formatArgs) throws Exception {

      getString(resourceId, formatArgs);
    }

    public void startActivityCalled() throws Exception {

      startActivity(intent);
    }

    public void startActivityForResultCalled(int resourceId) throws Exception {

      startActivityForResult(intent, resourceId);
    }

    public SharedPreferences getSharedPreferencesCalled() throws Exception {

      return getSharedPreferences();
    }
  }
}
