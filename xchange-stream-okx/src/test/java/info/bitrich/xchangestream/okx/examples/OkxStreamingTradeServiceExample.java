package info.bitrich.xchangestream.okx.examples;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.okx.OkxStreamingExchange;
import info.bitrich.xchangestream.okx.dto.enums.OkxInstType;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;

public class OkxStreamingTradeServiceExample {

    public static void main(String[] args) throws InterruptedException {
        // Enter your authentication details here to run private endpoint tests
        final String API_KEY = System.getenv("okx_apikey");
        final String SECRET_KEY = System.getenv("okx_secretkey");
        final String PASSPHRASE = System.getenv("okx_passphrase");

        ExchangeSpecification spec = new OkxStreamingExchange().getDefaultExchangeSpecification();
        spec.setApiKey(API_KEY);
        spec.setSecretKey(SECRET_KEY);
        spec.setExchangeSpecificParametersItem("passphrase", PASSPHRASE);

        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(OkxStreamingExchange.class);
        exchange.applySpecification(spec);

        exchange.connect().blockingAwait();

        Thread.sleep(3000);


        Instrument instrument = CurrencyPair.BTC_USDT;
        exchange.getStreamingTradeService().getUserTrades(
                instrument
                , OkxInstType.SPOT
                , OkexAdapters.adaptCurrencyPairId(instrument)
                , OkexAdapters.adaptInstrumentId(instrument)
        ).forEach(System.out::println);
    }
}
