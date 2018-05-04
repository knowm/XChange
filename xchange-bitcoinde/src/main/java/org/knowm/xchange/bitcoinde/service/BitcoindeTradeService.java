package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.trade.BitcoindeIdResponse;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

/** @author kaiserfr */
public class BitcoindeTradeService extends BitcoindeTradeServiceRaw implements TradeService {

  public BitcoindeTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return BitcoindeAdapters.adaptOpenOrders(getBitcoindeOpenOrders());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BitcoindeIdResponse response = bitcoindePlaceLimitOrder(limitOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdAndCurrencyPair) {
      CancelOrderByIdAndCurrencyPair cob = (CancelOrderByIdAndCurrencyPair) orderParams;
      bitcoindeCancelOrders(cob.getId(), cob.getCurrencyPair());
    }
    return true;
  }
}
