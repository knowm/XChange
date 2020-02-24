package org.knowm.xchange.cryptofacilities;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccount;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccountInfo;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccounts;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFill;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrder;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrderBook;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesAdapters {

  public static Ticker adaptTicker(
      CryptoFacilitiesTicker cryptoFacilitiesTicker, CurrencyPair currencyPair) {

    if (cryptoFacilitiesTicker != null) {
      Ticker.Builder builder = new Ticker.Builder();

      builder.ask(cryptoFacilitiesTicker.getAsk());
      builder.bid(cryptoFacilitiesTicker.getBid());
      builder.last(cryptoFacilitiesTicker.getLast());
      builder.currencyPair(currencyPair);
      builder.low(cryptoFacilitiesTicker.getLow24H());
      builder.high(cryptoFacilitiesTicker.getHigh24H());
      builder.volume(cryptoFacilitiesTicker.getVol24H());
      builder.timestamp(cryptoFacilitiesTicker.getLastTime());

      return builder.build();
    }
    return null;
  }

  public static Currency adaptCurrency(String code) {
    return Currency.getInstance(code);
  }

  public static AccountInfo adaptAccount(
      CryptoFacilitiesAccount cryptoFacilitiesAccount, String username) {

    List<Balance> balances = new ArrayList<>(cryptoFacilitiesAccount.getBalances().size());
    Balance balance;

    for (Entry<String, BigDecimal> balancePair : cryptoFacilitiesAccount.getBalances().entrySet()) {
      if (balancePair.getKey().equalsIgnoreCase("xbt")) {
        // For xbt balance we construct both total=deposited xbt and available=total - margin
        // balances
        balance =
            new Balance(
                Currency.BTC,
                balancePair.getValue(),
                cryptoFacilitiesAccount.getAuxiliary().get("af"));
      } else {
        Currency currency = adaptCurrency(balancePair.getKey());
        balance = new Balance(currency, balancePair.getValue());
      }
      balances.add(balance);
    }
    return new AccountInfo(username, Wallet.Builder.from(balances).build());
  }

  public static AccountInfo adaptAccounts(
      CryptoFacilitiesAccounts cryptoFacilitiesAccounts, String username) {

    Map<String, CryptoFacilitiesAccountInfo> accounts = cryptoFacilitiesAccounts.getAccounts();
    List<Wallet> wallets = new ArrayList<>();

    for (String accountName : accounts.keySet()) {
      List<Balance> balances = new ArrayList<>(accounts.get(accountName).getBalances().size());
      Balance balance;

      for (Entry<String, BigDecimal> balancePair :
          accounts.get(accountName).getBalances().entrySet()) {
        if (!accountName.equalsIgnoreCase("cash") && balancePair.getKey().equalsIgnoreCase("xbt")) {
          // For xbt balance we construct both total=deposited xbt and available=total - margin
          // balances
          balance =
              new Balance(
                  Currency.BTC,
                  balancePair.getValue(),
                  accounts.get(accountName).getAuxiliary().get("af"));
        } else {
          Currency currency = adaptCurrency(balancePair.getKey());
          balance = new Balance(currency, balancePair.getValue());
        }
        balances.add(balance);
      }

      wallets.add(Wallet.Builder.from(balances).id(accountName).name(accountName).build());
    }
    return new AccountInfo(username, wallets);
  }

  public static String adaptOrderId(CryptoFacilitiesOrder order) {

    return order.getOrderId();
  }

  public static OrderType adaptOrderType(String cryptoFacilitiesOrderType) {

    return cryptoFacilitiesOrderType.equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static OrderStatus adaptOrderStatus(String cryptoFacilitiesOrderStatus) {

    if (cryptoFacilitiesOrderStatus != null
        && cryptoFacilitiesOrderStatus.equalsIgnoreCase("untouched")) return OrderStatus.NEW;
    if (cryptoFacilitiesOrderStatus != null
        && cryptoFacilitiesOrderStatus.equalsIgnoreCase("partiallyFilled"))
      return OrderStatus.PARTIALLY_FILLED;

    return OrderStatus.PENDING_NEW;
  }

  public static LimitOrder adaptLimitOrder(CryptoFacilitiesOpenOrder ord) {
    return new LimitOrder(
        adaptOrderType(ord.getDirection()),
        ord.getQuantity(),
        new CurrencyPair(ord.getSymbol(), ord.getSymbol().substring(6, 9)),
        ord.getId(),
        ord.getTimestamp(),
        ord.getLimitPrice(),
        BigDecimal.ZERO,
        ord.getFilled(),
        null,
        adaptOrderStatus(ord.getStatus()));
  }

  public static OpenOrders adaptOpenOrders(CryptoFacilitiesOpenOrders orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();

    if (orders != null && orders.isSuccess()) {
      for (CryptoFacilitiesOpenOrder ord : orders.getOrders()) {
        // how to handle stop-loss orders?
        // ignore anything but a plain limit order for now
        if (ord.getType().equalsIgnoreCase("lmt")) {
          limitOrders.add(adaptLimitOrder(ord));
        }
      }
    }

    return new OpenOrders(limitOrders);
  }

  public static UserTrade adaptFill(CryptoFacilitiesFill fill) {
    return new UserTrade.Builder()
        .type(adaptOrderType(fill.getSide()))
        .originalAmount(fill.getSize())
        .currencyPair(new CurrencyPair(fill.getSymbol(), fill.getSymbol().substring(6, 9)))
        .price(fill.getPrice())
        .timestamp(fill.getFillTime())
        .id(fill.getFillId())
        .orderId(fill.getOrderId())
        .build();
  }

  public static UserTrades adaptFills(CryptoFacilitiesFills cryptoFacilitiesFills) {
    List<UserTrade> trades = new ArrayList<>();

    if (cryptoFacilitiesFills != null && cryptoFacilitiesFills.isSuccess()) {
      for (CryptoFacilitiesFill fill : cryptoFacilitiesFills.getFills()) {
        trades.add(adaptFill(fill));
      }
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static OrderBook adaptOrderBook(CryptoFacilitiesOrderBook cryptoFacilitiesOrderBook) {

    List<LimitOrder> asks =
        createOrders(
            cryptoFacilitiesOrderBook.getCurrencyPair(),
            Order.OrderType.ASK,
            cryptoFacilitiesOrderBook.getAsks());
    List<LimitOrder> bids =
        createOrders(
            cryptoFacilitiesOrderBook.getCurrencyPair(),
            Order.OrderType.BID,
            cryptoFacilitiesOrderBook.getBids());
    return new OrderBook(cryptoFacilitiesOrderBook.getServerTime(), asks, bids);
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

  public static boolean adaptCryptoFacilitiesCancel(CryptoFacilitiesCancel cancel) {
    return cancel.isSuccess();
  }
}
