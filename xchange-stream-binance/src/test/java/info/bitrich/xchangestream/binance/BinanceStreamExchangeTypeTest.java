package info.bitrich.xchangestream.binance;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;
import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.ExchangeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

public class BinanceStreamExchangeTypeTest {

  public static void main(String[] args) throws InterruptedException, IOException {
    testConnection(new CurrencyPair("ETH/USDT"), SPOT, false);
    testConnection(new CurrencyPair("ETH/USDT"), SPOT, true);
    testConnection(new FuturesContract("ETH/USDT/PERP"), FUTURES, false);
    testConnection(new FuturesContract("ETH/USDT/PERP"), FUTURES, true);
  }

  private static void testConnection(Instrument instrument, ExchangeType exchangeType,
      boolean useSandbox)
      throws InterruptedException, IOException {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(getSpec(exchangeType, useSandbox));
//  another implementation
//    StreamingExchange exchange =
//        StreamingExchangeFactory.INSTANCE.createExchange(getSpec1(exchangeType, useSandbox));
    ProductSubscription subscription = ProductSubscription.create()
        .addOrderbook(instrument)
        .addTicker(instrument)
        .addFundingRates(instrument)
        .addTrades(instrument)
        .build();
    exchange.connect(subscription).blockingAwait();
    System.out.printf("trades %s%n", exchange.getMarketDataService().getTrades(instrument));
    Disposable disposable = exchange.getStreamingMarketDataService().getTicker(instrument)
        .subscribe(t -> System.out.printf("last price: %s%n", t.getLast()));
    Thread.sleep(3000L);
    disposable.dispose();
    Thread.sleep(500L);
    exchange.disconnect().blockingAwait();
  }

  private static ExchangeSpecification getSpec(ExchangeType exchangeType, boolean useSandbox) {
    ExchangeSpecification exchangeSpecification;
    if (exchangeType == SPOT) {
      exchangeSpecification = new BinanceStreamingExchange().getDefaultExchangeSpecification();
    } else {
      exchangeSpecification = new BinanceFutureStreamingExchange().getDefaultExchangeSpecification();
    }
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, exchangeType);
    if (useSandbox) {
      exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    }
    return exchangeSpecification;
  }

  private static ExchangeSpecification getSpec1(ExchangeType exchangeType, boolean useSandbox) {
    ExchangeSpecification exchangeSpecification;
    if (exchangeType == SPOT) {
      exchangeSpecification =
          new ExchangeSpecification(BinanceStreamingExchange.class);
    } else {
      exchangeSpecification =
          new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    }
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, exchangeType);
    if (useSandbox) {
      exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    }
    return exchangeSpecification;
  }
}
