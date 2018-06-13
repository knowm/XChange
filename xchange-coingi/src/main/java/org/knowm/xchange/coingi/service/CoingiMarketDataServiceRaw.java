package org.knowm.xchange.coingi.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.Coingi;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderBook;
import org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;

public class CoingiMarketDataServiceRaw extends CoingiBaseService {
  private final Coingi coingi;

  protected CoingiMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    coingi =
        RestProxyFactory.createProxy(
            Coingi.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public CoingiOrderBook getCoingiOrderBook(
      CurrencyPair currencyPair, int maxAskCount, int maxBidCount, int maxDepthRangeCount)
      throws IOException {
    return coingi.getOrderBook(
        CoingiAdapters.adaptCurrency(currencyPair), maxAskCount, maxBidCount, maxDepthRangeCount);
  }

  public List<CoingiTransaction> getTransactions(CurrencyPair currencyPair, int maxCount)
      throws IOException {
    return coingi.getTransaction(CoingiAdapters.adaptCurrency(currencyPair), maxCount);
  }
}
