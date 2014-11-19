package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.BleutradeUtils;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class BleutradeTradeServiceRaw extends BleutradeBasePollingService<BleutradeAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BleutradeTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BleutradeAuthenticated.class, exchangeSpecification);
  }

  public String buyLimit(LimitOrder limitOrder) throws IOException {

    try {
      String pairString = BleutradeUtils.toPairString(limitOrder.getCurrencyPair());

      BleutradePlaceOrderReturn response =
          bleutrade.buyLimit(apiKey, signatureCreator, String.valueOf(nextNonce()), pairString, limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult().getOrderid();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public String sellLimit(LimitOrder limitOrder) throws IOException {

    try {
      String pairString = BleutradeUtils.toPairString(limitOrder.getCurrencyPair());

      BleutradePlaceOrderReturn response =
          bleutrade.sellLimit(apiKey, signatureCreator, String.valueOf(nextNonce()), pairString, limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult().getOrderid();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public boolean cancel(String orderId) throws IOException {

    try {
      BleutradeCancelOrderReturn response = bleutrade.cancel(apiKey, signatureCreator, String.valueOf(nextNonce()), orderId);

      return response.getSuccess();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<BleutradeOpenOrder> getBleutradeOpenOrders() throws IOException {

    try {
      BleutradeOpenOrdersReturn response = bleutrade.getOrders(apiKey, signatureCreator, String.valueOf(nextNonce()));

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}
