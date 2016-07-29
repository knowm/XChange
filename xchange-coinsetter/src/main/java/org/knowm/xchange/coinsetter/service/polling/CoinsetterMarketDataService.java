package org.knowm.xchange.coinsetter.service.polling;

import static org.knowm.xchange.coinsetter.CoinsetterExchange.DEFAULT_DEPTH;
import static org.knowm.xchange.coinsetter.CoinsetterExchange.DEFAULT_EXCHANGE;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterAdapters;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

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
