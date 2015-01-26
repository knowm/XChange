package com.xeiam.xchange.bitmarket.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarket;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author kpysniak
 */
public class BitMarketDataServiceRaw extends BitMarketBasePollingService {

  private final BitMarket bitMarket;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitMarket = RestProxyFactory.createProxy(BitMarket.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitMarketTicker getBitMarketTicker(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getTicker(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public BitMarketOrderBook getBitMarketOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getOrderBook(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public BitMarketTrade[] getBitMarketTrades(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getTrades(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

}
