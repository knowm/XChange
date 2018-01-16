package org.knowm.xchange.bitcointoyou.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcointoyou.BitcointoyouAdapters;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouOrderBook;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouPublicTrade;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * {@link MarketDataService} implementation for Bitcointoyou Exchange.
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
public class BitcointoyouMarketDataService extends BitcointoyouMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange the Bitcointoyou Exchange
   */
  public BitcointoyouMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BitcointoyouTicker bitcointoyouTicker = getBitcointoyouTicker(currencyPair);
    return BitcointoyouAdapters.adaptBitcointoyouTicker(bitcointoyouTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    BitcointoyouOrderBook ob = getBitcointoyouOrderBook();
    return BitcointoyouAdapters.adaptBitcointoyouOrderBook(ob, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Long tradeTimeStamp = null;
    Long minTradeId = null;

    if (args != null) {
      switch (args.length) {
      case 2:
        if (args[1] != null && args[1] instanceof Long) {
          minTradeId = (Long) args[1];
        }
      case 1:
        if (args[0] != null && args[0] instanceof Long) {
          tradeTimeStamp = (Long) args[0];
        }
      }
    }
    BitcointoyouPublicTrade[] bitcointoyouPublicTrades;
    if (tradeTimeStamp == null && minTradeId == null) {
      bitcointoyouPublicTrades = getBitcointoyouPublicTrades(currencyPair);
    } else {
      bitcointoyouPublicTrades = getBitcointoyouPublicTrades(currencyPair, tradeTimeStamp, minTradeId);
    }
    return BitcointoyouAdapters.adaptBitcointoyouPublicTrades(bitcointoyouPublicTrades, currencyPair);
  }

}
