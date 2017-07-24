package com.abozaid.cityguide.domain.callbacks;

/**
 * Created by aliabozaid on 5/2/17.
 */

public interface InteractorCallback<T> {
  void success(T t);

  void error(Throwable throwable);
}
