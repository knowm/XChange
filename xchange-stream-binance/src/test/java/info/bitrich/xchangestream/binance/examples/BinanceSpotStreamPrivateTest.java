package info.bitrich.xchangestream.binance.examples;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BinanceSpotStreamPrivateTest {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceSpotStreamPrivateTest.class);
  private static StreamingExchange exchange;
  BinanceStreamingExchange binanceStreamingExchange;
  private static final Instrument instrument = new CurrencyPair("ETH/USDT");
  private static final Instrument instrument2 = new CurrencyPair("SOL/USDT");
  private static final boolean logOutput = false;

  @Before
  public void setUp() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceStreamingExchange.class);
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(spec, "binance-demo");
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, SPOT);
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
    binanceStreamingExchange = (BinanceStreamingExchange) exchange;
  }

  @Test
  public void getOrderAndPositionChanges() throws IOException, InterruptedException {
    ProductSubscription subscription =
        ProductSubscription.create()
            // workaround to connect to userDataStream
            .addUserTrades(instrument)
            .build();

    exchange.connect(subscription).blockingAwait();

    Disposable orderChangesDisposable =
        exchange
            .getStreamingTradeService()
            .getOrderChanges(instrument)
            .subscribe(
                orderChanges -> {
                  if (logOutput) {
                    LOG.info("OrderChanges subscribe: {}", orderChanges);
                  }
                  assertThat(orderChanges.getInstrument().equals(instrument)).isTrue();
                  assertThat(orderChanges.getType().equals(OrderType.BID)).isTrue();
                });
    Disposable userTradeLiteDisposable =
        exchange
            .getStreamingTradeService()
            .getUserTrades(instrument)
            .subscribe(
                trade -> {
                  if (logOutput) {
                    LOG.info("trade lite subscribe: {}", trade);
                  }
                  assertThat(trade.getInstrument().equals(instrument)).isTrue();
                  assertThat(trade.getType().equals(OrderType.BID)).isTrue();
                });
    Disposable balanceChangeDisposable =
        binanceStreamingExchange
            .getStreamingAccountService()
            .getBalanceChanges()
            .subscribe(
                balanceChange -> {
                  if (logOutput) {
                    LOG.info("balanceChange subscribe: {}", balanceChange);
                  }
                });
    Thread.sleep(3000);
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    BigDecimal amount = new BigDecimal("0.01");
    // place limit order
    String orderId =
        exchange
            .getTradeService()
            .placeLimitOrder(
                new LimitOrder.Builder(OrderType.BID, instrument)
                    .limitPrice(ticker.getLast())
                    .originalAmount(amount)
                    .build());
    // place market order
    String marketOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder.Builder(OrderType.BID, instrument).originalAmount(amount).build());

    Thread.sleep(20000);
    orderChangesDisposable.dispose();
    userTradeLiteDisposable.dispose();
    balanceChangeDisposable.dispose();
    exchange.disconnect().blockingAwait();
  }
}
