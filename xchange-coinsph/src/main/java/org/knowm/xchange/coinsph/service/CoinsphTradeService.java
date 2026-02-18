package org.knowm.xchange.coinsph.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphAdapters;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.trade.CoinsphOrder;
import org.knowm.xchange.coinsph.dto.trade.CoinsphTradeHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinsphTradeService extends CoinsphTradeServiceRaw implements TradeService {

  private static final Logger LOG = LoggerFactory.getLogger(CoinsphTradeService.class);

  public CoinsphTradeService(CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, CoinsphException {
    List<CoinsphOrder> coinsphOpenOrders = getCoinsphOpenOrders(null); // null for all symbols
    return CoinsphAdapters.adaptOpenOrders(coinsphOpenOrders);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException, CoinsphException {
    CurrencyPair currencyPair = null;
    if (params
        instanceof org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair) {
      currencyPair =
          ((org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair) params)
              .getCurrencyPair();
    } else if (params
        instanceof org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument) {
      org.knowm.xchange.instrument.Instrument instrument =
          ((org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument) params)
              .getInstrument();
      if (instrument instanceof org.knowm.xchange.currency.CurrencyPair) {
        currencyPair = (org.knowm.xchange.currency.CurrencyPair) instrument;
      }
    }
    // Add other param checks if Coins.ph supports more specific open order queries
    List<CoinsphOrder> coinsphOpenOrders = getCoinsphOpenOrders(currencyPair);
    return CoinsphAdapters.adaptOpenOrders(coinsphOpenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, CoinsphException {
    CoinsphOrder placedOrder = placeCoinsphMarketOrder(marketOrder);
    return String.valueOf(placedOrder.getOrderId());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, CoinsphException {
    CoinsphOrder placedOrder = placeCoinsphLimitOrder(limitOrder);
    return String.valueOf(placedOrder.getOrderId());
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException, CoinsphException {
    CoinsphOrder placedOrder = placeCoinsphStopOrder(stopOrder);
    return String.valueOf(placedOrder.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, CoinsphException {
    // For now, assume cancelOrder(CancelOrderParams) is used
    throw new UnsupportedOperationException(
        "cancelOrder by orderId only is not supported. Use CancelOrderParams.");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException, CoinsphException {
    return cancelCoinsphOrder(params);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws IOException, CoinsphException {
    // Mandatory param: either currencyPair or instrument
    String symbol = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (currencyPair != null) {
        symbol = CoinsphAdapters.toSymbol(currencyPair);
      }
    }
    if (params instanceof TradeHistoryParamInstrument) {
      Instrument instrument = ((TradeHistoryParamInstrument) params).getInstrument();
      if (instrument != null) {
        symbol = CoinsphAdapters.toSymbol(instrument);
      }
    }
    if (symbol == null) {
      throw new IllegalArgumentException(
          "TradeHistoryParams must include either CurrencyPair or Instrument for Coins.ph");
    }

    Long startTime = null;
    if (params instanceof org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan) {
      org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan timeParams =
          (org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan) params;
      if (timeParams.getStartTime() != null) {
        startTime = timeParams.getStartTime().getTime();
      }
    }

    Long endTime = null;
    if (params instanceof org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan) {
      org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan timeParams =
          (org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan) params;
      if (timeParams.getEndTime() != null) {
        endTime = timeParams.getEndTime().getTime();
      }
    }

    Integer limit = null;
    if (params instanceof org.knowm.xchange.service.trade.params.TradeHistoryParamLimit) {
      limit = ((org.knowm.xchange.service.trade.params.TradeHistoryParamLimit) params).getLimit();
    }

    Long fromTradeId = null;
    if (params instanceof CoinsphTradeHistoryParams) {
      String startIdStr = ((CoinsphTradeHistoryParams) params).getStartId();
      if (startIdStr != null) {
        try {
          fromTradeId = Long.parseLong(startIdStr);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              "startId from CoinsphTradeHistoryParams must be a Long for Coins.ph", e);
        }
      }
    } else if (params instanceof org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan) {
      String startIdStr =
          ((org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan) params).getStartId();
      if (startIdStr != null) {
        try {
          fromTradeId = Long.parseLong(startIdStr);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              "startId from TradeHistoryParamsIdSpan must be a Long for Coins.ph", e);
        }
      }
    }
    // TradeHistoryParamPaging (pageNumber, pageLength) is not used for fromTradeId here.

    Long orderId = null;
    if (params instanceof org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId) {
      String orderIdStr =
          ((org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId) params).getOrderId();
      if (orderIdStr != null) {
        try {
          orderId = Long.parseLong(orderIdStr);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("orderId must be a Long for Coins.ph", e);
        }
      }
    }

    List<org.knowm.xchange.coinsph.dto.trade.CoinsphUserTrade> userTrades =
        getCoinsphUserTrades(symbol, orderId, startTime, endTime, fromTradeId, limit);
    return CoinsphAdapters.adaptUserTrades(userTrades);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinsphTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new org.knowm.xchange.coinsph.dto.trade.CoinsphOpenOrdersParams();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException, CoinsphException {
    List<Order> orders = new ArrayList<>();

    // Coins.ph API requires a symbol for getOrderStatus, so we can't directly fetch multiple orders
    // Instead, we'll fetch each order individually and combine the results
    for (String orderId : orderIds) {
      try {
        // Try to find the order in open orders first (which includes symbol information)
        List<CoinsphOrder> openOrders = getCoinsphOpenOrders(null);
        Optional<CoinsphOrder> matchingOpenOrder =
            openOrders.stream()
                .filter(order -> String.valueOf(order.getOrderId()).equals(orderId))
                .findFirst();

        if (matchingOpenOrder.isPresent()) {
          orders.add(CoinsphAdapters.adaptOrder(matchingOpenOrder.get()));
        } else {
          // If not found in open orders, we need to query order history
          // This is a limitation as we don't know the symbol for a specific orderId
          // In a real implementation, you might want to cache symbol-orderId mappings
          // or query all symbols the user has traded
          LOG.warn(
              "Order {} not found in open orders. Cannot query by ID without symbol.", orderId);
        }
      } catch (Exception e) {
        LOG.error("Error fetching order {}: {}", orderId, e.getMessage());
      }
    }

    return orders;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... params)
      throws IOException, CoinsphException {
    List<Order> orders = new java.util.ArrayList<>();
    for (OrderQueryParams param : params) {
      String orderId = param.getOrderId();
      org.knowm.xchange.currency.CurrencyPair pair = null;

      if (param instanceof org.knowm.xchange.service.trade.params.CurrencyPairParam) {
        pair = ((org.knowm.xchange.service.trade.params.CurrencyPairParam) param).getCurrencyPair();
      } else if (param instanceof org.knowm.xchange.service.trade.params.InstrumentParam) {
        org.knowm.xchange.instrument.Instrument instrument =
            ((org.knowm.xchange.service.trade.params.InstrumentParam) param).getInstrument();
        if (instrument instanceof org.knowm.xchange.currency.CurrencyPair) {
          pair = (org.knowm.xchange.currency.CurrencyPair) instrument;
        }
      }

      if (orderId == null || pair == null) {
        // Coins.ph getOrderStatus requires a symbol (CurrencyPair) and orderId
        throw new IllegalArgumentException(
            "OrderQueryParams must include OrderId and CurrencyPair (via CurrencyPairParam or InstrumentParam) for Coins.ph");
      }
      CoinsphOrder coinsphOrder = getCoinsphOrderStatus(orderId, CoinsphAdapters.toSymbol(pair));
      if (coinsphOrder != null) { // Ensure order is found before adapting
        orders.add(CoinsphAdapters.adaptOrder(coinsphOrder));
      }
    }
    return orders;
  }
}
