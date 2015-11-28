package com.xeiam.xchange.cexio;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.cexio.dto.account.CexIOBalance;
import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Author: brox Since: 2/6/14
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
  public static Trade adaptTrade(CexIOTrade trade, CurrencyPair currencyPair) {

    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getDate() * 1000L);
    // Cex.IO API does not return trade type
    return new Trade(null, amount, currencyPair, price, date, String.valueOf(trade.getTid()));
  }

  /**
   * Adapts a CexIOTrade[] to a Trades Object
   *
   * @param cexioTrades The CexIO trade data returned by API
   * @param tradableIdentifier First currency of the pair
   * @param currency Second currency of the pair
   * @return The trades
   */
  public static Trades adaptTrades(CexIOTrade[] cexioTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (CexIOTrade trade : cexioTrades) {
      long tradeId = trade.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a CexIOTicker to a Ticker Object
   *
   * @param ticker The exchange specific ticker
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CexIOTicker ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getLast();
    BigDecimal bid = ticker.getBid();
    BigDecimal ask = ticker.getAsk();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal volume = ticker.getVolume();
    Date timestamp = new Date(ticker.getTimestamp() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();
  }

  /**
   * Adapts Cex.IO Depth to OrderBook Object
   *
   * @param depth Cex.IO order book
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(CexIODepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, depth.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, depth.getBids());
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
      wallets.add(new Wallet(Currency.BTC, balance.getBalanceBTC().getAvailable(), "available"));
      wallets.add(new Wallet(Currency.BTC, balance.getBalanceBTC().getOrders(), "orders"));
      wallets.add(adaptWallet(Currency.BTC, balance.getBalanceBTC()));
    }
    if (balance.getBalanceLTC() != null) {
      wallets.add(new Wallet(Currency.LTC, balance.getBalanceLTC().getAvailable(), "available"));
      wallets.add(new Wallet(Currency.LTC, balance.getBalanceLTC().getOrders(), "orders"));
      wallets.add(adaptWallet(Currency.LTC, balance.getBalanceLTC()));
    }
    if (balance.getBalanceNMC() != null) {
      wallets.add(new Wallet(Currency.NMC, balance.getBalanceNMC().getAvailable(), "available"));
      wallets.add(new Wallet(Currency.NMC, balance.getBalanceNMC().getOrders(), "orders"));
      wallets.add(adaptWallet(Currency.NMC, balance.getBalanceNMC()));
    }
    if (balance.getBalanceIXC() != null) {
      wallets.add(new Wallet(Currency.IXC, balance.getBalanceIXC().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.IXC, balance.getBalanceIXC()));
    }
    if (balance.getBalanceDVC() != null) {
      wallets.add(new Wallet(Currency.DVC, balance.getBalanceDVC().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.DVC, balance.getBalanceDVC()));
    }
    if (balance.getBalanceGHS() != null) {
      wallets.add(new Wallet(Currency.GHs, balance.getBalanceGHS().getAvailable(), "available"));
      wallets.add(new Wallet(Currency.GHs, balance.getBalanceGHS().getOrders(), "orders"));
      wallets.add(adaptWallet(Currency.GHs, balance.getBalanceGHS()));
    }
    if (balance.getBalanceUSD() != null) {
      wallets.add(new Wallet(Currency.USD, balance.getBalanceUSD().getAvailable(), "available"));
      wallets.add(new Wallet(Currency.USD, balance.getBalanceUSD().getOrders(), "orders"));
      wallets.add(adaptWallet(Currency.USD, balance.getBalanceUSD()));
    }
    if (balance.getBalanceDRK() != null) {
      wallets.add(new Wallet(Currency.DRK, balance.getBalanceDRK().getAvailable(), "available"));
      wallets.add(new Wallet(Currency.DRK, balance.getBalanceDRK().getOrders(), "orders"));
      wallets.add(adaptWallet(Currency.DRK, balance.getBalanceDRK()));
    }
    if (balance.getBalanceEUR() != null) {
      wallets.add(new Wallet(Currency.EUR, balance.getBalanceEUR().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.EUR, balance.getBalanceEUR()));
    }
    if (balance.getBalanceDOGE() != null) {
      wallets.add(new Wallet(Currency.DOGE, balance.getBalanceDOGE().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.DOGE, balance.getBalanceDOGE()));
    }
    if (balance.getBalanceFTC() != null) {
      wallets.add(new Wallet(Currency.FTC, balance.getBalanceFTC().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.FTC, balance.getBalanceFTC()));
    }
    if (balance.getBalanceMEC() != null) {
      wallets.add(new Wallet(Currency.MEC, balance.getBalanceMEC().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.MEC, balance.getBalanceMEC()));
    }
    if (balance.getBalanceWDC() != null) {
      wallets.add(new Wallet(Currency.WDC, balance.getBalanceWDC().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.WDC, balance.getBalanceWDC()));
    }
    if (balance.getBalanceMYR() != null) {
      wallets.add(new Wallet(Currency.MYR, balance.getBalanceMYR().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.MYR, balance.getBalanceMYR()));
    }
    if (balance.getBalanceAUR() != null) {
      wallets.add(new Wallet(Currency.AUR, balance.getBalanceAUR().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.AUR, balance.getBalanceAUR()));
    }
    if (balance.getBalancePOT() != null) {
      wallets.add(new Wallet(Currency.POT, balance.getBalancePOT().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.POT, balance.getBalancePOT()));
    }
    if (balance.getBalanceANC() != null) {
      wallets.add(new Wallet(Currency.ANC, balance.getBalanceANC().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.ANC, balance.getBalanceANC()));
    }
    if (balance.getBalanceDGB() != null) {
      wallets.add(new Wallet(Currency.DGB, balance.getBalanceDGB().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.DGB, balance.getBalanceDGB()));
    }
    if (balance.getBalanceUSDE() != null) {
      wallets.add(new Wallet(Currency.USDE, balance.getBalanceUSDE().getAvailable(), "available"));
      wallets.add(adaptWallet(Currency.USDE, balance.getBalanceUSDE()));
    }

    return new AccountInfo(userName, wallets);
  }

  public static Wallet adaptWallet(Currency currency, CexIOBalance balance) {
    return new Wallet(currency, balance.getAvailable().add(balance.getOrders()), balance.getAvailable(), balance.getOrders());
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> o : orders) {
      checkArgument(o.size() == 2, "Expected a pair (price, amount) but got {0} elements.", o.size());
      limitOrders.add(createOrder(currencyPair, o, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static OpenOrders adaptOpenOrders(List<CexIOOrder> cexIOOrderList) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CexIOOrder cexIOOrder : cexIOOrderList) {
      Order.OrderType orderType = cexIOOrder.getType() == CexIOOrder.Type.buy ? Order.OrderType.BID : Order.OrderType.ASK;
      String id = Long.toString(cexIOOrder.getId());
      limitOrders.add(new LimitOrder(orderType, cexIOOrder.getPending(),
          new CurrencyPair(cexIOOrder.getTradableIdentifier(), cexIOOrder.getTransactionCurrency()), id,
          DateUtils.fromMillisUtc(cexIOOrder.getTime()), cexIOOrder.getPrice()));
    }

    return new OpenOrders(limitOrders);

  }

}
