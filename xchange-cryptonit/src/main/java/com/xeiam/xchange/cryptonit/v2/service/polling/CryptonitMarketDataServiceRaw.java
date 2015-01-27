package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptonitMarketDataServiceRaw extends CryptonitBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
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
