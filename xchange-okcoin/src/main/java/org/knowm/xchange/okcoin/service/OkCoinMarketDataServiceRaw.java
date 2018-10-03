package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoin;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTrade;
import si.mazi.rescu.RestProxyFactory;

public class OkCoinMarketDataServiceRaw extends OkCoinBaseService {

  private final OkCoin okCoin;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    okCoin =
        RestProxyFactory.createProxy(
            OkCoin.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public OkCoinTickerResponse getTicker(CurrencyPair currencyPair) throws IOException {

    return okCoin.getTicker("1", OkCoinAdapters.adaptSymbol(currencyPair));
  }

  public OkCoinTickerResponse getFuturesTicker(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {

    return okCoin.getFuturesTicker(OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName());
  }

  public OkCoinDepth getDepth(CurrencyPair currencyPair) throws IOException {

    return okCoin.getDepth("1", OkCoinAdapters.adaptSymbol(currencyPair));
  }

  public OkCoinDepth getFuturesDepth(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {

    return okCoin.getFuturesDepth(
        "1", OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName().toLowerCase());
  }

  public OkCoinTrade[] getTrades(CurrencyPair currencyPair) throws IOException {

    return okCoin.getTrades("1", OkCoinAdapters.adaptSymbol(currencyPair));
  }

  public OkCoinTrade[] getTrades(CurrencyPair currencyPair, long since) throws IOException {

    return okCoin.getTrades("1", OkCoinAdapters.adaptSymbol(currencyPair), since);
  }

  public OkCoinTrade[] getFuturesTrades(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {

    return okCoin.getFuturesTrades(
        "1", OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName().toLowerCase());
  }

  public List<Object[]> getKlines(CurrencyPair currencyPair, String type) throws IOException {

    return okCoin.getKlines(OkCoinAdapters.adaptSymbol(currencyPair), type);
  }
}
