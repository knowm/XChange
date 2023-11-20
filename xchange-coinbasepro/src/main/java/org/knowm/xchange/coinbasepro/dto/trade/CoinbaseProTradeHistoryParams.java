package org.knowm.xchange.coinbasepro.dto.trade;

import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;

@Getter
@Setter
public class CoinbaseProTradeHistoryParams
    implements TradeHistoryParamTransactionId,
        TradeHistoryParamCurrencyPair,
        TradeHistoryParamInstrument,
        TradeHistoryParamLimit,
        HistoryParamsFundingType {

  private CurrencyPair currencyPair;
  private Instrument instrument;
  private String txId;
  private Integer afterTradeId;
  private Integer beforeTradeId;
  private String afterTransferId;
  private String beforeTransferId;
  private Integer limit;
  private Type type;

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public String getTransactionId() {
    return txId;
  }

  @Override
  public void setTransactionId(String txId) {
    this.txId = txId;
  }

  @Override
  public Integer getLimit() {
    return this.limit;
  }

  @Override
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }
}
