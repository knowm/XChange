package org.knowm.xchange.examples.binance;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;

public class BinanceDemoUtils {

  public static Exchange createExchange() {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
    ExchangeSpecification binanceSpec = exchange.getDefaultExchangeSpecification();

    binanceSpec.setApiKey("");
    binanceSpec.setSecretKey("");
    binanceSpec.setExchangeSpecificParametersItem("Portfolio_Margin_Enabled", true);

    exchange.applySpecification(binanceSpec);

    return exchange;
  }
}
