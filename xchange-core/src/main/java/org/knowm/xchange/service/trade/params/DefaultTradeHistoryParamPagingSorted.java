package org.knowm.xchange.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamPagingSorted extends DefaultTradeHistoryParamPaging
    implements TradeHistoryParamsSorted {

  @Builder.Default private Order order = Order.asc;
}
