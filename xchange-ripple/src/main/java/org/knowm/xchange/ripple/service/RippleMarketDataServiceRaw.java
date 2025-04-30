package org.knowm.xchange.ripple.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrderBook;
import org.knowm.xchange.ripple.service.params.RippleMarketDataParams;

public class RippleMarketDataServiceRaw extends RippleBaseService {

  public RippleMarketDataServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public RippleOrderBook getRippleOrderBook(
      final CurrencyPair pair, final RippleMarketDataParams params) throws IOException {
    if (params.getAddress().isEmpty()) {
      throw new ExchangeException("address field must be populated in supplied parameters");
    }

    final String base;
    if (pair.getBase().equals(Currency.XRP)) {
      base = pair.getBase().getCurrencyCode(); // XRP is the native currency - no counterparty
    } else if (params.getBaseCounterparty().isEmpty()) {
      throw new ExchangeException(
          "base counterparty must be populated for currency: " + pair.getBase().getCurrencyCode());
    } else {
      base = String.format("%s+%s", pair.getBase().getCurrencyCode(), params.getBaseCounterparty());
    }

    final String counter;
    if (pair.getCounter().equals(Currency.XRP)) {
      counter = pair.getCounter().getCurrencyCode(); // XRP is the native currency - no counterparty
    } else if (params.getCounterCounterparty().isEmpty()) {
      throw new ExchangeException(
          "counter counterparty must be populated for currency: "
              + pair.getCounter().getCurrencyCode());
    } else {
      counter =
          String.format(
              "%s+%s", pair.getCounter().getCurrencyCode(), params.getCounterCounterparty());
    }

    return ripplePublic.getOrderBook(params.getAddress(), base, counter, params.getLimit());
  }
}
