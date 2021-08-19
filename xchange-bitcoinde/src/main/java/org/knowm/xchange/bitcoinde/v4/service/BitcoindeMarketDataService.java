package org.knowm.xchange.bitcoinde.v4.service;

import static org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters.*;

import java.io.IOException;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

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

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    Integer since = null; // all trades possible
    if (args != null && args.length > 0) {
      // parameter 1, if present, is the since param
      if (args[0] instanceof Integer) {
        since = (Integer) args[0];
      } else {
        throw new IllegalArgumentException(
            "Extra argument #1,  'since', must be an int (was " + args[0].getClass() + ")");
      }
    }

    return adaptTrades(getBitcoindeTrades(currencyPair, since), currencyPair);
  }
}
