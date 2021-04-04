package info.bitrich.xchangestream.ftx;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.ftx.dto.FtxOrderbookResponse;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    TimeUnit.SECONDS.sleep(6);
    dis.dispose();
    dis2.dispose();
  }

  @Test
  public void testParser() throws IOException {
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
