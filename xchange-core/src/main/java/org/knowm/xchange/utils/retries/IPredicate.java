package org.knowm.xchange.utils.retries;

public interface IPredicate<T> {
  public boolean test(T t);
}
