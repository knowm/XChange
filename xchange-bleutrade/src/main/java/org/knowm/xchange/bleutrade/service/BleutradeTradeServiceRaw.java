package org.knowm.xchange.bleutrade.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeException;
import org.knowm.xchange.bleutrade.BleutradeUtils;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.util.List;

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

  public List<BleutradeOpenOrder> getTrades(TradeHistoryParams params) throws IOException {
    BleutradeTradeHistoryParams bleutradeTradeHistoryParams;
    if (params instanceof BleutradeTradeHistoryParams) {
      bleutradeTradeHistoryParams = (BleutradeTradeHistoryParams) params;
    } else {
      bleutradeTradeHistoryParams = BleutradeTradeHistoryParams.ALL;
    }

    try {
      BleutradeOpenOrdersReturn response = bleutrade.getTrades(apiKey, signatureCreator, exchange.getNonceFactory(),
          bleutradeTradeHistoryParams.market,
          bleutradeTradeHistoryParams.orderStatus,
          bleutradeTradeHistoryParams.orderType
      );

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public static class BleutradeTradeHistoryParams implements TradeHistoryParams {
    public static final BleutradeTradeHistoryParams ALL = new BleutradeTradeHistoryParams("ALL", "ALL", "ALL");
    /**
     * DIVIDEND_DIVISOR or ALL
     */
    public final String market;

    /**
     * ALL, OK, OPEN, CANCELED
     */
    public final String orderStatus;

    /**
     * ALL, BUY, SELL
     */
    public final String orderType;

    public BleutradeTradeHistoryParams(String market, String orderStatus, String orderType) {
      this.market = market;
      this.orderStatus = orderStatus;
      this.orderType = orderType;
    }
  }

}
