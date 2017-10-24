package org.knowm.xchange.utils.retries;

public interface IPredicate<T> {
  boolean test(T t);
}
