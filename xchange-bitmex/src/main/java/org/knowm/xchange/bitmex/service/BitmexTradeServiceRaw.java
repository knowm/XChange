package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;

public class BitmexTradeServiceRaw extends BitmexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  String apiKey = null;

  public BitmexTradeServiceRaw(Exchange exchange) {

    super(exchange);
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
