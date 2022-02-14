package org.knowm.xchange.lgo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lgo.dto.marketdata.LgoGranularity;
import org.knowm.xchange.lgo.dto.marketdata.LgoPriceHistory;
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
    try {
      return new String(
          Files.readAllBytes(Paths.get(getClass().getResource(path).toURI())),
          StandardCharsets.UTF_8);
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
