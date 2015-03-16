package com.xeiam.xchange.bitbay.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitbay.BitbayAdapters;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayMarketAll;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.MarketData;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author kpysniak
 */
public class BitbayMarketDataService extends BitbayMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitbayMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitbayAdapters.adaptTicker(getBitbayTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitbayAdapters.adaptOrderBook(getBitbayOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

      Long sinceTid = null;
      if (args != null && args.length > 0) {
          if (args[0] instanceof Long) {
              sinceTid = (Long)args[0];
          }
      }

    return BitbayAdapters.adaptTrades(getBitbayTrades(currencyPair, sinceTid), currencyPair);
  }

    public MarketData getAllMarketData(CurrencyPair currencyPair) throws IOException {

        BitbayMarketAll marketData = getBitbatAllMarketData(currencyPair);

        Ticker ticker = BitbayAdapters.adaptTicker(marketData, currencyPair);

        BitbayOrderBook bitbayOrderBook = new BitbayOrderBook(marketData.getAsks(), marketData.getBids());
        OrderBook orderBook = BitbayAdapters.adaptOrderBook(bitbayOrderBook, currencyPair);

        Trades trades = BitbayAdapters.adaptTrades(marketData.getTrades(), currencyPair);

        return new MarketData(ticker, orderBook, trades);
    }
}
