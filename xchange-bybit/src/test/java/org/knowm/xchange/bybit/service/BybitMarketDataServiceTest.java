package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BybitMarketDataServiceTest extends BaseWiremockTest {

  @Test
  public void testGetTicker() throws Exception {
    Exchange bybitExchange = createExchange();
    MarketDataService marketDataService = bybitExchange.getMarketDataService();

    stubFor(
        get(urlPathEqualTo("/v5/market/tickers"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        IOUtils.resourceToString(
                            "/getTickerInverse.json5", StandardCharsets.UTF_8))));

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    assertThat(ticker.getInstrument().toString()).isEqualTo("BTC/USD");
    assertThat(ticker.getOpen()).isEqualTo(new BigDecimal("16464.50"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("16597.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("16596.00"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("16597.50"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("30912.50"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("15700.00"));
    assertThat(ticker.getVwap()).isNull();
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("49337318"));
    assertThat(ticker.getQuoteVolume()).isEqualTo(new BigDecimal("2352.94950046"));
    assertThat(ticker.getTimestamp()).isEqualTo(new Date(1672376496682L));
    assertThat(ticker.getBidSize()).isNull();
    assertThat(ticker.getAskSize()).isNull();
    assertThat(ticker.getPercentageChange()).isEqualTo(new BigDecimal("0.008047"));
  }
}
