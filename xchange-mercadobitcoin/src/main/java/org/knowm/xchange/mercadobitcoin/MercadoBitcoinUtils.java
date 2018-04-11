package org.knowm.xchange.mercadobitcoin;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

/** @author Felipe Micaroni Lalli */
public final class MercadoBitcoinUtils {

  private MercadoBitcoinUtils() {}

  /** Return something like <code>btc_brl:83948239</code> */
  public static String makeMercadoBitcoinOrderId(CurrencyPair currencyPair, String orderId) {

    String pair;

    if (currencyPair.equals(CurrencyPair.BTC_BRL)) {
      pair = "btc_brl";
    } else if (currencyPair.equals(new CurrencyPair(Currency.LTC, Currency.BRL))) {
      pair = "ltc_brl";
    } else {
      throw new NotAvailableFromExchangeException();
    }

    return pair + ":" + orderId;
  }

  /** @see #makeMercadoBitcoinOrderId(org.knowm.xchange.currency.CurrencyPair, String) */
  public static String makeMercadoBitcoinOrderId(LimitOrder limitOrder) {

    return makeMercadoBitcoinOrderId(limitOrder.getCurrencyPair(), limitOrder.getId());
  }
}
