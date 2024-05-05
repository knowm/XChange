package org.knowm.xchange.gateio.service.params;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@Builder
public class GateioFundingHistoryParams implements TradeHistoryParamPaging, TradeHistoryParamsTimeSpan {

  private Currency currency;

  private Integer pageLength;

  private Integer pageNumber;

  private Date startTime;

  private Date endTime;

  private String type;

}
