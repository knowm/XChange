package org.knowm.xchange.examples.binance;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.utils.AuthUtils;

public class BinanceDemoUtils {

  public static Exchange createExchange() {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
    ExchangeSpecification binanceSpec = exchange.getDefaultExchangeSpecification();
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(binanceSpec, "binance-demo-futures");
    binanceSpec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    binanceSpec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    binanceSpec.setExchangeSpecificParametersItem("Portfolio_Margin_Enabled", true);
    exchange.applySpecification(binanceSpec);

    return exchange;
  }
}
