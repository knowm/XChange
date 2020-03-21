package org.knowm.xchange.kucoin;

import java.util.Date;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.*;

@Data
public class KucoinTradeHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamCurrencyPair,
        HistoryParamsFundingType,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamNextPageCursor {

  private Date startTime;
  private Date endTime;
  private Type type;
  private CurrencyPair currencyPair;
  private String nextPageCursor;
}
