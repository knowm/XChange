package org.knowm.xchange.deribit.v2.service;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeribitTradeHistoryParams
    implements TradeHistoryParamInstrument,
        TradeHistoryParamCurrency,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamLimit,
        TradeHistoryParamsSorted,
        DeribitTradeHistoryParamsOld {

  private Currency currency;

  /** mandatory if currency is not specified */
  private Instrument instrument;

  /** mandatory if instrument is not specified, ignored otherwise */
  private CurrencyPair currencyPair;

  /** optional */
  private Date startTime;

  /** optional */
  private Date endTime;

  /** optional, ignored if startTime and endTime are specified */
  private String startId;

  /** optional, ignored if startTime and endTime are specified */
  private String endId;

  /** optional */
  private Integer limit;

  /** optional */
  private Order order;

  /** optional */
  private Boolean includeOld;

  @Override
  public Boolean isIncludeOld() {
    return includeOld;
  }
}
