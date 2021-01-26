package info.bitrich.xchangestream.coinmate.v2;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmate.Coinmate;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinmateManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(CoinmateStreamingExchange.class);

  public static void main(String[] args) {

    ExchangeSpecification exSpec = new CoinmateStreamingExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("2985");
    exSpec.setApiKey("WRBBKkKPp43ST5yeUhfsba1E2ZikE8afQDSxDLCKSXg");
    exSpec.setSecretKey("wyhudaS7irE3MXTRR8F9FSxt6QKo0-Wn8GQSvgLiCXk");
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exSpec);
    exchange.connect().blockingAwait();

//    exchange
//        .getStreamingMarketDataService()
//        .getOrderBook(CurrencyPair.BTC_EUR)
//        .subscribe(
//            orderBook -> {
//              LOG.info("Ask: {}", orderBook.getAsks().get(0));
//              LOG.info("Bid: {}", orderBook.getBids().get(0));
//            });

            Disposable subscribe =
     exchange.getStreamingAccountService().getBalanceChanges(Currency.BTC).subscribe(trade -> {
                LOG.info("Trade {}", trade);
            });

    //        Disposable subscribe =
    // exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(trade -> {
    //            LOG.info("Trade {}", trade);
    //        });

    //        subscribe.dispose();

    //        exchange.disconnect().subscribe(() -> LOG.info("Disconnected from the Exchange"));

    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
