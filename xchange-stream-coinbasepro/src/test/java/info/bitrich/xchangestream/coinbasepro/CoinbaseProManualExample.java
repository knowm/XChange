package info.bitrich.xchangestream.coinbasepro;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProManualExample.class);

  public static void main(String[] args) {

    // Far safer than temporarily adding these to code that might get committed to VCS
    String apiKey = System.getProperty("coinbasepro-api-key");
    String apiSecret = System.getProperty("coinbasepro-api-secret");
    String apiPassphrase = System.getProperty("coinbasepro-api-passphrase");

    ProductSubscription productSubscription =
        ProductSubscription.create()
            .addTicker(CurrencyPair.ETH_USD)
            .addOrders(CurrencyPair.LTC_EUR)
            .addOrderbook(CurrencyPair.BTC_USD)
            .addTrades(CurrencyPair.BTC_USD)
            .addUserTrades(CurrencyPair.LTC_EUR)
            .build();

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(CoinbaseProStreamingExchange.class.getName())
            .getDefaultExchangeSpecification();
    spec.setApiKey(apiKey);
    spec.setSecretKey(apiSecret);
    spec.setExchangeSpecificParametersItem("passphrase", apiPassphrase);
    CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect(productSubscription).blockingAwait();

    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_USD)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.ETH_USD)
        .subscribe(
            ticker -> {
              LOG.info("TICKER: {}", ticker);
            },
            throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.BTC_USD)
        .subscribe(
            trade -> {
              LOG.info("TRADE: {}", trade);
            },
            throwable -> LOG.error("ERROR in getting trades: ", throwable));

    if (StringUtils.isNotEmpty(apiKey)) {

      exchange
          .getStreamingMarketDataService()
          .getRawWebSocketTransactions(CurrencyPair.LTC_EUR, false)
          .subscribe(
              transaction -> {
                LOG.info("RAW WEBSOCKET TRANSACTION: {}", transaction);
              },
              throwable -> LOG.error("ERROR in getting raw websocket transactions: ", throwable));

      exchange
          .getStreamingTradeService()
          .getUserTrades(CurrencyPair.LTC_EUR)
          .subscribe(
              trade -> {
                LOG.info("USER TRADE: {}", trade);
              },
              throwable -> LOG.error("ERROR in getting user trade: ", throwable));

      exchange
          .getStreamingTradeService()
          .getOrderChanges(CurrencyPair.LTC_EUR)
          .subscribe(
              order -> {
                LOG.info("USER ORDER: {}", order);
              },
              throwable -> LOG.error("ERROR in getting user orders: ", throwable));
    }

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    exchange.disconnect().blockingAwait();
  }
}
