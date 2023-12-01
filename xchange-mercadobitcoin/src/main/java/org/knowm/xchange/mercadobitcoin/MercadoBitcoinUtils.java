package org.knowm.xchange.mercadobitcoin;

import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

/**
 * @author Felipe Micaroni Lalli
 */
public final class MercadoBitcoinUtils {

  public static final List<CurrencyPair> availablePairs =
      Arrays.asList(
          CurrencyPair.BTC_BRL,
          new CurrencyPair(Currency.LTC, Currency.BRL),
          new CurrencyPair(Currency.BCH, Currency.BRL));

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

  /**
   * @see #makeMercadoBitcoinOrderId(org.knowm.xchange.currency.CurrencyPair, String)
   */
  public static String makeMercadoBitcoinOrderId(LimitOrder limitOrder) {

    return makeMercadoBitcoinOrderId(limitOrder.getCurrencyPair(), limitOrder.getId());
  }

  public static void verifyCurrencyPairAvailability(CurrencyPair currencyPair) {

    if (!availablePairs.contains(currencyPair)) {
      throw new NotAvailableFromExchangeException();
    }
  }
}
