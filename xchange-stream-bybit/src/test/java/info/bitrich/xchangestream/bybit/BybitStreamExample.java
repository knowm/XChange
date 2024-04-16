package info.bitrich.xchangestream.bybit;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.instrument.Instrument;


public class BybitStreamExample {

  public static void main(String[] args) {
    try {
      spot();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    futures();
  }

  private static final Instrument BTC_PERP = new FuturesContract("BTC/USDT/PERP");
  private static final Instrument ETH_PERP = new CurrencyPair("ETH/USDT/PERP");
  private static final Instrument BTC_SPOT = new CurrencyPair("BTC/USDT");
  private static final Instrument ETH_SPOT = new CurrencyPair("ETH/USDT");

  public static void spot() throws IOException {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BybitStreamingExchange.class);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    exchangeSpecification.setExchangeSpecificParametersItem(BybitStreamingExchange.EXCHANGE_TYPE,
        BybitCategory.SPOT.getValue());
    exchangeSpecification.setExchangeSpecificParametersItem(SPECIFIC_PARAM_ACCOUNT_TYPE,
        BybitAccountType.UNIFIED);
    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(
        exchangeSpecification);
    exchange.connect().blockingAwait();
    int count_currencyPair = 0;
    int count_futureContractPerp = 0;
    int count_futureContractDate = 0;
    List<Instrument> instruments = new ArrayList<>(exchange
        .getExchangeMetaData()
        .getInstruments().keySet());
    for (Instrument instrument : instruments) {
      if (instrument instanceof CurrencyPair) {
        count_currencyPair++;
      }
      if (instrument instanceof FuturesContract) {
        count_futureContractPerp++;
      }
      if (instrument instanceof OptionsContract) {
        count_futureContractDate++;
      }
    }
    System.out.println("Currency pairs: " + count_currencyPair);
    System.out.println("Futures: " + count_futureContractPerp);
    System.out.println("Futures date: " + count_futureContractDate);

    System.out.println(exchange.getMarketDataService().getTicker(BTC_SPOT));
    System.out.println(exchange.getMarketDataService().getTicker(BTC_PERP));
    List<Disposable> tradesDisposable = new ArrayList<>();
    Disposable bookDisposable =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(ETH_SPOT)
            .subscribe();
    tradesDisposable.add(exchange
        .getStreamingMarketDataService().getTrades(ETH_SPOT).subscribe(
            trade -> System.out.println("trade: " + trade)));
    tradesDisposable.add(exchange
        .getStreamingMarketDataService().getTrades(BTC_SPOT).subscribe(
            trade -> System.out.println("trade: " + trade)));
    try {
      Thread.sleep(2000);
    } catch (
        InterruptedException ignored) {
    }
    bookDisposable.dispose();
    for (Disposable disposable : tradesDisposable) {
      disposable.dispose();
    }

    try {
      Thread.sleep(5000);
    } catch (
        InterruptedException ignored) {
    }
    exchange.disconnect().blockingAwait();
  }


  public static void futures() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BybitStreamingExchange.class);
    exchangeSpecification.setExchangeSpecificParametersItem(SPECIFIC_PARAM_ACCOUNT_TYPE,
        BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(BybitStreamingExchange.EXCHANGE_TYPE,
        BybitCategory.LINEAR.getValue());
    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(
        exchangeSpecification);
    exchange.connect().blockingAwait();
    List<Disposable> tradesDisposable = new ArrayList<>();
    List<Disposable> booksDisposable = new ArrayList<>();
    List<Disposable> booksUpdatesDisposable = new ArrayList<>();
    booksDisposable.add(exchange
        .getStreamingMarketDataService()
        .getOrderBook(BTC_PERP)
        .subscribe());
    booksDisposable.add(exchange
        .getStreamingMarketDataService()
        .getOrderBook(ETH_PERP)
        .subscribe());
    booksUpdatesDisposable.add(exchange
        .getStreamingMarketDataService()
        .getOrderBookUpdates(BTC_PERP)
        .subscribe(
            orderBookUpdates -> System.out.printf("orderBookUpdates: %s\n", orderBookUpdates)
        ));
    booksUpdatesDisposable.add(exchange
        .getStreamingMarketDataService()
        .getOrderBookUpdates(ETH_PERP)
        .subscribe(
            orderBookUpdates -> System.out.printf("orderBookUpdates: %s\n", orderBookUpdates)
        ));
    tradesDisposable.add(exchange
        .getStreamingMarketDataService().getTrades(BTC_PERP).subscribe(
            trade -> System.out.println("trade: " + trade)));
    tradesDisposable.add(exchange
        .getStreamingMarketDataService().getTrades(ETH_PERP).subscribe(
            trade -> System.out.println("trade: " + trade)));
    try {
      Thread.sleep(2000);
    } catch (InterruptedException ignored) {

    }
    for (Disposable disposable : booksUpdatesDisposable) {
      disposable.dispose();
    }
    for (Disposable disposable : booksDisposable) {
      disposable.dispose();
    }
    for (Disposable disposable : tradesDisposable) {
      disposable.dispose();
    }
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ignored) {

    }
    exchange.disconnect().blockingAwait();
  }

}
