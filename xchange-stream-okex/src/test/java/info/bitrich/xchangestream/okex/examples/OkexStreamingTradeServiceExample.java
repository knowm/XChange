package info.bitrich.xchangestream.okex.examples;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.okex.OkexStreamingExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public class OkexStreamingTradeServiceExample {

  public static void main(String[] args) throws InterruptedException {
    // Enter your authentication details here to run private endpoint tests
    final String API_KEY = System.getenv("okx_apikey");
    final String SECRET_KEY = System.getenv("okx_secretkey");
    final String PASSPHRASE = System.getenv("okx_passphrase");

    ExchangeSpecification spec = new OkexStreamingExchange().getDefaultExchangeSpecification();
    spec.setApiKey(API_KEY);
    spec.setSecretKey(SECRET_KEY);
    spec.setExchangeSpecificParametersItem("passphrase", PASSPHRASE);

    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(OkexStreamingExchange.class);
    exchange.applySpecification(spec);

    exchange.connect().blockingAwait();

    Thread.sleep(3000);

    Instrument instrument = CurrencyPair.BTC_USDT;
    exchange.getStreamingTradeService().getUserTrades(instrument).forEach(System.out::println);
  }
}
