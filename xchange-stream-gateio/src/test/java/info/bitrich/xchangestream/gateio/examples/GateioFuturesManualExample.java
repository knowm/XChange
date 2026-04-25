package info.bitrich.xchangestream.gateio.examples;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.gateio.GateioStreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;

import static org.knowm.xchange.gateio.GateioExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.gateio.dto.GateioExchangeType.FUTURES;

@Slf4j
public class GateioFuturesManualExample {
  private final Instrument instrument = new FuturesContract("ETH/USDT/PERP");
  public GateioStreamingExchange exchange;
  private final boolean logOutput = true;

  @Before
  public void before() {
    init();
  }

  @Test
  @Ignore
  public void geOrderBook() throws InterruptedException {
    Disposable disposable = exchange.getStreamingMarketDataService().getOrderBook(instrument, 400).subscribe(
        orderBook -> {
          if (logOutput) {
            log.info("{}", orderBook);
          }
        });
    Thread.sleep(1000);
    disposable.dispose();
  }

  @Test
  @Ignore
  public void getUserOrders() throws InterruptedException {
    Disposable disposable = exchange.getStreamingMarketDataService().getTrades(instrument).subscribe(
        trade -> {
          if (logOutput) {
            log.info("{}", trade);
          }
        });
    Thread.sleep(3000);
    disposable.dispose();
  }

  @Test
  @Ignore
  public void getTrades() throws InterruptedException {
    Disposable disposable = exchange.getStreamingMarketDataService().getTrades(instrument).subscribe(
        trade -> {
          if (logOutput) {
            log.info("{}", trade);
          }
        });
    Thread.sleep(3000);
    disposable.dispose();
  }

  private void init() {
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(GateioStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    AuthUtils.setApiAndSecretKey(spec, "gateio-main");
    exchange = (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    exchange.connect().blockingAwait();
  }
}
