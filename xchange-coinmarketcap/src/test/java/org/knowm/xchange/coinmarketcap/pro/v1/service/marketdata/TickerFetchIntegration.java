package org.knowm.xchange.coinmarketcap.pro.v1.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcExchange;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CmcMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;

public class TickerFetchIntegration {
  private static Exchange exchange;
  private static CmcMarketDataService cmcMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CmcExchange.class);
    exchange.applySpecification(((CmcExchange) exchange).getSandboxExchangeSpecification());
    cmcMarketDataService = (CmcMarketDataService) exchange.getMarketDataService();

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void getTickerTest() throws Exception {
    Ticker ticker = cmcMarketDataService.getTicker(CurrencyPair.BTC_USD);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(ticker.getVolume()).isGreaterThan(new BigDecimal("0"));
    assertThat(ticker.getLast()).isGreaterThan(new BigDecimal("0"));
  }

  @Test
  public void getTickersTest() throws Exception {
    CurrencyPairsParam pairsParam =
        () -> {
          Set<CurrencyPair> pairs = new HashSet<>();
          pairs.add(CurrencyPair.BTC_USD);
          pairs.add(CurrencyPair.ETH_USD);
          pairs.add(CurrencyPair.LTC_USD);
          return pairs;
        };

    List<Ticker> tickerList = cmcMarketDataService.getTickers(pairsParam);

    assertThat(tickerList).isNotNull();
    assertThat(tickerList).isNotEmpty();
    assertThat(tickerList.size()).isEqualTo(3);
  }

  @Test
  public void getAllTickersTest() throws Exception {
    List<Ticker> tickerList = cmcMarketDataService.getAllTickers();

    assertThat(tickerList).isNotNull();
    assertThat(tickerList).isNotEmpty();
  }
}
