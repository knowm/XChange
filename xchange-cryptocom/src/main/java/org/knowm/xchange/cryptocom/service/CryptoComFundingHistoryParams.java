package org.knowm.xchange.cryptocom.service;

import java.util.Date;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
public class CryptoComFundingHistoryParams
    implements TradeHistoryParamCurrency, TradeHistoryParamsTimeSpan {

  private Currency currency;
  private Date startTime;
  private Date endTime;
}
