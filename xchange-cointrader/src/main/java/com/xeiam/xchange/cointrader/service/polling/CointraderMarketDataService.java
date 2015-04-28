package com.xeiam.xchange.cointrader.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cointrader.Cointrader;
import com.xeiam.xchange.cointrader.CointraderAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Matija Mazi
 */
public class CointraderMarketDataService extends CointraderMarketDataServiceRaw implements PollingMarketDataService {

  public CointraderMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    Cointrader.OrderBookType type = args.length >= 1 && args[0] instanceof Cointrader.OrderBookType ? (Cointrader.OrderBookType) args[0] : Cointrader.OrderBookType.all;
    Integer limit = args.length >= 2 && args[1] instanceof Number ? ((Number)args[1]).intValue() : null;
    return CointraderAdapters.adaptOrderBook(getCointraderOrderBook(new Cointrader.Pair(currencyPair), limit, type));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
