package org.knowm.xchange.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Common implementation of {@link TradeHistoryParamPaging} interface */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamPaging implements TradeHistoryParamPaging {

  private Integer pageLength;

  /** 0-based page number */
  @Builder.Default private Integer pageNumber = 0;
}
