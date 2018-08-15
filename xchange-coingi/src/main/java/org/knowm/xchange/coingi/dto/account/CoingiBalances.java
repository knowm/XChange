package org.knowm.xchange.coingi.dto.account;

import java.util.Collection;

/** List of user balances */
public class CoingiBalances extends CoingiResultList<CoingiBalance> {
  CoingiBalances() {}

  public CoingiBalances(Collection<CoingiBalance> c) {
    super(c);
  }
}
