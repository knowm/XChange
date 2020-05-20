package org.knowm.xchange.okcoin.v3.dto.trade;

import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

@Data
public class OkexTradeHistoryParams implements TradeHistoryParams, TradeHistoryParamCurrencyPair {

  private CurrencyPair currencyPair;

  /** provide an order id, so the result will contain only the orders which are newer */
  private String sinceOrderId;
}
