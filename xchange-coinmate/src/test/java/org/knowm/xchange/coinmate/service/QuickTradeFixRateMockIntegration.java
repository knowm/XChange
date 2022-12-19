package org.knowm.xchange.coinmate.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmate.CoinmateExchange;
import org.knowm.xchange.coinmate.CoinmateUtils;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateQuickRate;
import org.knowm.xchange.coinmate.dto.trade.CoinmateBuyFixRateResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateBuyFixRateResponseData;
import org.knowm.xchange.coinmate.dto.trade.CoinmateSellFixRateResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateSellFixRateResponseData;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.TradeService;

public class QuickTradeFixRateMockIntegration {
  public Exchange createMockExchange() {
    CoinmateExchange exchange =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CoinmateExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("apimocks.test.coinmate.cz");
    specification.setSslUri("https://apimocks.test.coinmate.cz");
    specification.setApiKey("yugkqYc-oYzquPWnlYFt2wV5UKUeWZJ2jb-8YVX_HLE");
    specification.setSecretKey("9vzVIJLUlMznNu5H0eter5tLnRMHFDzR2l9A_qUxApw");
    specification.setUserName("6892");
    specification.setShouldLoadRemoteMetaData(false);
    exchange.applySpecification(specification);
    return exchange;
  }

  public Exchange createMockExchangeUnauthenticated() {
    CoinmateExchange exchange =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CoinmateExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("apimocks.test.coinmate.cz");
    specification.setSslUri("https://apimocks.test.coinmate.cz");
    specification.setShouldLoadRemoteMetaData(false);
    exchange.applySpecification(specification);
    return exchange;
  }

  @Test
  public void testGetBuyQuickRate() throws Exception {
    Exchange exchange = createMockExchangeUnauthenticated();
    CoinmateMarketDataServiceRaw marketDataService = (CoinmateMarketDataServiceRaw) exchange.getMarketDataService();
    CoinmateQuickRate response = marketDataService.getCoinmateBuyQuickRate(new BigDecimal("1.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    assertNotNull(response.getData());
  }

  @Test
  public void testGetSellQuickRate() throws Exception {
    Exchange exchange = createMockExchangeUnauthenticated();
    CoinmateMarketDataServiceRaw marketDataService = (CoinmateMarketDataServiceRaw) exchange.getMarketDataService();
    CoinmateQuickRate response = marketDataService.getCoinmateSellQuickRate(new BigDecimal("1.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    assertNotNull(response.getData());
  }

  @Test
  public void testBuyFixRateTotal() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateBuyFixRateResponse response = tradeServiceRaw.coinmateBuyQuickFixRate(
        new BigDecimal("1.0"),
        null,
        CoinmateUtils.getPair(CurrencyPair.BTC_EUR)
    );
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    CoinmateBuyFixRateResponseData data = response.getData();
    assertNotNull(data.getRateId());
    assertNotNull(data.getExpiresAt());
    assertNotNull(data.getCurrencyPair());
    assertNotNull(data.getRate());
    assertEquals(0, data.getTotal().compareTo(new BigDecimal("1.0")));
    assertNotNull(data.getAmountReceived());
  }

  @Test
  public void testBuyFixRateAmount() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateBuyFixRateResponse response = tradeServiceRaw.coinmateBuyQuickFixRate(
        null,
        new BigDecimal("100.0"),
        CoinmateUtils.getPair(CurrencyPair.BTC_EUR)
    );
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    CoinmateBuyFixRateResponseData data = response.getData();
    assertNotNull(data.getRateId());
    assertNotNull(data.getExpiresAt());
    assertNotNull(data.getCurrencyPair());
    assertNotNull(data.getRate());
    assertEquals(0, data.getAmountReceived().compareTo(new BigDecimal("100.0")));
    assertNotNull(data.getTotal());
  }

  @Test
  public void testSellFixRateTotal() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateSellFixRateResponse response = tradeServiceRaw.coinmateSellQuickFixRate(
        new BigDecimal("1.0"),
        null,
        CoinmateUtils.getPair(CurrencyPair.BTC_EUR)
    );
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    CoinmateSellFixRateResponseData data = response.getData();
    assertNotNull(data.getRateId());
    assertNotNull(data.getExpiresAt());
    assertNotNull(data.getCurrencyPair());
    assertNotNull(data.getRate());
    assertEquals(0, data.getAmount().compareTo(new BigDecimal("1.0")));
    assertNotNull(data.getTotalReceived());
  }

  @Test
  public void testSellFixRateAmount() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateSellFixRateResponse response = tradeServiceRaw.coinmateSellQuickFixRate(
        null,
        new BigDecimal("100.0"),
        CoinmateUtils.getPair(CurrencyPair.BTC_EUR)
    );
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    CoinmateSellFixRateResponseData data = response.getData();
    assertNotNull(data.getRateId());
    assertNotNull(data.getExpiresAt());
    assertNotNull(data.getCurrencyPair());
    assertNotNull(data.getRate());
    assertEquals(0, data.getTotalReceived().compareTo(new BigDecimal("100.0")));
    assertNotNull(data.getAmount());
  }

  @Test
  public void testBuyFixRateExecute() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateBuyFixRateResponse response = tradeServiceRaw.coinmateBuyQuickFixRate(
        null,
        new BigDecimal("100.0"),
        CoinmateUtils.getPair(CurrencyPair.BTC_EUR)
    );
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    CoinmateBuyFixRateResponseData data = response.getData();
    String rateId = data.getRateId();
    assertNotNull(rateId);

    CoinmateTradeResponse response2 = tradeServiceRaw.buyCoinmateQuickFix(rateId, null);
    assertFalse(response2.isError());
    assertNull(response2.getErrorMessage());
    assertNotNull(response2.getData());
  }

  @Test
  public void testSellFixRateExecute() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateSellFixRateResponse response = tradeServiceRaw.coinmateSellQuickFixRate(
        new BigDecimal("1.0"),
        null,
        CoinmateUtils.getPair(CurrencyPair.BTC_EUR)
    );
    assertFalse(response.isError());
    assertNull(response.getErrorMessage());
    CoinmateSellFixRateResponseData data = response.getData();
    String rateId = data.getRateId();
    assertNotNull(rateId);

    CoinmateTradeResponse response2 = tradeServiceRaw.sellCoinmateQuickFix(rateId, null);
    assertFalse(response2.isError());
    assertNull(response2.getErrorMessage());
    assertNotNull(response2.getData());
  }
}
