package org.knowm.xchange.binance.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;


public class BinanceQueryOrderParams implements OrderQueryParamCurrencyPair{
  private String orderId;
  private CurrencyPair pair;

 public BinanceQueryOrderParams(){

 }

  public BinanceQueryOrderParams(CurrencyPair pair, String orderId) {
    this.pair = pair;
    this.orderId = orderId;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
   this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public void setOrderId(String orderId){
   this.orderId = orderId;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

}
