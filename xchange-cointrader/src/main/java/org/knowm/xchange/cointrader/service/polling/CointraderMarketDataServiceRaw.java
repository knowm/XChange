package org.knowm.xchange.cointrader.service.polling;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cointrader.Cointrader;
import org.knowm.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import org.knowm.xchange.cointrader.dto.marketdata.CointraderTicker;

import si.mazi.rescu.RestProxyFactory;

public class CointraderMarketDataServiceRaw extends CointraderBasePollingService {

  private final Cointrader cointrader;

  public CointraderMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.cointrader = RestProxyFactory.createProxy(Cointrader.class, exchange.getExchangeSpecification().getSslUri());
  }

  public CointraderTicker getCointraderTicker(Cointrader.Pair currencyPair, CointraderTicker.Type type) {
    Map<Cointrader.Pair, CointraderTicker> tck = cointrader.getTicker(currencyPair, type).getData();
    return tck.get(currencyPair);
  }

  public CointraderOrderBook getCointraderOrderBook(Cointrader.Pair currencyPair, Integer limit, Cointrader.OrderBookType type) throws IOException {
    return type == null ? cointrader.getOrderBook(currencyPair)
        : limit == null ? cointrader.getOrderBook(currencyPair, type) : cointrader.getOrderBook(currencyPair, limit, type);
  }
}
