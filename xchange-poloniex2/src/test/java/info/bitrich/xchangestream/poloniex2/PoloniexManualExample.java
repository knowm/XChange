package info.bitrich.xchangestream.poloniex2;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoloniexManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(PoloniexManualExample.class);

    public static void main(String[] args) {
        CurrencyPair usdtBtc = new CurrencyPair(new Currency("BTC"), new Currency("USDT"));
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(PoloniexStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

        exchange.getStreamingMarketDataService().getOrderBook(usdtBtc).subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

        exchange.getStreamingMarketDataService().getTicker(usdtBtc).subscribe(ticker -> {
            LOG.info("TICKER: {}", ticker);
        }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        exchange.getStreamingMarketDataService().getTrades(usdtBtc).subscribe(trade -> {
            LOG.info("TRADE: {}", trade);
        }, throwable -> LOG.error("ERROR in getting trades: ", throwable));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exchange.disconnect().blockingAwait();
    }
}
