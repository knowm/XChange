package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

@Slf4j
public class GateioManualExample {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(GateioStreamingExchange.class)
            .getDefaultExchangeSpecification();

    GateioStreamingExchange exchange =
        (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect().blockingAwait();

    Disposable sub1 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.ETH_USDT)
            .subscribe(
                orderBook -> {
                  log.info("First ask: {}", orderBook.getAsks().get(0));
                  log.info("First bid: {}", orderBook.getBids().get(0));
                },
                throwable -> log.error("ERROR in getting order book: ", throwable));
    Disposable sub2 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USDT)
            .subscribe(
                orderBook -> {
                  log.info("First ask: {}", orderBook.getAsks().get(0));
                  log.info("First bid: {}", orderBook.getBids().get(0));
                },
                throwable -> log.error("ERROR in getting order book: ", throwable));
    Disposable sub3 =
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USDT)
            .subscribe(
                trade -> {
                  log.info("Trade Price: {}", trade.getPrice());
                  log.info("Trade Amount: {}", trade.getOriginalAmount());
                },
                throwable -> log.error("ERROR in getting trade: ", throwable));

    Thread.sleep(1000);
    sub1.dispose();
    sub2.dispose();
    sub3.dispose();
  }
}
