package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.ftx.dto.FtxOrderbookResponse;
import info.bitrich.xchangestream.ftx.dto.FtxTickerResponse;
import io.reactivex.disposables.Disposable;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FtxStreamingMarketDataServiceTest {

  private static final Logger LOG =
      LoggerFactory.getLogger(FtxStreamingMarketDataServiceTest.class);

  @Test
  public void ftxStreamingMarketDataServiceTest() throws Exception {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(FtxStreamingExchange.class);

    exchange
        .connect(
            ProductSubscription.create()
                .addAll(CurrencyPair.BTC_USD)
                .addAll(new CurrencyPair("BTC-PERP"))
                .build())
        .blockingAwait();

    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USD)
            .subscribe(
                orderBook -> {
                  if (orderBook.getBids().size() > 0 && orderBook.getAsks().size() > 0) {
                    assertThat(orderBook.getAsks().get(0).getLimitPrice())
                        .isLessThan(orderBook.getAsks().get(1).getLimitPrice());
                    assertThat(orderBook.getBids().get(0).getLimitPrice())
                        .isGreaterThan(orderBook.getBids().get(1).getLimitPrice());
                    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(Order.OrderType.ASK);
                    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(Order.OrderType.BID);
                    assertThat(orderBook.getBids().get(0).getLimitPrice())
                        .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                  }
                });

    Disposable dis2 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(new CurrencyPair("BTC-PERP"))
            .subscribe(
                orderBook -> {
                  if (orderBook.getBids().size() > 0 && orderBook.getAsks().size() > 0) {
                    assertThat(orderBook.getAsks().get(0).getLimitPrice())
                        .isLessThan(orderBook.getAsks().get(1).getLimitPrice());
                    assertThat(orderBook.getBids().get(0).getLimitPrice())
                        .isGreaterThan(orderBook.getBids().get(1).getLimitPrice());
                    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(Order.OrderType.ASK);
                    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(Order.OrderType.BID);
                    assertThat(orderBook.getBids().get(0).getLimitPrice())
                        .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                  }
                });

    Disposable dis3 =
        exchange
            .getStreamingMarketDataService()
            .getTicker(CurrencyPair.BTC_USD)
            .subscribe(
                ticker -> {
                  if (ticker.getBid() != null && ticker.getAsk() != null) {
                    assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
                  }
                });

    TimeUnit.SECONDS.sleep(6);
    dis.dispose();
    dis2.dispose();
    dis3.dispose();
  }

  @Test
  public void testParserMarket() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        FtxStreamingMarketDataServiceTest.class.getResourceAsStream(
            "/ftxOrderbookResponse-example.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxOrderbookResponse ftxResponse = mapper.readValue(is, FtxOrderbookResponse.class);

    // Verify that the example data was unmarshalled correctly

    assertThat(ftxResponse.getAsks().get(0).get(0)).isEqualTo(BigDecimal.valueOf(55114));
  }

  @Test
  public void testParserTicker() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        FtxStreamingMarketDataServiceTest.class.getResourceAsStream(
            "/ftxTickerResponse-example.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxTickerResponse ftxResponse = mapper.readValue(is, FtxTickerResponse.class);

    // Verify that the example data was unmarshalled correctly

    assertThat(
            new FtxTickerResponse(
                1621712252.2447793,
                BigDecimal.valueOf(37819.0),
                BigDecimal.valueOf(2.0809),
                BigDecimal.valueOf(3.783E+4),
                BigDecimal.valueOf(0.0793),
                BigDecimal.valueOf(37829.0)))
        .isEqualTo(ftxResponse);
  }

  @Test
  @Ignore
  public void orderbookCorrectnessTest() throws Exception {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(FtxStreamingExchange.class);

    exchange
        .connect(ProductSubscription.create().addAll(new CurrencyPair("BTC-PERP")).build())
        .blockingAwait();

    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(new CurrencyPair("BTC-PERP"))
            .retry()
            .subscribe(
                orderBook -> {
                  if (orderBook.getBids().size() > 0 && orderBook.getAsks().size() > 0) {
                    LOG.info(
                        "Ask 3: "
                            + orderBook.getAsks().get(2).getLimitPrice()
                            + " volume "
                            + orderBook.getAsks().get(2).getOriginalAmount());
                    LOG.info(
                        "Ask 2: "
                            + orderBook.getAsks().get(1).getLimitPrice()
                            + " volume "
                            + orderBook.getAsks().get(1).getOriginalAmount());
                    LOG.info(
                        "Ask 1: "
                            + orderBook.getAsks().get(0).getLimitPrice()
                            + " volume "
                            + orderBook.getAsks().get(0).getOriginalAmount());
                    LOG.info("--");
                    LOG.info(
                        "Bid 1: "
                            + orderBook.getBids().get(0).getLimitPrice()
                            + " volume "
                            + orderBook.getBids().get(0).getOriginalAmount());
                    LOG.info(
                        "Bid 2: "
                            + orderBook.getBids().get(1).getLimitPrice()
                            + " volume "
                            + orderBook.getBids().get(1).getOriginalAmount());
                    LOG.info(
                        "Bid 3: "
                            + orderBook.getBids().get(2).getLimitPrice()
                            + " volume "
                            + orderBook.getBids().get(2).getOriginalAmount());
                    LOG.info("=================");
                  }
                });
    while (true) {
      TimeUnit.SECONDS.sleep(60);
    }
  }
}
