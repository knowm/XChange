package org.knowm.xchange.bitget.service.params;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamClientOid;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@Builder
public class BitgetFundingHistoryParams
    implements TradeHistoryParamCurrency,
        TradeHistoryParamLimit,
        TradeHistoryParamOrderId,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamClientOid {

  private Currency currency;

  private String subAccountUid;

  private String orderId;

  private String clientOid;

  private Date startTime;

  private Date endTime;

  private Integer limit;

  private String startId;

  private String endId;
}
