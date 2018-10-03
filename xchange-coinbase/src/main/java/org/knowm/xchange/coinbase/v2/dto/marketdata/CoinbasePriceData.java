package org.knowm.xchange.coinbase.v2.dto.marketdata;

import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;

public class CoinbasePriceData {

  private CoinbasePrice data;

  public CoinbasePrice getData() {
    return data;
  }

  public void setData(CoinbasePrice data) {
    this.data = data;
  }
}
