package org.knowm.xchange.coinone;

import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.coinone.dto.CoinoneException;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesResponse;
import org.knowm.xchange.coinone.dto.marketdata.*;
import org.knowm.xchange.coinone.dto.trade.CoinoneOrderInfo;
import org.knowm.xchange.coinone.dto.trade.CoinoneOrderInfoResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CoinoneAdapters {

  public static final Logger log = LoggerFactory.getLogger(CoinoneAdapters.class);

  private CoinoneAdapters() {}

  public static OrderBook adaptOrderBook(
      CoinoneOrderBook coinoneOrderBook, CurrencyPair currencyPair) {
    if (!"0".equals(coinoneOrderBook.getErrorCode())) {
      throw new CoinoneException(coinoneOrderBook.getResult());
    }

    List<LimitOrder> asks =
        adaptMarketOrderToLimitOrder(coinoneOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptMarketOrderToLimitOrder(coinoneOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(
        DateUtils.fromMillisUtc(Long.valueOf(coinoneOrderBook.getTimestamp()) * 1000), asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(
      CoinoneOrderBookData[] coinoneOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<>(coinoneOrders.length);
    for (CoinoneOrderBookData coinoneOrder : coinoneOrders) {
      BigDecimal price = coinoneOrder.getPrice();
      BigDecimal amount = coinoneOrder.getQty();
      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Order adaptOrderInfo(CoinoneOrderInfoResponse coinoneOrderInfoResponse) {
    ArrayList<Order> orders = new ArrayList<>();
    OrderStatus status = OrderStatus.NEW;
    if (coinoneOrderInfoResponse.getStatus().equals("live")) status = OrderStatus.NEW;
    else if (coinoneOrderInfoResponse.getStatus().equals("filled")) status = OrderStatus.FILLED;
    else if (coinoneOrderInfoResponse.getStatus().equals("partially_filled"))
      status = OrderStatus.PARTIALLY_FILLED;
    else status = OrderStatus.CANCELED;
    CoinoneOrderInfo orderInfo = coinoneOrderInfoResponse.getInfo();
    OrderType type = orderInfo.getType().equals("ask") ? OrderType.ASK : OrderType.BID;
    BigDecimal originalAmount = orderInfo.getQty();
    CurrencyPair currencyPair =
        CurrencyPair.build(Currency.valueOf(orderInfo.getCurrency().toUpperCase()), Currency.KRW);
    String orderId = orderInfo.getOrderId();
    BigDecimal cumulativeAmount = orderInfo.getQty().subtract(orderInfo.getRemainQty());
    BigDecimal price = orderInfo.getPrice();
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(orderInfo.getTimestamp());
    LimitOrder order =
        new LimitOrder(
            type,
            originalAmount,
            currencyPair,
            orderId,
            cal.getTime(),
            price,
            price,
            cumulativeAmount,
            orderInfo.getFee(),
            status);
    return order;
  }

  public static Wallet adaptWallet(CoinoneBalancesResponse coninoneResponse) {
    if (!"0".equals(coninoneResponse.getErrorCode())) {
      throw new CoinoneException(coninoneResponse.getResult());
    }
    List<Balance> balances = new ArrayList<>();
    BigDecimal total6 = new BigDecimal(coninoneResponse.getKrw().getBalance());
    BigDecimal available6 = new BigDecimal(coninoneResponse.getKrw().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("KRW"))
            .setTotal(total6)
            .setAvailable(available6)
            .setFrozen(total6.add(available6.negate()))
            .createBalance());
    BigDecimal total5 = new BigDecimal(coninoneResponse.getBch().getBalance());
    BigDecimal available5 = new BigDecimal(coninoneResponse.getBch().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("BCH"))
            .setTotal(total5)
            .setAvailable(available5)
            .setFrozen(total5.add(available5.negate()))
            .createBalance());
    BigDecimal total4 = new BigDecimal(coninoneResponse.getBtc().getBalance());
    BigDecimal available4 = new BigDecimal(coninoneResponse.getBtc().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("BTC"))
            .setTotal(total4)
            .setAvailable(available4)
            .setFrozen(total4.add(available4.negate()))
            .createBalance());
    BigDecimal total3 = new BigDecimal(coninoneResponse.getEtc().getBalance());
    BigDecimal available3 = new BigDecimal(coninoneResponse.getEtc().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("ETC"))
            .setTotal(total3)
            .setAvailable(available3)
            .setFrozen(total3.add(available3.negate()))
            .createBalance());
    BigDecimal total2 = new BigDecimal(coninoneResponse.getEth().getBalance());
    BigDecimal available2 = new BigDecimal(coninoneResponse.getEth().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("ETH"))
            .setTotal(total2)
            .setAvailable(available2)
            .setFrozen(total2.add(available2.negate()))
            .createBalance());
    BigDecimal total1 = new BigDecimal(coninoneResponse.getQtum().getBalance());
    BigDecimal available1 = new BigDecimal(coninoneResponse.getQtum().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("QTUM"))
            .setTotal(total1)
            .setAvailable(available1)
            .setFrozen(total1.add(available1.negate()))
            .createBalance());
    BigDecimal total = new BigDecimal(coninoneResponse.getXrp().getBalance());
    BigDecimal available = new BigDecimal(coninoneResponse.getXrp().getAvail());
    balances.add(
        new Balance.Builder()
            .setCurrency(Currency.valueOf("XRP"))
            .setTotal(total)
            .setAvailable(available)
            .setFrozen(total.add(available.negate()))
            .createBalance());
    return Wallet.build(balances);
  }

  public static Ticker adaptTicker(CoinoneTicker ticker) {
    CurrencyPair currencyPair =
        CurrencyPair.build(Currency.valueOf(ticker.getCurrency()), Currency.KRW);
    final Date date = DateUtils.fromMillisUtc(Long.valueOf(ticker.getTimestamp()) * 1000);
    return new Builder()
        .currencyPair(currencyPair)
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .last(ticker.getLast())
        .volume(ticker.getVolume())
        .open(ticker.getFirst())
        .timestamp(date)
        .build();
  }

  public static Trades adaptTrades(CoinoneTrades trades, CurrencyPair currencyPair) {
    if (!"0".equals(trades.getErrorCode())) {
      throw new CoinoneException(trades.getResult());
    }
    List<Trade> tradeList = new ArrayList<>(trades.getCompleteOrders().length);
    for (CoinoneTradeData trade : trades.getCompleteOrders()) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradeList, 0, TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(CoinoneTradeData trade, CurrencyPair currencyPair) {
    return new Trade(
        null,
        trade.getQty(),
        currencyPair,
        trade.getPrice(),
        DateUtils.fromMillisUtc(Long.valueOf(trade.getTimestamp()) * 1000),
        "");
  }
}
