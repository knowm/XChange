package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class CryptoTradeMarketDataService extends CryptoTradeMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoTradeMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    CryptoTradeTicker cryptoTradeTicker = super.getCryptoTradeTicker(currencyPair);

    return CryptoTradeAdapters.adaptTicker(currencyPair, cryptoTradeTicker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    CryptoTradeDepth cryptoTradeDepth = super.getCryptoTradeOrderBook(currencyPair);

    return CryptoTradeAdapters.adaptOrderBook(currencyPair, cryptoTradeDepth);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long sinceTimestamp = 0;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("arg[0] must be a Number used to represent an epoch timestamp in seconds from which to retreive trades since.");
      }
      sinceTimestamp = ((Number) arg0).longValue();
    }

    List<CryptoTradePublicTrade> publicTradeHistory = sinceTimestamp == 0 ? super.getCryptoTradeTradeHistory(currencyPair) : super
        .getCryptoTradeTradeHistory(currencyPair, sinceTimestamp);

    return CryptoTradeAdapters.adaptPublicTradeHistory(currencyPair, publicTradeHistory);
  }

}
