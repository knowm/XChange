package info.bitrich.xchangestream.bybit;

import static java.math.RoundingMode.UP;
import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BybitStreamExample {
  private static final Logger log = LoggerFactory.getLogger(BybitStreamExample.class);

  public static void main(String[] args) {
    try {
//      spot();
      auth();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
//    futures();

  }

  private static final Instrument BTC_PERP = new FuturesContract("BTC/USDT/PERP");
  private static final Instrument DOGE_PERP = new FuturesContract("DOGE/USDT/PERP");
  private static final Instrument ETH_PERP = new CurrencyPair("ETH/USDT/PERP");
  private static final Instrument BTC_SPOT = new CurrencyPair("BTC/USDT");
  private static final Instrument ETH_SPOT = new CurrencyPair("ETH/USDT");


  private static void auth() throws IOException, InterruptedException {
    ExchangeSpecification exchangeSpecification =
        new BybitStreamingExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
    exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    exchangeSpecification.setExchangeSpecificParametersItem(SPECIFIC_PARAM_ACCOUNT_TYPE,
        BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(BybitStreamingExchange.EXCHANGE_TYPE,
        BybitCategory.LINEAR);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(
        exchangeSpecification);
    exchange.connect().blockingAwait();
    Ticker ticker = (exchange.getMarketDataService().getTicker(DOGE_PERP));
    BigDecimal amount = exchange.getExchangeMetaData().getInstruments().get(DOGE_PERP).getMinimumAmount();
    if(amount.multiply(ticker.getLast()).compareTo(new BigDecimal("5.0")) <= 0) {
      amount = new BigDecimal("5").divide(ticker.getAsk(), exchange.getExchangeMetaData().getInstruments().get(DOGE_PERP).getVolumeScale(), UP);
    }
    LimitOrder limitOrder = new LimitOrder(OrderType.BID,amount, DOGE_PERP, "", new Date(), ticker.getAsk());
    Thread.sleep(2000L);
    AtomicReference<Order> order = new AtomicReference<>();
    Disposable disposableOrderChanges = ((BybitStreamingTradeService)exchange.getStreamingTradeService()).getOrderChanges(BybitCategory.LINEAR)
        .doOnError(
            error -> log.error("OrderChanges error {}",error.getMessage()))
        .subscribe( c -> {
              log.info("Order Changes {}", c);
              order.set(c);
            },
            throwable -> log.error("OrderChanges throwable,{}",throwable.getMessage()));
//    Disposable disposablePositionChanges = ((BybitStreamingTradeService)exchange.getStreamingTradeService()).getPositionChanges(BybitCategory.LINEAR)
//        .doOnError(
//            error -> log.error("PositionChanges error {}",error.getMessage()))
//        .subscribe( p -> log.info("PositionChanges Changes {}", p),
//            throwable -> log.error("Position throwable,{}",throwable.getMessage()));

    Disposable disposableComplexPositionChanges = ((BybitStreamingTradeService)exchange.getStreamingTradeService()).getBybitPositionChanges(BybitCategory.LINEAR)
        .doOnError(
            error -> log.error("ComplexPositionChanges error {}",error.getMessage()))
        .subscribe( p -> log.info("ComplexPositionChanges Changes {}", p),
            throwable -> log.error("ComplexPosition throwable,{}",throwable.getMessage()));

    Thread.sleep(3000L);
    exchange.getTradeService().placeLimitOrder(limitOrder);
    Thread.sleep(30000L);
    disposableOrderChanges.dispose();
//    disposablePositionChanges.dispose();
    disposableComplexPositionChanges.dispose();
    exchange.disconnect().blockingAwait();
    }

  public static void spot() throws IOException {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BybitStreamingExchange.class);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    exchangeSpecification.setExchangeSpecificParametersItem(BybitStreamingExchange.EXCHANGE_TYPE,
        BybitCategory.SPOT);
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
        BybitCategory.LINEAR);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
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
