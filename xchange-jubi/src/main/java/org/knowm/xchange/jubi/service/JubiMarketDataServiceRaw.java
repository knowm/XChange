package org.knowm.xchange.jubi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.jubi.Jubi;
import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;
import org.knowm.xchange.jubi.dto.marketdata.JubiTrade;

import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 3/17/2015.
 * Market Data Service.
 */
public class JubiMarketDataServiceRaw extends JubiBaseService {

  private final Jubi jubi;

  public JubiMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.jubi = RestProxyFactory.createProxy(Jubi.class, exchange.getExchangeSpecification().getSslUri());
  }

  public JubiTicker getJubiTicker(String baseCurrency, String targetCurrency) throws IOException {
    // Base currency needs to be in lower case, otherwise API throws an error
    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(baseCurrency) && cp.counter.getCurrencyCode().equalsIgnoreCase(targetCurrency)) {
        return this.jubi.getTicker(baseCurrency.toLowerCase());
      }
    }
    return null;
  }

  public Map<CurrencyPair, JubiTicker> getAllJubiTicker() throws IOException {
    Map<CurrencyPair, JubiTicker> result = new HashMap<>();
    Map<String, JubiTicker> rawResult = this.jubi.getAllTicker();
    if (rawResult != null) {
      for (Map.Entry<String, JubiTicker> entry : rawResult.entrySet()) {
        result.put(new CurrencyPair(entry.getKey(), "cny"), entry.getValue());
      }
    }
    return result;
  }

  public JubiTrade[] getJubiTrades(CurrencyPair currencyPair, Object[] args) throws IOException {
    return (args != null && args.length > 0 && args[0] != null && args[0] instanceof Long)
        ? jubi.getTradesSince(currencyPair.base.getCurrencyCode().toLowerCase(), (Long) args[0])
        : jubi.getTrades(currencyPair.base.getCurrencyCode().toLowerCase());
  }
}
