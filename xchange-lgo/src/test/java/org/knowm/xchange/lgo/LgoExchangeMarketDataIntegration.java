package org.knowm.xchange.lgo;

import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.knowm.xchange.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lgo.service.LgoMarketDataService;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@Ignore
public class LgoExchangeMarketDataIntegration {

  @Test
  public void fetchOrderBook() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoMarketDataService tradeService = lgoExchange.getMarketDataService();
    OrderBook ob = tradeService.getOrderBook(CurrencyPair.BTC_USD, "");
    assertThat(ob.getBids()).isNotEmpty();
    assertThat(ob.getAsks()).isNotEmpty();
  }

  // api key and secret key are expected to be in test resources under
  // integration directory
  // this directory is added to .gitignore to avoid committing a real usable key
  protected LgoExchange exchangeWithCredentials() throws IOException {
    ExchangeSpecification spec = LgoEnv.devel();
    spec.setSecretKey(readResource("/integration/private_key.pem"));
    spec.setApiKey(readResource("/integration/api_key.txt"));

    return (LgoExchange) ExchangeFactory.INSTANCE.createExchange(spec);
  }

  private String readResource(String path) throws IOException {
    InputStream stream = LgoExchange.class.getResourceAsStream(path);
    return IOUtils.toString(stream, "utf8");
  }
}
