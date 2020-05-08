package org.knowm.xchange.utils.retries;

@Deprecated
public interface IPredicate<T> {
  boolean test(T t);
}
