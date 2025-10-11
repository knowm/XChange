package org.knowm.xchange.dase.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseAdapters;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig;
import org.knowm.xchange.dase.dto.trade.DaseOrder;
import org.knowm.xchange.dase.dto.trade.DaseOrderFlags;
import org.knowm.xchange.dase.dto.trade.DasePlaceOrderInput;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class DaseTradeService extends DaseTradeServiceRaw implements TradeService {

  private final DaseMarketDataServiceRaw marketDataRaw;

  public DaseTradeService(Exchange exchange) {
    super(exchange);
    this.marketDataRaw = new DaseMarketDataServiceRaw(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair(null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    String market = null;
    if (params instanceof DefaultOpenOrdersParamCurrencyPair) {
      CurrencyPair cp = ((DefaultOpenOrdersParamCurrencyPair) params).getCurrencyPair();
      market = cp == null ? null : DaseAdapters.toMarketString(cp);
    }
    List<LimitOrder> converted = new ArrayList<>();
    try {
      for (DaseOrder o : getOrders(market, "open", null, null).getOrders()) {
        Order adapted = DaseAdapters.adaptOrder(o);
        if (adapted instanceof LimitOrder) {
          converted.add((LimitOrder) adapted);
        }
      }
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
    return new OpenOrders(converted);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    validateOrderLimits(limitOrder);
    CurrencyPair pair = requireCurrencyPairInstrument(limitOrder);
    DasePlaceOrderInput body = new DasePlaceOrderInput();
    body.market = DaseAdapters.toMarketString(pair);
    body.type = "limit";
    body.side = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
    body.size = toStringOrNull(limitOrder.getOriginalAmount());
    body.price = toStringOrNull(limitOrder.getLimitPrice());
    body.postOnly = limitOrder.hasFlag(DaseOrderFlags.POST_ONLY);
    body.clientId = isUuid(limitOrder.getUserReference()) ? limitOrder.getUserReference() : null;
    try {
      return placeOrder(body).getOrderId();
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    validateOrderLimits(marketOrder);
    CurrencyPair pair = requireCurrencyPairInstrument(marketOrder);
    DasePlaceOrderInput body = new DasePlaceOrderInput();
    body.market = DaseAdapters.toMarketString(pair);
    body.type = "market";
    body.side = marketOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
    body.size = toStringOrNull(marketOrder.getOriginalAmount());
    body.funds = null;
    body.clientId = isUuid(marketOrder.getUserReference()) ? marketOrder.getUserReference() : null;
    try {
      return placeOrder(body).getOrderId();
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    try {
      cancelOrderInternal(orderId);
      return true;
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
  }

  private void cancelOrderInternal(String orderId) throws IOException {
    try {
      super.cancelOrderRaw(orderId);
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      String id = ((CancelOrderByIdParams) orderParams).getOrderId();
      return cancelOrder(id);
    }
    if (orderParams instanceof CancelAllOrders) {
      super.cancelAllOrdersRaw(null);
      return true;
    }
    throw new ExchangeException(
        "Unsupported cancel params: " + orderParams.getClass().getSimpleName());
  }

  @Override
  public Collection<Order> getOrder(
      org.knowm.xchange.service.trade.params.orders.OrderQueryParams... orderQueryParams)
      throws IOException {
    List<Order> out = new ArrayList<>();
    for (org.knowm.xchange.service.trade.params.orders.OrderQueryParams p : orderQueryParams) {
      String id = p.getOrderId();
      try {
        DaseOrder o = super.getOrder(id);
        out.add(DaseAdapters.adaptOrder(o));
      } catch (IOException e) {
        throw e;
      } catch (Exception ex) {
        throw new ExchangeException(ex.getMessage(), ex);
      }
    }
    return out;
  }

  public List<Order> batchGetOrders(List<String> orderIds) throws IOException {
    try {
      List<Order> out = new ArrayList<>();
      for (DaseOrder o : super.batchGetOrdersRaw(orderIds).getOrders()) {
        out.add(DaseAdapters.adaptOrder(o));
      }
      return out;
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
  }

  public void batchCancelOrders(List<String> orderIds) throws IOException {
    try {
      super.batchCancelOrdersRaw(orderIds);
    } catch (IOException e) {
      throw e;
    } catch (Exception ex) {
      throw new ExchangeException(ex.getMessage(), ex);
    }
  }

  public void cancelAll(CurrencyPair pair) throws IOException {
    super.cancelAllOrdersRaw(pair == null ? null : DaseAdapters.toMarketString(pair));
  }

  private static String toStringOrNull(BigDecimal v) {
    return v == null ? null : v.toPlainString();
  }

  protected void validateOrderLimits(Order order) throws IOException {
    Objects.requireNonNull(order, "Order required");
    CurrencyPair pair = requireCurrencyPairInstrument(order);
    String market = DaseAdapters.toMarketString(pair);
    DaseMarketConfig cfg = marketDataRaw.getMarket(market);
    if (cfg == null) {
      throw new IllegalArgumentException("Unknown market: " + market);
    }
    if (order instanceof LimitOrder) {
      LimitOrder lo = (LimitOrder) order;
      requirePrecision("size", lo.getOriginalAmount(), cfg.sizePrecision);
      requirePrecision("price", lo.getLimitPrice(), cfg.pricePrecision);
      if (lo.getLimitPrice() == null) {
        throw new IllegalArgumentException("LimitOrder price is required");
      }
      if (lo.getLimitPrice().compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("price must be positive");
      }
      if (lo.getOriginalAmount() == null) {
        throw new IllegalArgumentException("LimitOrder size is required");
      }
      BigDecimal minOrderSize = parseDecimalOrNull(cfg.minOrderSize);
      if (minOrderSize != null && minOrderSize.compareTo(lo.getOriginalAmount()) > 0) {
        throw new IllegalArgumentException("size below min_order_size");
      }
    } else if (order instanceof MarketOrder) {
      MarketOrder mo = (MarketOrder) order;
      if (mo.getOriginalAmount() == null) {
        throw new IllegalArgumentException("MarketOrder size is required");
      }
      requirePrecision("size", mo.getOriginalAmount(), cfg.sizePrecision);
      BigDecimal minOrderSize = parseDecimalOrNull(cfg.minOrderSize);
      if (minOrderSize != null && minOrderSize.compareTo(mo.getOriginalAmount()) > 0) {
        throw new IllegalArgumentException("size below min_order_size");
      }
    }
  }

  private static CurrencyPair requireCurrencyPairInstrument(Order order) {
    Objects.requireNonNull(order, "Order required");
    Objects.requireNonNull(order.getInstrument(), "Instrument required");
    if (!(order.getInstrument() instanceof CurrencyPair)) {
      throw new IllegalArgumentException(
          "Instrument must be CurrencyPair, got: " + order.getInstrument().getClass().getName());
    }
    return (CurrencyPair) order.getInstrument();
  }

  private static void requirePrecision(String field, BigDecimal v, Integer precision) {
    if (v == null) return;
    if (precision == null) return;
    int scale = v.stripTrailingZeros().scale();
    if (scale < 0) scale = 0;
    if (scale > precision) {
      throw new IllegalArgumentException(field + " exceeds precision " + precision);
    }
  }

  private static BigDecimal parseDecimalOrNull(String s) {
    if (s == null) return null;
    try {
      return new BigDecimal(s);
    } catch (Exception e) {
      return null;
    }
  }

  private static boolean isUuid(String s) {
    if (s == null) return false;
    try {
      UUID.fromString(s);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
