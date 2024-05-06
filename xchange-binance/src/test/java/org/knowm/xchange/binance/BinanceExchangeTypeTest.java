package org.knowm.xchange.binance;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;
import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.ExchangeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;

public class BinanceExchangeTypeTest {
  public static void main(String[] args) throws InterruptedException, IOException {
    testConnection(new CurrencyPair("ETH/USDT"), SPOT, false);
    testConnection(new CurrencyPair("ETH/USDT"), SPOT, true);
    testConnection(new FuturesContract("ETH/USDT/PERP"), FUTURES, false);
    testConnection(new FuturesContract("ETH/USDT/PERP"), FUTURES, true);
  }

  private static void testConnection(Instrument instrument, ExchangeType exchangeType,
      boolean useSandbox)
      throws InterruptedException, IOException {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(getSpec(exchangeType, useSandbox));
//  another implementation
//    StreamingExchange exchange =
//        StreamingExchangeFactory.INSTANCE.createExchange(getSpec1(exchangeType, useSandbox));
    System.out.printf("trades %s%n", exchange.getMarketDataService().getTrades(instrument));
    System.out.printf("ticker %s%n", exchange.getMarketDataService().getTicker(instrument));
    Thread.sleep(500L);
  }

  private static ExchangeSpecification getSpec(ExchangeType exchangeType, boolean useSandbox) {
    ExchangeSpecification exchangeSpecification;
      exchangeSpecification = new BinanceExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, exchangeType);
    if (useSandbox) {
      exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    }
    return exchangeSpecification;
  }

  private static ExchangeSpecification getSpec1(ExchangeType exchangeType, boolean useSandbox) {
    ExchangeSpecification exchangeSpecification;
      exchangeSpecification =
          new ExchangeSpecification(BinanceExchange.class);
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, exchangeType);
    if (useSandbox) {
      exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    }
    return exchangeSpecification;
  }
}
