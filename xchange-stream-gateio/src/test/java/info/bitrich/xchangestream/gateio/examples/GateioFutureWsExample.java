package info.bitrich.xchangestream.gateio.examples;

import info.bitrich.xchangestream.gateio.GateioStreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OrderHedgeMode;
import org.knowm.xchange.gateio.dto.trade.GateioCancelOrderParams;
import org.knowm.xchange.gateio.service.GateioAccountService;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import static info.bitrich.xchangestream.gateio.examples.Utils.getMinAmount;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

@Slf4j
public class GateioFutureWsExample {
  private final Instrument instrument = new FuturesContract("SOL/USDT/PERP");
  public GateioStreamingExchange exchange;
  private final boolean logOutput = false;

  @BeforeEach
  public void before() {
    exchange = GateioExampleSetUp.initFutures();
  }

  @Test
  @Disabled
  public void placeLimitOrder() throws InterruptedException, IOException {
    while (!exchange.isAlive())
      Thread.sleep(100);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    minAmount =
        getMinAmount(
            new BigDecimal("15"),
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    String orderUserReference = "t-" + System.currentTimeMillis() + System.nanoTime() / 1000 % 1000;
    LimitOrder limitBuyOrder = new LimitOrder.Builder(BID, instrument).originalAmount(minAmount)
        .userReference(orderUserReference).limitPrice(ticker.getLow()).build();
    AtomicReference<String> limitSellOrderId = new AtomicReference<>("");
    Disposable orderChangeDisposable = exchange.getStreamingTradeService().getOrderChanges(instrument).subscribe(
        orderChange -> {
          log.info("orderChange: {}", orderChange);
          if (!orderChange.getUserReference().equals(orderUserReference))
            limitSellOrderId.set(orderChange.getId());
        }
    );
    Disposable limitBuyOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(limitBuyOrder)
            .subscribe(
                result -> {
                  log.info("limitBuyOrder is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitBuyOrder is disposed: {}", limitBuyOrderDisposable.isDisposed());
    LimitOrder limitSellOrder = new LimitOrder.Builder(ASK, instrument).originalAmount(minAmount).limitPrice(ticker.getHigh()).build();
    Disposable limitSellOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(limitSellOrder)
            .subscribe(
                result -> {
                  log.info("limitSellOrder is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitSellOrder is disposed: {}", limitSellOrderDisposable.isDisposed());
    LimitOrder limitBuyOrderAmend = new LimitOrder.Builder(BID, instrument).originalAmount(minAmount.add(new BigDecimal("0.001")))
        .userReference(orderUserReference).build();
    Disposable limitBuyOrderAmendDisposable =
        exchange
            .getStreamingTradeService()
            .changeOrder(limitBuyOrderAmend)
            .subscribe(
                result -> {
                  log.info("limitBuyOrderAmend is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitBuyOrderAmendDisposable is disposed: {}", limitBuyOrderAmendDisposable.isDisposed());
    LimitOrder limitSellOrderAmend = new LimitOrder.Builder(ASK, instrument).limitPrice(ticker.getHigh().add(new BigDecimal("0.1")))
        .id(limitSellOrderId.get()).build();
    Disposable limitSellOrderAmendDisposable =
        exchange
            .getStreamingTradeService()
            .changeOrder(limitSellOrderAmend)
            .subscribe(
                result -> {
                  log.info("limitSellOrderAmend is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitSellOrderAmend is disposed: {}", limitSellOrderAmendDisposable.isDisposed());
    GateioCancelOrderParams cancelOrderParams1 = new GateioCancelOrderParams("", instrument, orderUserReference);
    Disposable cancelOrder1 = exchange.getStreamingTradeService().cancelOrder(cancelOrderParams1).subscribe(result -> {
          log.info("cancel limitBuyOrder is send, retCode: {}", result);
        },
        throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("cancelOrder1 is disposed: {}", cancelOrder1.isDisposed());
    DefaultCancelOrderByInstrumentAndIdParams cancelOrderParams2 = new DefaultCancelOrderByInstrumentAndIdParams(instrument, limitSellOrderId.get());
    Disposable cancelOrder2 = exchange.getStreamingTradeService().cancelOrder(cancelOrderParams2).subscribe(result -> {
          log.info("cancel limitSellOrder is send, retCode: {}", result);
        },
        throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("cancelOrder2 is disposed: {}", cancelOrder2.isDisposed());
  }

  @Test
  @Disabled
  public void placeMarketOrder() throws InterruptedException, IOException {
    while (!exchange.isAlive())
      Thread.sleep(100);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    minAmount =
        getMinAmount(
            new BigDecimal("15"),
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    String orderUserReference = "t-" + System.currentTimeMillis() + System.nanoTime() / 1000 % 1000;
    MarketOrder marketBuyOrder = new MarketOrder.Builder(BID, instrument).originalAmount(minAmount).userReference(orderUserReference).build();
    Disposable orderChangeDisposable = exchange.getStreamingTradeService().getOrderChanges(instrument).subscribe(
        orderChange -> {
          log.info("orderChange: {}", orderChange);
        }
    );
    Disposable marketBuyOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketBuyOrder)
            .subscribe(
                result -> {
                  log.info("marketBuyOrder is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("marketBuyOrder is disposed: {}", marketBuyOrderDisposable.isDisposed());
    MarketOrder marketSellOrder = new MarketOrder.Builder(Order.OrderType.ASK, instrument).originalAmount(minAmount)
        .build();
    Disposable marketSellOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketSellOrder)
            .subscribe(
                result -> {
                  log.info("marketSellOrder is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("marketSellOrder is disposed: {}", marketSellOrderDisposable.isDisposed());
  }

  // dual position mode
  @Test
  @Disabled
  public void websocketTradeExampleHedgeMode() throws IOException, InterruptedException {
    // switch mode to two-way
    ((GateioAccountService) exchange.getAccountService()).setDualMode("usdt", true);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    minAmount =
        getMinAmount(
            new BigDecimal("5"),
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    String limitOrderAskUserId = "t-" + System.currentTimeMillis() + System.nanoTime() / 1000 % 1000;
    LimitOrder limitOrderAsk =
        new LimitOrder.Builder(Order.OrderType.ASK, instrument)
            .originalAmount(minAmount)
            .limitPrice(ticker.getHigh())
            .userReference(limitOrderAskUserId)
            .flag(OrderHedgeMode.TWOWAY)
            .build();
    Disposable limitOrderAskDisposable =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(limitOrderAsk)
            .subscribe(
                result -> {
                  log.info("limitOrderAsk is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitOrderAsk is disposed: {}", limitOrderAskDisposable.isDisposed());
    String limitOrderBidUserId = "t-" + System.currentTimeMillis() + System.nanoTime() / 1000 % 1000;
    LimitOrder limitOrderBid =
        new LimitOrder.Builder(Order.OrderType.BID, instrument)
            .originalAmount(minAmount)
            .limitPrice(ticker.getLow())
            .userReference(limitOrderBidUserId)
            .flag(OrderHedgeMode.TWOWAY)
            .build();
    Disposable limitOrderBidDisposable =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(limitOrderBid)
            .subscribe(
                result -> {
                  log.info("limitOrderBid is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitOrderBid is disposed: {}", limitOrderBidDisposable.isDisposed());
    Disposable cancelLimitOrderAskDisposable =
        exchange
            .getStreamingTradeService()
            .cancelOrder(new DefaultCancelOrderByInstrumentAndIdParams(instrument, limitOrderAskUserId))
            .subscribe(
                result -> {
                  log.info("cancelLimitOrderAsk is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("cancelLimitOrderAsk is disposed: {}", cancelLimitOrderAskDisposable.isDisposed());
    Disposable cancelLimitOrderBidDisposable =
        exchange
            .getStreamingTradeService()
            .cancelOrder(new DefaultCancelOrderByInstrumentAndIdParams(instrument, limitOrderBidUserId))
            .subscribe(
                result -> {
                  log.info("cancelLimitOrderBid is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("cancelLimitOrderBid is disposed: {}", cancelLimitOrderBidDisposable.isDisposed());
    MarketOrder marketOrderAsk = new MarketOrder.Builder(Order.OrderType.ASK, instrument).originalAmount(minAmount).flag(OrderHedgeMode.TWOWAY).build();
    Disposable marketOrderAskDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderAsk)
            .subscribe(
                result -> {
                  log.info("marketOrderAsk is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("marketOrderAsk is disposed: {}", marketOrderAskDisposable.isDisposed());

    MarketOrder marketOrderBid = new MarketOrder.Builder(Order.OrderType.BID, instrument).originalAmount(minAmount).flag(OrderHedgeMode.TWOWAY).build();
    Disposable marketOrderBidDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderBid)
            .subscribe(
                result -> {
                  log.info("marketOrderBid is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("marketOrderBid is disposed: {}", marketOrderBidDisposable.isDisposed());

    MarketOrder marketOrderCloseAsk = new MarketOrder.Builder(Order.OrderType.EXIT_ASK, instrument).originalAmount(minAmount).flag(OrderHedgeMode.TWOWAY).build();
    Disposable marketOrderCloseAskDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderCloseAsk)
            .subscribe(
                result -> {
                  log.info("marketOrderCloseAsk is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("marketOrderCloseAsk is disposed: {}", marketOrderCloseAskDisposable.isDisposed());

    MarketOrder marketOrderCloseBid = new MarketOrder.Builder(Order.OrderType.EXIT_BID, instrument).originalAmount(minAmount).flag(OrderHedgeMode.TWOWAY).build();
    Disposable marketOrderCloseBidDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderCloseBid)
            .subscribe(
                result -> {
                  log.info("marketOrderCloseBid is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("marketOrderCloseBid is disposed: {}", marketOrderCloseBidDisposable.isDisposed());
    // switch back to one-way mode
//    ((GateioAccountService) exchange.getAccountService()).setDualMode("usdt", false);
  }

}
