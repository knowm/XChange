package info.bitrich.xchangestream.krakenfutures;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.krakenfutures.KrakenFuturesExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class KrakenFuturesStreamingPrivateDataTest {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingPrivateDataTest.class);
    StreamingExchange exchange;
    Instrument instrument = new FuturesContract("BTC/USD/PERP");

    @Before
    public void setUp(){
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/secret.keys"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Enter your authentication details here to run private endpoint tests
        final String API_KEY = (properties.getProperty("apiKey") == null) ? System.getenv("krakenfutures_apikey"): properties.getProperty("apiKey");
        final String SECRET_KEY = (properties.getProperty("secret") == null) ? System.getenv("krakenfutures_secretkey"): properties.getProperty("secret");

        ExchangeSpecification spec = new KrakenFuturesExchange().getDefaultExchangeSpecification();
        spec.setApiKey(API_KEY);
        spec.setSecretKey(SECRET_KEY);
        spec.setExchangeSpecificParametersItem(KrakenFuturesStreamingExchange.USE_SANDBOX, true);

        exchange = StreamingExchangeFactory.INSTANCE.createExchange(KrakenFuturesStreamingExchange.class);
        exchange.applySpecification(spec);
        exchange.connect().blockingAwait();

        InstrumentMetaData metaData = exchange.getExchangeMetaData().getInstruments().get(instrument);
        assertThat(metaData.getPriceScale()).isNotNull();
        assertThat(metaData.getVolumeScale()).isNotNull();
        assertThat(metaData.getMinimumAmount()).isNotNull();
    }

    @Test
    public void checkUserTrades() throws InterruptedException, IOException {
        int counter = 0;

        Disposable dis = exchange.getStreamingTradeService().getUserTrades(instrument)
                .retry()
                .subscribe(fill -> {
                    LOG.info(fill.toString());
                    assertThat(fill).isNotNull();
                    assertThat(fill.getInstrument()).isEqualTo(instrument);
                });

        Disposable dis2 = exchange.getStreamingTradeService().getUserTrades(new FuturesContract("ETH/USD/PERP"))
                .retry()
                .subscribe(fill -> {
                    LOG.info(fill.toString());
                    assertThat(fill).isNotNull();
                    assertThat(fill.getInstrument()).isEqualTo(new FuturesContract("ETH/USD/PERP"));
                });
        while (counter < 4){
            String orderId;
            if(counter == 3){
                orderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.ASK, new FuturesContract("ETH/USD/PERP"))
                        .originalAmount(BigDecimal.ONE)
                        .build());
            } else {
                orderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.BID, instrument)
                        .originalAmount(BigDecimal.ONE)
                        .build());
            }
            LOG.info("OrderId: "+orderId);
            counter++;
            TimeUnit.SECONDS.sleep(1);
        }
        dis.dispose();
        dis2.dispose();
    }

    @Test
    public void checkAllUserTrades() throws InterruptedException, IOException {
        int counter = 0;

        Disposable dis = exchange.getStreamingTradeService().getUserTrades()
                .map(fill->{
                    if(fill.getOrderUserReference().equals("2")){
                        throw new IOException("Error");
                    } else {
                        LOG.info(fill.toString());
                        assertThat(fill).isNotNull();
                    }
                    return fill;
                })
                .retry()
                .subscribe();

        while (counter < 5){
            String orderId;
            if(counter == 3){
                orderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.ASK, new FuturesContract("ETH/USD/PERP"))
                        .originalAmount(BigDecimal.valueOf(0.1))
                                .userReference(Integer.toString(counter))
                        .build());
            } else {
                orderId = exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(Order.OrderType.BID, instrument)
                        .originalAmount(BigDecimal.valueOf(0.1))
                        .userReference(Integer.toString(counter))
                        .build());
            }
            LOG.info("OrderId: "+orderId);
            counter++;
            TimeUnit.SECONDS.sleep(1);
        }
        TimeUnit.SECONDS.sleep(2);
        dis.dispose();
    }
}
