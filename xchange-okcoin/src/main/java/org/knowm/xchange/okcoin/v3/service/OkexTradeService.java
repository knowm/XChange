package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.okcoin.OkexAdaptersV3;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexOpenOrder;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexOrderFlags;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexTradeHistoryParams;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexTransaction;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderCancellationRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderCancellationResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderPlacementResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderPlacementType;
import org.knowm.xchange.okcoin.v3.dto.trade.Side;
import org.knowm.xchange.okcoin.v3.dto.trade.SpotOrderPlacementRequest;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class OkexTradeService extends OkexTradeServiceRaw implements TradeService {

  private static final int orders_limit = 100;
  private static final int transactions_limit = 100;

  public OkexTradeService(OkexExchangeV3 exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder o) throws IOException {

    // 0: Normal limit order (Unfilled and 0 represent normal limit order) 1: Post only 2: Fill Or
    // Kill 3: Immediatel Or Cancel
    OrderPlacementType orderType =
        o.hasFlag(OkexOrderFlags.POST_ONLY)
            ? OrderPlacementType.post_only
            : OrderPlacementType.normal;

    SpotOrderPlacementRequest req =
        SpotOrderPlacementRequest.builder()
            .instrumentId(OkexAdaptersV3.toSpotInstrument(o.getCurrencyPair()))
            .price(o.getLimitPrice())
            .size(o.getOriginalAmount())
            .side(o.getType() == OrderType.ASK ? Side.sell : Side.buy)
            .orderType(orderType)
            .build();
    OrderPlacementResponse placed = spotPlaceOrder(req);
    return placed.getOrderId();
  }

  public String placeMarginLimitOrder(LimitOrder o) throws IOException {

    // 0: Normal limit order (Unfilled and 0 represent normal limit order) 1: Post only 2: Fill Or
    // Kill 3: Immediatel Or Cancel
    OrderPlacementType orderType =
        o.hasFlag(OkexOrderFlags.POST_ONLY)
            ? OrderPlacementType.post_only
            : OrderPlacementType.normal;

    SpotOrderPlacementRequest req =
        SpotOrderPlacementRequest.builder()
            .instrumentId(OkexAdaptersV3.toSpotInstrument(o.getCurrencyPair()))
            .price(o.getLimitPrice())
            .size(o.getOriginalAmount())
            .side(o.getType() == OrderType.ASK ? Side.sell : Side.buy)
            .orderType(orderType)
            .build();
    OrderPlacementResponse placed = marginPlaceOrder(req);
    return placed.getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (!(orderParams instanceof CancelOrderByIdParams)
        || !(orderParams instanceof CancelOrderByCurrencyPair)) {
      throw new UnsupportedOperationException(
          "Cancelling an order is only available for a single market and a single id.");
    }

    String id = ((CancelOrderByIdParams) orderParams).getOrderId();
    String instrumentId =
        OkexAdaptersV3.toSpotInstrument(
            ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair());

    OrderCancellationRequest req =
        OrderCancellationRequest.builder().instrumentId(instrumentId).build();
    OrderCancellationResponse o = spotCancelOrder(id, req);
    return true;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new UnsupportedOperationException(
          "Getting open orders is only available for a single market.");
    }
    CurrencyPair pair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();

    final String instrument = OkexAdaptersV3.toSpotInstrument(pair);
    final String state = "6"; // "6": Incomplete（open+partially filled）

    String from = null;
    List<OkexOpenOrder> all = new ArrayList<>();
    boolean stop = false;
    do {

      List<OkexOpenOrder> l = getSpotOrderList(instrument, from, null, orders_limit, state);
      all.addAll(l);
      stop = l.size() < orders_limit;
      if (!stop) {
        from = l.get(l.size() - 1).getOrderId();
      }
    } while (!stop);
    return new OpenOrders(all.stream().map(OkexAdaptersV3::convert).collect(Collectors.toList()));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new OkexTradeHistoryParams();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new UnsupportedOperationException(
          "Getting open orders is only available for a single market.");
    }
    final String instrument =
        OkexAdaptersV3.toSpotInstrument(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());

    // the 'to' parameter means, fetch all orders newer than that
    final String to =
        params instanceof OkexTradeHistoryParams
            ? ((OkexTradeHistoryParams) params).getSinceOrderId()
            : null;

    final String state = "2"; // "2":Fully Filled

    String from = null;
    List<OkexOpenOrder> allOrdersWithTrades = new ArrayList<>();
    boolean stop = false;
    do {
      List<OkexOpenOrder> l = getSpotOrderList(instrument, from, to, orders_limit, state);
      allOrdersWithTrades.addAll(l);
      stop = l.size() < orders_limit;
      if (!stop) {
        from = l.get(l.size() - 1).getOrderId();
      }
    } while (!stop);
    // instrumentId, from, to, limit, state)

    List<UserTrade> userTrades = new ArrayList<>();
    allOrdersWithTrades.forEach(
        o -> {
          try {
            fetchTradesForOrder(o).stream()
                .filter(
                    t ->
                        Side.buy
                            == t.getSide()) // we consider only the "buy" transactions, since there
                // is the fee defined!
                .forEach(
                    t -> {
                      CurrencyPair p = OkexAdaptersV3.toPair(t.getInstrumentId());

                      BigDecimal amount = null;
                      Currency feeCurrency = null;

                      if (o.getSide() == Side.buy) { // the same side as the order!
                        amount = t.getSize();
                        feeCurrency = p.base;
                      } else { // order and trade (transaction) have different sides!
                        amount =
                            stripTrailingZeros(
                                t.getSize().divide(t.getPrice(), 16, RoundingMode.HALF_UP));
                        feeCurrency = p.counter;
                      }

                      UserTrade ut =
                          new UserTrade.Builder()
                              .currencyPair(p)
                              .id(t.getLedgerId())
                              .orderId(o.getOrderId())
                              .originalAmount(amount)
                              .price(t.getPrice())
                              .timestamp(t.getTimestamp())
                              .type(o.getSide() == Side.buy ? OrderType.BID : OrderType.ASK)
                              .feeAmount(t.getFee())
                              .feeCurrency(feeCurrency)
                              .build();
                      userTrades.add(ut);
                    });

          } catch (IOException e) {
            throw new ExchangeException("Could not fetch transactions for " + o, e);
          }
        });
    Collections.sort(userTrades, (t1, t2) -> t1.getTimestamp().compareTo(t2.getTimestamp()));
    return new UserTrades(userTrades, TradeSortType.SortByTimestamp);
  }

  public UserTrades getMarginTradeHistory(TradeHistoryParams params) throws IOException {

    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new UnsupportedOperationException(
          "Getting open orders is only available for a single market.");
    }
    final String instrument =
        OkexAdaptersV3.toSpotInstrument(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());

    // the 'to' parameter means, fetch all orders newer than that
    final String to =
        params instanceof OkexTradeHistoryParams
            ? ((OkexTradeHistoryParams) params).getSinceOrderId()
            : null;

    final String state = "2"; // "2":Fully Filled

    String from = null;
    List<OkexOpenOrder> allOrdersWithTrades = new ArrayList<>();
    boolean stop = false;
    do {
      List<OkexOpenOrder> l = getMarginOrderList(instrument, from, to, orders_limit, state);
      allOrdersWithTrades.addAll(l);
      stop = l.size() < orders_limit;
      if (!stop) {
        from = l.get(l.size() - 1).getOrderId();
      }
    } while (!stop);
    // instrumentId, from, to, limit, state)

    List<UserTrade> userTrades = new ArrayList<>();
    allOrdersWithTrades.forEach(
        o -> {
          try {
            fetchMarginTradesForOrder(o).stream()
                .filter(
                    t ->
                        Side.buy
                            == t.getSide()) // we consider only the "buy" transactions, since there
                // is the fee defined!
                .forEach(
                    t -> {
                      CurrencyPair p = OkexAdaptersV3.toPair(t.getInstrumentId());

                      BigDecimal amount = null;
                      Currency feeCurrency = null;

                      if (o.getSide() == Side.buy) { // the same side as the order!
                        amount = t.getSize();
                        feeCurrency = p.base;
                      } else { // order and trade (transaction) have different sides!
                        amount =
                            stripTrailingZeros(
                                t.getSize().divide(t.getPrice(), 16, RoundingMode.HALF_UP));
                        feeCurrency = p.counter;
                      }

                      UserTrade ut =
                          new UserTrade.Builder()
                              .currencyPair(p)
                              .id(t.getLedgerId())
                              .orderId(o.getOrderId())
                              .originalAmount(amount)
                              .price(t.getPrice())
                              .timestamp(t.getTimestamp())
                              .type(o.getSide() == Side.buy ? OrderType.BID : OrderType.ASK)
                              .feeAmount(t.getFee())
                              .feeCurrency(feeCurrency)
                              .build();
                      userTrades.add(ut);
                    });

          } catch (IOException e) {
            throw new ExchangeException("Could not fetch transactions for " + o, e);
          }
        });
    Collections.sort(userTrades, (t1, t2) -> t1.getTimestamp().compareTo(t2.getTimestamp()));
    return new UserTrades(userTrades, TradeSortType.SortByTimestamp);
  }

  private static BigDecimal stripTrailingZeros(BigDecimal bd) {
    bd = bd.stripTrailingZeros();
    bd = bd.setScale(Math.max(bd.scale(), 0));
    return bd;
  }

  private List<OkexTransaction> fetchTradesForOrder(OkexOpenOrder o) throws IOException {
    String from = null;
    List<OkexTransaction> all = new ArrayList<>();
    boolean stop = false;
    do {
      List<OkexTransaction> l =
          getSpotTransactionDetails(o.getOrderId(), o.getInstrumentId(), from, null, null);
      all.addAll(l);
      stop = l.size() < transactions_limit;
      if (!stop) {
        from = l.get(l.size() - 1).getLedgerId();
      }
    } while (!stop);
    return all;
  }

  private List<OkexTransaction> fetchMarginTradesForOrder(OkexOpenOrder o) throws IOException {
    String from = null;
    List<OkexTransaction> all = new ArrayList<>();
    boolean stop = false;
    do {
      List<OkexTransaction> l =
          getMarginTransactionDetails(o.getOrderId(), o.getInstrumentId(), from, null, null);
      all.addAll(l);
      stop = l.size() < transactions_limit;
      if (!stop) {
        from = l.get(l.size() - 1).getLedgerId();
      }
    } while (!stop);
    return all;
  }
}
