package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.OkCoinUtils;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinTradeResult;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkCoinTradeService extends OkCoinTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder>emptyList());

  private final Logger log = LoggerFactory.getLogger(OkCoinTradeService.class);

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws IOException {
    // TODO use params to specify currency pair
    List<CurrencyPair> exchangeSymbols = exchange.getExchangeSymbols();

    List<OkCoinOrderResult> orderResults = new ArrayList<>(exchangeSymbols.size());

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      log.debug("Getting order: {}", symbol);
      OkCoinOrderResult orderResult = getOrder(-1, OkCoinAdapters.adaptSymbol(symbol));
      if (orderResult.getOrders().length > 0) {
        orderResults.add(orderResult);
      }
    }

    if (orderResults.size() <= 0) {
      return noOpenOrders;
    }

    return OkCoinAdapters.adaptOpenOrders(orderResults);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    String marketOrderType = null;
    String rate = null;
    String amount = null;

    if (marketOrder.getType().equals(OrderType.BID)) {
      marketOrderType = "buy_market";
      rate = marketOrder.getOriginalAmount().toPlainString();
      amount = "1";
    } else {
      marketOrderType = "sell_market";
      rate = "1";
      amount = marketOrder.getOriginalAmount().toPlainString();
    }

    long orderId = trade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()), marketOrderType, rate, amount).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    long orderId = trade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()), limitOrder.getType() == OrderType.BID ? "buy" : "sell",
        limitOrder.getLimitPrice().toPlainString(), limitOrder.getOriginalAmount().toPlainString()).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    boolean ret = false;
    long id = Long.valueOf(orderId);

    List<CurrencyPair> exchangeSymbols = exchange.getExchangeSymbols();
    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      try {
        OkCoinTradeResult cancelResult = cancelOrder(id, OkCoinAdapters.adaptSymbol(symbol));

        if (id == cancelResult.getOrderId()) {
          ret = true;
        }
        break;
      } catch (ExchangeException e) {
        if (e.getMessage().equals(OkCoinUtils.getErrorMessage(1009))) {
          // order not found.
          continue;
        }
      }
    }
    return ret;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Required parameters {@link TradeHistoryParamPaging} Supported parameters {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
    Integer pageLength = paging.getPageLength();
    Integer pageNumber = paging.getPageNumber();

    // pages supposedly start from 1
    ++pageNumber;

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    if (pair == null) {
      pair = useIntl ? CurrencyPair.BTC_USD : CurrencyPair.BTC_CNY;
    }

    OkCoinOrderResult orderHistory = getOrderHistory(OkCoinAdapters.adaptSymbol(pair), "1", toString(pageNumber), toString(pageLength));
    return OkCoinAdapters.adaptTrades(orderHistory);
  }

  private static String toString(Object o) {

    return o == null ? null : o.toString();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new OkCoinTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  public static class OkCoinTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair {

    private CurrencyPair pair;

    public OkCoinTradeHistoryParams() {
    }

    public OkCoinTradeHistoryParams(Integer pageLength, Integer pageNumber, CurrencyPair pair) {

      super(pageLength, pageNumber);
      this.pair = pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
