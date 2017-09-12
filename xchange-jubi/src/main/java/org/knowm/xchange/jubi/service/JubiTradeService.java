package org.knowm.xchange.jubi.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.jubi.JubiAdapters;
import org.knowm.xchange.jubi.dto.trade.JubiOrderHistory;
import org.knowm.xchange.jubi.dto.trade.JubiOrderType;
import org.knowm.xchange.jubi.dto.trade.JubiTradeResult;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * Created by Dzf on 2017/7/16.
 */
public class JubiTradeService extends JubiTradeServiceRaw implements TradeService {

  public JubiTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, IOException {
    CurrencyPair currencyPair = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }
    //A specific e-coin type(CurrencyPair) should be assigned, otherwise the request would be invalid.
    if (null == currencyPair) {
      throw new NotYetImplementedForExchangeException();
    }
    return JubiAdapters.adaptOpenOrders(getJubiOpenOrder(currencyPair), currencyPair);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException {
    JubiOrderType type = order.getType().equals(Order.OrderType.BID) ? JubiOrderType.Buy : JubiOrderType.Sell;
    CurrencyPair currencyPair = order.getCurrencyPair();
    JubiTradeResult result = placeJubiOrder(currencyPair, order.getTradableAmount(), order.getLimitPrice(), type);
    if (result.isSuccess()) {
      return result.getId().toPlainString();
    } else {
      throw new ExchangeException(Integer.toString(result.getErrorCode()));
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    //A specific e-coin type(CurrencyPair) should be assigned, otherwise the request would be invalid.
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof JubiCancelOrderParams) {
      JubiCancelOrderParams params = (JubiCancelOrderParams) orderParams;
      return cancelJubiOrder(params.getCurrencyPair(), params.getOrderId());
    }
    return false;
  }

  /**
   * Required parameter types: {@link TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException, NotYetImplementedForExchangeException {
    CurrencyPair currencyPair = null;
    Date startTime = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (params instanceof TradeHistoryParamsTimeSpan) {
      startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
    }
    //A specific e-coin type(CurrencyPair) should be assigned, otherwise the request would be invalid.
    if (null == currencyPair) {
      throw new NotYetImplementedForExchangeException();
    }
    JubiOrderHistory jubiOrderHistroys = getJubiOrderHistory(currencyPair, startTime);
    return JubiAdapters.adaptUserTrades(jubiOrderHistroys, currencyPair);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    //A specific e-coin type(CurrencyPair) should be assigned, otherwise the request would be invalid.
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    //A specific e-coin type(CurrencyPair) should be assigned, otherwise the request would be invalid.
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
