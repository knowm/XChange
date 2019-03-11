package org.knowm.xchange.coinfloor.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinfloor.CoinfloorPublic;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorOrderBook;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTicker;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import si.mazi.rescu.RestProxyFactory;

public class CoinfloorMarketDataServiceRaw extends BaseExchangeService {

  private final CoinfloorPublic coinfloor;

  protected CoinfloorMarketDataServiceRaw(Exchange exchange) {
    super(exchange);

    coinfloor =
        RestProxyFactory.createProxy(
            CoinfloorPublic.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  public CoinfloorTicker getCoinfloorTicker(CurrencyPair pair) throws IOException {
    return coinfloor.getTicker(normalise(pair.base), normalise(pair.counter));
  }

  public CoinfloorOrderBook getCoinfloorOrderBook(CurrencyPair pair) throws IOException {
    return coinfloor.getOrderBook(normalise(pair.base), normalise(pair.counter));
  }

  public CoinfloorTransaction[] getCoinfloorTransactions(
      CurrencyPair pair, CoinfloorInterval interval) throws IOException {
    return coinfloor.getTransactions(normalise(pair.base), normalise(pair.counter), interval);
  }

  private Currency normalise(Currency xchange) {
    if (xchange == Currency.BTC) {
      return Currency.XBT;
    } else {
      return xchange;
    }
  }

  public enum CoinfloorInterval {
    DAY,
    HOUR,
    MINUTE;

    @Override
    public String toString() {
      return super.toString().toLowerCase();
    }
  }
}
