package org.knowm.xchange.huobi;

import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.CNY;
import static org.knowm.xchange.currency.Currency.LTC;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.dto.marketdata.Trades.TradeSortType.SortByTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.dto.account.BitVcAccountInfo;
import org.knowm.xchange.huobi.dto.account.HuobiAccountInfo;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.huobi.dto.marketdata.HuobiOrderBookTAS;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTickerObject;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTradeObject;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

public final class HuobiAdapters {

  private static final long FIVE_MINUTES = 5L * 60L * 1000L;

  private HuobiAdapters() {

  }

  public static Ticker adaptTicker(HuobiTicker BitVcTicker, CurrencyPair currencyPair) {

    HuobiTickerObject ticker = BitVcTicker.getTicker();
    return new Ticker.Builder().currencyPair(currencyPair).last(ticker.getLast()).bid(ticker.getBuy()).ask(ticker.getSell()).high(ticker.getHigh())
        .low(ticker.getLow()).volume(ticker.getVol()).build();
  }

  public static OrderBook adaptOrderBook(HuobiDepth BitVcDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptOrderBook(BitVcDepth.getAsks(), ASK, currencyPair);
    List<LimitOrder> bids = adaptOrderBook(BitVcDepth.getBids(), BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptOrderBook(BigDecimal[][] orders, OrderType type, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);
    for (BigDecimal[] order : orders) {
      LimitOrder limitOrder = new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }

  public static Trades adaptTrades(HuobiOrderBookTAS bitvcDetail, CurrencyPair currencyPair) {

    List<Trade> trades = adaptTrades(bitvcDetail.getTrades(), currencyPair);
    return new Trades(trades, SortByTimestamp);
  }

  private static List<Trade> adaptTrades(HuobiTradeObject[] trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.length);
    for (HuobiTradeObject trade : trades) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    return tradeList;
  }

  private static Trade adaptTrade(HuobiTradeObject trade, CurrencyPair currencyPair) {

    OrderType type = trade.getType().equals("买入") ? BID : ASK;
    Date timestamp = adaptTime(trade.getTime());
    return new Trade(type, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, null);
  }

  private static Date adaptTime(String time) {
    String[] hms = time.split(":");
    TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
    Calendar now = Calendar.getInstance();
    Calendar timestamp = Calendar.getInstance(timeZone);
    timestamp.setTime(now.getTime());
    timestamp.set(Calendar.HOUR, Integer.parseInt(hms[0]));
    timestamp.set(Calendar.MINUTE, Integer.parseInt(hms[1]));
    timestamp.set(Calendar.SECOND, Integer.parseInt(hms[2]));
    timestamp.set(Calendar.MILLISECOND, 0);
    if (timestamp.getTimeInMillis() > now.getTimeInMillis() + FIVE_MINUTES) {
      timestamp.add(Calendar.DAY_OF_MONTH, -1);
    }
    return timestamp.getTime();
  }

  public static Wallet adaptWallet(BitVcAccountInfo a) {

    Balance cny = adaptBalance(CNY, a.getAvailableCnyDisplay(), a.getFrozenCnyDisplay(), a.getLoanCnyDisplay());
    Balance btc = adaptBalance(BTC, a.getAvailableBtcDisplay(), a.getFrozenBtcDisplay(), a.getLoanBtcDisplay());
    Balance ltc = adaptBalance(LTC, a.getAvailableLtcDisplay(), a.getFrozenLtcDisplay(), a.getLoanLtcDisplay());

    return new Wallet(cny, btc, ltc);
  }

  public static Wallet adaptHuobiWallet(HuobiAccountInfo a) {

    Balance cny = adaptBalance(CNY, a.getAvailableCnyDisplay(), a.getFrozenCnyDisplay(), a.getLoanCnyDisplay());
    Balance btc = adaptBalance(BTC, a.getAvailableBtcDisplay(), a.getFrozenBtcDisplay(), a.getLoanBtcDisplay());
    Balance ltc = adaptBalance(LTC, a.getAvailableLtcDisplay(), a.getFrozenLtcDisplay(), a.getLoanLtcDisplay());

    return new Wallet(cny, btc, ltc);
  }

  public static Balance adaptBalance(Currency currency, BigDecimal available, BigDecimal frozen, BigDecimal loan) {
    return new Balance(currency, null, available, frozen, loan, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  public static String adaptPlaceOrderResult(HuobiPlaceOrderResult result) {

    if (result.getCode() == 0) {
      return String.valueOf(result.getId());
    } else {
      throw new ExchangeException("Error code: " + result.getCode());
    }
  }

  public static List<LimitOrder> adaptOpenOrders(HuobiOrder[] orders, CurrencyPair currencyPair) {

    List<LimitOrder> openOrders = new ArrayList<>(orders.length);
    for (HuobiOrder order : orders) {
      openOrders.add(adaptOpenOrder(order, currencyPair));
    }
    return openOrders;
  }

  public static LimitOrder adaptOpenOrder(HuobiOrder order, CurrencyPair currencyPair) {

    return new LimitOrder(order.getType() == 1 ? BID : ASK, order.getOrderAmount().subtract(order.getProcessedAmount()), currencyPair,
        String.valueOf(order.getId()), new Date(order.getOrderTime() * 1000), order.getOrderPrice());
  }

}
