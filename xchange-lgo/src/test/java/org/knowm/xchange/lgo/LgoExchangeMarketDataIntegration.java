package org.knowm.xchange.lgo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lgo.service.LgoMarketDataService;

@Ignore
public class LgoExchangeMarketDataIntegration {

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
