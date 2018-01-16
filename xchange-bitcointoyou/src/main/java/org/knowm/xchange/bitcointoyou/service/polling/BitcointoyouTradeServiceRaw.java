package org.knowm.xchange.bitcointoyou.service.polling;

import java.io.IOException;
import java.util.HashMap;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcointoyou.BitcointoyouException;
import org.knowm.xchange.bitcointoyou.dto.trade.BitcointoyouOrderResponse;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * TradeService raw implementation for Bitcointoyou Exchange.
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
public class BitcointoyouTradeServiceRaw extends BitcointoyouBasePollingService {

  /**
   * Constructor
   *
   * @param exchange the Bitcointoyou Exchange
   */
  public BitcointoyouTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitcointoyouOrderResponse returnOpenOrders() throws IOException {

    return bitcointoyouAuthenticated.returnOpenOrders(apiKey, exchange.getNonceFactory(), signatureCreator);
  }

  public BitcointoyouOrderResponse returnOrderById(String orderId) throws IOException {

    return bitcointoyouAuthenticated.returnOrderById(apiKey, exchange.getNonceFactory(), signatureCreator, orderId);
  }

  public BitcointoyouOrderResponse buy(LimitOrder limitOrder) throws IOException {

    return createOrder("buy", limitOrder);
  }

  public BitcointoyouOrderResponse sell(LimitOrder limitOrder) throws IOException {

    return createOrder("sell", limitOrder);
  }

  private BitcointoyouOrderResponse createOrder(String action, LimitOrder limitOrder) throws IOException {
    try {
      String asset = limitOrder.getCurrencyPair().base.getSymbol();
      return bitcointoyouAuthenticated.createOrder(apiKey, exchange.getNonceFactory(), signatureCreator, asset, action, limitOrder
          .getOriginalAmount(), limitOrder.getLimitPrice());
    } catch (BitcointoyouException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public boolean cancel(String orderId) throws IOException {

    /*
     * No need to look up CurrencyPair associated with orderId, as the caller will provide it.
     */
    HashMap<String, String> response = bitcointoyouAuthenticated.deleteOrder(apiKey, exchange.getNonceFactory(), signatureCreator, orderId);
    if (response.containsKey("error")) {
      throw new ExchangeException(response.get("error"));
    }
    return response.get("success").equals("1");
  }

}
