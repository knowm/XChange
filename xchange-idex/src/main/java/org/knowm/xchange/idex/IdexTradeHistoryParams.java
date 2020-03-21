package org.knowm.xchange.idex;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.idex.dto.TradeHistoryReq;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public final class IdexTradeHistoryParams extends TradeHistoryReq
    implements TradeHistoryParams, TradeHistoryParamsTimeSpan, TradeHistoryParamInstrument {

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
  public Instrument getInstrument() {
    CurrencyPair currencyPair;
    String[] marketSplit = getMarket().split("_");
    String currencyCounter = marketSplit[0];
    String currencyBase = marketSplit[1];
    currencyPair = new CurrencyPair(currencyBase, currencyCounter);
    return currencyPair;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    if (instrument instanceof CurrencyPair) {
      CurrencyPair currencyPair = (CurrencyPair) instrument;
      setMarket(currencyPair.counter.getSymbol() + "_" + currencyPair.base.getSymbol());
    }
  }
}
