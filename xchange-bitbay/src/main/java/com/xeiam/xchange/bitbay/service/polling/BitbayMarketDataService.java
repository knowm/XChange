package com.xeiam.xchange.bitbay.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitbay.BitbayAdapters;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayMarketAll;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.MarketData;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author kpysniak
 */
public class BitbayMarketDataService extends BitbayMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitbayMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return BitbayAdapters.adaptTicker(getBitbayTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return BitbayAdapters.adaptOrderBook(getBitbayOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

      Long sinceTid = null;
      if (args != null && args.length > 0) {
          if (args[0] instanceof Long) {
              sinceTid = (Long)args[0];
          }
      }

    return BitbayAdapters.adaptTrades(getBitbayTrades(currencyPair, sinceTid), currencyPair);
  }

    public MarketData getAllMarketData(CurrencyPair currencyPair) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

        BitbayMarketAll marketData = getBitbatAllMarketData(currencyPair);

        Ticker ticker = BitbayAdapters.adaptTicker(marketData, currencyPair);

        BitbayOrderBook bitbayOrderBook = new BitbayOrderBook(marketData.getAsks(), marketData.getBids());
        OrderBook orderBook = BitbayAdapters.adaptOrderBook(bitbayOrderBook, currencyPair);

        Trades trades = BitbayAdapters.adaptTrades(marketData.getTrades(), currencyPair);

        return new MarketData(ticker, orderBook, trades);
    }
}
