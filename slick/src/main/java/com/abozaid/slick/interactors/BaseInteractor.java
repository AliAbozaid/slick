package com.abozaid.slick.interactors;

/**
 * Created by aliabozaid on 5/2/17.
 */

public abstract class BaseInteractor<Params> {
  public abstract void execute(Params params);
}

