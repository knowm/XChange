package org.knowm.xchange.gateio.service.params;

import java.util.Date;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@SuperBuilder
public class GateioTradeHistoryParams implements TradeHistoryParamCurrencyPair,
    TradeHistoryParamPaging, TradeHistoryParamTransactionId, TradeHistoryParamsTimeSpan {

  private CurrencyPair currencyPair;

  private Integer pageLength;

  private Integer pageNumber;

  private String transactionId;

  private Date startTime;

  private Date endTime;


}
