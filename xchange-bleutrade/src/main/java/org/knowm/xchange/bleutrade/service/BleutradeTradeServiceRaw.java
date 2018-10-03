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
import org.knowm.xchange.bleutrade.dto.trade.BluetradeExecutedTrade;
import org.knowm.xchange.bleutrade.dto.trade.BluetradeExecutedTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import si.mazi.rescu.IRestProxyFactory;

public class BleutradeTradeServiceRaw extends BleutradeBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeTradeServiceRaw(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange, restProxyFactory);
  }

  private static String toMarket(CurrencyPair currencyPair) {
    return currencyPair.base + "_" + currencyPair.counter;
  }

  public String buyLimit(LimitOrder limitOrder) throws IOException {

    try {
      String pairString = BleutradeUtils.toPairString(limitOrder.getCurrencyPair());

      BleutradePlaceOrderReturn response =
          bleutrade.buyLimit(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              pairString,
              limitOrder.getOriginalAmount().toPlainString(),
              limitOrder.getLimitPrice().toPlainString());

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

      BleutradePlaceOrderReturn response =
          bleutrade.sellLimit(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              pairString,
              limitOrder.getOriginalAmount().toPlainString(),
              limitOrder.getLimitPrice().toPlainString());

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
      BleutradeCancelOrderReturn response =
          bleutrade.cancel(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);

      return response.getSuccess();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public List<BleutradeOpenOrder> getBleutradeOpenOrders() throws IOException {

    try {
      BleutradeOpenOrdersReturn response =
          bleutrade.getOrders(apiKey, signatureCreator, exchange.getNonceFactory());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public List<BluetradeExecutedTrade> getTrades(TradeHistoryParams params) throws IOException {
    String market = null;
    String orderStatus = null;
    String orderType = null;
    Integer limit = null;

    BleutradeTradeHistoryParams bleutradeTradeHistoryParams;

    if (params instanceof BleutradeTradeHistoryParams) {
      bleutradeTradeHistoryParams = (BleutradeTradeHistoryParams) params;
      market = bleutradeTradeHistoryParams.market;
      orderStatus = bleutradeTradeHistoryParams.orderStatus;
      orderType = bleutradeTradeHistoryParams.orderType;
      limit = bleutradeTradeHistoryParams.getLimit();
    }

    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit tradeHistoryParamLimit = (TradeHistoryParamLimit) params;
      limit = tradeHistoryParamLimit.getLimit();
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();

      if (currencyPair != null) market = toMarket(currencyPair);
    }

    if (market == null) {
      market = BleutradeTradeHistoryParams.ALL.market;
    }

    if (orderStatus == null) {
      orderStatus = BleutradeTradeHistoryParams.ALL.orderStatus;
    }

    if (orderType == null) {
      orderType = BleutradeTradeHistoryParams.ALL.orderType;
    }

    try {
      BluetradeExecutedTradesWrapper response =
          bleutrade.getTrades(
              apiKey,
              signatureCreator,
              exchange.getNonceFactory(),
              market,
              orderStatus,
              orderType,
              limit);

      if (!response.success) {
        throw new ExchangeException(response.message);
      }

      return response.result;
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
    }
  }

  public static class BleutradeTradeHistoryParams
      implements TradeHistoryParams, TradeHistoryParamLimit {
    public static final BleutradeTradeHistoryParams ALL =
        new BleutradeTradeHistoryParams("ALL", "OK", "ALL", 500);

    /** DIVIDEND_DIVISOR or ALL */
    public final String market;

    /** ALL, OK, OPEN, CANCELED */
    public final String orderStatus;

    /** ALL, BUY, SELL */
    public final String orderType;

    /** Max is 20000 */
    public Integer limit = 500;

    public BleutradeTradeHistoryParams(
        CurrencyPair currencyPair, String orderStatus, String orderType, Integer limit) {
      this(toMarket(currencyPair), orderStatus, orderType, limit);
    }

    public BleutradeTradeHistoryParams(
        String market, String orderStatus, String orderType, Integer limit) {
      this.market = market;
      this.orderStatus = orderStatus;
      this.orderType = orderType;
      this.limit = limit;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }
  }
}
