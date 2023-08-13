package info.bitrich.xchangestream.binancefutures;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceLiveFuturesUserDataExample {

  private static final Logger LOG = LoggerFactory.getLogger(
      BinanceLiveFuturesUserDataExample.class);

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

    Disposable account = null;
    Disposable balances = null;
    Disposable positions = null;

    LOG.info("Subscribing authenticated channels");

    account =
        exchange
            .getStreamingAccountService()
            .getAccountUpdates()
            .subscribe(
                acc -> LOG.info("Account: {}", acc),
                e -> LOG.error("Error in account stream", e));

    balances =
        exchange
            .getStreamingAccountService()
            .getBalanceChanges(new Currency("USDT"))
            .subscribe(
                balance -> LOG.info("Balance: {}", balance),
                e -> LOG.error("Error in balance stream", e));

    positions =
        exchange
            .getStreamingAccountService()
            .getPositionChanges(contract)
            .subscribe(
                position -> LOG.info("Position: {}", position),
                e -> LOG.error("Error in position stream", e));

    Thread.sleep(1000000);

    balances.dispose();
    account.dispose();
    positions.dispose();

    exchange.disconnect().blockingAwait();
  }
}
