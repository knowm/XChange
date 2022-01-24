package org.knowm.xchange.idex;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.idex.dto.TradeHistoryReq;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public final class IdexTradeHistoryParams extends TradeHistoryReq
    implements TradeHistoryParams, TradeHistoryParamsTimeSpan, TradeHistoryParamCurrencyPair {

  public IdexTradeHistoryParams(String address) {
    setAddress(address);
  }

  @Override
  public Date getStartTime() {
    return new Date(Long.valueOf(getStart()) * 1000);
  }

  @Override
  public void setStartTime(Date date) {
    setStart(String.valueOf(date.getTime() / 1000));
  }

  @Override
  public Date getEndTime() {
    return new Date(Long.valueOf(getEnd()) * 1000);
  }

  @Override
  public void setEndTime(Date date) {
    setEnd(String.valueOf(date.getTime() / 1000));
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    CurrencyPair currencyPair;
    String[] marketSplit = getMarket().split("_");
    String currencyCounter = marketSplit[0];
    String currencyBase = marketSplit[1];
    currencyPair = new CurrencyPair(currencyBase, currencyCounter);
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    setMarket(currencyPair.counter.getSymbol() + "_" + currencyPair.base.getSymbol());
  }
}
