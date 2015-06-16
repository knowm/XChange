package com.xeiam.xchange.cointrader.service.polling;

import java.io.IOException;
import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cointrader.Cointrader;
import com.xeiam.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import com.xeiam.xchange.cointrader.dto.marketdata.CointraderTicker;

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

  public CointraderOrderBook getCointraderOrderBook(Cointrader.Pair currencyPair, Integer limit, Cointrader.OrderBookType type)
      throws IOException {
    return type == null ? cointrader.getOrderBook(currencyPair)
        : limit == null ? cointrader.getOrderBook(currencyPair, type)
        : cointrader.getOrderBook(currencyPair, limit, type);
  }
}
