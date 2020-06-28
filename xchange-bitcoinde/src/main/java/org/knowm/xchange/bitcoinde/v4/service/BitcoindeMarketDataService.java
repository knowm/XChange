package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

import static org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters.adaptCompactOrderBook;
import static org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters.adaptOrderBook;

public class BitcoindeMarketDataService extends BitcoindeMarketDataServiceRaw
    implements MarketDataService {

  public BitcoindeMarketDataService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args == null || args.length == 0) {
      return adaptCompactOrderBook(getBitcoindeCompactOrderBook(currencyPair), currencyPair);
    } else if (args[0] instanceof BitcoindeOrderbookOrdersParams) {
      final BitcoindeOrderbookOrdersParams params = (BitcoindeOrderbookOrdersParams) args[0];

      return adaptOrderBook(
          getBitcoindeOrderBook(currencyPair, OrderType.BID, params),
          getBitcoindeOrderBook(currencyPair, OrderType.ASK, params),
          currencyPair);
    }

    throw new IllegalArgumentException(
        String.format(
            "Only support %s as an argument",
            BitcoindeOrderbookOrdersParams.class.getSimpleName()));
  }
}
