package org.knowm.xchange.coindirect.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.dto.CoindirectException;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrder;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrderRequest;

public class CoindirectTradeServiceRaw extends CoindirectBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindirectTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  List<CoindirectOrder> listExchangeOrders(String symbol, boolean completed, long offset, long max)
      throws IOException, CoindirectException {
    return coindirect.listExchangeOrders(symbol, completed, offset, max, signatureCreator);
  }

  CoindirectOrder placeExchangeOrder(CoindirectOrderRequest coindirectOrderRequest)
      throws IOException, CoindirectException {
    return coindirect.placeExchangeOrder(coindirectOrderRequest, signatureCreator);
  }

  CoindirectOrder cancelExchangeOrder(String uuid) throws IOException, CoindirectException {
    return coindirect.cancelExchangeOrder(uuid, signatureCreator);
  }

  CoindirectOrder getExchangeOrder(String uuid) throws IOException, CoindirectException {
    return coindirect.getExchangeOrder(uuid, signatureCreator);
  }
}
