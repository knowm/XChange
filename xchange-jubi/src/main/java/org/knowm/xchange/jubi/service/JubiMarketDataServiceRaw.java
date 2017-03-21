package org.knowm.xchange.jubi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.jubi.Jubi;
import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class JubiMarketDataServiceRaw extends JubiBaseService<Jubi> {

  /**
   * Constructor for JubiMarketDataServiceRaw
   *
   * @param exchange The {@link org.knowm.xchange.Exchange}
   */
  public JubiMarketDataServiceRaw(Exchange exchange) {

    super(Jubi.class, exchange);
  }

  /**
   * Gets ticker from Jubi
   *
   * @param baseCurrency Base currency
   * @param targetCurrency Target currency
   * @return JubiTicker object
   * @throws java.io.IOException
   */
  public JubiTicker getJubiTicker(String baseCurrency, String targetCurrency) throws IOException {

    // Base currency needs to be in lower case, otherwise API throws an error
    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(baseCurrency) && cp.counter.getCurrencyCode().equalsIgnoreCase(targetCurrency)) {
        return this.jubi.getTicker(baseCurrency.toLowerCase());
      }
    }

    return null;
  }
}
