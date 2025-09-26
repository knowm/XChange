package org.knowm.xchange.coinsph.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;

/**
 * Implementation of {@link org.knowm.xchange.service.trade.params.orders.OpenOrdersParams} for
 * Coins.ph. Supports filtering by currency pair.
 */
public class CoinsphOpenOrdersParams
    implements OpenOrdersParamCurrencyPair, OpenOrdersParamInstrument {

  private CurrencyPair currencyPair;
  private Instrument instrument;

  public CoinsphOpenOrdersParams() {
    // No-arg constructor for default params (all open orders)
  }

  public CoinsphOpenOrdersParams(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
    this.instrument = currencyPair;
  }

  public CoinsphOpenOrdersParams(Instrument instrument) {
    this.instrument = instrument;
    if (instrument instanceof CurrencyPair) {
      this.currencyPair = (CurrencyPair) instrument;
    }
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
    this.instrument = currencyPair;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
    if (instrument instanceof CurrencyPair) {
      this.currencyPair = (CurrencyPair) instrument;
    }
  }

  @Override
  public boolean accept(LimitOrder order) {
    return accept((Order) order);
  }

  @Override
  public boolean accept(Order order) {
    // If instrument is set, use it for filtering
    if (instrument != null) {
      return order != null && instrument.equals(order.getInstrument());
    }
    // Otherwise if currency pair is set, use it for filtering
    else if (currencyPair != null) {
      return order != null && currencyPair.equals(order.getCurrencyPair());
    }
    // If neither is set, accept all orders
    return true;
  }
}
