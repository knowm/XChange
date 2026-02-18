package org.knowm.xchange.bitfinex.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BitfinexFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
    implements TradeHistoryParamCurrency, TradeHistoryParamLimit, HistoryParamsFundingType {

  private Integer limit;
  private Currency currency;

  private FundingRecord.Type type;
}
