package org.knowm.xchange.coinbasepro.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;

public class CoinbaseProTradeHistoryParams
    implements TradeHistoryParamTransactionId,
        TradeHistoryParamCurrencyPair,
        TradeHistoryParamLimit,
        HistoryParamsFundingType {

  private CurrencyPair currencyPair;
  private String txId;
  private Integer afterTradeId;
  private Integer beforeTradeId;
  private String afterTransferId;
  private String beforeTransferId;
  private Integer limit;
  private Type type;

  public Integer getAfterTradeId() {
    return afterTradeId;
  }

  public String getAfterTransferId() {
    return afterTransferId;
  }

  public void setAfterTransferId(String afterTransferId) {
    this.afterTransferId = afterTransferId;
  }

  public String getBeforeTransferId() {
    return beforeTransferId;
  }

  public void setBeforeTransferId(String beforeTransferId) {
    this.beforeTransferId = beforeTransferId;
  }

  public void setAfterTradeId(Integer startingOrderId) {
    this.afterTradeId = startingOrderId;
  }

  public Integer getBeforeTradeId() {
    return beforeTradeId;
  }

  public void setBeforeTradeId(Integer beforeTradeId) {
    this.beforeTradeId = beforeTradeId;
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
}
