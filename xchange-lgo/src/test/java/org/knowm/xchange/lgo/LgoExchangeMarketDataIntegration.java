package org.knowm.xchange.lgo;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.util.TimeZone;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lgo.dto.marketdata.*;
import org.knowm.xchange.lgo.service.LgoMarketDataService;

@Ignore
public class LgoExchangeMarketDataIntegration {

  private SimpleDateFormat dateFormat;

  @Before
  public void setUp() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Test
  public void fetchOrderBook() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoMarketDataService tradeService = lgoExchange.getMarketDataService();
    OrderBook ob = tradeService.getOrderBook(CurrencyPair.BTC_USD, "");
    //    assertThat(ob.getBids()).isNotEmpty();
    //   assertThat(ob.getAsks()).isNotEmpty();
    ob.getBids().forEach(l -> System.out.println(l.toString()));
    ob.getAsks().forEach(l -> System.out.println(l.toString()));
  }

  @Test
  public void fetchPriceHistory() throws IOException, ParseException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoMarketDataService tradeService = lgoExchange.getMarketDataService();
    LgoPriceHistory history =
        tradeService.getLgoPriceHistory(
            CurrencyPair.BTC_USD,
            dateFormat.parse("2019-12-20T15:00:00.000Z"),
            dateFormat.parse("2019-12-21T15:00:00.000Z"),
            LgoGranularity.ONE_HOUR);
    assertThat(history.getPrices()).hasSize(24);
    System.out.println(history);
  }

  // api key and secret key are expected to be in test resources under
  // integration directory
  // this directory is added to .gitignore to avoid committing a real usable key
  private LgoExchange exchangeWithCredentials() throws IOException {
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setSecretKey(readResource("/integration/private_key.pem"));
    spec.setApiKey(readResource("/integration/api_key.txt"));

    return (LgoExchange) ExchangeFactory.INSTANCE.createExchange(spec);
  }

  private String readResource(String path) throws IOException {
    InputStream stream = LgoExchange.class.getResourceAsStream(path);
    return IOUtils.toString(stream, StandardCharsets.UTF_8);
  }
}
