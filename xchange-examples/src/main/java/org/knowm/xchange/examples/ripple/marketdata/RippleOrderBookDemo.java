package org.knowm.xchange.examples.ripple.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrderBook;
import org.knowm.xchange.ripple.service.RippleMarketDataServiceRaw;
import org.knowm.xchange.ripple.service.params.RippleMarketDataParams;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting an order book from Ripple. You can access both the raw data from Ripple or
 * the XChange generic DTO data format.
 */
public class RippleOrderBookDemo {

  public static void main(final String[] args) throws IOException {
    // Use the factory to get Riiple exchange API using default settings
    final Exchange ripple = ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    final MarketDataService marketDataService = ripple.getMarketDataService();

    // Ripple specific objects
    raw((RippleMarketDataServiceRaw) marketDataService);

    // Xchange objects
    generic(marketDataService);
  }

  private static void generic(final MarketDataService marketDataService) throws IOException {
    final RippleMarketDataParams params = new RippleMarketDataParams();

    // rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B is Bitstamp's account
    params.setAddress("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    // Base symbol is BTC, this requires a counterparty
    params.setBaseCounterparty("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    // Counter symbol is XRP, this is the native currency so does not need counterparty
    // params.setCounterCounterparty("");

    // Set number of orders on each bid/ask side to return
    params.setLimit(10);

    // Fetch order book for Bitstamp issued BTC vs XRP
    final OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_XRP, params);
    System.out.println(orderBook.toString());
  }

  private static void raw(final RippleMarketDataServiceRaw marketDataService) throws IOException {
    final RippleMarketDataParams params = new RippleMarketDataParams();

    // rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q is SnapSwap's address
    params.setAddress("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");

    // Base symbol is BTC, this requires a counterparty
    params.setBaseCounterparty("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");

    // Counter symbol is USD, this requires a counterparty
    params.setCounterCounterparty("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");

    // Set number of orders on each bid/ask side to return
    params.setLimit(10);

    // fetch SnapSwap's EUR/USD order book
    final RippleOrderBook orderBook =
        marketDataService.getRippleOrderBook(CurrencyPair.EUR_USD, params);
    System.out.println(orderBook.toString());
  }
}
