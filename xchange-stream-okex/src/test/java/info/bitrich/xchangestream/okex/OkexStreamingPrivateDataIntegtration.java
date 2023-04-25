package info.bitrich.xchangestream.okex;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexExchange;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;

@Ignore
public class OkexStreamingPrivateDataIntegtration {

    StreamingExchange exchange;
    private final Instrument instrument = new FuturesContract("BTC/USDT/SWAP");

    @Before
    public void setUp() {
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/secret.keys"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Enter your authentication details here to run private endpoint tests
        final String API_KEY = (properties.getProperty("apikey") == null) ? System.getenv("okx_apikey"): properties.getProperty("apikey");
        final String SECRET_KEY = (properties.getProperty("secret") == null) ? System.getenv("okx_secretkey"): properties.getProperty("secret");
        final String PASSPHRASE = (properties.getProperty("passphrase") == null) ? System.getenv("okx_passphrase"): properties.getProperty("passphrase");

        ExchangeSpecification spec = new OkexStreamingExchange().getDefaultExchangeSpecification();
        spec.setApiKey(API_KEY);
        spec.setSecretKey(SECRET_KEY);
        spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_PASSPHRASE, PASSPHRASE);
        spec.setExchangeSpecificParametersItem(OkexExchange.USE_SANDBOX, true);
        spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_SIMULATED,"1");

        exchange = StreamingExchangeFactory.INSTANCE.createExchange(OkexStreamingExchange.class);
        exchange.applySpecification(spec);

        exchange.connect().blockingAwait();
    }

    @Test
    public void checkUserTradesStream() throws InterruptedException {
        Disposable dis = exchange.getStreamingTradeService().getUserTrades(instrument).subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(3);

        dis.dispose();
    }

    @Test
    public void checkPositionsStream() throws InterruptedException {
        OkexStreamingExchange okexStreamingExchange = (OkexStreamingExchange) exchange;
        Disposable dis = okexStreamingExchange.getStreamingPositionService()
                                              .getPositions(instrument)
                                              .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(3);

        dis.dispose();
    }

}
