package com.xeiam.xchange.bitvc;

import static com.xeiam.xchange.currency.Currencies.BTC;
import static com.xeiam.xchange.currency.Currencies.CNY;
import static com.xeiam.xchange.currency.Currencies.LTC;
import static com.xeiam.xchange.dto.Order.OrderType.ASK;
import static com.xeiam.xchange.dto.Order.OrderType.BID;
import static com.xeiam.xchange.dto.marketdata.Trades.TradeSortType.SortByTimestamp;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.xeiam.xchange.bitvc.dto.account.BitVcAccountInfo;
import com.xeiam.xchange.bitvc.dto.account.HuobiAccountInfo;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcDepth;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcOrderBookTAS;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcTicker;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcTickerObject;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcTradeObject;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.exceptions.ExchangeException;

public final class BitVcAdapters {

  private static final SimpleDateFormat tradeDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

  private BitVcAdapters() {

  }

  public static Ticker adaptTicker(BitVcTicker BitVcTicker, CurrencyPair currencyPair) {

    BitVcTickerObject ticker = BitVcTicker.getTicker();
    return new Ticker.Builder().currencyPair(currencyPair).last(ticker.getLast()).bid(ticker.getBuy()).ask(ticker.getSell()).high(ticker.getHigh())
        .low(ticker.getLow()).volume(ticker.getVol()).build();
  }

  public static OrderBook adaptOrderBook(BitVcDepth BitVcDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptOrderBook(BitVcDepth.getAsks(), ASK, currencyPair);
    Collections.reverse(asks);

    List<LimitOrder> bids = adaptOrderBook(BitVcDepth.getBids(), BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptOrderBook(BigDecimal[][] orders, OrderType type, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);
    for (int i = 0; i < orders.length; i++) {
      BigDecimal[] order = orders[i];

      LimitOrder limitOrder = new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }

  public static Trades adaptTrades(BitVcOrderBookTAS bitvcDetail, CurrencyPair currencyPair) {

    List<Trade> trades = adaptTrades(bitvcDetail.getTrades(), currencyPair);
    return new Trades(trades, SortByTimestamp);
  }

  private static List<Trade> adaptTrades(BitVcTradeObject[] trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>(trades.length);
    for (int i = 0; i < trades.length; i++) {
      tradeList.add(adaptTrade(trades[i], currencyPair));
    }
    return tradeList;
  }

  private static Trade adaptTrade(BitVcTradeObject trade, CurrencyPair currencyPair) {

    OrderType type = trade.getType().equals("买入") ? BID : ASK;
    final Date time;
    try {
      time = tradeDateFormat.parse(trade.getTime());
    } catch (ParseException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
    return new Trade(type, trade.getAmount(), currencyPair, trade.getPrice(), time, null);
  }

  public static AccountInfo adaptAccountInfo(BitVcAccountInfo a) {

    Wallet cny = new Wallet(CNY, a.getAvailableCnyDisplay().add(a.getFrozenCnyDisplay()).subtract(a.getLoanCnyDisplay()), "available");
    Wallet btc = new Wallet(BTC, a.getAvailableBtcDisplay().add(a.getFrozenBtcDisplay()).subtract(a.getLoanBtcDisplay()), "available");
    Wallet ltc = new Wallet(LTC, a.getAvailableLtcDisplay().add(a.getFrozenLtcDisplay()).subtract(a.getLoanLtcDisplay()), "available");

    // loaned wallets
    Wallet cnyLoan = new Wallet(CNY, a.getLoanCnyDisplay(), "loan");
    Wallet btcLoan = new Wallet(BTC, a.getLoanBtcDisplay(), "loan");
    Wallet ltcLoan = new Wallet(LTC, a.getLoanLtcDisplay(), "loan");

    List<Wallet> wallets = Arrays.asList(cny, btc, ltc, cnyLoan, btcLoan, ltcLoan);
    return new AccountInfo(null, wallets);
  }

  public static AccountInfo adaptHuobiAccountInfo(HuobiAccountInfo a) {

    Wallet cny = new Wallet(CNY, a.getAvailableCnyDisplay().add(a.getFrozenCnyDisplay()).subtract(a.getLoanCnyDisplay()), "available");
    Wallet btc = new Wallet(BTC, a.getAvailableBtcDisplay().add(a.getFrozenBtcDisplay()).subtract(a.getLoanBtcDisplay()), "available");
    Wallet ltc = new Wallet(LTC, a.getAvailableLtcDisplay().add(a.getFrozenLtcDisplay()).subtract(a.getLoanLtcDisplay()), "available");

    // loaned wallets
    Wallet cnyLoan = new Wallet(CNY, a.getLoanCnyDisplay(), "loan");
    Wallet btcLoan = new Wallet(BTC, a.getLoanBtcDisplay(), "loan");
    Wallet ltcLoan = new Wallet(LTC, a.getLoanLtcDisplay(), "loan");

    List<Wallet> wallets = Arrays.asList(cny, btc, ltc, cnyLoan, btcLoan, ltcLoan);
    return new AccountInfo(null, wallets);
  }

  public static String adaptPlaceOrderResult(BitVcPlaceOrderResult result) {

    if (result.getCode() == 0) {
      return String.valueOf(result.getId());
    } else {
      throw new ExchangeException("Error code: " + result.getCode());
    }
  }

  public static List<LimitOrder> adaptOpenOrders(BitVcOrder[] orders, CurrencyPair currencyPair) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>(orders.length);
    for (int i = 0; i < orders.length; i++) {
      openOrders.add(adaptOpenOrder(orders[i], currencyPair));
    }
    return openOrders;
  }

  public static LimitOrder adaptOpenOrder(BitVcOrder order, CurrencyPair currencyPair) {

    return new LimitOrder(order.getType() == 1 ? BID : ASK, order.getOrderAmount().subtract(order.getProcessedAmount()), currencyPair,
        String.valueOf(order.getId()), new Date(order.getOrderTime() * 1000), order.getOrderPrice());
  }

}
