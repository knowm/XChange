package org.knowm.xchange.oer.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.oer.OER;
import org.knowm.xchange.oer.dto.marketdata.OERRates;
import org.knowm.xchange.oer.dto.marketdata.OERTickers;

/** @author timmolter */
public class OERMarketDataServiceRaw extends OERBaseService {

  private final OER openExchangeRates;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OERMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.openExchangeRates =
        ExchangeRestProxyBuilder.forInterface(OER.class, exchange.getExchangeSpecification())
            .build();
  }

  public OERRates getOERTicker(CurrencyPair pair) throws IOException {

    // Request data
    OERTickers oERTickers =
        openExchangeRates.getTickers(
            exchange.getExchangeSpecification().getApiKey(),
            pair.base.toString(),
            pair.counter.toString());
    if (oERTickers == null) {
      throw new ExchangeException("Null response returned from Open Exchange Rates!");
    }
    return oERTickers.getRates();
  }
}
