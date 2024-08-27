package org.knowm.xchange.bitget;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitget.service.BitgetMarketDataService;

public class BitgetExchange extends BaseExchange {

  @Override
  protected void initServices() {
//    accountService = new BitgetAccountService(this);
    marketDataService = new BitgetMarketDataService(this);
//    tradeService = new BitgetTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(getClass());
    specification.setSslUri("https://api.bitget.com");
    specification.setHost("www.bitget.com");
    specification.setExchangeName("Bitget");
    return specification;
  }

  @Override
  public void remoteInit() throws IOException {
  }
}
