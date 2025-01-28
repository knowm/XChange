package info.bitrich.xchangestream.bybit.example;

import static info.bitrich.xchangestream.bybit.example.BaseBybitExchange.connect;
import static java.math.RoundingMode.UP;

import info.bitrich.xchangestream.bybit.BybitStreamingTradeService;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamPositionChangeExample {

  private static final Logger log = LoggerFactory.getLogger(BybitStreamPositionChangeExample.class);

  public static void main(String[] args) {
    try {
      // Stream positionChange and trade example
      positionChangeExample();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Instrument ETH = new CurrencyPair("ETH/USDT");
  private static final Instrument ETH_PERP = new FuturesContract("ETH/USDT/PERP");
  static Ticker ticker;
  static BigDecimal amount;


  private static void positionChangeExample() throws IOException {
    StreamingExchange exchange = connect(BybitCategory.LINEAR, true);
    ticker = (exchange.getMarketDataService().getTicker(ETH_PERP));
    amount = exchange.getExchangeMetaData().getInstruments().get(ETH_PERP).getMinimumAmount();
    //minimal trade size - 5 USDT
    if (amount.multiply(ticker.getBid()).compareTo(new BigDecimal("5.0")) <= 0) {
      amount =
          new BigDecimal("5")
              .divide(
                  ticker.getBid(),
                  exchange.getExchangeMetaData().getInstruments().get(ETH_PERP).getVolumeScale(),
                  UP);
    }
    BybitAccountService bybitAccountService = (BybitAccountService) exchange.getAccountService();
    log.info(
        "switch mode to one-way, result {}",
        bybitAccountService.switchPositionMode(BybitCategory.LINEAR, null, "USDT", 0));
//    set leverage to 1.1
    bybitAccountService.setLeverage(ETH_PERP, 1.1);
    Disposable positionChangesDisposable =
        ((BybitStreamingTradeService) exchange.getStreamingTradeService())
            .getBybitPositionChanges(BybitCategory.LINEAR)
            .doOnError(
                error -> {
                  log.error(error.getMessage());
                })
            .subscribe(p -> log.info("position change {}", p));
    Disposable tradesDisposable =
        exchange.getStreamingMarketDataService().getTrades(ETH_PERP)
            .doOnError(
                error -> {
                  log.error(error.getMessage());
                })
            .subscribe(
                t -> log.info("trade {}", t));
    try {
      Thread.sleep(1000L);
      MarketOrder marketOrder = new MarketOrder(OrderType.BID, amount, ETH_PERP);
      log.info("market order id: {}", exchange.getTradeService().placeMarketOrder(marketOrder));
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    positionChangesDisposable.dispose();
  }


}


