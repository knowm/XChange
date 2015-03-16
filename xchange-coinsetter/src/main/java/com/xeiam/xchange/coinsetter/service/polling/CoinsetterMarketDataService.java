package com.xeiam.xchange.coinsetter.service.polling;

import static com.xeiam.xchange.coinsetter.CoinsetterExchange.DEFAULT_DEPTH;
import static com.xeiam.xchange.coinsetter.CoinsetterExchange.DEFAULT_EXCHANGE;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterAdapters;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Market data service.
 */
public class CoinsetterMarketDataService extends CoinsetterMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return CoinsetterAdapters.adaptTicker(getCoinsetterTicker());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    final CoinsetterListDepth coinsetterListDepth;
    final int argsLength = args.length;

    if (argsLength == 0) {
      coinsetterListDepth = getCoinsetterFullDepth();
    } else if (argsLength == 1) {
      String exchange = (String) args[0];
      coinsetterListDepth = getCoinsetterFullDepth(exchange == null ? DEFAULT_EXCHANGE : exchange);
    } else {
      String exchange = (String) args[0];
      Number depth = (Number) args[1];
      coinsetterListDepth = getCoinsetterListDepth(depth == null ? DEFAULT_DEPTH : depth.intValue(), exchange == null ? DEFAULT_EXCHANGE : exchange);
    }

    return CoinsetterAdapters.adaptOrderBook(coinsetterListDepth);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
