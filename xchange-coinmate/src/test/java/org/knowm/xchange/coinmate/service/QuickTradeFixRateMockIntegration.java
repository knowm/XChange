package org.knowm.xchange.coinmate.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
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
  void getBuyQuickRate() throws Exception {
    Exchange exchange = createMockExchangeUnauthenticated();
    CoinmateMarketDataServiceRaw marketDataService =
        (CoinmateMarketDataServiceRaw) exchange.getMarketDataService();
    CoinmateQuickRate response =
        marketDataService.getCoinmateBuyQuickRate(
            new BigDecimal("1.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    assertThat(response.getData()).isNotNull();
  }

  @Test
  void getSellQuickRate() throws Exception {
    Exchange exchange = createMockExchangeUnauthenticated();
    CoinmateMarketDataServiceRaw marketDataService =
        (CoinmateMarketDataServiceRaw) exchange.getMarketDataService();
    CoinmateQuickRate response =
        marketDataService.getCoinmateSellQuickRate(
            new BigDecimal("1.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    assertThat(response.getData()).isNotNull();
  }

  @Test
  void buyFixRateTotal() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateBuyFixRateResponse response =
        tradeServiceRaw.coinmateBuyQuickFixRate(
            new BigDecimal("1.0"), null, CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    CoinmateBuyFixRateResponseData data = response.getData();
    assertThat(data.getRateId()).isNotNull();
    assertThat(data.getExpiresAt()).isNotNull();
    assertThat(data.getCurrencyPair()).isNotNull();
    assertThat(data.getRate()).isNotNull();
    assertThat(data.getTotal().compareTo(new BigDecimal("1.0"))).isEqualTo(0);
    assertThat(data.getAmountReceived()).isNotNull();
  }

  @Test
  void buyFixRateAmount() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateBuyFixRateResponse response =
        tradeServiceRaw.coinmateBuyQuickFixRate(
            null, new BigDecimal("100.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    CoinmateBuyFixRateResponseData data = response.getData();
    assertThat(data.getRateId()).isNotNull();
    assertThat(data.getExpiresAt()).isNotNull();
    assertThat(data.getCurrencyPair()).isNotNull();
    assertThat(data.getRate()).isNotNull();
    assertThat(data.getAmountReceived().compareTo(new BigDecimal("100.0"))).isEqualTo(0);
    assertThat(data.getTotal()).isNotNull();
  }

  @Test
  void sellFixRateTotal() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateSellFixRateResponse response =
        tradeServiceRaw.coinmateSellQuickFixRate(
            new BigDecimal("1.0"), null, CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    CoinmateSellFixRateResponseData data = response.getData();
    assertThat(data.getRateId()).isNotNull();
    assertThat(data.getExpiresAt()).isNotNull();
    assertThat(data.getCurrencyPair()).isNotNull();
    assertThat(data.getRate()).isNotNull();
    assertThat(data.getAmount().compareTo(new BigDecimal("1.0"))).isEqualTo(0);
    assertThat(data.getTotalReceived()).isNotNull();
  }

  @Test
  void sellFixRateAmount() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateSellFixRateResponse response =
        tradeServiceRaw.coinmateSellQuickFixRate(
            null, new BigDecimal("100.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    CoinmateSellFixRateResponseData data = response.getData();
    assertThat(data.getRateId()).isNotNull();
    assertThat(data.getExpiresAt()).isNotNull();
    assertThat(data.getCurrencyPair()).isNotNull();
    assertThat(data.getRate()).isNotNull();
    assertThat(data.getTotalReceived().compareTo(new BigDecimal("100.0"))).isEqualTo(0);
    assertThat(data.getAmount()).isNotNull();
  }

  @Test
  void buyFixRateExecute() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateBuyFixRateResponse response =
        tradeServiceRaw.coinmateBuyQuickFixRate(
            null, new BigDecimal("100.0"), CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    CoinmateBuyFixRateResponseData data = response.getData();
    String rateId = data.getRateId();
    assertThat(rateId).isNotNull();

    CoinmateTradeResponse response2 = tradeServiceRaw.buyCoinmateQuickFix(rateId, null);
    assertThat(response2.isError()).isFalse();
    assertThat(response2.getErrorMessage()).isNull();
    assertThat(response2.getData()).isNotNull();
  }

  @Test
  void sellFixRateExecute() throws Exception {
    Exchange exchange = createMockExchange();
    TradeService tradeService = exchange.getTradeService();
    CoinmateTradeServiceRaw tradeServiceRaw = (CoinmateTradeServiceRaw) tradeService;

    CoinmateSellFixRateResponse response =
        tradeServiceRaw.coinmateSellQuickFixRate(
            new BigDecimal("1.0"), null, CoinmateUtils.getPair(CurrencyPair.BTC_EUR));
    assertThat(response.isError()).isFalse();
    assertThat(response.getErrorMessage()).isNull();
    CoinmateSellFixRateResponseData data = response.getData();
    String rateId = data.getRateId();
    assertThat(rateId).isNotNull();

    CoinmateTradeResponse response2 = tradeServiceRaw.sellCoinmateQuickFix(rateId, null);
    assertThat(response2.isError()).isFalse();
    assertThat(response2.getErrorMessage()).isNull();
    assertThat(response2.getData()).isNotNull();
  }
}
