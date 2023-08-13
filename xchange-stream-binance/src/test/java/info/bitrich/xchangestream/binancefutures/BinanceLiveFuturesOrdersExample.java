package info.bitrich.xchangestream.binancefutures;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceLiveFuturesOrdersExample {

  private static final Logger LOG = LoggerFactory.getLogger(
      BinanceLiveFuturesOrdersExample.class);

  public static void main(String[] args) throws InterruptedException {
    // Far safer than temporarily adding these to code that might get committed to VCS
    String apiKey = System.getProperty("binance-api-key");
    String apiSecret = System.getProperty("binance-api-secret");

    FuturesContract contract = new FuturesContract(CurrencyPair.ETH_USDT, "PERP");

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(BinanceFutureStreamingExchange.class)
            .getDefaultExchangeSpecification();

    spec.setApiKey(apiKey);
    spec.setSecretKey(apiSecret);

    BinanceFutureStreamingExchange exchange =
        (BinanceFutureStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    ProductSubscription subscription =
        ProductSubscription.create()
            .addOrders(contract)
            .build();

    exchange.connect(subscription).blockingAwait();

    Disposable orders = null;

    LOG.info("Subscribing authenticated channels");

    orders =
        exchange
            .getStreamingTradeService()
            .getOrderUpdate(contract)
            .subscribe(
                ord -> LOG.info("Order: {}", ord),
                e -> LOG.error("Error in orders stream", e));

    Thread.sleep(1000000);

    orders.dispose();

    exchange.disconnect().blockingAwait();
  }
}
