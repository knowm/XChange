package org.knowm.xchange.kucoin;

import java.util.Date;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
public class KucoinTradeHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamCurrency,
        HistoryParamsFundingType,
        TradeHistoryParamsTimeSpan {

  private Date startTime;
  private Date endTime;
  private Type type;
  private Currency currency;
}
