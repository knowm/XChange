package info.bitrich.xchangestream.lgo;

import static java.util.stream.Collectors.toList;

import info.bitrich.xchangestream.lgo.domain.LgoBatchOrderEvent;
import info.bitrich.xchangestream.lgo.domain.LgoMatchOrderEvent;
import info.bitrich.xchangestream.lgo.domain.LgoPendingOrderEvent;
import info.bitrich.xchangestream.lgo.dto.LgoTrade;
import info.bitrich.xchangestream.lgo.dto.LgoUserSnapshotData;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public class LgoAdapter {

  static String channelName(String name, CurrencyPair currencyPair) {
    return name + "-" + currencyPair.base.toString() + "-" + currencyPair.counter.toString();
  }

  public static Trade adaptTrade(CurrencyPair currencyPair, LgoTrade lgoTrade) {
    return new Trade.Builder()
        .type(parseTradeType(lgoTrade))
        .originalAmount(lgoTrade.getQuantity())
        .currencyPair(currencyPair)
        .price(lgoTrade.getPrice())
        .timestamp(lgoTrade.getCreationTime())
        .id(lgoTrade.getId())
        .build();
  }

  static List<Balance> adaptBalances(List<List<String>> data) {
    return data.stream()
        .map(
            balance -> {
              Currency currency = Currency.getInstance(balance.get(0));
              BigDecimal available = new BigDecimal(balance.get(1));
              BigDecimal escrow = new BigDecimal(balance.get(2));
              BigDecimal total = available.add(escrow);
              return new Balance.Builder()
                  .currency(currency)
                  .total(total)
                  .available(available)
                  .frozen(escrow)
                  .build();
            })
        .collect(toList());
  }

  private static Order.OrderType parseTradeType(LgoTrade lgoTrade) {
    // the XChange API requires the taker order type whereas the LGO API gives the maker order type
    return lgoTrade.getSide().equals("B") ? Order.OrderType.ASK : Order.OrderType.BID;
  }

  private static Order.OrderType parseOrderType(String side) {
    return side.equals("B") ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  static Collection<LimitOrder> adaptOrdersSnapshot(
      List<LgoUserSnapshotData> orderEvents, CurrencyPair currencyPair) {
    return orderEvents.stream()
        .map(
            orderEvent -> {
              Order.OrderStatus status =
                  orderEvent.getQuantity().equals(orderEvent.getRemainingQuantity())
                      ? Order.OrderStatus.NEW
                      : Order.OrderStatus.PARTIALLY_FILLED;
              return new LimitOrder.Builder(parseOrderType(orderEvent.getSide()), currencyPair)
                  .id(orderEvent.getOrderId())
                  .userReference(null)
                  .originalAmount(orderEvent.getQuantity())
                  .remainingAmount(orderEvent.getRemainingQuantity())
                  .limitPrice(orderEvent.getPrice())
                  .orderStatus(status)
                  .timestamp(orderEvent.getOrderCreationTime())
                  .build();
            })
        .collect(toList());
  }

  public static Order adaptPendingOrder(
      LgoPendingOrderEvent orderEvent, CurrencyPair currencyPair) {
    if (orderEvent.getOrderType().equals("L")) {
      return adaptPendingLimitOrder(orderEvent, currencyPair);
    }
    return adaptPendingMarketOrder(orderEvent, currencyPair);
  }

  private static LimitOrder adaptPendingLimitOrder(
      LgoPendingOrderEvent orderEvent, CurrencyPair currencyPair) {
    return new LimitOrder.Builder(orderEvent.getSide(), currencyPair)
        .id(orderEvent.getOrderId())
        .originalAmount(orderEvent.getInitialAmount())
        .remainingAmount(orderEvent.getInitialAmount())
        .limitPrice(orderEvent.getLimitPrice())
        .orderStatus(Order.OrderStatus.PENDING_NEW)
        .timestamp(orderEvent.getTime())
        .build();
  }

  private static MarketOrder adaptPendingMarketOrder(
      LgoPendingOrderEvent orderEvent, CurrencyPair currencyPair) {
    return new MarketOrder.Builder(orderEvent.getSide(), currencyPair)
        .id(orderEvent.getOrderId())
        .originalAmount(orderEvent.getInitialAmount())
        .cumulativeAmount(BigDecimal.ZERO)
        .orderStatus(Order.OrderStatus.PENDING_NEW)
        .timestamp(orderEvent.getTime())
        .build();
  }

  static List<LgoBatchOrderEvent> adaptOrderEvent(
      List<LgoBatchOrderEvent> data, Long batchId, List<Order> openOrders) {
    data.forEach(
        e -> {
          e.setBatchId(batchId);
          if ("match".equals(e.getType())) {
            LgoMatchOrderEvent matchEvent = (LgoMatchOrderEvent) e;
            Optional<Order> matchedOrder =
                openOrders.stream()
                    .filter(order -> order.getId().equals(e.getOrderId()))
                    .findFirst();
            matchEvent.setOrderType(matchedOrder.map(Order::getType).orElse(null));
          }
        });
    return data;
  }

  static UserTrade adaptUserTrade(CurrencyPair currencyPair, LgoMatchOrderEvent event) {
    return new UserTrade.Builder()
        .type(event.getOrderType())
        .originalAmount(event.getFilledQuantity())
        .currencyPair(currencyPair)
        .price(event.getTradePrice())
        .timestamp(event.getTime())
        .id(event.getTradeId())
        .orderId(event.getOrderId())
        .feeAmount(event.getFees())
        .feeCurrency(currencyPair.counter)
        .build();
  }
}
