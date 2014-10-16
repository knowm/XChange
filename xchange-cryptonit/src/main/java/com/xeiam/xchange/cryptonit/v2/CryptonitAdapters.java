package com.xeiam.xchange.cryptonit.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitCoin;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitCoins;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitFund;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitFunds;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrder;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitRate;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Cryptonit DTOs to XChange DTOs
 */

public final class CryptonitAdapters {

  /**
   * private Constructor
   */
  private CryptonitAdapters() {

  }

  /**
   * Adapts a CryptonitOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptDepth(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, Date date, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, date, price);

  }

  public static List<LimitOrder> adaptDepths(CryptonitOrders cryptonitOrders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    Map<String, CryptonitOrder> orders = cryptonitOrders.getOrders();
    for (Map.Entry<String, CryptonitOrder> trade : orders.entrySet()) {
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(adaptDepth(trade.getValue().getAskAmount(), trade.getValue().getAskRate(), currencyPair, orderType, DateUtils.fromMillisUtc(trade.getValue().getCreated() * 1000L), String
                .valueOf(trade.getKey())));
      }
      else {
        limitOrders.add(adaptDepth(trade.getValue().getBidAmount(), trade.getValue().getBidRate(), currencyPair, orderType, DateUtils.fromMillisUtc(trade.getValue().getCreated() * 1000L), String
                .valueOf(trade.getKey())));
      }
    }
    Collections.sort(limitOrders);

    return limitOrders;
  }

    public static List<LimitOrder> adaptOrders(CryptonitOrders cryptonitOrders) {

        List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
        if (cryptonitOrders == null) {
            return limitOrders;
        }

        Map<String, CryptonitOrder> orders = cryptonitOrders.getOrders();
        for (Map.Entry<String, CryptonitOrder> entry : orders.entrySet()) {
            limitOrders.add(adaptOrder(entry.getKey(), entry.getValue()));
        }

        Collections.sort(limitOrders);
        return limitOrders;
    }


    public static List<Trade> adaptFilledOrders(CryptonitOrders cryptonitOrders) {

        List<Trade> trades = new ArrayList<Trade>();
        if (cryptonitOrders == null) {
            return trades;
        }

        Map<String, CryptonitOrder> orders = cryptonitOrders.getOrders();
        for (Map.Entry<String, CryptonitOrder> entry : orders.entrySet()) {
            trades.add(adaptFilledOrder(entry.getKey(), entry.getValue()));
        }
        return trades;
    }

    private static LimitOrder adaptOrder(String id, CryptonitOrder cryptonitOrder) {
        OrderType orderType = null;
        CurrencyPair currencyPair = null;
        BigDecimal price = null;
        BigDecimal amount = null;

        if ("USD".equals(cryptonitOrder.getAskCurrency()) || "EUR".equals(cryptonitOrder.getAskCurrency())) {
            orderType = OrderType.ASK;
            currencyPair = new CurrencyPair(cryptonitOrder.getAskCurrency(), cryptonitOrder.getBidCurrency());
            price = cryptonitOrder.getAskAmount().divide(cryptonitOrder.getBidAmount());
            amount = cryptonitOrder.getBidAmount();

        } else if ("USD".equals(cryptonitOrder.getBidCurrency()) || "EUR".equals(cryptonitOrder.getBidCurrency())) {
            orderType = OrderType.BID;
            currencyPair = new CurrencyPair(cryptonitOrder.getBidCurrency(), cryptonitOrder.getAskCurrency());
            price = cryptonitOrder.getBidAmount().divide(cryptonitOrder.getAskAmount());
            amount = cryptonitOrder.getAskAmount();

        } else if ("BTC".equals(cryptonitOrder.getAskCurrency())) {
            orderType = OrderType.ASK;
            currencyPair = new CurrencyPair(cryptonitOrder.getAskCurrency(), cryptonitOrder.getBidCurrency());
            price = cryptonitOrder.getAskAmount().divide(cryptonitOrder.getBidAmount());
            amount = cryptonitOrder.getBidAmount();

        } else if ("BTC".equals(cryptonitOrder.getBidCurrency())) {
            orderType = OrderType.BID;
            currencyPair = new CurrencyPair(cryptonitOrder.getBidCurrency(), cryptonitOrder.getAskCurrency());
            price = cryptonitOrder.getBidAmount().divide(cryptonitOrder.getAskAmount());
            amount = cryptonitOrder.getAskAmount();
        }

        Date date = new Date(cryptonitOrder.getCreated() * 1000);
        return new LimitOrder(orderType, amount, currencyPair, id, date, price);
    }

    private static Trade adaptFilledOrder(String id, CryptonitOrder cryptonitOrder) {
        OrderType orderType = null;
        CurrencyPair currencyPair = null;
        BigDecimal price = null;
        BigDecimal amount = null;

        if ("USD".equals(cryptonitOrder.getAskCurrency()) || "EUR".equals(cryptonitOrder.getAskCurrency())) {
            orderType = OrderType.ASK;
            currencyPair = new CurrencyPair(cryptonitOrder.getAskCurrency(), cryptonitOrder.getBidCurrency());
            price = cryptonitOrder.getAskAmount().divide(cryptonitOrder.getBidAmount());
            amount = cryptonitOrder.getBidAmount();

        } else if ("USD".equals(cryptonitOrder.getBidCurrency()) || "EUR".equals(cryptonitOrder.getBidCurrency())) {
            orderType = OrderType.BID;
            currencyPair = new CurrencyPair(cryptonitOrder.getBidCurrency(), cryptonitOrder.getAskCurrency());
            price = cryptonitOrder.getBidAmount().divide(cryptonitOrder.getAskAmount());
            amount = cryptonitOrder.getAskAmount();

        } else if ("BTC".equals(cryptonitOrder.getAskCurrency())) {
            orderType = OrderType.ASK;
            currencyPair = new CurrencyPair(cryptonitOrder.getAskCurrency(), cryptonitOrder.getBidCurrency());
            price = cryptonitOrder.getAskAmount().divide(cryptonitOrder.getBidAmount());
            amount = cryptonitOrder.getBidAmount();

        } else if ("BTC".equals(cryptonitOrder.getBidCurrency())) {
            orderType = OrderType.BID;
            currencyPair = new CurrencyPair(cryptonitOrder.getBidCurrency(), cryptonitOrder.getAskCurrency());
            price = cryptonitOrder.getBidAmount().divide(cryptonitOrder.getAskAmount());
            amount = cryptonitOrder.getAskAmount();
        }

        Date date = new Date(cryptonitOrder.getFilled() * 1000);
        return new Trade(orderType, amount, currencyPair, price, date, id);
    }

    /**
   * Adapts a CryptonitTrade to a Trade Object
   * 
   * @param cryptonitTrade A Cryptonit trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(String tradeId, CryptonitOrder cryptonitTrade, CurrencyPair currencyPair) {

    BigDecimal amount = cryptonitTrade.getBidAmount();
    BigDecimal price = cryptonitTrade.getBidRate();

    return new Trade(null, amount, currencyPair, price, DateUtils.fromMillisUtc(cryptonitTrade.getFilled() * 1000L), tradeId);
  }

  /**
   * Adapts a CryptonitTrade[] to a Trades Object
   * 
   * @param cryptonitTrades The Cryptonit trade data
   * @return The trades
   */
  public static Trades adaptTrades(CryptonitOrders cryptonitTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();

    long lastTradeId = 0;
    Map<String, CryptonitOrder> orders = cryptonitTrades.getOrders();
    for (Map.Entry<String, CryptonitOrder> trade : orders.entrySet()) {
      String tradeId = trade.getKey();
      long tradeIdAsLong = Long.valueOf(tradeId);
      if (tradeIdAsLong > lastTradeId)
        lastTradeId = tradeIdAsLong;
      tradesList.add(adaptTrade(tradeId, trade.getValue(), currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a cryptonitTicker to a Ticker Object
   * 
   * @param cryptonitTicker
   * @return
   */
  public static Ticker adaptTicker(CryptonitTicker cryptonitTicker, CurrencyPair currencyPair) {

    CryptonitRate rate = cryptonitTicker.getRate();

    BigDecimal last = rate.getLast();
    BigDecimal high = rate.getHigh();
    BigDecimal low = rate.getLow();
    BigDecimal bid = rate.getBid();
    BigDecimal ask = rate.getAsk();
    BigDecimal volume = cryptonitTicker.getVolume().getVolume(currencyPair.baseSymbol);

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withBid(bid).withAsk(ask).withVolume(volume).build();
  }

  public static Collection<CurrencyPair> adaptCurrencyPairs(List<List<String>> tradingPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();
    for (List<String> tradingPair : tradingPairs) {
      if (tradingPair.size() == 2) {
        CurrencyPair currencyPair = new CurrencyPair(tradingPair.get(1), tradingPair.get(0));
        currencyPairs.add(currencyPair);
      }
    }
    return currencyPairs;
  }

    public static AccountInfo adaptAccountInfo(CryptonitFunds funds, CryptonitCoins coins) {

        List<Wallet> wallets = new ArrayList<Wallet>();

        if (funds != null) {
            Collection<CryptonitFund> fundsList = funds.getFunds();
            for (CryptonitFund cryptonitFund : fundsList) {
                Wallet wallet = new Wallet(cryptonitFund.getCurrencyCode(), cryptonitFund.getBalance(), cryptonitFund.getCurrencyCode());
                wallets.add(wallet);
            }
        }

        if (coins != null) {
            Collection<CryptonitCoin> coinsList = coins.getCoins();
            for (CryptonitCoin cryptonitCoin : coinsList) {
                Wallet wallet = new Wallet(cryptonitCoin.getType(), cryptonitCoin.getCurrent(), cryptonitCoin.getLabel());
                wallets.add(wallet);
            }
        }

        return new AccountInfo(null, wallets);
    }
}
