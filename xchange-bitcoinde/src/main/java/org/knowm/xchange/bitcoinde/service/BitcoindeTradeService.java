package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.trade.BitcoindeIdResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

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
