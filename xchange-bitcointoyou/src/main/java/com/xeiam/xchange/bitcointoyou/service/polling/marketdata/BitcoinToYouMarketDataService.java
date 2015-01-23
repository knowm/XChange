package com.xeiam.xchange.bitcointoyou.service.polling.marketdata;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouMarketDataService extends BitcoinToYouMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitcoinToYouMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitcoinToYouAdapters.adaptTicker(getBitcoinToYouTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitcoinToYouAdapters.adaptOrderBook(getBitcoinToYouOrderBook(currencyPair), currencyPair, new Date().getTime());
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitcoinToYouAdapters.adaptTrades(getBitcoinToYouTransactions(currencyPair, args), currencyPair);
  }

}
