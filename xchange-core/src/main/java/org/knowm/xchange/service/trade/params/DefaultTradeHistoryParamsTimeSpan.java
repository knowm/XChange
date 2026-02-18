package org.knowm.xchange.service.trade.params;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Common implementation of {@link TradeHistoryParamsTimeSpan}. */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamsTimeSpan implements TradeHistoryParamsTimeSpan {

  private Date endTime;
  private Date startTime;
}
