package org.knowm.xchange.examples.poloniex.marketdata;

import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.utils.CertHelper;

/** Retrieves and prints information about supported currency pairs. */
public class PoloniexExchangeInfoDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());

    final Map<CurrencyPair, CurrencyPairMetaData> currencyPairs =
        poloniex.getExchangeMetaData().getCurrencyPairs();

    for (CurrencyPair pair : currencyPairs.keySet()) {
      final CurrencyPairMetaData pairMetaData = currencyPairs.get(pair);
      System.out.printf("%s: %s%nn", pair, pairMetaData);
    }
  }
}
