package com.xeiam.xchange.ripple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.ripple.dto.RippleAmount;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;
import com.xeiam.xchange.ripple.dto.account.RippleBalance;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrder;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrders;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrdersBody;
import com.xeiam.xchange.ripple.service.polling.params.RippleMarketDataParams;

/**
 * Various adapters for converting from Ripple DTOs to XChange DTOs
 */
public abstract class RippleAdapters {

  /**
   * private Constructor
   */
  private RippleAdapters() {
  }

  /**
   * Adapts a Ripple Account to an XChange AccountInfo object.
   * <p>
   * Counterparties are added to symbol since there is no other way of the application receiving this information.
   */
  public static AccountInfo adaptAccountInfo(final RippleAccount account, String username) {
    // Adapt account balances to XChange wallets
    final Map<String, Wallet> wallets = new TreeMap<String, Wallet>();
    for (final RippleBalance balance : account.getBalances()) {
      final String currency;
      if (balance.getCurrency().equals(Currencies.XRP)) {
        currency = balance.getCurrency();
      } else {
        currency = String.format("%s.%s", balance.getCurrency(), balance.getCounterparty());
      }
      final Wallet wallet = new Wallet(currency, balance.getValue());
      wallets.put(wallet.getCurrency(), wallet);
    }
    return new AccountInfo(username, BigDecimal.ZERO, wallets);
  }

  /**
   * Adapts a Ripple OrderBook to an XChange OrderBook object.
   * <p>
   * Counterparties are not mapped since the application calling this should know and keep track of the counterparties it is using in the polling
   * thread.
   */
  public static OrderBook adaptOrderBook(final RippleOrderBook rippleOrderBook, final RippleMarketDataParams params, final CurrencyPair currencyPair) {
    final String orderBook = rippleOrderBook.getOrderBook(); // e.g. XRP/BTC+rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B
    final String[] splitPair = orderBook.split("/");

    final String[] baseSplit = splitPair[0].split("\\+");
    final String baseSymbol = baseSplit[0];
    if (baseSymbol.equals(currencyPair.baseSymbol) == false) {
      throw new IllegalStateException(String.format("base symbol in Ripple order book %s does not match requested base %s", orderBook, currencyPair));
    }
    final String baseCounterparty;
    if (baseSymbol.equals(Currencies.XRP)) {
      baseCounterparty = ""; // native currency
    } else {
      baseCounterparty = baseSplit[1];
    }
    if (baseCounterparty.equals(params.getBaseCounterparty()) == false) {
      throw new IllegalStateException(String.format("base counterparty in Ripple order book %s does not match requested counterparty %s", orderBook,
          params.getBaseCounterparty()));
    }

    final String[] counterSplit = splitPair[1].split("\\+");
    final String counterSymbol = counterSplit[0];
    if (counterSymbol.equals(currencyPair.counterSymbol) == false) {
      throw new IllegalStateException(String.format("counter symbol in Ripple order book %s does not match requested base %s", orderBook,
          currencyPair));
    }
    final String counterCounterparty;
    if (counterSymbol.equals(Currencies.XRP)) {
      counterCounterparty = ""; // native currency
    } else {
      counterCounterparty = counterSplit[1];
    }
    if (counterCounterparty.equals(params.getCounterCounterparty()) == false) {
      throw new IllegalStateException(String.format("counter counterparty in Ripple order book %s does not match requested counterparty %s",
          orderBook, params.getCounterCounterparty()));
    }

    final List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, rippleOrderBook.getBids(), baseCounterparty, counterCounterparty);
    final List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, rippleOrderBook.getAsks(), baseCounterparty, counterCounterparty);
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(final CurrencyPair currencyPair, final OrderType orderType, final List<RippleOrder> orders,
      final String baseCounterparty, final String counterCounterparty) {
    final List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (final RippleOrder rippleOrder : orders) {

      // Taker Pays = the amount the taker must pay to consume this order.
      // Taker Gets = the amount the taker will get once the order is consumed.
      //
      // Funded vs Unfunded https://wiki.ripple.com/Unfunded_offers

      // amount of base currency
      final BigDecimal tradableAmount;
      if (orderType == OrderType.BID) {
        tradableAmount = rippleOrder.getTakerPaysFunded().getValue();
      } else {
        tradableAmount = rippleOrder.getTakerGetsFunded().getValue();
      }

      // price in counter currency
      final BigDecimal price = rippleOrder.getPrice().getValue();

      final LimitOrder order = new LimitOrder(orderType, tradableAmount, currencyPair, Integer.toString(rippleOrder.getSequence()), null,
          price);
      if (baseCounterparty.length() > 0) {
        order.putAdditionalData(RippleExchange.DATA_BASE_COUNTERPARTY, baseCounterparty);
      }
      if (counterCounterparty.length() > 0) {
        order.putAdditionalData(RippleExchange.DATA_COUNTER_COUNTERPARTY, counterCounterparty);
      }
      limitOrders.add(order);
    }
    return limitOrders;
  }

  /**
   * Adapts a Ripple Account Orders object to an XChange OpenOrders object
   * <p>
   * Counterparties set in additional data since there is no other way of the application receiving this information.
   */
  public static OpenOrders adaptOpenOrders(final RippleAccountOrders rippleOrders) {
    final List<LimitOrder> list = new ArrayList<LimitOrder>();
    for (final RippleAccountOrdersBody order : rippleOrders.getOrders()) {

      final OrderType orderType;

      final RippleAmount baseAmount;
      final RippleAmount counterAmount;
      if (order.getType().equals("buy")) {
        // buying: we receive base and pay with counter, taker receives counter and pays with base
        counterAmount = order.getTakerGets();
        baseAmount = order.getTakerPays();
        orderType = OrderType.BID;
      } else {
        // selling: we receive counter and pay with base, taker receives base and pays with counter 
        baseAmount = order.getTakerGets();
        counterAmount = order.getTakerPays();
        orderType = OrderType.ASK;
      }

      final String baseSymbol = baseAmount.getCurrency();
      final String counterSymbol = counterAmount.getCurrency();

      final BigDecimal price = counterAmount.getValue().divide(baseAmount.getValue());
      final CurrencyPair pair = new CurrencyPair(baseSymbol, counterSymbol);

      final LimitOrder xchangeOrder = new LimitOrder.Builder(orderType, pair).id(Long.toString(order.getSequence())).limitPrice(price)
          .timestamp(null).tradableAmount(baseAmount.getValue()).build();
      if (baseSymbol.equals(Currencies.XRP) == false) {
        xchangeOrder.putAdditionalData(RippleExchange.DATA_BASE_COUNTERPARTY, baseAmount.getCounterparty());
      }
      if (counterSymbol.equals(Currencies.XRP) == false) {
        xchangeOrder.putAdditionalData(RippleExchange.DATA_COUNTER_COUNTERPARTY, counterAmount.getCounterparty());
      }
      list.add(xchangeOrder);
    }

    return new OpenOrders(list);
  }
}
