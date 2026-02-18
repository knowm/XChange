package org.knowm.xchange.service.trade.params;

import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamMultiCurrencyPair
    implements TradeHistoryParamMultiCurrencyPair {

  @Builder.Default private Collection<CurrencyPair> currencyPairs = Collections.emptySet();
}
