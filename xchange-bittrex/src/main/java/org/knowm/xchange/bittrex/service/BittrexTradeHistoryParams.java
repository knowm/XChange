package org.knowm.xchange.bittrex.service;

import java.util.Date;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
public class BittrexTradeHistoryParams
    implements TradeHistoryParams, TradeHistoryParamCurrencyPair, TradeHistoryParamsTimeSpan {
  private Date startTime;
  private Date endTime;
  private CurrencyPair currencyPair;
}
