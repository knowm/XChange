package org.knowm.xchange.coinsph.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
// import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging; // Removed, using startId
// directly
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId; // For orderId

public class CoinsphTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan
    implements TradeHistoryParamCurrencyPair,
        TradeHistoryParamInstrument,
        TradeHistoryParamLimit,
        // TradeHistoryParamPaging, // Removed as Coins.ph uses fromId (startId) and limit, not page
        // numbers
        TradeHistoryParamOrderId {

  private CurrencyPair currencyPair; // Mandatory for Coins.ph if instrument is not specified
  private Instrument instrument; // Mandatory for Coins.ph if currencyPair is not specified
  private Integer limit;
  private String startId; // Corresponds to fromId in Coins.ph API
  private String orderId; // Optional

  public CoinsphTradeHistoryParams() {}

  public CoinsphTradeHistoryParams(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public CoinsphTradeHistoryParams(Instrument instrument) {
    this.instrument = instrument;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getStartId() {
    return startId;
  }

  public void setStartId(String startId) {
    this.startId = startId;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  @Override
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
