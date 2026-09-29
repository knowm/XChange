package info.bitrich.xchangestream.gateio.examples;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.gateio.GateioStreamingExchange;
import info.bitrich.xchangestream.gateio.GateioStreamingMarketDataService;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;

import java.math.BigDecimal;

import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.gateio.GateioExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.gateio.dto.GateioExchangeType.SPOT;

@Slf4j
public class GateioSpotManualExample {
  private final Instrument instrument = new CurrencyPair("SOL/USDT");
  public GateioStreamingExchange exchange;
  private final boolean logOutput = true;

  @BeforeEach
  void before() {
    init();
  }


  @Test
  @Disabled
  void getBalance() throws Exception {
    Disposable disposable = exchange.getStreamingAccountService().getBalanceChanges(Currency.USDT).
        subscribe(
            balance -> {
              if (logOutput) {
                log.info("balance {}", balance);
              }
            }, throwable -> {
              log.error("throwable encountered error while subscribing to pair {}", instrument);
              log.error("", throwable);
            });
    Thread.sleep(20000000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Disabled
  void getOrderBook() throws Exception {
    Disposable disposable = exchange.getStreamingMarketDataService().getOrderBook(instrument, 400).
        subscribe(
            orderBook -> {
              if (logOutput) {
                log.info("OB ask.size: {}, bid.size: {}", orderBook.getAsks().size(), orderBook.getBids().size());
              }
            }, throwable -> {
              log.error("throwable encountered error while subscribing to pair {}", instrument);
              log.error("", throwable);
            });
    Thread.sleep(2000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Disabled
  void geOrderBookTicker() throws Exception {
    Thread.sleep(2000000);
    Disposable disposable = ((GateioStreamingMarketDataService) exchange.getStreamingMarketDataService())
        .getOrderBookTicker(instrument).subscribe(
            orderBookTicker -> {
              if (logOutput) {
                log.info("OB ticker bid: {}:{}, ask: {}:{}", orderBookTicker.getBidPrice(), orderBookTicker.getBidSize(),
                    orderBookTicker.getAskPrice(), orderBookTicker.getAskSize());
              }
            }, throwable -> {
              log.error("Future throwable encountered error while subscribing to pair {}", instrument);
              log.error("", throwable);
            });
    Thread.sleep(20000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Disabled
  void getOrderChanges() throws Exception {
    Disposable disposable = exchange.getStreamingTradeService().getOrderChanges(instrument).subscribe(
        order -> {
          if (logOutput) {
            log.info("order changes {}", order);
          }
        }, throwable -> {
          log.error("throwable encountered error while subscribing to pair {}", instrument);
          log.error("", throwable);
        });
    MarketOrder order = new MarketOrder.Builder(BID, instrument).userReference("t-" + System.currentTimeMillis()).originalAmount(new BigDecimal("3")).build();
    Thread.sleep(1000);
    String result = exchange.getTradeService().placeMarketOrder(order);
    Thread.sleep(3000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Disabled
  void getOrderBookSpotLegacy() throws Exception {
    Disposable disposable = ((GateioStreamingMarketDataService) exchange.getStreamingMarketDataService()).getOrderBookLegacy((CurrencyPair) instrument).
        subscribe(
            orderBook -> {
              if (logOutput) {
                log.info("OB ask.size: {}, bid.size: {}", orderBook.getAsks().size(), orderBook.getBids().size());
              }
            }, throwable -> {
              log.error("throwable encountered error while subscribing to pair {}", instrument);
              log.error("", throwable);
            });
    Thread.sleep(2000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Disabled
  void getTrades() throws Exception {
    Disposable sub1 =
        exchange
            .getStreamingMarketDataService()
            .getTrades(instrument)
            .subscribe(
                trade -> {
                  if (logOutput) {
                    log.info("Trade Price: {}", trade.getPrice());
                    log.info("Trade Amount: {}", trade.getOriginalAmount());
                  }
                },
                throwable -> log.error("ERROR in getting trade: ", throwable));

    Thread.sleep(10000);
    sub1.dispose();
  }

  private void init() {
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(GateioStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, SPOT);
    AuthUtils.setApiAndSecretKey(spec, "gateio-main");
    exchange = (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    exchange.connect().blockingAwait();
  }
}
