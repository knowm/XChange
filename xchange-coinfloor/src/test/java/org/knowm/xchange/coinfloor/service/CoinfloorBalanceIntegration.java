package org.knowm.xchange.coinfloor.service;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinfloorBalanceIntegration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void fetchBalanceTest() throws IOException {
    final ExchangeSpecification specification = new ExchangeSpecification(CoinfloorExchange.class);

    String username = System.getProperty("xchange.coinfloor.username");
    String password = System.getProperty("xchange.coinfloor.password");

    if (username == null || password == null) {
      return;
    }

    specification.setUserName(username);
    specification.setPassword(password);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    AccountService service = exchange.getAccountService();

    AccountInfo info = service.getAccountInfo();
    logger.info("{}", info);
  }
}
