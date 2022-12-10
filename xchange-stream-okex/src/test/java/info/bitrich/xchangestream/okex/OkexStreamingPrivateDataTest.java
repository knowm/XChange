package info.bitrich.xchangestream.okex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

import java.util.concurrent.TimeUnit;

@Ignore
public class OkexStreamingPrivateDataTest {

    StreamingExchange exchange;
    private final Instrument instrument = new FuturesContract("BTC/USDT/SWAP");

    @Before
    public void setUp() {
        // Enter your authentication details here to run private endpoint tests
        final String API_KEY = System.getenv("okx_apikey");
        final String SECRET_KEY = System.getenv("okx_secretkey");
        final String PASSPHRASE = System.getenv("okx_passphrase");

        ExchangeSpecification spec = new OkexStreamingExchange().getDefaultExchangeSpecification();
        spec.setApiKey(API_KEY);
        spec.setSecretKey(SECRET_KEY);
        spec.setExchangeSpecificParametersItem("passphrase", PASSPHRASE);

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
}
