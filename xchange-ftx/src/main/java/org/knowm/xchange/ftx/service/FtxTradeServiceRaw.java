package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.FtxPositionDto;
import org.knowm.xchange.ftx.dto.trade.*;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FtxTradeServiceRaw extends FtxBaseService {

  public FtxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public String placeMarketOrderForSubaccount(String subaccount, MarketOrder marketOrder)
      throws IOException {
    return placeNewFtxOrder(subaccount, FtxAdapters.adaptMarketOrderToFtxOrderPayload(marketOrder))
        .getResult()
        .getId();
  }

  public String placeLimitOrderForSubaccount(String subaccount, LimitOrder limitOrder)
      throws IOException {
    return placeNewFtxOrder(subaccount, FtxAdapters.adaptLimitOrderToFtxOrderPayload(limitOrder))
        .getResult()
        .getId();
  }

  public FtxResponse<FtxOrderDto> placeNewFtxOrder(
      String subaccount, FtxOrderRequestPayload payload) throws FtxException, IOException {
    try {
      return ftx.placeOrder(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          payload);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxOrderDto> modifyFtxOrder(
      String subaccount, String orderId, FtxModifyOrderRequestPayload payload)
      throws FtxException, IOException {

    return ftx.modifyOrder(
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory().createValue(),
        signatureCreator,
        subaccount,
        orderId,
        payload);
  }

  public FtxResponse<FtxOrderDto> modifyFtxOrderByClientId(
      String subaccount, String clientId, FtxModifyOrderRequestPayload payload)
      throws FtxException, IOException {

    return ftx.modifyOrderByClientId(
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory().createValue(),
        signatureCreator,
        subaccount,
        clientId,
        payload);
  }

  public boolean cancelOrderForSubaccount(String subaccount, String orderId) throws IOException {
    return cancelFtxOrder(subaccount, orderId);
  }

  public boolean cancelFtxOrder(String subaccount, String orderId)
      throws FtxException, IOException {
    try {
      return ftx.cancelOrder(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount,
              orderId)
          .isSuccess();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public boolean cancelFtxByClientId(String subaccount, String clientId)
      throws FtxException, IOException {
    try {
      return ftx.cancelOrderByClientId(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount,
              clientId)
          .isSuccess();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public boolean cancelOrderForSubaccount(String subaccount, CancelOrderParams orderParams)
      throws IOException {
    if (orderParams instanceof CancelOrderByCurrencyPair) {
      return cancelAllFtxOrders(
          subaccount,
          new CancelAllFtxOrdersParams(
              FtxAdapters.adaptCurrencyPairToFtxMarket(
                  ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair())));
    } else if (orderParams instanceof CancelOrderByUserReferenceParams) {
      return cancelFtxByClientId(
          subaccount, ((CancelOrderByUserReferenceParams) orderParams).getUserReference());
    } else if (orderParams instanceof CancelConditionalOrderFtxParams) {
      return cancelFtxConditionalOrderForSubAccount(
          subaccount, ((CancelConditionalOrderFtxParams) orderParams).getOrderId());
    } else {
      throw new IOException(
          "CancelOrderParams must implement CancelOrderByCurrencyPair interface.");
    }
  }

  public boolean cancelAllFtxOrders(String subaccount, CancelAllFtxOrdersParams payLoad)
      throws FtxException, IOException {
    try {
      return ftx.cancelAllOrders(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount,
              payLoad)
          .isSuccess();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public Collection<Order> getOrderFromSubaccount(String subaccount, String... orderIds)
      throws IOException {
    List<Order> orderList = new ArrayList<>();
    for (String orderId : orderIds) {
      Order order = FtxAdapters.adaptLimitOrder(getFtxOrderStatus(subaccount, orderId).getResult());
      orderList.add(order);
    }
    return orderList;
  }

  public FtxResponse<List<FtxOrderDto>> getFtxOpenOrders(String subaccount, String market)
      throws FtxException, IOException {
    try {
      return ftx.openOrders(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          market);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public UserTrades getTradeHistoryForSubaccount(String subaccount, TradeHistoryParams params)
      throws IOException {
    if (params instanceof TradeHistoryParamsAll) {
      CurrencyPair currencyPair =
          new CurrencyPair(((TradeHistoryParamsAll) params).getInstrument().toString());
      return FtxAdapters.adaptUserTrades(
          getFtxOrderHistory(
                  subaccount,
                  FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair),
                  ((TradeHistoryParamsAll) params).getStartTime().getTime(),
                  ((TradeHistoryParamsAll) params).getEndTime().getTime())
              .getResult());
    } else if (params instanceof TradeHistoryParamCurrencyPair) {
      return FtxAdapters.adaptUserTrades(
          getFtxOrderHistory(
                  subaccount,
                  FtxAdapters.adaptCurrencyPairToFtxMarket(
                      ((TradeHistoryParamCurrencyPair) params).getCurrencyPair()),
                  null,
                  null)
              .getResult());
    } else if (params instanceof TradeHistoryParamInstrument) {
      CurrencyPair currencyPair =
          new CurrencyPair(((TradeHistoryParamInstrument) params).getInstrument().toString());
      return FtxAdapters.adaptUserTrades(
          getFtxOrderHistory(
                  subaccount, FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair), null, null)
              .getResult());
    } else {
      throw new IOException(
          "TradeHistoryParams must implement TradeHistoryParamCurrencyPair or TradeHistoryParamInstrument interface.");
    }
  }

  public FtxResponse<List<FtxOrderDto>> getFtxOrderHistory(
      String subaccount, String market, Long startTime, Long endTime)
      throws FtxException, IOException {
    try {
      return ftx.orderHistory(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          market,
          startTime,
          endTime);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public OpenOrders getOpenOrdersForSubaccount(String subaccount) throws IOException {
    return FtxAdapters.adaptOpenOrders(getFtxAllOpenOrdersForSubaccount(subaccount));
  }

  public OpenOrders getOpenOrdersForSubaccount(String subaccount, OpenOrdersParams params)
      throws IOException {
    if (params instanceof FtxTriggerOpenOrdersParams) {
      return FtxAdapters.adaptTriggerOpenOrders(
          getFtxOpenConditionalOrdersForSubAccount(
              subaccount,
              FtxAdapters.adaptCurrencyPairToFtxMarket(
                  ((OpenOrdersParamCurrencyPair) params).getCurrencyPair())));
    } else if (params instanceof CurrencyPairParam) {
      return FtxAdapters.adaptOpenOrders(
          getFtxOpenOrders(
              subaccount,
              FtxAdapters.adaptCurrencyPairToFtxMarket(
                  ((CurrencyPairParam) params).getCurrencyPair())));
    } else {
      throw new IOException("OpenOrdersParams must implement CurrencyPairParam interface.");
    }
  }

  public FtxResponse<List<FtxOrderDto>> getFtxAllOpenOrdersForSubaccount(String subaccount)
      throws FtxException, IOException {
    try {
      return ftx.openOrdersWithoutMarket(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxOrderDto> getFtxOrderStatus(String subaccount, String orderId)
      throws FtxException, IOException {
    try {
      return ftx.getOrderStatus(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          orderId);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public OpenPositions getOpenPositionsForSubaccount(String subaccount) throws IOException {
    return FtxAdapters.adaptOpenPositions(getFtxPositions(subaccount).getResult());
  }

  public FtxResponse<List<FtxPositionDto>> getFtxPositions(String subaccount)
      throws FtxException, IOException {
    try {
      return ftx.getFtxPositions(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          true);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public String placeStopOrderForSubAccount(String subaccount, StopOrder stopOrder)
      throws IOException {
    return placeNewFtxConditionalOrderForSubAccount(
            subaccount, FtxAdapters.adaptStopOrderToFtxOrderPayload(stopOrder))
        .getResult()
        .getId();
  }

  public boolean cancelStopOrder(String orderId) throws IOException {
    return cancelFtxConditionalOrderForSubAccount(
        exchange.getExchangeSpecification().getUserName(), orderId);
  }

  public String changeStopOrder(StopOrder stopOrder) throws IOException {
    return modifyFtxConditionalOrderForSubAccount(
            exchange.getExchangeSpecification().getUserName(),
            stopOrder.getId(),
            FtxAdapters.adaptModifyConditionalOrderToFtxOrderPayload(stopOrder))
        .getResult()
        .getId();
  }

  public FtxResponse<FtxConditionalOrderDto> placeNewFtxConditionalOrderForSubAccount(
      String subaccount, FtxConditionalOrderRequestPayload payload)
      throws FtxException, IOException {
    try {
      return ftx.placeConditionalOrder(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          payload);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxConditionalOrderDto> modifyFtxConditionalOrderForSubAccount(
      String subaccount, String orderId, FtxModifyConditionalOrderRequestPayload payload)
      throws FtxException, IOException {

    return ftx.modifyConditionalOrder(
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory().createValue(),
        signatureCreator,
        subaccount,
        orderId,
        payload);
  }

  public boolean cancelFtxConditionalOrderForSubAccount(String subaccount, String orderId)
      throws FtxException, IOException {
    try {
      return ftx.cancelConditionalOrder(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount,
              orderId)
          .isSuccess();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxConditionalOrderDto>> getFtxOpenConditionalOrdersForSubAccount(
      String subaccount, String market) throws FtxException, IOException {
    try {
      return ftx.openConditionalOrders(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          market);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxConditionalOrderDto>> getFtxConditionalOrderHistory(
      String subaccount, String market) throws FtxException, IOException {
    try {
      return ftx.conditionalOrderHistory(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          market);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxTriggerDto>> getFtxTriggersForSubAccount(
      String subaccount, String orderId) throws FtxException, IOException {
    try {
      return ftx.getTriggers(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          orderId);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public List<FtxTriggerDto> getFtxTriggers(String orderId) throws FtxException, IOException {
    return getFtxTriggersForSubAccount(exchange.getExchangeSpecification().getUserName(), orderId)
        .getResult();
  }
}
