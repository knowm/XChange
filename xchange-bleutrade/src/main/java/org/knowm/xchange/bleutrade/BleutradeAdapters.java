package org.knowm.xchange.bleutrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalance;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeLevel;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import org.knowm.xchange.bleutrade.dto.trade.BluetradeExecutedTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class BleutradeAdapters {

  public static Set<CurrencyPair> adaptBleutradeCurrencyPairs(BleutradeMarketsReturn response) {

    List<BleutradeMarket> markets = response.getResult();
    Set<CurrencyPair> currencyPairs = new HashSet<>();

    for (BleutradeMarket market : markets) {
      currencyPairs.add(BleutradeUtils.toCurrencyPair(market.getMarketName()));
    }

    return currencyPairs;
  }

  public static Ticker adaptBleutradeTicker(BleutradeTicker bleutradeTicker) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.ask(bleutradeTicker.getAsk());
    builder.bid(bleutradeTicker.getBid());
    builder.currencyPair(BleutradeUtils.toCurrencyPair(bleutradeTicker.getMarketName()));
    builder.high(bleutradeTicker.getHigh());
    builder.last(bleutradeTicker.getLast());
    builder.low(bleutradeTicker.getLow());
    builder.timestamp(BleutradeUtils.toDate(bleutradeTicker.getTimeStamp()));
    builder.volume(bleutradeTicker.getVolume());
    builder.vwap(bleutradeTicker.getAverage());

    return builder.build();
  }

  public static OrderBook adaptBleutradeOrderBook(
      BleutradeOrderBook bleutradeOrderBook, CurrencyPair currencyPair) {

    List<BleutradeLevel> bleutradeAsks = bleutradeOrderBook.getSell();
    List<BleutradeLevel> bleutradeBids = bleutradeOrderBook.getBuy();

    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    for (BleutradeLevel ask : bleutradeAsks) {

      LimitOrder.Builder builder = new LimitOrder.Builder(OrderType.ASK, currencyPair);
      builder.limitPrice(ask.getRate());
      builder.originalAmount(ask.getQuantity());
      builder.cumulativeAmount(BigDecimal.ZERO);
      asks.add(builder.build());
    }

    for (BleutradeLevel bid : bleutradeBids) {

      LimitOrder.Builder builder = new LimitOrder.Builder(OrderType.BID, currencyPair);
      builder.limitPrice(bid.getRate());
      builder.originalAmount(bid.getQuantity());
      builder.cumulativeAmount(BigDecimal.ZERO);
      bids.add(builder.build());
    }

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptBleutradeMarketHistory(
      List<BleutradeTrade> bleutradeTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();

    for (BleutradeTrade bleutradeTrade : bleutradeTrades) {

      Trade.Builder builder = new Trade.Builder();
      builder.currencyPair(currencyPair);
      builder.price(bleutradeTrade.getPrice());
      builder.timestamp(BleutradeUtils.toDate(bleutradeTrade.getTimeStamp()));
      builder.originalAmount(bleutradeTrade.getQuantity());
      builder.type(bleutradeTrade.getOrderType().equals("BUY") ? OrderType.BID : OrderType.ASK);
      trades.add(builder.build());
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptBleutradeBalances(List<BleutradeBalance> bleutradeBalances) {

    List<Balance> balances = new ArrayList<>();

    for (BleutradeBalance bleutradeBalance : bleutradeBalances) {
      balances.add(
          new Balance(
              Currency.getInstance(bleutradeBalance.getCurrency()),
              bleutradeBalance.getBalance(),
              bleutradeBalance.getAvailable(),
              bleutradeBalance.getPending()));
    }

    return Wallet.Builder.from(balances).build();
  }

  public static OpenOrders adaptBleutradeOpenOrders(List<BleutradeOpenOrder> bleutradeOpenOrders) {

    List<LimitOrder> openOrders = new ArrayList<>();

    for (BleutradeOpenOrder bleuTradeOpenOrder : bleutradeOpenOrders) {

      OrderType type = bleuTradeOpenOrder.getType().equals("BUY") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = BleutradeUtils.toCurrencyPair(bleuTradeOpenOrder.getExchange());

      LimitOrder.Builder builder = new LimitOrder.Builder(type, currencyPair);
      builder.id(bleuTradeOpenOrder.getOrderId());
      builder.limitPrice(bleuTradeOpenOrder.getPrice());
      builder.remainingAmount(bleuTradeOpenOrder.getQuantityRemaining());
      builder.originalAmount(bleuTradeOpenOrder.getQuantity());
      builder.timestamp(BleutradeUtils.toDate(bleuTradeOpenOrder.getCreated()));
      openOrders.add(builder.build());
    }

    return new OpenOrders(openOrders);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      List<BleutradeCurrency> bleutradeCurrencies, List<BleutradeMarket> bleutradeMarkets) {

    Map<CurrencyPair, CurrencyPairMetaData> marketMetaDataMap = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencyMetaDataMap = new HashMap<>();

    for (BleutradeCurrency bleutradeCurrency : bleutradeCurrencies) {
      // the getTxFee parameter is the withdrawal charge in the currency in question
      currencyMetaDataMap.put(
          Currency.getInstance(bleutradeCurrency.getCurrency()), new CurrencyMetaData(8, null));
    }

    // https://bleutrade.com/help/fees_and_deadlines 11/25/2015 all == 0.25%
    BigDecimal txFee = new BigDecimal("0.0025");

    for (BleutradeMarket bleutradeMarket : bleutradeMarkets) {
      CurrencyPair currencyPair =
          CurrencyPairDeserializer.getCurrencyPairFromString(bleutradeMarket.getMarketName());
      CurrencyPairMetaData marketMetaData =
          new CurrencyPairMetaData(txFee, bleutradeMarket.getMinTradeSize(), null, 8, null);
      marketMetaDataMap.put(currencyPair, marketMetaData);
    }

    return new ExchangeMetaData(marketMetaDataMap, currencyMetaDataMap, null, null, null);
  }

  public static UserTrade adaptUserTrade(BluetradeExecutedTrade trade) {
    OrderType orderType = trade.type.equalsIgnoreCase("sell") ? OrderType.ASK : OrderType.BID;
    CurrencyPair currencyPair = BleutradeUtils.toCurrencyPair(trade.exchange);
    return new UserTrade.Builder()
        .type(orderType)
        .originalAmount(trade.quantity)
        .currencyPair(currencyPair)
        .price(trade.price)
        .timestamp(BleutradeUtils.toDate(trade.created))
        .id(trade.orderId)
        .orderId(trade.orderId)
        .build();
  }
}
