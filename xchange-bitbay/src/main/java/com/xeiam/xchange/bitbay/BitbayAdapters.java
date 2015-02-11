package com.xeiam.xchange.bitbay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.bitbay.dto.account.BitbayBalance;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;
import com.xeiam.xchange.bitbay.dto.trade.BitbayOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * @author kpysniak
 */
public class BitbayAdapters {

  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

  /**
   * Singleton
   */
  private BitbayAdapters() {

  }

  /**
   * Adapts a BitbayTicker to a Ticker Object
   * 
   * @param bitbayTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitbayTicker bitbayTicker, CurrencyPair currencyPair) {

    BigDecimal ask = bitbayTicker.getAsk();
    BigDecimal bid = bitbayTicker.getBid();
    BigDecimal high = bitbayTicker.getMax();
    BigDecimal low = bitbayTicker.getMin();
    BigDecimal volume = bitbayTicker.getVolume();
    BigDecimal last = bitbayTicker.getLast();
    Date timestamp = new Date();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp).build();
  }

  /**
   * @param orders
   * @param orderType
   * @param currencyPair
   * @return
   */
  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  /**
   * @param bitbayOrderBook
   * @param currencyPair
   * @return
   */
  public static OrderBook adaptOrderBook(BitbayOrderBook bitbayOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook =
        new OrderBook(new Date(), transformArrayToLimitOrders(bitbayOrderBook.getAsks(), OrderType.ASK, currencyPair), transformArrayToLimitOrders(bitbayOrderBook.getBids(), OrderType.BID,
            currencyPair));

    return orderBook;
  }

  /**
   * @param bitbayTrades
   * @param currencyPair
   * @return
   */
  public static Trades adaptTrades(BitbayTrade[] bitbayTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitbayTrade bitbayTrade : bitbayTrades) {

      Trade trade = new Trade(null, bitbayTrade.getAmount(), currencyPair, bitbayTrade.getPrice(), new Date(bitbayTrade.getDate() * 1000), bitbayTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }

    public static AccountInfo adaptAccount(BitbayAccount bitbayAccount) {
        List<Wallet> wallets = new ArrayList<Wallet>();

        Map<String, BitbayBalance> balances = bitbayAccount.getBalances();

        for (Map.Entry<String, BitbayBalance> balanceEntry : balances.entrySet()) {
            Wallet wallet = new Wallet(balanceEntry.getKey(), balanceEntry.getValue().getAvailable(), "Available");
            wallets.add(wallet);
//            wallet = new Wallet(balanceEntry.getKey(), balanceEntry.getValue().getLocked(), "Locked");
//            wallets.add(wallet);
        }

        return new AccountInfo(null, wallets);
    }

    public static OpenOrders adaptOpenOrders(List<BitbayOrder> bitbayOrders) {
        List<LimitOrder> orders = new ArrayList<LimitOrder>();

        for (BitbayOrder bitbayOrder : bitbayOrders) {
            if ("active".equals(bitbayOrder.getStatus())) {

                OrderType type = "ask".equals(bitbayOrder.getType()) ? OrderType.ASK : OrderType.BID;
                CurrencyPair pair = new CurrencyPair(bitbayOrder.getOrderCurrency(), bitbayOrder.getPaymentCurrency());

                Date date = null;
                try {
                    date = dateFormatter.parse(bitbayOrder.getOrderDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                BigDecimal rate = bitbayOrder.getStartPrice().divide(bitbayOrder.getStartUnits());
                orders.add(new LimitOrder(type, bitbayOrder.getUnits(), pair, bitbayOrder.getOrderId(), date, rate));
            }
        }

        return new OpenOrders(orders);
    }
}
