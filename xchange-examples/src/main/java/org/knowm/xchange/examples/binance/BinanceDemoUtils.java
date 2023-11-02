package org.knowm.xchange.examples.binance;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;

public class BinanceDemoUtils {

  public static Exchange createExchange() {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
    ExchangeSpecification binanceSpec = exchange.getDefaultExchangeSpecification();

    binanceSpec.setApiKey("SBv6nd0JBVtDDENXu7jkBVbsrGIleKK3skaYa9hYmrqlpup1vJWk06RF4IFEJc44");
    binanceSpec.setSecretKey("nbB6x4HDgLyjbEFij6OXe3fBfTGNYLUBPw1GiziVYjUVgKq5gKHHeXOLszd9iIWj");
    binanceSpec.setExchangeSpecificParametersItem("Portfolio_Margin_Enabled", true);

    exchange.applySpecification(binanceSpec);

    return exchange;
  }
}
