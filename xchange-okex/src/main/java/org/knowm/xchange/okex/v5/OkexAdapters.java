package org.knowm.xchange.okex.v5;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.v5.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexPendingOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAdapters {

  public static OpenOrders adaptOpenOrders(List<OkexPendingOrder> orders) {
    List<LimitOrder> openOrders =
        orders.stream()
            .map(
                order ->
                    new LimitOrder(
                        "buy".equals(order.getSide()) ? Order.OrderType.BID : Order.OrderType.ASK,
                        new BigDecimal(order.getAmount()),
                        new CurrencyPair(order.getInstrumentId()),
                        order.getOrderId(),
                        new Date(Long.parseLong(order.getCreationTime())),
                        new BigDecimal(order.getPrice()),
                        order.getAverageFilledPrice().isEmpty()
                            ? BigDecimal.ZERO
                            : new BigDecimal(order.getAverageFilledPrice()),
                        new BigDecimal(order.getAccumulatedFill()),
                        new BigDecimal(order.getFee()),
                        "live".equals(order.getState())
                            ? Order.OrderStatus.OPEN
                            : Order.OrderStatus.PARTIALLY_FILLED,
                        null))
            .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  public static OkexAmendOrderRequest adaptAmendOrder(LimitOrder order) {
    return OkexAmendOrderRequest.builder()
        .instrumentId(adaptCurrencyPairId((CurrencyPair) order.getInstrument()))
        .orderId(order.getId())
        .amendedAmount(order.getOriginalAmount().toString())
        .amendedPrice(order.getLimitPrice().toString())
        .build();
  }

  public static OkexOrderRequest adaptOrder(LimitOrder order) {
    return OkexOrderRequest.builder()
        .instrumentId(adaptCurrencyPairId((CurrencyPair) order.getInstrument()))
        .tradeMode("cash")
        .side(order.getType() == Order.OrderType.BID ? "buy" : "sell")
        .orderType("limit")
        .amount(order.getOriginalAmount().toString())
        .price(order.getLimitPrice().toString())
        .build();
  }

  public static String adaptCurrencyPairId(CurrencyPair currencyPair) {
    return currencyPair.toString().replace('/', '-');
  }

  private static Currency adaptCurrency(OkexCurrency currency) {
    return new Currency(currency.getCurrency());
  }

  public static CurrencyPair adaptCurrencyPair(OkexInstrument instrument) {
    return new CurrencyPair(instrument.getBaseCurrency(), instrument.getQuoteCurrency());
  }

  private static int numberOfDecimals(BigDecimal value) {
    double d = value.doubleValue();
    return -(int) Math.round(Math.log10(d));
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData exchangeMetaData,
      List<OkexInstrument> instruments,
      List<OkexCurrency> currs) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs =
        exchangeMetaData.getCurrencyPairs() == null
            ? new HashMap<>()
            : exchangeMetaData.getCurrencyPairs();

    Map<Currency, CurrencyMetaData> currencies =
        exchangeMetaData.getCurrencies() == null
            ? new HashMap<>()
            : exchangeMetaData.getCurrencies();

    for (OkexInstrument instrument : instruments) {
      if (!"live".equals(instrument.getState())) {
        continue;
      }
      CurrencyPair pair = adaptCurrencyPair(instrument);

      CurrencyPairMetaData staticMetaData = currencyPairs.get(pair);
      int priceScale = numberOfDecimals(new BigDecimal(instrument.getTickSize()));

      currencyPairs.put(
          pair,
          new CurrencyPairMetaData(
              new BigDecimal("0.50"),
              new BigDecimal(instrument.getMinSize()),
              null,
              null,
              null,
              null,
              priceScale,
              null,
              staticMetaData != null ? staticMetaData.getFeeTiers() : null,
              null,
              pair.counter,
              true));
    }

    if (currs != null) {
      currs.stream()
          .forEach(
              currency ->
                  currencies.put(
                      adaptCurrency(currency),
                      new CurrencyMetaData(
                          null,
                          new BigDecimal(currency.getMaxFee()),
                          new BigDecimal(currency.getMinWd()),
                          currency.isCanWd() && currency.isCanDep()
                              ? WalletHealth.ONLINE
                              : WalletHealth.OFFLINE)));
    }

    return new ExchangeMetaData(
        currencyPairs,
        currencies,
        exchangeMetaData == null ? null : exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData == null ? null : exchangeMetaData.getPrivateRateLimits(),
        true);
  }
}
