package org.knowm.xchange.examples.paymium;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.paymium.PaymiumExchange;
import org.knowm.xchange.paymium.service.PaymiumMarketDataServiceRaw;

/** @author ObsessiveOrange */
public class PaymiumMarketDataExample {

  public static void main(String[] args) throws IOException {

    Exchange btcCentralExchange =
        ExchangeFactory.INSTANCE.createExchange(PaymiumExchange.class.getName());
    PaymiumMarketDataServiceRaw btcCentralMarketDataServiceRaw =
        (PaymiumMarketDataServiceRaw) btcCentralExchange.getMarketDataService();

    System.out.println(btcCentralMarketDataServiceRaw.getPaymiumTicker());
    System.out.println("\n");

    System.out.println(btcCentralMarketDataServiceRaw.getPaymiumMarketDepth());
    System.out.println("\n");

    System.out.println(Arrays.toString(btcCentralMarketDataServiceRaw.getPaymiumTrades()));
    System.out.println("\n");
  }
}
