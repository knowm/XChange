package org.knowm.xchange.coinmate.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmate.CoinmateExchange;
import org.knowm.xchange.coinmate.CoinmateUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class TradeFixRateMockTests {
  public Exchange createMockExchange() throws IOException {
    CoinmateExchange exchange =
        (CoinmateExchange) ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CoinmateExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("apimocks.test.coinmate.cz");
    specification.setSslUri("https://apimocks.test.coinmate.cz");
    specification.setApiKey("yugkqYc-oYzquPWnlYFt2wV5UKUeWZJ2jb-8YVX_HLE");
    specification.setSecretKey("9vzVIJLUlMznNu5H0eter5tLnRMHFDzR2l9A_qUxApw");
    specification.setUserName("6892");
    specification.setShouldLoadRemoteMetaData(false);
    exchange.applySpecification(specification);
    exchange.setNonceFactory(() -> 1l);
    return exchange;
  }

  @Test
  public void testFixRate() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    tradeServiceRaw.coinmateBuyQuickFixRate(new BigDecimal("1.0"), null, CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
  }
}
