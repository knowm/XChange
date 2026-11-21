package info.bitrich.xchangestream.binance.examples;

import static info.bitrich.xchangestream.binance.examples.Util.getMinAmount;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.trade.BinanceCancelOrderParams;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OrderHedgeMode;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Disabled
class BinanceFutureStreamWebsocketTradeTest {

  private static final Logger LOG =
      LoggerFactory.getLogger(BinanceFutureStreamWebsocketTradeTest.class);
  private static StreamingExchange exchange;
  BinanceFutureStreamingExchange binanceFutureStreamingExchange;
  private static final Instrument instrument = new FuturesContract("BULLA/USDT/PERP");
  private static final Instrument instrument2 = new FuturesContract("SOL/USDT/PERP");
  private final boolean logOutput = false;

  @BeforeEach
  void setUp() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    //  futures websocket trade not work on test net, main net only
    AuthUtils.setApiAndSecretKey(spec, "binance-main-ed25519"); // apikey and ed2519 private key
    spec.setExchangeSpecificParametersItem("ed25519", true);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
    binanceFutureStreamingExchange = (BinanceFutureStreamingExchange) exchange;
  }

  @Test
  public void websocketTradePositionDualMode() throws InterruptedException, IOException {
    ProductSubscription subscription =
        ProductSubscription.create()
            // workaround to connect to userDataStream
            .addUserTrades(instrument)
            // workaround to connect to BinanceStreamingService
            .addTicker(instrument)
            .build();
    exchange.connect(subscription).blockingAwait();
    // wait for authorization
    while (!exchange.isAlive()) {
      Thread.sleep(100L);
    }
    // switch mode to two-way
    ((BinanceAccountService) exchange.getAccountService()).setDualSidePositionMode(true);
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    minAmount =
        getMinAmount(
            new BigDecimal("5"),
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    minAmount = new BigDecimal("140");
    String limitOrderAskUserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrderAsk =
        new LimitOrder.Builder(OrderType.ASK, instrument)
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
                  LOG.info("limitOrderAsk is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("limitOrderAsk is disposed: {}", limitOrderAskDisposable.isDisposed());
    String limitOrderBidUserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrderBid =
        new LimitOrder.Builder(OrderType.BID, instrument)
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
                  LOG.info("limitOrderBid is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("limitOrderBid is disposed: {}", limitOrderBidDisposable.isDisposed());
    Disposable cancelLimitOrderAskDisposable =
        exchange
            .getStreamingTradeService()
            .cancelOrder(new BinanceCancelOrderParams(instrument, "", limitOrderAskUserId))
            .subscribe(
                result -> {
                  LOG.info("cancelLimitOrderAsk is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("cancelLimitOrderAsk is disposed: {}", cancelLimitOrderAskDisposable.isDisposed());
    Disposable cancelLimitOrderBidDisposable =
        exchange
            .getStreamingTradeService()
            .cancelOrder(new BinanceCancelOrderParams(instrument, "", limitOrderBidUserId))
            .subscribe(
                result -> {
                  LOG.info("cancelLimitOrderBid is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("cancelLimitOrderBid is disposed: {}", cancelLimitOrderBidDisposable.isDisposed());
    MarketOrder marketOrderAsk =
        new MarketOrder.Builder(OrderType.ASK, instrument)
            .originalAmount(minAmount)
            .flag(OrderHedgeMode.TWOWAY)
            .build();
    Disposable marketOrderAskDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderAsk)
            .subscribe(
                result -> {
                  LOG.info("marketOrderAsk is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("marketOrderAsk is disposed: {}", marketOrderAskDisposable.isDisposed());

    MarketOrder marketOrderBid =
        new MarketOrder.Builder(OrderType.BID, instrument)
            .originalAmount(minAmount)
            .flag(OrderHedgeMode.TWOWAY)
            .build();
    Disposable marketOrderBidDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderBid)
            .subscribe(
                result -> {
                  LOG.info("marketOrderBid is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("marketOrderBid is disposed: {}", marketOrderBidDisposable.isDisposed());

    MarketOrder marketOrderCloseAsk =
        new MarketOrder.Builder(OrderType.EXIT_ASK, instrument)
            .originalAmount(minAmount)
            .flag(OrderHedgeMode.TWOWAY)
            .build();
    Disposable marketOrderCloseAskDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderCloseAsk)
            .subscribe(
                result -> {
                  LOG.info("marketOrderCloseAsk is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("marketOrderCloseAsk is disposed: {}", marketOrderCloseAskDisposable.isDisposed());

    MarketOrder marketOrderCloseBid =
        new MarketOrder.Builder(OrderType.EXIT_BID, instrument)
            .originalAmount(minAmount)
            .flag(OrderHedgeMode.TWOWAY)
            .build();
    Disposable marketOrderCloseBidDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrderCloseBid)
            .subscribe(
                result -> {
                  LOG.info("marketOrderCloseBid is send, retCode: {}", result);
                },
                throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("marketOrderCloseBid is disposed: {}", marketOrderCloseBidDisposable.isDisposed());
    // switch mode back to one-way
    ((BinanceAccountService) exchange.getAccountService()).setDualSidePositionMode(false);
  }

  @Test
  void websocketTrade() throws Exception {
    ProductSubscription subscription =
        ProductSubscription.create()
            // workaround to connect to userDataStream
            .addUserTrades(instrument2)
            // workaround to connect to BinanceStreamingService
            .addTicker(instrument2)
            .build();
    exchange.connect(subscription).blockingAwait();
    // wait for authorization
    while (!exchange.isAlive()) {
      Thread.sleep(100L);
    }
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument2).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument2);
    BigDecimal minAmountUsdt = new BigDecimal("5");
    minAmount =
        getMinAmount(
            minAmountUsdt,
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument2).getVolumeScale());
    String limitOrderUserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, instrument2)
            .originalAmount(minAmount)
            .limitPrice(ticker.getLow())
            .userReference(limitOrderUserId)
            .build();
    Disposable limitOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(limitOrder)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("placeLimitOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("placeLimitOrder error", throwable));
    Thread.sleep(1000);

    LimitOrder changeOrder =
        new LimitOrder.Builder(OrderType.BID, instrument2)
            .originalAmount(minAmount)
            .limitPrice(ticker.getLow().add(BigDecimal.ONE.negate()))
            .userReference(limitOrderUserId)
            .build();
    Disposable changeOrderDisposable =
        exchange
            .getStreamingTradeService()
            .changeOrder(changeOrder)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("changeOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("changeOrder error", throwable));
    Thread.sleep(1000);
    LOG.info("changeOrder disposed: {}", changeOrderDisposable.isDisposed());

    Disposable cancelOrderDisposable =
        exchange
            .getStreamingTradeService()
            .cancelOrder(new BinanceCancelOrderParams(instrument2, null, limitOrderUserId))
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("cancelOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("cancelOrder error", throwable));
    Thread.sleep(1000);

    String marketOrderUserId = RandomStringUtils.randomAlphanumeric(20);
    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.ASK, instrument2)
            .originalAmount(minAmount)
            .userReference(marketOrderUserId)
            .build();
    Disposable marketOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketOrder)
            .doOnError(error -> LOG.error("placeMarketOrder error", error))
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("placeMarketOrder result: {}", result.toString());
                  }
                });
    Thread.sleep(1000);
  }
}
