package org.knowm.xchange.bitfinex.service.trade.params;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;

@Data
@Builder
public class BitfinexOrderQueryParams implements OrderQueryParamCurrencyPair {

  private String orderId;

  private CurrencyPair currencyPair;

  private Instant from;

  private Instant to;

  private Long limit;
}
