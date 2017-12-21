package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexTrade;

public class BitmexTradeServiceRaw extends BitmexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<BitmexTrade> getBitmexTrades() throws IOException {

    try {
      return bitmex.getTrades(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexTrade> getBitmexTrades(String symbol) throws IOException {

    try {
      return bitmex.getTrades(apiKey, exchange.getNonceFactory(), signatureCreator, symbol);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPosition> getBitmexPositions() throws IOException {

    try {
      return bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPosition> getBitmexPositions(String symbol) throws IOException {

    try {
      return bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, null);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }
}
