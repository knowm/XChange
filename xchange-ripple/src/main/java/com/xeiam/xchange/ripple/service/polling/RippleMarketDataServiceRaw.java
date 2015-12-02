package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.service.polling.params.RippleMarketDataParams;

public class RippleMarketDataServiceRaw extends RippleBasePollingService {

  public RippleMarketDataServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public RippleOrderBook getRippleOrderBook(final CurrencyPair pair, final RippleMarketDataParams params) throws IOException {
    if (params.getAddress().isEmpty()) {
      throw new ExchangeException("address field must be populated in supplied parameters");
    }

    final String base;
    if (pair.base.equals(Currency.XRP)) {
      base = pair.base.getCurrencyCode(); // XRP is the native currency - no counterparty
    } else if (params.getBaseCounterparty().isEmpty()) {
      throw new ExchangeException("base counterparty must be populated for currency: " + pair.base.getCurrencyCode());
    } else {
      base = String.format("%s+%s", pair.base.getCurrencyCode(), params.getBaseCounterparty());
    }

    final String counter;
    if (pair.counter.equals(Currency.XRP)) {
      counter = pair.counter.getCurrencyCode(); // XRP is the native currency - no counterparty
    } else if (params.getCounterCounterparty().isEmpty()) {
      throw new ExchangeException("counter counterparty must be populated for currency: " + pair.counter.getCurrencyCode());
    } else {
      counter = String.format("%s+%s", pair.counter.getCurrencyCode(), params.getCounterCounterparty());
    }

    return ripplePublic.getOrderBook(params.getAddress(), base, counter, params.getLimit());
  }
}
