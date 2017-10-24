package org.knowm.xchange.vircurex;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.vircurex.dto.account.VircurexAccountInfoReturn;
import org.knowm.xchange.vircurex.dto.trade.VircurexOpenOrder;

/**
 * Various adapters for converting from Vircurex DTOs to XChange DTOs
 */
public final class VircurexAdapters {

  private static SimpleDateFormat vircurexDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  /**
   * private Constructor
   */
  private VircurexAdapters() {

  }

  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, String id) {

    // place a limit order
    return new LimitOrder(orderType, amount, currencyPair, "", null, price);
  }

  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> someOrders, CurrencyPair currencyPair, String orderTypeString, String id) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    for (BigDecimal[] order : someOrders) {
      limitOrders.add(adaptOrder(order[1], order[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  public static AccountInfo adaptAccountInfo(VircurexAccountInfoReturn vircurexAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    Map<String, Map<String, BigDecimal>> funds = vircurexAccountInfo.getAvailableFunds();

    for (String lcCurrency : funds.keySet()) {
      Currency currency = Currency.getInstance(lcCurrency.toUpperCase());
      // TODO does vircurex offer total balance as well? the api page lists two output keys
      balances.add(new Balance(currency, null, funds.get(lcCurrency).get("availablebalance")));
    }
    return new AccountInfo(new Wallet(balances));
  }

  public static List<LimitOrder> adaptOpenOrders(List<VircurexOpenOrder> openOrders) {

    ArrayList<LimitOrder> adaptedOrders = new ArrayList<>();

    for (VircurexOpenOrder vircurexOpenOrder : openOrders) {

      OrderType orderType = vircurexOpenOrder.getOrderType().equalsIgnoreCase(VircurexUtils.BID) ? OrderType.BID : OrderType.ASK;

      Date timeStamp;

      try {
        timeStamp = vircurexDateFormat.parse(vircurexOpenOrder.getReleaseDate());
      } catch (ParseException e) {
        timeStamp = null;
      }

      adaptedOrders.add(
          new LimitOrder(orderType, BigDecimal.ONE, new CurrencyPair(vircurexOpenOrder.getBaseCurrency(), vircurexOpenOrder.getCounterCurrency()),
              vircurexOpenOrder.getOrderId(), timeStamp, vircurexOpenOrder.getUnitPrice()));
    }

    return adaptedOrders;
  }
}
