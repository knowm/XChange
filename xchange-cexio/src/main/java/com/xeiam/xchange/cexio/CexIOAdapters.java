package com.xeiam.xchange.cexio;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOAdapters {

  /**
   * Adapts a CexIOTrade to a Trade Object
   * 
   * @param trade CexIO trade object
   * @param tradableIdentifier First currency in the pair
   * @param currency Second currency in the pair
   * @return The XChange Trade
   */
  public static Trade adaptTrade(CexIOTrade trade, String tradableIdentifier, String currency) {

    BigDecimal amount = trade.getAmount();
    BigMoney price = MoneyUtils.parse(currency + " " + trade.getPrice());
    Date date = DateUtils.fromMillisUtc(trade.getDate() * 1000L);
    // Cex.IO API does not return trade type
    return new Trade(null, amount, tradableIdentifier, currency, price, date, String.valueOf(trade.getTid()), null);
  }

  /**
   * Adapts a CexIOTrade[] to a Trades Object
   * 
   * @param cexioTrades The CexIO trade data returned by API
   * @param tradableIdentifier First currency of the pair
   * @param currency Second currency of the pair
   * @return The trades
   */
  public static Trades adaptTrades(CexIOTrade[] cexioTrades, String tradableIdentifier, String currency) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (CexIOTrade trade : cexioTrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(trade, tradableIdentifier, currency));
    }
    return new Trades(tradesList);
  }

  /**
   * Adapts a CexIOTicker to a Ticker Object
   * 
   * @param ticker The exchange specific ticker
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CexIOTicker ticker, String tradableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parse(currency + " " + ticker.getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + ticker.getBid());
    BigMoney ask = MoneyUtils.parse(currency + " " + ticker.getAsk());
    BigMoney high = MoneyUtils.parse(currency + " " + ticker.getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + ticker.getLow());
    BigDecimal volume = ticker.getVolume();
    Date timestamp = new Date(ticker.getTimestamp() * 1000L);

    return Ticker.TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(
        timestamp).build();
  }

  /**
   * Adapts Cex.IO Depth to OrderBook Object
   * 
   * @param depth Cex.IO order book
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(CexIODepth depth, String tradableIdentifier, String currency) {

    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, depth.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, depth.getBids());
    Date date = new Date(depth.getTimestamp() * 1000);
    return new OrderBook(date, asks, bids);
  }

  /**
   * Adapts CexIOBalanceInfo to AccountInfo
   * 
   * @param balance CexIOBalanceInfo balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(CexIOBalanceInfo balance, String userName) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    // Adapt to XChange DTOs
    if (balance.getBalanceBTC() != null) {
      wallets.add(Wallet.createInstance(Currencies.BTC, balance.getBalanceBTC().getAvailable(), "available"));
      wallets.add(Wallet.createInstance(Currencies.BTC, balance.getBalanceBTC().getOrders(), "orders"));
    }
    if (balance.getBalanceNMC() != null) {
      wallets.add(Wallet.createInstance(Currencies.NMC, balance.getBalanceNMC().getAvailable(), "available"));
      wallets.add(Wallet.createInstance(Currencies.NMC, balance.getBalanceNMC().getOrders(), "orders"));
    }
    if (balance.getBalanceIXC() != null) {
      wallets.add(Wallet.createInstance(Currencies.IXC, balance.getBalanceIXC().getAvailable(), "available"));
    }
    if (balance.getBalanceDVC() != null) {
      wallets.add(Wallet.createInstance(Currencies.DVC, balance.getBalanceDVC().getAvailable(), "available"));
    }
    if (balance.getBalanceGHS() != null) {
      wallets.add(Wallet.createInstance(Currencies.GHs, balance.getBalanceGHS().getAvailable(), "available"));
      wallets.add(Wallet.createInstance(Currencies.GHs, balance.getBalanceGHS().getOrders(), "orders"));
    }
    return new AccountInfo(userName, null, wallets);
  }

  private static List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> o : orders) {
      checkArgument(o.size() == 2, "Expected a pair (price, amount) but got {0} elements.", o.size());
      limitOrders.add(createOrder(tradableIdentifier, currency, o, orderType));
    }
    return limitOrders;
  }

  private static LimitOrder createOrder(String tradableIdentifier, String currency, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), tradableIdentifier, currency, "", null, BigMoney.of(CurrencyUnit.USD, priceAndAmount.get(0)));
  }

  private static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

}
