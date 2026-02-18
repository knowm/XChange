package org.knowm.xchange.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamCurrency implements TradeHistoryParamCurrency {

  private Currency currency;
}
