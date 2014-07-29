package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrades;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * <p>
 * Implementation of the market data service for CryptoTrade
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class CryptoTradeMarketDataServiceRaw extends CryptoTradeBasePollingService<CryptoTrade> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptoTrade.class, exchangeSpecification);
  }

  public CryptoTradeTicker getCryptoTradeTicker(CurrencyPair currencyPair) throws IOException {

    CryptoTradeTicker cryptoTradeTicker = cryptoTradeProxy.getTicker(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeTicker);
  }

  public CryptoTradeDepth getCryptoTradeOrderBook(CurrencyPair currencyPair) throws IOException {

    CryptoTradeDepth cryptoTradeDepth = cryptoTradeProxy.getFullDepth(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeDepth);
  }

  public List<CryptoTradePublicTrade> getCryptoTradeTradeHistory(CurrencyPair currencyPair) throws IOException {

    CryptoTradePublicTrades cryptoTradeDepth = cryptoTradeProxy.getTradeHistory(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeDepth).getPublicTrades();
  }

  public List<CryptoTradePublicTrade> getCryptoTradeTradeHistory(CurrencyPair currencyPair, long sinceTimestamp) throws IOException {

    CryptoTradePublicTrades cryptoTradeDepth = cryptoTradeProxy.getTradeHistory(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase(), sinceTimestamp);

    return handleResponse(cryptoTradeDepth).getPublicTrades();
  }

}
