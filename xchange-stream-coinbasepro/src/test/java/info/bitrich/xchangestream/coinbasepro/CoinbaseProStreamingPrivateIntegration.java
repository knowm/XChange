package info.bitrich.xchangestream.coinbasepro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProStreamingPrivateIntegration {

  StreamingExchange exchange;
  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProStreamingPrivateIntegration.class);
  Instrument instrument = new CurrencyPair("BTC/USD");

  @Before
  public void setUp() {
    Properties properties = new Properties();

    try {
      properties.load(CoinbaseProStreamingPrivateIntegration.class.getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ExchangeSpecification spec = new CoinbaseProStreamingExchange().getDefaultExchangeSpecification();

    spec.setApiKey(properties.getProperty("coinbaseApi"));
    spec.setSecretKey(properties.getProperty("coinbaseSecret"));
    spec.setExchangeSpecificParametersItem("passphrase", properties.getProperty("coinbasePassphrase"));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);

    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
  }

  @Test
  public void testUserTrades() throws InterruptedException, IOException {
    exchange
        .connect(ProductSubscription.create().addAll(instrument).build())
        .blockingAwait();

    List<UserTrade> userTradeList = new ArrayList<>();

    Disposable dis = exchange.getStreamingTradeService().getUserTrades(instrument).subscribe(userTrade -> {
      LOG.info(userTrade.toString());
      userTradeList.add(userTrade);
    });
    int count = 0;

    while (count < 5){
      TimeUnit.SECONDS.sleep(2);
      exchange.getTradeService().placeMarketOrder(new MarketOrder.Builder(OrderType.BID, instrument)
          .originalAmount(BigDecimal.valueOf(0.0001))
          .build());
      count++;
    }
    dis.dispose();

    assertThat(userTradeList.size()).isGreaterThanOrEqualTo(4);
    userTradeList.forEach(userTrade -> {
      assertThat(userTrade.getPrice()).isGreaterThan(BigDecimal.ZERO);
      assertThat(userTrade.getType()).isEqualTo(OrderType.BID);
      assertThat(userTrade.getInstrument()).isEqualTo(instrument);
      assertThat(userTrade.getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
      assertThat(userTrade.getFeeAmount()).isGreaterThan(BigDecimal.ZERO);
      assertThat(userTrade.getOrderId()).isNotNull();
      assertThat(userTrade.getId()).isNotNull();
      assertThat(userTrade.getFeeCurrency()).isInstanceOf(Currency.class);
      assertThat(userTrade.getTimestamp()).isAfter(Date.from(Instant.now().minus(5, ChronoUnit.MINUTES)));
    });
  }
}
