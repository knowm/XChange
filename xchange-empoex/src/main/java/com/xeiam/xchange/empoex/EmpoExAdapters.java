package com.xeiam.xchange.empoex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.empoex.dto.account.EmpoExBalance;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExLevel;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTicker;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTrade;
import com.xeiam.xchange.empoex.dto.trade.EmpoExOpenOrder;

public final class EmpoExAdapters {

  public static Ticker adaptEmpoExTicker(EmpoExTicker raw) {

    CurrencyPair currencyPair = EmpoExUtils.toCurrencyPair(raw.getPairname());
    BigDecimal last = new BigDecimal(raw.getLast().replace(",", ""));
    BigDecimal high = new BigDecimal(raw.getHigh().replace(",", ""));
    BigDecimal low = new BigDecimal(raw.getLow().replace(",", ""));
    BigDecimal bid = new BigDecimal(raw.getBid().replace(",", ""));
    BigDecimal ask = new BigDecimal(raw.getAsk().replace(",", ""));
    BigDecimal counterVolume = new BigDecimal(raw.getBaseVolume24hr().replace(",", ""));
    BigDecimal volume = counterVolume.divide(last, new MathContext(8, RoundingMode.HALF_UP));

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(bid).ask(ask).volume(volume).build();
  }

  public static Trades adaptEmpoExTrades(List<EmpoExTrade> raw, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();

    for (EmpoExTrade trade : raw) {

      OrderType type = trade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      Date date = EmpoExUtils.toDate(trade.getDate());
      BigDecimal amount = new BigDecimal(trade.getAmount().replace(",", ""));
      BigDecimal price = new BigDecimal(trade.getPrice().replace(",", ""));

      trades.add(new Trade.Builder().currencyPair(currencyPair).price(price).tradableAmount(amount).timestamp(date).type(type).build());
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static OrderBook adaptEmpoExDepth(Map<String, List<EmpoExLevel>> raw, CurrencyPair currencyPair) {

    List<EmpoExLevel> rawAsks = raw.get("sell");
    List<EmpoExLevel> rawBids = raw.get("buy");

    List<LimitOrder> asks = new ArrayList<LimitOrder>();
    List<LimitOrder> bids = new ArrayList<LimitOrder>();

    for (EmpoExLevel ask : rawAsks) {

      BigDecimal amount = new BigDecimal(ask.getAmount().replace(",", ""));
      BigDecimal price = new BigDecimal(ask.getPrice().replace(",", ""));
      asks.add(new LimitOrder.Builder(OrderType.ASK, currencyPair).tradableAmount(amount).limitPrice(price).build());
    }

    for (EmpoExLevel bid : rawBids) {

      BigDecimal amount = new BigDecimal(bid.getAmount().replace(",", ""));
      BigDecimal price = new BigDecimal(bid.getPrice().replace(",", ""));
      bids.add(new LimitOrder.Builder(OrderType.BID, currencyPair).tradableAmount(amount).limitPrice(price).build());
    }

    return new OrderBook(null, asks, bids);
  }

  public static AccountInfo adaptBalances(List<EmpoExBalance> raw) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (EmpoExBalance empoExBalance : raw) {

      BigDecimal balance = new BigDecimal(empoExBalance.getAmount().replace(",", ""));
      wallets.add(new Wallet(empoExBalance.getCoin().toUpperCase(), balance));
    }

    return new AccountInfo(null, wallets);
  }

  public static OpenOrders adaptOpenOrders(Map<String, List<EmpoExOpenOrder>> raw) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();

    for (String pairString : raw.keySet()) {

      CurrencyPair currencyPair = EmpoExUtils.toCurrencyPair(pairString);
      List<EmpoExOpenOrder> pairOrders = raw.get(pairString);

      for (EmpoExOpenOrder order : pairOrders) {

        openOrders.add(adaptOpenOrder(order, currencyPair));
      }
    }

    return new OpenOrders(openOrders);
  }

  public static LimitOrder adaptOpenOrder(EmpoExOpenOrder raw, CurrencyPair currencyPair) {

    String orderId = raw.getOrderId();
    BigDecimal amount = new BigDecimal(raw.getAmountRemaining().replace(",", ""));
    BigDecimal price = new BigDecimal(raw.getValue().replace(",", ""));
    OrderType type = raw.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder.Builder(type, currencyPair).id(orderId).tradableAmount(amount).limitPrice(price).build();
  }

}
