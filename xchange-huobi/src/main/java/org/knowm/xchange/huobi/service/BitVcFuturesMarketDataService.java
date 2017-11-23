package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.huobi.BitVcFuturesAdapter;
import org.knowm.xchange.huobi.FuturesContract;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitVcFuturesMarketDataService extends BitVcFuturesMarketDataServiceRaw implements MarketDataService {

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
