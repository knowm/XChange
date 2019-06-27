package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class KrakenManualExample {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenManualExample.class);

    public static void main(String[] args) throws InterruptedException {

        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(KrakenStreamingExchange.class);

        StreamingExchange krakenExchange = StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
        krakenExchange.connect().blockingAwait();

        Disposable btcEurOrderBookDis = krakenExchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_EUR, 100).subscribe(s -> {
            LOG.info("Received order book {}({},{}) {} ask[0] = {} bid[0] = {}", CurrencyPair.BTC_EUR, s.getAsks().size(), s.getBids().size(), s.getAsks().get(0), s.getBids().get(0));
        }, throwable -> {
            LOG.error("Order book FAILED {}", throwable.getMessage(), throwable);
        });
        Disposable btcUsdOrderBookDis = krakenExchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.LTC_BTC, 10).subscribe(s -> {
            LOG.info("Received order book {}({},{}) {} ask[0] = {} bid[0] = {}", CurrencyPair.LTC_BTC, s.getAsks().size(), s.getBids().size(), s.getAsks().get(0), s.getBids().get(0));
        }, throwable -> {
            LOG.error("Order book FAILED {}", throwable.getMessage(), throwable);
        });
        Disposable tickerDis = krakenExchange.getStreamingMarketDataService().getTicker(CurrencyPair.LTC_USD).subscribe(s -> {
            LOG.info("Received {}", s);
        }, throwable -> {
            LOG.error("Fail to get ticker {}", throwable.getMessage(), throwable);
        });

        Disposable tradeDis = krakenExchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(s -> {
            LOG.info("Received {}", s);
        }, throwable -> {
            LOG.error("Fail to get trade {}", throwable.getMessage(), throwable);
        });
        TimeUnit.SECONDS.sleep(5);

        btcEurOrderBookDis.dispose();
        btcUsdOrderBookDis.dispose();
        tickerDis.dispose();
        tradeDis.dispose();

        krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
    }

}
