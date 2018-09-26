package org.knowm.xchange.getbtc.dto.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class GetbtcOpenOrdersParams implements OpenOrdersParams {
  private CurrencyPair currencyPair;
  private String type = "buy";
  private int pageIndex = 1;

  /** No args constructor for use in serialization */
  public GetbtcOpenOrdersParams() {}

  /**
   * @param type
   * @param pageIndex
   * @param currency
   */
  public GetbtcOpenOrdersParams(CurrencyPair currencyPair, String type, int pageIndex) {
    super();
    this.currencyPair = currencyPair;
    this.type = type;
    this.pageIndex = pageIndex;
  }

  public boolean accept(LimitOrder order) {
    return false;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }
}
