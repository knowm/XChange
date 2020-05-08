package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.CertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexAuthenticatedExample {

  private static final Logger LOG = LoggerFactory.getLogger(BitmexAuthenticatedExample.class);

  public static void main(String[] args) throws Exception {
    CertHelper.trustAllCerts();
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(BitmexStreamingExchange.class.getName());
    ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();
    //
    // defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_HOST, "localhost");
    //
    // defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_PORT, 8889);

    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.USE_SANDBOX, true);
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.ACCEPT_ALL_CERITICATES, true);
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.ENABLE_LOGGING_HANDLER, true);

    defaultExchangeSpecification.setApiKey("API-KEY");
    defaultExchangeSpecification.setSecretKey("SECRET-KEY");

    exchange.applySpecification(defaultExchangeSpecification);
    exchange.connect().blockingAwait();
    final BitmexStreamingMarketDataService streamingMarketDataService =
        (BitmexStreamingMarketDataService) exchange.getStreamingMarketDataService();
    //        streamingMarketDataService.authenticate();
    CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
    /* streamingMarketDataService.getOrderBook(xbtUsd).subscribe(orderBook -> {
                if(!orderBook.getAsks().isEmpty())
                LOG.info("First ask: {}", orderBook.getAsks());
                if(!orderBook.getBids().isEmpty())
                LOG.info("First bid: {}", orderBook.getBids());
            }, throwable -> LOG.error("ERROR in getting order book: ", throwable));

            streamingMarketDataService.getRawTicker(xbtUsd).subscribe(ticker -> {
                LOG.info("TICKER: {}", ticker);
            }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

            streamingMarketDataService.getTicker(xbtUsd).subscribe(ticker -> {
                LOG.info("TICKER: {}", ticker);
            }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));
    */
    /* streamingMarketDataService.getTrades(xbtUsd)
    .subscribe(trade -> LOG.info("TRADE: {}", trade),
            throwable -> LOG.error("ERROR in getting trades: ", throwable));*/
    streamingMarketDataService
        .getRawExecutions("XBTUSD")
        .subscribe(
            bitmexExecution -> {
              LOG.info("bitmexExecution = {}", bitmexExecution);
            });
    try {
      Thread.sleep(100_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
