package org.knowm.xchange.okex;

import static org.knowm.xchange.okex.dto.trade.OkexOrderFlags.POST_ONLY;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkexRateLimiterTest {

  static Exchange exchange;
  static int instrumentListSize = 10;
  static List<Instrument> instruments = new ArrayList<>();
  static Instrument instrument = new FuturesContract("BTC/USDT/SWAP");
  private static final Logger LOG = LoggerFactory.getLogger(OkexRateLimiterTest.class);

  static org.knowm.xchange.okex.service.OkexAccountService okexAccountService;

  // it seems rate limiter needs full rework
  // for example 20 req per 2 seconds - look like not less than 100 ms between any req(based on
  // server receive time)
  public static void main(String[] args) throws InterruptedException {
    Properties properties = new Properties();
    try {
      properties.load(OkexRateLimiterTest.class.getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ExchangeSpecification spec = new OkexExchange().getDefaultExchangeSpecification();
    spec.setApiKey(properties.getProperty("apikey"));
    spec.setSecretKey(properties.getProperty("secret"));
    spec.setExchangeSpecificParametersItem(
        OkexExchange.PARAM_PASSPHRASE, properties.getProperty("passphrase"));
    spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_SIMULATED, "1");
    spec.setProxyHost("127.0.0.1");
    spec.setProxyPort(1079);
    exchange = ExchangeFactory.INSTANCE.createExchange(spec);
    okexAccountService =
        (org.knowm.xchange.okex.service.OkexAccountService) exchange.getAccountService();
    instruments =
        exchange.getExchangeMetaData().getInstruments().keySet().stream()
            .filter(f -> f instanceof FuturesContract)
            .limit(instrumentListSize)
            .collect(Collectors.toList());
    //    Thread.sleep(1000L);
    amendOrder();
    //    setLeverage();
  }

  static long lastRequestTime = 0;

  public static void amendOrder() {
    try {
      Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
      InstrumentMetaData instrumentMetaData =
          exchange.getExchangeMetaData().getInstruments().get(instrument);
      BigDecimal size = instrumentMetaData.getMinimumAmount();
      BigDecimal price = ticker.getLow();
      String userReference = RandomStringUtils.randomAlphanumeric(20);
      String orderId =
          exchange
              .getTradeService()
              .placeLimitOrder(
                  new LimitOrder.Builder(Order.OrderType.BID, instrument)
                      .originalAmount(size)
                      .limitPrice(price)
                      .flag(POST_ONLY)
                      .userReference(userReference)
                      .build());
      ExecutorService pool = Executors.newFixedThreadPool(4);
      for (int i = 0; i < 25; i++) {
        int finalI = i;
        if (new Date().getTime() - lastRequestTime > 100) {
          pool.execute(
              () -> {
                try {
                  lastRequestTime = new Date().getTime();
                  exchange
                      .getTradeService()
                      .changeOrder(
                          new LimitOrder.Builder(Order.OrderType.BID, instrument)
                              .limitPrice(price.add(new BigDecimal(finalI)))
                              .originalAmount(size)
                              .id(orderId)
                              .build());
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              });
        } else Thread.sleep(10);
      }
      Thread.sleep(1000);
      pool.shutdown();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setLeverage() {
    ExecutorService pool = Executors.newFixedThreadPool(1);
    try {
      List<Callable<Boolean>> tasks = new ArrayList<>();
      int delay = 0;
      for (Instrument instrument : instruments) {
        int finalDelay = delay;
        tasks.add(
            () -> {
              Thread.sleep(finalDelay);
              return okexAccountService.setLeverage(instrument, 1);
            });
        delay += 100;
      }
      List<Future<Boolean>> invokeAll;
      try {
        invokeAll = pool.invokeAll(tasks);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      int counter = 0;
      for (Future<Boolean> future : invokeAll) {
        try {
          counter++;
          boolean result = future.get();
          if (result) {
            LOG.info("set leverage success, counter: {}", counter);
          } else {
            throw new InterruptedException();
          }
        } catch (InterruptedException | ExecutionException e) {
          throw new RuntimeException(e);
        }
      }
    } finally {
      pool.shutdown();
    }
  }
}
