package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.Bitfinex;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexLend;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexLendDepth;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;

/**
 * <p>
 * Implementation of the market data service for Bitfinex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitfinexMarketDataServiceRaw extends BitfinexBasePollingService<Bitfinex> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Bitfinex.class, exchangeSpecification);
  }

  public BitfinexTicker getBitfinexTicker(String pair) throws IOException {

    BitfinexTicker bitfinexTicker = bitfinex.getTicker(pair);

    return bitfinexTicker;
  }

  public BitfinexDepth getBitfinexOrderBook(String pair, int limitBids, int limitAsks) throws IOException {

    BitfinexDepth bitfinexDepth = bitfinex.getBook(pair, limitBids, limitAsks);

    return bitfinexDepth;
  }
  
  public BitfinexLendDepth getBitfinexLendBook(String currency, int limitBids, int limitAsks) throws IOException {
	  
	  BitfinexLendDepth bitfinexLendDepth = bitfinex.getLendBook(currency, limitBids, limitAsks);
	  
	  return bitfinexLendDepth;
  }

  public BitfinexTrade[] getBitfinexTrades(String pair, long sinceTimestamp) throws IOException {

    BitfinexTrade[] bitfinexTrades = bitfinex.getTrades(pair, sinceTimestamp);

    return bitfinexTrades;
  }
  
  public BitfinexLend[] getBitfinexLends(String currency, long sinceTimestamp, int limitTrades) throws IOException {
	  BitfinexLend[] bitfinexLends = bitfinex.getLends(currency, sinceTimestamp, limitTrades);
	  
	  return bitfinexLends;
  }

  public Collection<String> getBitfinexSymbols() throws IOException {

    return bitfinex.getSymbols();
  }
}
