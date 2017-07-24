package com.abozaid.slick.interactors;

import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aliabozaid on 7/11/17.
 */

public abstract class RxBaseInteractor<T, Params> {

  private final CompositeDisposable disposables;

  public RxBaseInteractor() {
    this.disposables = new CompositeDisposable();
  }

  abstract public Observable<T> buildUseCaseObservable(Params params);

  public void execute(DisposableObserver<T> observer, Params params) {
    Preconditions.checkNotNull(observer);
    final Observable<T> observable = this.buildUseCaseObservable(params)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    addDisposable(observable.subscribeWith(observer));
  }

  public void dispose() {
    if (!disposables.isDisposed()) {
      disposables.dispose();
    }
  }

  protected void addDisposable(Disposable disposable) {
    Preconditions.checkNotNull(disposable);
    Preconditions.checkNotNull(disposables);
    disposables.add(disposable);
  }
}
