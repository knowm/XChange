package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.Bittrex;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexDepth;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexDepthResponse;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTicker;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTickerResponse;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTickersResponse;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTrade;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTradesResponse;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BittrexMarketDataServiceRaw extends BittrexBasePollingService<Bittrex> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BittrexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Bittrex.class, exchangeSpecification);
  }

  public BittrexTicker getBittrexTicker(String pair) throws IOException {

    BittrexTickerResponse response = bittrex.getTicker(pair);

    if (response.getSuccess()) {
      return response.getTicker();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }

  }
  
  public ArrayList<BittrexTicker> getBittrexTickers() throws IOException {

    BittrexTickersResponse response = bittrex.getTickers();

    if (response.isSuccess()) {
      return response.getTickers();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public BittrexDepth getBittrexOrderBook(String pair) throws IOException {

    BittrexDepthResponse response = bittrex.getBook(pair, "both", 50L);

    if (response.getSuccess()) {

      BittrexDepth bittrexDepth = response.getDepth();
      return bittrexDepth;
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public BittrexTrade[] getBittrexTrades(String pair, long count) throws IOException {

    if (count > 100) {
      count = 100;
    }

    BittrexTradesResponse response = bittrex.getTrades(pair, count);

    if (response.getSuccess()) {

      BittrexTrade[] bittrexTrades = response.getTrades();
      return bittrexTrades;
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }
}
