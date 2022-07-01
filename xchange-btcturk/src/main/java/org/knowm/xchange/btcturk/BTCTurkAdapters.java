package org.knowm.xchange.btcturk;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.account.BTCTurkAccountBalance;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrades;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOpenOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;

/**
 * @author semihunaldi Various adapters for converting from BTCTurk DTOs to XChange DTOs
 * @author mertguner
 */
public final class BTCTurkAdapters {

  private BTCTurkAdapters() {}

  /**
   * Adapts a BTCTurkTicker to a Ticker Object
   *
   * @param btcTurkTicker The exchange specific ticker
   * @return The ticker
   */
  public static Ticker adaptTicker(BTCTurkTicker btcTurkTicker) {
    CurrencyPair pair = btcTurkTicker.getPair();
    BigDecimal high = btcTurkTicker.getHigh();
    BigDecimal last = btcTurkTicker.getLast();
    Date timestamp = new Date(btcTurkTicker.getTimestamp());
    BigDecimal bid = btcTurkTicker.getBid();
    BigDecimal volume = btcTurkTicker.getVolume();
    BigDecimal low = btcTurkTicker.getLow();
    BigDecimal ask = btcTurkTicker.getAsk();
    BigDecimal open = btcTurkTicker.getOpen();
    BigDecimal average = btcTurkTicker.getAverage();

    return new Ticker.Builder()
        .currencyPair(pair != null ? pair : null)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .vwap(average)
        .open(open)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static List<Ticker> adaptTicker(List<BTCTurkTicker> btcTurkTickers) {
    List<Ticker> result = new ArrayList<Ticker>();
    for (BTCTurkTicker ticker : btcTurkTickers) {
      result.add(adaptTicker(ticker));
    }
    return result;
  }
  /**
   * Adapts a BTCTurkTrade[] to a Trades Object
   *
   * @param btcTurkTrades The BTCTurk trades
   * @param currencyPair (e.g. BTC/TRY)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(List<BTCTurkTrades> btcTurkTrades, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    BigDecimal lastTradeId = new BigDecimal("0");
    for (BTCTurkTrades btcTurkTrade : btcTurkTrades) {
      if (btcTurkTrade.getTid().compareTo(lastTradeId) > 0) {
        lastTradeId = btcTurkTrade.getTid();
      }
      trades.add(adaptTrade(btcTurkTrade, currencyPair));
    }
    return new Trades(trades, lastTradeId.longValue(), Trades.TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCTurkTrade to a Trade Object
   *
   * @param btcTurkTrade The BTCTurkTrade trade
   * @param currencyPair (e.g. BTC/TRY)
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCTurkTrades btcTurkTrade, CurrencyPair currencyPair) {

    return new Trade.Builder()
        .originalAmount(btcTurkTrade.getAmount())
        .currencyPair(currencyPair)
        .price(btcTurkTrade.getPrice())
        .timestamp(btcTurkTrade.getDate())
        .id(btcTurkTrade.getTid().toString())
        .build();
  }

  /**
   * Adapts org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook to a OrderBook Object
   *
   * @param btcTurkOrderBook
   * @param currencyPair (e.g. BTC/TRY)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      BTCTurkOrderBook btcTurkOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        createOrders(currencyPair, Order.OrderType.ASK, btcTurkOrderBook.getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, Order.OrderType.BID, btcTurkOrderBook.getBids());
    return new OrderBook(btcTurkOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static Wallet adaptWallet(String name, BTCTurkAccountBalance btcTurkBalance) {

    List<Balance> balances = new ArrayList<>(7);

    balances.add(
        new Balance(
            Currency.TRY,
            null,
            btcTurkBalance.getTry_available(),
            btcTurkBalance.getTry_reserved()));
    balances.add(
        new Balance(
            Currency.BTC,
            null,
            btcTurkBalance.getBtc_available(),
            btcTurkBalance.getBtc_reserved()));
    balances.add(
        new Balance(
            Currency.ETH,
            null,
            btcTurkBalance.getEth_available(),
            btcTurkBalance.getEth_reserved()));
    balances.add(
        new Balance(
            Currency.XRP,
            null,
            btcTurkBalance.getXrp_available(),
            btcTurkBalance.getXrp_reserved()));
    balances.add(
        new Balance(
            Currency.LTC,
            null,
            btcTurkBalance.getLtc_available(),
            btcTurkBalance.getLtc_reserved()));
    balances.add(
        new Balance(
            Currency.USDT,
            null,
            btcTurkBalance.getUsdt_available(),
            btcTurkBalance.getUsdt_reserved()));
    balances.add(
        new Balance(
            Currency.XLM,
            null,
            btcTurkBalance.getXlm_available(),
            btcTurkBalance.getXlm_reserved()));

    return Wallet.Builder.from(balances).id(name).name(name).build();
  }

  public static FundingRecord adaptTransaction(BTCTurkUserTransactions transaction) {

    String description = transaction.getOperation().toString();
    if (transaction.getId() != null) {
      description += ", index: " + transaction.getId();
    }

    return new FundingRecord.Builder()
        .setInternalId(transaction.getId().toString())
        .setDate(transaction.getDate())
        .setType(transaction.getOperation().getType())
        .setCurrency(transaction.getCurrency())
        .setAmount(transaction.getAmount())
        .setFee(transaction.getFee())
        .setBalance(transaction.getFunds())
        .setDescription(description)
        .build();
  }

  public static BTCTurkOrderTypes adaptOrderType(OrderType type) {
    return type.equals(OrderType.ASK) ? BTCTurkOrderTypes.Buy : BTCTurkOrderTypes.Sell;
  }

  public static OpenOrders adaptOpenOrders(List<BTCTurkOpenOrders> openOrdersRaw) {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (BTCTurkOpenOrders BTCTurkOpenOrder : openOrdersRaw) {
      limitOrders.add(adaptOrder(BTCTurkOpenOrder));
    }
    return new OpenOrders(limitOrders);
  }

  private static LimitOrder adaptOrder(BTCTurkOpenOrders order) {

    return new LimitOrder(
        order.getType().equals("BuyBtc") ? OrderType.BID : OrderType.ASK,
        order.getAmount(),
        order.getPairsymbol().pair,
        order.getId().toString(),
        order.getDatetime(),
        order.getPrice(),
        order.getPrice(),
        order.getAmount(),
        BigDecimal.ZERO,
        OrderStatus.FILLED);
  }
}
