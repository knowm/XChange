package org.knowm.xchange.bity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bity.dto.account.BityInputTransaction;
import org.knowm.xchange.bity.dto.account.BityOrder;
import org.knowm.xchange.bity.dto.account.BityOutputTransaction;
import org.knowm.xchange.bity.dto.marketdata.BityPair;
import org.knowm.xchange.bity.dto.marketdata.BityTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public final class BityAdapters {

  private BityAdapters() {}

  public static UserTrades adaptTrades(List<BityOrder> orders) {
    final List<UserTrade> trades = new ArrayList<>();

    for (BityOrder order : orders) {

      if (order.getStatus().equals("EXEC")) {
        trades.add(adaptTrade(order));
      }
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptTrade(BityOrder order) {
    BityInputTransaction inputT = order.getBityInputTransactions().get(0);
    BityOutputTransaction outputT = order.getBityOutputTransactions().get(0);
    BigDecimal fee = inputT.getPaymentProcessorFee();

    BigDecimal price = inputT.getAmount().divide(outputT.getAmount(), 6, RoundingMode.HALF_UP);
    CurrencyPair currencyPair = new CurrencyPair(outputT.getCurrency(), inputT.getCurrency());

    Order.OrderType orderType =
        order.getCategory().contains("BUY") ? Order.OrderType.BID : Order.OrderType.ASK;
    BigDecimal amount = outputT.getAmount();

    Date date = order.getTimestampCreated();
    String orderId = order.getResourceUri();

    return new UserTrade.Builder()
        .type(orderType)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(orderId)
        .orderId(orderId)
        .feeAmount(fee)
        .feeCurrency(currencyPair.counter)
        .build();
  }

  public static ExchangeMetaData adaptMetaData(
      List<BityPair> rawSymbols, ExchangeMetaData metaData) {

    List<CurrencyPair> currencyPairs = BityAdapters.adaptCurrencyPairs(rawSymbols);

    Map<CurrencyPair, CurrencyPairMetaData> pairsMap = metaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currenciesMap = metaData.getCurrencies();

    for (CurrencyPair c : currencyPairs) {
      if (!pairsMap.containsKey(c)) {
        pairsMap.put(c, null);
      }
      if (!currenciesMap.containsKey(c.base)) {
        currenciesMap.put(c.base, null);
      }
      if (!currenciesMap.containsKey(c.counter)) {
        currenciesMap.put(c.counter, null);
      }
    }

    return metaData;
  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<BityPair> bityPairs) {

    List<CurrencyPair> currencyPairs = new ArrayList<>();
    for (BityPair symbol : bityPairs) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(BityPair bityPair) {
    return new CurrencyPair(bityPair.getOutput(), bityPair.getInput());
  }

  public static List<Ticker> adaptTickers(Collection<BityTicker> bityTickers) {
    List<Ticker> tickers = new ArrayList<>();

    for (BityTicker ticker : bityTickers) {
      tickers.add(adaptTicker(ticker));
    }
    return tickers;
  }

  public static Ticker adaptTicker(BityTicker bityTicker) {

    CurrencyPair currencyPair = new CurrencyPair(bityTicker.getSource(), bityTicker.getTarget());

    BigDecimal last = bityTicker.getRate();
    BigDecimal bid = bityTicker.getRateWeBuy();
    BigDecimal ask = bityTicker.getRateWeSell();

    Date timestamp = bityTicker.getTimestamp();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .timestamp(timestamp)
        .build();
  }
}
