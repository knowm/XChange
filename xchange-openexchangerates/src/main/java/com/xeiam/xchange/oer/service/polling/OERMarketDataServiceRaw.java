package com.xeiam.xchange.oer.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.oer.OER;
import com.xeiam.xchange.oer.dto.marketdata.OERRates;
import com.xeiam.xchange.oer.dto.marketdata.OERTickers;

/**
 * @author timmolter
 */
public class OERMarketDataServiceRaw extends OERBasePollingService {

  private final OER openExchangeRates;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OERMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.openExchangeRates = RestProxyFactory.createProxy(OER.class, exchange.getExchangeSpecification().getPlainTextUri());
  }

  public OERRates getOERTicker() throws IOException {

    // Request data
    OERTickers oERTickers = openExchangeRates.getTickers(exchange.getExchangeSpecification().getApiKey());
    if (oERTickers == null) {
      throw new ExchangeException("Null response returned from Open Exchange Rates!");
    }

    OERRates rates = oERTickers.getRates();

    // Adapt to XChange DTOs
    return rates;
  }

}
