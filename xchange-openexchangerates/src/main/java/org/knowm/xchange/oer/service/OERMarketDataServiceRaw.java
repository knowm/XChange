package org.knowm.xchange.oer.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.oer.OER;
import org.knowm.xchange.oer.dto.marketdata.OERRates;
import org.knowm.xchange.oer.dto.marketdata.OERTickers;
import si.mazi.rescu.RestProxyFactory;

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
        RestProxyFactory.createProxy(
            OER.class, exchange.getExchangeSpecification().getPlainTextUri(), getClientConfig());
  }

  public OERRates getOERTicker() throws IOException {

    // Request data
    OERTickers oERTickers =
        openExchangeRates.getTickers(exchange.getExchangeSpecification().getApiKey());
    if (oERTickers == null) {
      throw new ExchangeException("Null response returned from Open Exchange Rates!");
    }

    OERRates rates = oERTickers.getRates();

    // Adapt to XChange DTOs
    return rates;
  }
}
