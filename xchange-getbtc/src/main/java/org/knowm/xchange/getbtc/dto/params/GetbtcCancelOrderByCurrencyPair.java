package org.knowm.xchange.getbtc.dto.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;

public class GetbtcCancelOrderByCurrencyPair implements CancelOrderByCurrencyPair {
  private CurrencyPair currencyPair;
  private String id;

  /** No args constructor for use in serialization */
  public GetbtcCancelOrderByCurrencyPair() {}

  /**
   * @param id
   * @param currencyPair
   */
  public GetbtcCancelOrderByCurrencyPair(CurrencyPair currencyPair, String id) {
    super();
    this.currencyPair = currencyPair;
    this.id = id;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
