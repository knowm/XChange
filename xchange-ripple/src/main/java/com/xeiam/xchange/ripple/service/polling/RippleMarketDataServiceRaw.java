package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
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
    if (pair.baseSymbol.equals(Currencies.XRP)) {
      base = pair.baseSymbol; // XRP is the native currency - no counterparty
    } else if (params.getBaseCounterparty().isEmpty()) {
      throw new ExchangeException("base counterparty must be populated for currency: " + pair.baseSymbol);
    } else {
      base = String.format("%s+%s", pair.baseSymbol, params.getBaseCounterparty());
    }

    final String counter;
    if (pair.counterSymbol.equals(Currencies.XRP)) {
      counter = pair.counterSymbol; // XRP is the native currency - no counterparty
    } else if (params.getCounterCounterparty().isEmpty()) {
      throw new ExchangeException("counter counterparty must be populated for currency: " + pair.counterSymbol);
    } else {
      counter = String.format("%s+%s", pair.counterSymbol, params.getCounterCounterparty());
    }

    return ripplePublic.getOrderBook(params.getAddress(), base, counter, params.getLimit());
  }
}
