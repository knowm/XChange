package org.knowm.xchange.bitfinex.service.trade.params;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

@Data
@Builder
public class BitfinexOpenOrdersParams extends DefaultOpenOrdersParamCurrencyPair {

  private List<Long> ids;
}
