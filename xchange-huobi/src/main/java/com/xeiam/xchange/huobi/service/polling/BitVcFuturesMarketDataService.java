package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.huobi.BitVcFuturesAdapter;
import com.xeiam.xchange.huobi.FuturesContract;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitVcFuturesMarketDataService extends BitVcFuturesMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   * @param contract
   */
  public BitVcFuturesMarketDataService(Exchange exchange, FuturesContract contract) {

    super(exchange, contract);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitVcFuturesAdapter.adaptTicker(getBitVcTicker(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitVcFuturesAdapter.adaptOrderBook(getBitVcDepth(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitVcFuturesAdapter.adaptTrades(getBitVcTrades(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

}
