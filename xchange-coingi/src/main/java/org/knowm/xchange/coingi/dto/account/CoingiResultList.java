package org.knowm.xchange.coingi.dto.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
