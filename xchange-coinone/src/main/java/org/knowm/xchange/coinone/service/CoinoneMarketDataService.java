package org.knowm.xchange.coinone.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.CoinoneAdapters;
import org.knowm.xchange.coinone.CoinoneExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author interwater */
public class CoinoneMarketDataService extends CoinoneMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinoneAdapters.adaptTicker(super.getTicker(currencyPair));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    return CoinoneAdapters.adaptOrderBook(getCoinoneOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    String period = "hour";
    if (args[0] != null) {
      try {
        period = CoinoneExchange.period.valueOf(args[0].toString()).name();
      } catch (IllegalArgumentException e) {
      }
    }
    return CoinoneAdapters.adaptTrades(super.getTrades(currencyPair, period), currencyPair);
  }
}
