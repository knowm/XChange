package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class CoinbaseMarketDataService extends CoinbaseMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbaseMarketDataService(Exchange exchange) {

    super(exchange);
  }

  /**
   * @param args Optional Boolean. If true an additional call to retrieve the spot price history will be made and used to populate the 24 hour high
   *        and low values for the Ticker.
   * @return A Ticker with Coinbase's current buy price as the best ask, sell price as the best bid, spot price as the last value, and can optionally
   *         use the spot price history to find the 24 hour high and low.
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, final Object... args) throws IOException {

    final String currency = currencyPair.counter.getCurrencyCode();
    final CoinbasePrice buyPrice = super.getCoinbaseBuyPrice(BigDecimal.ONE, currency);
    final CoinbasePrice sellPrice = super.getCoinbaseSellPrice(BigDecimal.ONE, currency);
    final CoinbaseMoney spotRate = super.getCoinbaseSpotRate(currency);

    final CoinbaseSpotPriceHistory coinbaseSpotPriceHistory = (args != null && args.length > 0 && args[0] != null && args[0] instanceof Boolean
        && (Boolean) args[0]) ? super.getCoinbaseHistoricalSpotRates() : null;

    return CoinbaseAdapters.adaptTicker(currencyPair, buyPrice, sellPrice, spotRate, coinbaseSpotPriceHistory);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

}
