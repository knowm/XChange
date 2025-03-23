package org.knowm.xchange.bitmex.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;

@Data
@SuperBuilder
@NoArgsConstructor
public class BitmexTradeHistoryParams
    implements TradeHistoryParamCurrencyPair,
        TradeHistoryParamLimit,
        TradeHistoryParamOffset,
        TradeHistoryParamOrderId {

  private String orderId;
  private CurrencyPair currencyPair;
  private Integer limit;
  private Long offset;
}
