package org.knowm.xchange.kraken.service;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KrakenTradeHistoryParams
    implements TradeHistoryParamOffset,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamCurrencyPair {

  private Long offset;
  private String startId;
  private String endId;

  private Date endTime;
  private Date startTime;

  private CurrencyPair currencyPair;
}
