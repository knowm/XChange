package org.knowm.xchange.bitcoinde.v4;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.bitcoinde.v4.dto.*;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
public final class BitcoindeAdapters {

  /** Private constructor. */
  private BitcoindeAdapters() {}

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook object to an OrderBook
   * object.
   *
   * @param bitcoindeOrderbookWrapper the exchange specific OrderBook object
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptCompactOrderBook(
      BitcoindeCompactOrderbookWrapper bitcoindeOrderbookWrapper, CurrencyPair currencyPair) {
    final List<LimitOrder> asks =
        createCompactOrders(
            currencyPair, OrderType.ASK, bitcoindeOrderbookWrapper.getBitcoindeOrders().getAsks());
    final List<LimitOrder> bids =
        createCompactOrders(
            currencyPair, OrderType.BID, bitcoindeOrderbookWrapper.getBitcoindeOrders().getBids());

    Collections.sort(asks);
    Collections.sort(bids);

    return new OrderBook(null, asks, bids);
  }

  public static OrderBook adaptOrderBook(
      BitcoindeOrderbookWrapper asksWrapper,
      BitcoindeOrderbookWrapper bidsWrapper,
      CurrencyPair currencyPair) {
    final List<LimitOrder> asks =
        createOrders(currencyPair, OrderType.ASK, asksWrapper.getBitcoindeOrders());
    final List<LimitOrder> bids =
        createOrders(currencyPair, OrderType.BID, bidsWrapper.getBitcoindeOrders());

    Collections.sort(asks);
    Collections.sort(bids);

    return new OrderBook(null, asks, bids);
  }

  /** Create a list of orders from a list of asks or bids. */
  public static List<LimitOrder> createCompactOrders(
      CurrencyPair currencyPair, OrderType orderType, BitcoindeCompactOrder[] orders) {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitcoindeCompactOrder order : orders) {
      limitOrders.add(
          new LimitOrder(orderType, order.getAmount(), currencyPair, null, null, order.getPrice()));
    }

    return limitOrders;
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, BitcoindeOrder[] orders) {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitcoindeOrder order : orders) {
      limitOrders.add(createOrder(currencyPair, order, orderType, order.getOrderId(), null));
    }

    return limitOrders;
  }

  /** Create an individual order. */
  public static LimitOrder createOrder(
      CurrencyPair currencyPair,
      BitcoindeOrder bitcoindeOrder,
      OrderType orderType,
      String orderId,
      Date timeStamp) {
    final LimitOrder.Builder limitOrder =
        new LimitOrder.Builder(orderType, currencyPair)
            .id(orderId)
            .timestamp(timeStamp)
            .originalAmount(bitcoindeOrder.getMaxAmount())
            .limitPrice(bitcoindeOrder.getPrice())
            .orderStatus(OrderStatus.NEW);

    limitOrder.flag(
        new BitcoindeOrderFlagsOrderQuantities(
            bitcoindeOrder.getMinAmount(),
            bitcoindeOrder.getMaxAmount(),
            bitcoindeOrder.getMinVolume(),
            bitcoindeOrder.getMaxVolume()));
    if (bitcoindeOrder.getTradingPartnerInformation() != null) {
      final BitcoindeOrderFlagsTradingPartnerInformation tpi =
          new BitcoindeOrderFlagsTradingPartnerInformation(
              bitcoindeOrder.getTradingPartnerInformation().getUserName(),
              bitcoindeOrder.getTradingPartnerInformation().getKycFull(),
              bitcoindeOrder.getTradingPartnerInformation().getTrustLevel());
      tpi.setBankName(bitcoindeOrder.getTradingPartnerInformation().getBankName());
      tpi.setBic(bitcoindeOrder.getTradingPartnerInformation().getBic());
      tpi.setSeatOfBank(bitcoindeOrder.getTradingPartnerInformation().getSeatOfBank());
      tpi.setRating(bitcoindeOrder.getTradingPartnerInformation().getRating());
      tpi.setNumberOfTrades(bitcoindeOrder.getTradingPartnerInformation().getAmountTrades());
      limitOrder.flag(tpi);
    }
    if (bitcoindeOrder.getOrderRequirements() != null) {
      limitOrder.flag(adaptOrderRequirements(bitcoindeOrder.getOrderRequirements()));
    }

    return limitOrder.build();
  }

  private static BitcoindeOrderFlagsOrderRequirements adaptOrderRequirements(
      BitcoindeOrderRequirements requirements) {
    return new BitcoindeOrderFlagsOrderRequirements(
        requirements.getMinTrustLevel(),
        requirements.getOnlyKycFull(),
        requirements.getSeatOfBank(),
        requirements.getPaymentOption());
  }
}
