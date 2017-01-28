package org.knowm.xchange;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.knowm.xchange.huobi.HuobiExchange;

public class ExchangeSpecificParamsTest {

  @Test
  public void parametersConsistencyBitVc() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());

    ExchangeSpecification bfxSpec = exchange.getDefaultExchangeSpecification();
    bfxSpec.setExchangeSpecificParametersItem(HuobiExchange.USE_BITVC, true);

    exchange.applySpecification(bfxSpec);

    assertEquals(exchange.getExchangeSpecification().getSslUri(), "https://api.bitvc.com");
  }

  @Test
  public void parametersConsistencyHuobi() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());

    ExchangeSpecification bfxSpec = exchange.getDefaultExchangeSpecification();
    bfxSpec.setExchangeSpecificParametersItem(HuobiExchange.USE_BITVC, false);

    exchange.applySpecification(bfxSpec);

    assertEquals(exchange.getExchangeSpecification().getSslUri(), "https://api.huobi.com/apiv3");
  }

  @Test
  public void parametersSetNewMarketData() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());

    ExchangeSpecification bfxSpec = exchange.getDefaultExchangeSpecification();
    bfxSpec.setExchangeSpecificParametersItem(HuobiExchange.HUOBI_MARKET_DATA, "http://market.huobi.com/");

    exchange.applySpecification(bfxSpec);

    assertEquals(exchange.getExchangeSpecification().getExchangeSpecificParametersItem(HuobiExchange.HUOBI_MARKET_DATA), "http://market.huobi.com/");

  }

}
