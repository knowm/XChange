package org.knowm.xchange.cryptofacilities;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccount;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulatedBidAsk;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFill;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrder;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
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

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesAdapters {

  public static Ticker adaptTicker(CryptoFacilitiesTicker cryptoFacilitiesTicker, CurrencyPair currencyPair) {

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
    return new Currency(code);
  }

  public static AccountInfo adaptAccount(CryptoFacilitiesAccount cryptoFacilitiesAccount, String username) {

    List<Balance> balances = new ArrayList<Balance>(cryptoFacilitiesAccount.getBalances().size());
    Balance balance;

    for (Entry<String, BigDecimal> balancePair : cryptoFacilitiesAccount.getBalances().entrySet()) {
      if (balancePair.getKey().equalsIgnoreCase("xbt")) {
        // For xbt balance we construct both total=deposited xbt and available=total - margin balances
        balance = new Balance(Currency.BTC, balancePair.getValue(), cryptoFacilitiesAccount.getAuxiliary().get("af"));
      } else {
        Currency currency = adaptCurrency(balancePair.getKey());
        balance = new Balance(currency, balancePair.getValue());
      }
      balances.add(balance);
    }
    return new AccountInfo(username, new Wallet(balances));
  }

  public static String adaptOrderId(CryptoFacilitiesOrder order) {

    return order.getOrderId();

  }

  public static OrderType adaptOrderType(String cryptoFacilitiesOrderType) {

    return cryptoFacilitiesOrderType.equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static LimitOrder adaptLimitOrder(CryptoFacilitiesOpenOrder ord) {
    return new LimitOrder(adaptOrderType(ord.getDirection()), ord.getQuantity(),
        new CurrencyPair(new Currency(ord.getTradeable()), new Currency(ord.getUnit())), ord.getUid(), ord.getTimestamp(), ord.getLimitPrice());
  }

  public static OpenOrders adaptOpenOrders(CryptoFacilitiesOpenOrders orders) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    if (orders != null && orders.isSuccess()) {
      for (CryptoFacilitiesOpenOrder ord : orders.getOrders()) {
        // how to handle stop-loss orders?
        // ignore anything but a plain limit order for now
        if (ord.getType().equals("LMT")) {
          limitOrders.add(adaptLimitOrder(ord));
        }
      }
    }

    return new OpenOrders(limitOrders);

  }

  public static UserTrade adaptFill(CryptoFacilitiesFill fill) {
    return new UserTrade(adaptOrderType(fill.getSide()), fill.getSize(), new CurrencyPair(fill.getSymbol(), "USD"), fill.getPrice(),
        fill.getFillTime(), fill.getFillId(), fill.getOrderId(), null, (Currency) null);
  }

  public static UserTrades adaptFills(CryptoFacilitiesFills cryptoFacilitiesFills) {
    List<UserTrade> trades = new ArrayList<UserTrade>();

    if (cryptoFacilitiesFills != null && cryptoFacilitiesFills.isSuccess()) {
      for (CryptoFacilitiesFill fill : cryptoFacilitiesFills.getFills()) {
        trades.add(adaptFill(fill));
      }
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static LimitOrder adaptOrderBookOrder(CryptoFacilitiesCumulatedBidAsk cumulBidAsk, String direction, String tradeable, String unit) {
    LimitOrder order = new LimitOrder(adaptOrderType(direction), cumulBidAsk.getQuantity(),
        new CurrencyPair(new Currency(tradeable), new Currency(unit)), null, null, cumulBidAsk.getPrice());

    return order;
  }

  public static List<LimitOrder> adaptOrderBookSide(List<CryptoFacilitiesCumulatedBidAsk> cumulBidAsks, String direction, String tradeable,
      String unit) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CryptoFacilitiesCumulatedBidAsk cumulBidAsk : cumulBidAsks) {
      limitOrders.add(adaptOrderBookOrder(cumulBidAsk, direction, tradeable, unit));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(CryptoFacilitiesCumulativeBidAsk cumul) throws IOException {
    List<CryptoFacilitiesCumulatedBidAsk> cumulBids = cumul.getCumulatedBids();
    List<CryptoFacilitiesCumulatedBidAsk> cumulAsks = cumul.getCumulatedAsks();

    return new OrderBook(null, adaptOrderBookSide(cumulAsks, "Sell", "Forward", "USD"), adaptOrderBookSide(cumulBids, "Buy", "Forward", "USD"));
  }

  public static boolean adaptCryptoFacilitiesCancel(CryptoFacilitiesCancel cancel) {
    return cancel.isSuccess();
  }

}
