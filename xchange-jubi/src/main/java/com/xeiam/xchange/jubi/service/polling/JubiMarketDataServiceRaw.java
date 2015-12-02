package com.xeiam.xchange.jubi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.jubi.Jubi;
import com.xeiam.xchange.jubi.dto.marketdata.JubiTicker;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class JubiMarketDataServiceRaw extends JubiBasePollingService<Jubi> {

  /**
   * Constructor for JubiMarketDataServiceRaw
   *
   * @param exchange The {@link com.xeiam.xchange.Exchange}
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
    for (CurrencyPair cp : this.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(baseCurrency) && cp.counter.getCurrencyCode().equalsIgnoreCase(targetCurrency)) {
        return this.jubi.getTicker(baseCurrency.toLowerCase());
      }
    }

    return null;
  }
}
