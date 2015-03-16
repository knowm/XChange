package com.xeiam.xchange.bitcointoyou;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouOrderBook;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTicker;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTransaction;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;
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
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from BitcoinToYou DTOs to XChange DTOs
 *
 * @author Felipe Micaroni Lalli
 */
public final class BitcoinToYouAdapters {

  /**
   * private Constructor
   */
  private BitcoinToYouAdapters() {

  }

  /**
   * Adapts a com.xeiam.xchange.bitcointoyou.dto.marketdata.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/BRL or LTC/BRL)
   * @param timestamp When the book was retrieved from server.
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(BitcoinToYouOrderBook bitcoinToYouOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, bitcoinToYouOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, bitcoinToYouOrderBook.getBids());
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a BitcoinToYouTicker to a Ticker Object
   *
   * @param bitcoinToYouTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitcoinToYouTicker bitcoinToYouTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitcoinToYouTicker.getTicker().getLast();
    BigDecimal bid = bitcoinToYouTicker.getTicker().getBuy();
    BigDecimal ask = bitcoinToYouTicker.getTicker().getSell();
    BigDecimal high = bitcoinToYouTicker.getTicker().getHigh();
    BigDecimal low = bitcoinToYouTicker.getTicker().getLow();
    BigDecimal volume = bitcoinToYouTicker.getTicker().getVol();
    Date timestamp = new Date(bitcoinToYouTicker.getTicker().getDate() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();

  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The BitcoinToYou transactions
   * @param currencyPair (e.g. BTC/BRL or LTC/BRL)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitcoinToYouTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (BitcoinToYouTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(new Trade(tx.getType().equals("buy") ? OrderType.BID : OrderType.ASK, tx.getAmount(), currencyPair, tx.getPrice(), DateUtils
          .fromMillisUtc(tx.getDate() * 1000L), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  /**
   * Adapts a BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> to an AccountInfo
   *
   * @param accountInfo The BitcoinToYou accountInfo
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> accountInfo, String userName) {

    BitcoinToYouBalance[] balances = accountInfo.getTheReturn();
    List<Wallet> wallets = new ArrayList<Wallet>(5);

    for (BitcoinToYouBalance balance : balances) {
      wallets.add(new Wallet(balance.getCurrency(), balance.getBalanceAvailable(), balance.getCurrency() + " balance"));
    }

    return new AccountInfo(userName, wallets);
  }

  public static List<LimitOrder> adaptOrders(BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> input) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    BitcoinToYouOrder[] orders = input.getTheReturn();
    for (BitcoinToYouOrder order : orders) {
      DateFormat time = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
      time.setTimeZone(TimeZone.getTimeZone("Brazil/East"));

      try {
        limitOrders.add(new LimitOrder(order.getAction().equals("buy") ? OrderType.BID : OrderType.ASK, order.getAmount(), new CurrencyPair(order
            .getAsset(), order.getCurrency()), order.getId() + "", time.parse(order.getDateCreated()), order.getPrice()));
      } catch (ParseException e) {
        throw new ExchangeException(e.getMessage(), e);
      }
    }

    return limitOrders;
  }
}
