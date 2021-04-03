package info.bitrich.xchangestream.dydx;

import static org.knowm.xchange.dydx.dydxExchange.V1;
import static org.knowm.xchange.dydx.dydxExchange.V3;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class dydxManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(dydxManualExample.class);

  public static void main(String[] args) throws Exception {
    // Layer-2 Perpetual Subscriptions
    ProductSubscription productSubscriptionV3 =
        ProductSubscription.create()
            .addOrderbook(CurrencyPair.ETH_USD)
            .addOrderbook(CurrencyPair.BTC_USD)
            .addOrderbook(CurrencyPair.LINK_USD)
            .build();

    // Layer-1 Spot and Margin Subscriptions
    ProductSubscription productSubscriptionV1 =
        ProductSubscription.create()
            .addOrderbook(CurrencyPair.WETH_USDC)
            .addOrderbook(CurrencyPair.WETH_DAI)
            .build();

    ExchangeSpecification specV3 =
        StreamingExchangeFactory.INSTANCE
            .createExchange(dydxStreamingExchange.class)
            .getDefaultExchangeSpecification();
    specV3.setExchangeSpecificParametersItem("version", V3);

    ExchangeSpecification specV1 =
        StreamingExchangeFactory.INSTANCE
            .createExchange(dydxStreamingExchange.class)
            .getDefaultExchangeSpecification();
    specV1.setExchangeSpecificParametersItem("version", V1);

    dydxStreamingExchange exchangeV3 =
        (dydxStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(specV3);

    dydxStreamingExchange exchangeV1 =
        (dydxStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(specV1);

    exchangeV3.connect(productSubscriptionV3).blockingAwait();
    exchangeV1.connect(productSubscriptionV1).blockingAwait();

    exchangeV3
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.ETH_USD)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    exchangeV3
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_USD)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> LOG.error("ERROR in getting order book: ", throwable));

    /*
    exchangeV1
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.WETH_USDC)
            .subscribe(
                    orderBook -> {
                        LOG.info("First ask: {}", orderBook.getAsks().get(0));
                        LOG.info("First bid: {}", orderBook.getBids().get(0));
                    },
                    throwable -> LOG.error("ERROR in getting order book: ", throwable));

    exchangeV1
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.WETH_DAI)
            .subscribe(
                    orderBook -> {
                        LOG.info("First ask: {}", orderBook.getAsks().get(0));
                        LOG.info("First bid: {}", orderBook.getBids().get(0));
                    },
                    throwable -> LOG.error("ERROR in getting order book: ", throwable));

     */
  }
}
