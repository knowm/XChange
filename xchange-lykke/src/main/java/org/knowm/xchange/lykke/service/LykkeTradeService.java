package org.knowm.xchange.lykke.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.lykke.LykkeAdapter;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class LykkeTradeService extends LykkeTradeServiceRaw implements TradeService {

  public LykkeTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    // default: 100
    return new OpenOrders(
        LykkeAdapter.adaptOpenOrders(exchange.getExchangeSymbols(), getLastOrders()));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return new OpenOrders(
        LykkeAdapter.adaptOpenOrders(exchange.getExchangeSymbols(), getLastOrders()));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeLimitOrder(LykkeAdapter.adaptLykkeOrder(limitOrder));
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelLykkeOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else if (orderParams instanceof CancelOrderByCurrencyPair) {
      return cancelAllLykkeOrders(
          LykkeAdapter.adaptToAssetPair(
              ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair()));
    }
    throw new NotYetImplementedForExchangeException(
        "This method accepts only instance of CancelOrderByIdParams.");
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (params instanceof TradeHistoryParamsAll) {
      return new UserTrades(
          LykkeAdapter.adaptUserTrades(
              exchange.getExchangeSymbols(),
              getMathedOrders(((TradeHistoryParamsAll) params).getPageLength())),
          Trades.TradeSortType.SortByTimestamp);
    }
    throw new NotYetImplementedForExchangeException(
        "This method accepts only instance of TradeHistoryParamsAll.");
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }
}
