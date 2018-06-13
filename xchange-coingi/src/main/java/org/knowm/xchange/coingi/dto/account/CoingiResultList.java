package org.knowm.xchange.coingi.dto.account;

import java.util.*;

/** Base class for result lists. */
class CoingiResultList<T> extends ArrayList<T> {
  CoingiResultList() {}

  CoingiResultList(Collection<T> c) {
    super(c);
  }

  public List<T> getList() {
    return Collections.unmodifiableList(this);
  }

  public Set<T> getSet() {
    return Collections.unmodifiableSet(new HashSet<>(this));
  }
}
