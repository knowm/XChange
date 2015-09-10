package com.xeiam.xchange.mercadobitcoin;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTicker;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrdersEntry;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Mercado Bitcoin DTOs to XChange DTOs
 *
 * @author Felipe Micaroni Lalli
 */
public final class MercadoBitcoinAdapters {

  /**
   * private Constructor
   */
  private MercadoBitcoinAdapters() {

  }

  /**
   * Adapts a com.xeiam.xchange.mercadobitcoin.dto.marketdata.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/BRL or LTC/BRL)
   * @param timestamp When the book was retrieved from server.
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(MercadoBitcoinOrderBook mercadoBitcoinOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, mercadoBitcoinOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, mercadoBitcoinOrderBook.getBids());
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
   * Adapts a MercadoBitcoinTicker to a Ticker Object
   *
   * @param mercadoBitcoinTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(MercadoBitcoinTicker mercadoBitcoinTicker, CurrencyPair currencyPair) {

    BigDecimal last = mercadoBitcoinTicker.getTicker().getLast();
    BigDecimal bid = mercadoBitcoinTicker.getTicker().getBuy();
    BigDecimal ask = mercadoBitcoinTicker.getTicker().getSell();
    BigDecimal high = mercadoBitcoinTicker.getTicker().getHigh();
    BigDecimal low = mercadoBitcoinTicker.getTicker().getLow();
    BigDecimal volume = mercadoBitcoinTicker.getTicker().getVol();
    Date timestamp = new Date(mercadoBitcoinTicker.getTicker().getDate() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();

  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Mercado Bitcoin transactions
   * @param currencyPair (e.g. BTC/BRL or LTC/BRL)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(MercadoBitcoinTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (MercadoBitcoinTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(new Trade(tx.getType().equals("buy") ? OrderType.BID : OrderType.ASK, tx.getAmount(), currencyPair, tx.getPrice(),
          DateUtils.fromMillisUtc(tx.getDate() * 1000L), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  /**
   * Adapts a MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> to a AccountInfo
   *
   * @param accountInfo The Mercado Bitcoin accountInfo
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo, String userName) {

    // Adapt to XChange DTOs
    Wallet brlWallet = new Wallet(Currencies.BRL, accountInfo.getTheReturn().getFunds().getBrl(), "Brazilian Reais balance (R$ / BRL)");
    Wallet btcWallet = new Wallet(Currencies.BTC, accountInfo.getTheReturn().getFunds().getBtc(), "Bitcoin balance (XBT / BTC)");
    Wallet ltcWallet = new Wallet(Currencies.LTC, accountInfo.getTheReturn().getFunds().getLtc(), "Litecoin balance (XLT / LTC)");

    return new AccountInfo(userName, Arrays.asList(brlWallet, btcWallet, ltcWallet));
  }

  public static List<LimitOrder> adaptOrders(CurrencyPair currencyPair, MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> input) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    MercadoBitcoinUserOrders orders = input.getTheReturn();

    for (Map.Entry<String, MercadoBitcoinUserOrdersEntry> entry : orders.entrySet()) {
      limitOrders.add(processOrderEntry(entry, currencyPair));
    }

    return limitOrders;
  }

  private static LimitOrder processOrderEntry(Map.Entry<String, MercadoBitcoinUserOrdersEntry> entry, CurrencyPair currencyPair) {

    String id = entry.getKey();
    MercadoBitcoinUserOrdersEntry userOrdersEntry = entry.getValue();

    OrderType orderType = userOrdersEntry.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal price = userOrdersEntry.getPrice();
    BigDecimal volume = userOrdersEntry.getVolume();
    long time = userOrdersEntry.getCreated() * 1000L;
    return new LimitOrder(orderType, volume, currencyPair, id, new Date(time), price);
  }
}
