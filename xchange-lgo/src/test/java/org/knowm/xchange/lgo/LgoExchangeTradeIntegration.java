package org.knowm.xchange.lgo;

import static org.assertj.core.api.Assertions.*;

import java.io.*;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.knowm.xchange.*;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.lgo.service.LgoTradeService;

@Ignore
public class LgoExchangeTradeIntegration {

  @Test
  public void fetchLastTrades() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();
    UserTrades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    assertThat(tradeHistory.getUserTrades()).isNotEmpty();
    System.out.println(tradeHistory.getUserTrades().size());
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
