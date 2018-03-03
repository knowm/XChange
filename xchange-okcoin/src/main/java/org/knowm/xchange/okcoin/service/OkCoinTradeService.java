package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.meta.RateLimit;
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
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class OkCoinTradeService extends OkCoinTradeServiceRaw implements TradeService {

  private static final String ORDER_STATUS_FILLED = "1";

  private static final OpenOrders NO_OPEN_ORDERS = new OpenOrders(Collections.<LimitOrder>emptyList());

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
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<OkCoinOrderResult> orderResults = new ArrayList<>();

    List<CurrencyPair> exchangeSymbols;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      CurrencyPair symbol = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      exchangeSymbols = Collections.singletonList(symbol);
    } else if (params instanceof OpenOrdersParamMultiCurrencyPair) {
      exchangeSymbols = new ArrayList<>(((OpenOrdersParamMultiCurrencyPair) params).getCurrencyPairs());
    } else {
      exchangeSymbols = exchange.getExchangeSymbols();
    }
    for (CurrencyPair symbol : exchangeSymbols) {
      try {
        OkCoinOrderResult orderResult = getOrder(-1, OkCoinAdapters.adaptSymbol(symbol));
        if (orderResult.getOrders().length > 0) {
          orderResults.add(orderResult);
        }
      } catch (Exception e) {
        if (exchangeSymbols.indexOf(symbol) == exchangeSymbols.size() - 1) {
          waitBecauseOfRateLimit();
        }
        // Market not present.
      }
    }

    if (orderResults.size() <= 0) {
      return NO_OPEN_ORDERS;
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
          if (i != exchangeSymbols.size() - 1) {
            waitBecauseOfRateLimit();
          }
          // order not found.
          continue;
        }
      }
    }
    return ret;
  }

  /**
   * Needs to be used in case multiple API calls need to be made. Use that carefully (only if there is absolutely no other way) as it is not
   * advised to make multiple calls since XChange calls need to be as atomical as possible.
   */
  private synchronized void waitBecauseOfRateLimit() {
    try {
      Optional<RateLimit> rateLimitOptional = Arrays.stream(exchange.getExchangeMetaData().getPublicRateLimits()).findAny();
      if (rateLimitOptional.isPresent()) {
        Thread.sleep(rateLimitOptional.get().getPollDelayMillis());
      }
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
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
   * OKEX does not support trade history in the usual way, it only provides a aggregated view on a per order basis of how much the order has been
   * filled and the average price. Individual trade details are not available. As a consequence of this, the trades supplied by this method will use
   * the order ID as their trade ID, and will be subject to being amended if a partially filled order if further filled.
   * <p>
   * Supported parameters are {@link TradeHistoryParamCurrencyPair} and {@link TradeHistoryParamPaging}, if not supplied then the query will default to
   * BTC/USD or BTC/CNY (depending on session configuration) and the last 200 trades.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer pageLength = null, pageNumber = null;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      pageLength = paging.getPageLength();
      pageNumber = paging.getPageNumber();
    }
    if (pageNumber == null) {
      pageNumber = 1; // pages start from 1
    }
    if (pageLength == null) {
      pageLength = 200; // 200 is the maximum number
    }

    CurrencyPair pair = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (pair == null) {
      pair = useIntl ? CurrencyPair.BTC_USD : CurrencyPair.BTC_CNY;
    }

    OkCoinOrderResult orderHistory = getOrderHistory(OkCoinAdapters.adaptSymbol(pair), ORDER_STATUS_FILLED, pageNumber.toString(),
        pageLength.toString());
    return OkCoinAdapters.adaptTrades(orderHistory);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new OkCoinTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
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
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
