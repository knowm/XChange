package info.bitrich.xchangestream.bybit.example;

import static java.math.RoundingMode.UP;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_TESTNET;

import info.bitrich.xchangestream.bybit.BybitStreamingExchange;
import info.bitrich.xchangestream.bybit.BybitStreamingTradeService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamTestNetExample {

  private static final Logger log = LoggerFactory.getLogger(BybitStreamTestNetExample.class);

  // Uses TEST_NET
  public static void main(String[] args) {
    try {
      withAuth();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Instrument BTC_PERP = new FuturesContract("BTC/USDT/PERP");
  private static final Instrument DOGE_PERP = new FuturesContract("DOGE/USDT/PERP");
  private static final Instrument ETH_PERP = new FuturesContract("ETH/USDT/PERP");

  private static final Instrument ETH_SPOT = new CurrencyPair("ETH/USDT");

  private static void withAuth() throws IOException, InterruptedException {
    ExchangeSpecification exchangeSpecification =
        new BybitStreamingExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
    exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(
        BybitStreamingExchange.EXCHANGE_TYPE, BybitCategory.LINEAR);
    exchangeSpecification.setExchangeSpecificParametersItem(SPECIFIC_PARAM_TESTNET, true);
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    exchange.connect().blockingAwait();
    Ticker ticker = (exchange.getMarketDataService().getTicker(DOGE_PERP));
    BigDecimal amount =
        exchange.getExchangeMetaData().getInstruments().get(DOGE_PERP).getMinimumAmount();
    // minimal amount to trade 5 usdt
    if (amount.multiply(ticker.getBid()).compareTo(new BigDecimal("5.0")) <= 0) {
      amount =
          new BigDecimal("5")
              .divide(
                  ticker.getBid(),
                  exchange.getExchangeMetaData().getInstruments().get(DOGE_PERP).getVolumeScale(),
                  UP);
    }
    LimitOrder limitOrder =
        new LimitOrder(OrderType.BID, amount, DOGE_PERP, "", new Date(), ticker.getAsk());
    Thread.sleep(2000L);
    AtomicReference<Order> order = new AtomicReference<>();
    Disposable disposableOrderChanges =
        exchange.getStreamingTradeService()
            .getOrderChanges(null,BybitCategory.LINEAR)
            .doOnError(error -> log.error("OrderChanges error", error))
            .subscribe(
                c -> {
                  log.info("Order Changes {}", c);
                  order.set(c);
                },
                throwable -> log.error("OrderChanges throwable", throwable));

    Disposable disposableComplexPositionChanges =
        ((BybitStreamingTradeService) exchange.getStreamingTradeService())
            .getBybitPositionChanges(BybitCategory.LINEAR)
            .doOnError(error -> log.error("ComplexPositionChanges error {}", error, error))
            .subscribe(
                p -> log.info("ComplexPositionChanges Changes {}", p),
                throwable -> log.error("ComplexPosition throwable,{}", throwable.getMessage()));
    Thread.sleep(3000L);
    exchange.getTradeService().placeLimitOrder(limitOrder);
    Thread.sleep(3000L);
    disposableOrderChanges.dispose();
    disposableComplexPositionChanges.dispose();
    exchange.disconnect().blockingAwait();
  }


}
