package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.Cryptonit;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * <p>
 * Implementation of the market data service for Cryptonit
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */

public class CryptonitMarketDataServiceRaw extends CryptonitBasePollingService<Cryptonit> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptonitMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Cryptonit.class, exchangeSpecification);
  }

  public List<List<String>> getCryptonitTradingPairs() throws IOException {

    final List<List<String>> tradingPairs = cryptonit.getPairs();
    return tradingPairs;
  }

  public CryptonitTicker getCryptonitTicker(CurrencyPair currencyPair) throws IOException {

    // Request data
    CryptonitTicker cryptonitTicker = cryptonit.getTicker(currencyPair.counterSymbol, currencyPair.baseSymbol);

    // Adapt to XChange DTOs
    return cryptonitTicker;
  }

  public CryptonitOrders getCryptonitAsks(CurrencyPair currencyPair, int limit) throws IOException {

    // Request data
    CryptonitOrders cryptonitDepth = cryptonit.getOrders(currencyPair.baseSymbol, currencyPair.counterSymbol, "placed", String.valueOf(limit));

    return cryptonitDepth;
  }

  public CryptonitOrders getCryptonitBids(CurrencyPair currencyPair, int limit) throws IOException {

    // Request data
    CryptonitOrders cryptonitDepth = cryptonit.getOrders(currencyPair.counterSymbol, currencyPair.baseSymbol, "placed", String.valueOf(limit));

    return cryptonitDepth;
  }

  public CryptonitOrders getCryptonitTrades(CurrencyPair currencyPair, int limit) throws IOException {

    // Request data
    CryptonitOrders cryptonitTrades = cryptonit.getOrders(currencyPair.baseSymbol, currencyPair.counterSymbol, "filled", String.valueOf(limit));

    return cryptonitTrades;
  }

}
