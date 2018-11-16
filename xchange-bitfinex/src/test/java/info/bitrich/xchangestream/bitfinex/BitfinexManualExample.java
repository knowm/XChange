package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.utils.CertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(BitfinexManualExample.class);

    public static void main(String[] args) throws Exception {
        CertHelper.trustAllCerts();
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitfinexStreamingExchange.class
                .getName());

        ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();

//        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);
        defaultExchangeSpecification.setProxyHost("localhost");
        defaultExchangeSpecification.setProxyPort(9999);

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_HOST, "localhost");
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_PORT, 8889);

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ACCEPT_ALL_CERITICATES, true);
//        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ENABLE_LOGGING_HANDLER, true);

        exchange.applySpecification(defaultExchangeSpecification);


        exchange.connect().blockingAwait();

        Observable<OrderBook> orderBookObserver = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD);
        Disposable orderBookSubscriber = orderBookObserver.subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> {
            LOG.error("ERROR in getting order book: ", throwable);
        });


        Disposable tickerSubscriber = exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_EUR).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        Disposable tradesSubscriber = exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD)
                .subscribe(trade -> {
                    LOG.info("TRADE: {}", trade);
                }, throwable -> {
                    LOG.error("ERROR in getting trade: ", throwable);
                });

        Thread.sleep(10000);

        tickerSubscriber.dispose();
        tradesSubscriber.dispose();
        orderBookSubscriber.dispose();

        LOG.info("disconnecting...");
        exchange.disconnect().subscribe(() -> LOG.info("disconnected"));
    }
}
