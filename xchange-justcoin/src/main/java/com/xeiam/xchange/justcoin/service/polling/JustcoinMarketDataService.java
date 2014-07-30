package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinPublicTrade;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class JustcoinMarketDataService extends JustcoinMarketDataServiceRaw implements PollingMarketDataService {

  public JustcoinMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    final List<JustcoinTicker> justcoinTickers = super.getTickers();

    return JustcoinAdapters.adaptTicker(justcoinTickers, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    final JustcoinDepth justcoinDepth = super.getMarketDepth(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return JustcoinAdapters.adaptOrderBook(currencyPair, justcoinDepth);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long sinceId = null;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Number) {
        sinceId = ((Number) arg0).longValue();
      }
      else {
        throw new ExchangeException("args[0] must be of type Number!");
      }
    }

    final List<JustcoinPublicTrade> justcoinTrades = super.getTrades(currencyPair.counterSymbol, sinceId);
    return JustcoinAdapters.adaptPublicTrades(currencyPair, justcoinTrades);
  }

}
