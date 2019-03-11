package org.knowm.xchange.exx.dto.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;

public class EXXCancelOrderByCurrencyPair implements CancelOrderByCurrencyPair {
  private CurrencyPair currencyPair;
  private String id;

  /** No args constructor for use in serialization */
  public EXXCancelOrderByCurrencyPair() {}

  /**
   * @param id
   * @param currencyPair
   */
  public EXXCancelOrderByCurrencyPair(CurrencyPair currencyPair, String id) {
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
