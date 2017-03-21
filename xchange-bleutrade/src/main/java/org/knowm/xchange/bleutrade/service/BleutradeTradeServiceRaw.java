package org.knowm.xchange.bleutrade.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeException;
import org.knowm.xchange.bleutrade.BleutradeUtils;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;

public class BleutradeTradeServiceRaw extends BleutradeBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public String buyLimit(LimitOrder limitOrder) throws IOException {

    try {
      String pairString = BleutradeUtils.toPairString(limitOrder.getCurrencyPair());

      BleutradePlaceOrderReturn response = bleutrade.buyLimit(apiKey, signatureCreator, exchange.getNonceFactory(), pairString,
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult().getOrderid();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public String sellLimit(LimitOrder limitOrder) throws IOException {

    try {
      String pairString = BleutradeUtils.toPairString(limitOrder.getCurrencyPair());

      BleutradePlaceOrderReturn response = bleutrade.sellLimit(apiKey, signatureCreator, exchange.getNonceFactory(), pairString,
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult().getOrderid();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public boolean cancel(String orderId) throws IOException {

    try {
      BleutradeCancelOrderReturn response = bleutrade.cancel(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);

      return response.getSuccess();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public List<BleutradeOpenOrder> getBleutradeOpenOrders() throws IOException {

    try {
      BleutradeOpenOrdersReturn response = bleutrade.getOrders(apiKey, signatureCreator, exchange.getNonceFactory());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

}
