package com.xeiam.xchange.btctrade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from BTCTrade DTOs to XChange DTOs.
 */
public final class BTCTradeAdapters {

  private static final Map<String, CurrencyPair> currencyPairs = getCurrencyPairs();

  private static final Map<String, CurrencyPair> getCurrencyPairs() {

    Map<String, CurrencyPair> currencyPairs = new HashMap<String, CurrencyPair>(4);
    currencyPairs.put("1", CurrencyPair.BTC_CNY);
    // Seems they only provides API methods for the BTC_CNY.
    // But, anyway, we can place LTC_CNY orders from the website,
    // and then we may got the open orders by API method.
    currencyPairs.put("2", CurrencyPair.LTC_CNY);
    // 3 -> CurrencyPair.DOGE_CNY?
    // 4 -> CurrencyPair.YBC_CNY?
    return currencyPairs;
  }

  /**
   * private Constructor
   */
  private BTCTradeAdapters() {

  }

  public static Date adaptDatetime(String datetime) {

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
      return sdf.parse(datetime);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static CurrencyPair adaptCurrencyPair(String coin) {

    return currencyPairs.get(coin);
  }

  public static Ticker adaptTicker(BTCTradeTicker btcTradeTicker, CurrencyPair currencyPair) {

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withHigh(btcTradeTicker.getHigh()).withLow(btcTradeTicker.getLow()).withBid(btcTradeTicker.getBuy()).withAsk(
        btcTradeTicker.getSell()).withLast(btcTradeTicker.getLast()).withVolume(btcTradeTicker.getVol()).build();
  }

  public static OrderBook adaptOrderBook(BTCTradeDepth btcTradeDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptLimitOrders(btcTradeDepth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = adaptLimitOrders(btcTradeDepth.getBids(), currencyPair, OrderType.BID);
    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptLimitOrders(BigDecimal[][] orders, CurrencyPair currencyPair, OrderType type) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);
    for (BigDecimal[] order : orders) {
      limitOrders.add(adaptLimitOrder(order, currencyPair, type));
    }
    return limitOrders;
  }

  private static LimitOrder adaptLimitOrder(BigDecimal[] order, CurrencyPair currencyPair, OrderType type) {

    return new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
  }

  public static Trades adaptTrades(BTCTradeTrade[] btcTradeTrades, CurrencyPair currencyPair) {

    int length = btcTradeTrades.length;
    List<Trade> trades = new ArrayList<Trade>(length);
    for (BTCTradeTrade btcTradeTrade : btcTradeTrades) {
      trades.add(adaptTrade(btcTradeTrade, currencyPair));
    }
    long lastID = length > 0 ? btcTradeTrades[length - 1].getTid() : 0L;
    return new Trades(trades, lastID, TradeSortType.SortByID);
  }

  private static Trade adaptTrade(BTCTradeTrade btcTradeTrade, CurrencyPair currencyPair) {

    return new Trade(adaptOrderType(btcTradeTrade.getType()), btcTradeTrade.getAmount(), currencyPair, btcTradeTrade.getPrice(), new Date(btcTradeTrade.getDate() * 1000), String.valueOf(btcTradeTrade
        .getTid()));
  }

  private static OrderType adaptOrderType(String type) {

    return type.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  private static void checkException(BTCTradeResult result) {

    if (!result.isSuccess()) {
      throw new ExchangeException(result.getMessage());
    }
  }

  public static boolean adaptResult(BTCTradeResult result) {

    checkException(result);

    return true;
  }

  public static AccountInfo adaptAccountInfo(BTCTradeBalance balance) {

    checkException(balance);

    List<Wallet> wallets = new ArrayList<Wallet>(4);
    wallets.add(new Wallet(Currencies.BTC, balance.getBtcBalance().add(balance.getBtcReserved())));
    wallets.add(new Wallet(Currencies.LTC, balance.getLtcBalance().add(balance.getLtcReserved())));
    wallets.add(new Wallet(Currencies.DOGE, balance.getDogeBalance().add(balance.getDogeReserved())));
    wallets.add(new Wallet("YBC", balance.getYbcBalance().add(balance.getYbcReserved())));
    wallets.add(new Wallet(Currencies.CNY, balance.getCnyBalance().add(balance.getCnyReserved())));
    return new AccountInfo(null, wallets);
  }

  public static String adaptDepositAddress(BTCTradeWallet wallet) {

    checkException(wallet);

    return wallet.getAddress();
  }

  public static String adaptPlaceOrderResult(BTCTradePlaceOrderResult result) {

    checkException(result);

    return result.getId();
  }

  public static OpenOrders adaptOpenOrders(BTCTradeOrder[] btcTradeOrders) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>(btcTradeOrders.length);
    for (BTCTradeOrder order : btcTradeOrders) {
      LimitOrder limitOrder = adaptLimitOrder(order);
      if (limitOrder != null) {
        openOrders.add(limitOrder);
      }
    }
    return new OpenOrders(openOrders);
  }

  private static LimitOrder adaptLimitOrder(BTCTradeOrder order) {

    CurrencyPair currencyPair = adaptCurrencyPair(order.getCoin());

    final LimitOrder limitOrder;
    if (currencyPair == null) {
      // Unknown currency pair
      limitOrder = null;
    }
    else {
      limitOrder = new LimitOrder(adaptOrderType(order.getType()), order.getAmountOutstanding(), currencyPair, order.getId(), adaptDatetime(order.getDatetime()), order.getPrice());
    }

    return limitOrder;
  }

  public static Trades adaptTrades(BTCTradeOrder[] btcTradeOrders, BTCTradeOrder[] btcTradeOrderDetails) {

    List<Trade> trades = new ArrayList<Trade>();
    for (int i = 0; i < btcTradeOrders.length; i++) {
      BTCTradeOrder order = btcTradeOrders[i];

      CurrencyPair currencyPair = adaptCurrencyPair(order.getCoin());

      if (currencyPair != null) {
        BTCTradeOrder orderDetail = btcTradeOrderDetails[i];

        for (com.xeiam.xchange.btctrade.dto.trade.BTCTradeTrade trade : orderDetail.getTrades()) {
          trades.add(adaptTrade(order, trade, currencyPair));
        }
      }
    }
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(BTCTradeOrder order, com.xeiam.xchange.btctrade.dto.trade.BTCTradeTrade trade, CurrencyPair currencyPair) {

    return new Trade(adaptOrderType(order.getType()), trade.getAmount(), currencyPair, trade.getPrice(), adaptDatetime(trade.getDatetime()), trade.getTradeId(), order.getId());
  }

}
