package org.knowm.xchange.cryptopia.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CryptopiaMarketDataService extends CryptopiaMarketDataServiceRaw implements MarketDataService {

  public CryptopiaMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      long hours = (long) args[0];

      return CryptopiaAdapters.adaptTicker(getCryptopiaTicker(currencyPair, hours), currencyPair);
    }

    return CryptopiaAdapters.adaptTicker(getCryptopiaTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      long orderCount = (long) args[0];

      return CryptopiaAdapters.adaptOrderBook(getCryptopiaOrderBook(currencyPair, orderCount), currencyPair);
    }

    return CryptopiaAdapters.adaptOrderBook(getCryptopiaOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      long hours = (long) args[0];

      return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair, hours));
    }

    return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair));
  }
}
