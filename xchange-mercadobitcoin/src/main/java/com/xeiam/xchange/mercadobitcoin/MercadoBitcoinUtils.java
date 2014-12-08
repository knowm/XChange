package com.xeiam.xchange.mercadobitcoin;

import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author Felipe Micaroni Lalli
 */
public final class MercadoBitcoinUtils {

  private MercadoBitcoinUtils() {

  }

  public static String getTonce() {

    return "" + (System.currentTimeMillis() / 1000L);
  }

  /**
   * Return something like <code>btc_brl:83948239</code>
   */
  public static String makeMercadoBitcoinOrderId(CurrencyPair currencyPair, String orderId) {

    String pair;

    if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
      pair = "btc_brl";
    }
    else if (currencyPair.equals(new CurrencyPair(Currencies.LTC, Currencies.BRL))) {
      pair = "ltc_brl";
    }
    else {
      throw new NotAvailableFromExchangeException();
    }

    return pair + ":" + orderId;
  }

  /**
   * @see #makeMercadoBitcoinOrderId(com.xeiam.xchange.currency.CurrencyPair, String)
   */
  public static String makeMercadoBitcoinOrderId(LimitOrder limitOrder) {

    return makeMercadoBitcoinOrderId(limitOrder.getCurrencyPair(), limitOrder.getId());
  }
}
