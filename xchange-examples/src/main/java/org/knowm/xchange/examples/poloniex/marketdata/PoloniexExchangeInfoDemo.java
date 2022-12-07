package org.knowm.xchange.examples.poloniex.marketdata;

import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.utils.CertHelper;

/** Retrieves and prints information about supported currency pairs. */
public class PoloniexExchangeInfoDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class);

    final Map<Instrument, InstrumentMetaData> currencyPairs =
        poloniex.getExchangeMetaData().getInstruments();

    for (Instrument pair : currencyPairs.keySet()) {
      final InstrumentMetaData pairMetaData = currencyPairs.get(pair);
      System.out.printf("%s: %s%nn", pair, pairMetaData);
    }
  }
}
