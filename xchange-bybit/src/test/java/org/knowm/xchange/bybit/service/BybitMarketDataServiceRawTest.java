package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import jakarta.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.marketdata.BybitSymbol;

public class BybitMarketDataServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetSymbols() throws Exception {
    Exchange bybitExchange = createExchange();
    BybitMarketDataServiceRaw marketDataServiceRaw = (BybitMarketDataServiceRaw) bybitExchange.getMarketDataService();

    stubFor(
        get(urlPathEqualTo("/v2/public/symbols"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(IOUtils.resourceToString("/getSymbols.json5", StandardCharsets.UTF_8))
            )
    );

    List<BybitSymbol> symbols = marketDataServiceRaw.getSymbols().getResult();

    assertThat(symbols).hasSize(2);

    BybitSymbol btcusdt = symbols.get(0);
    assertThat(btcusdt.getName()).isEqualTo("BTCUSDT");
    assertThat(btcusdt.getAlias()).isEqualTo("BTCUSDT");
    assertThat(btcusdt.getStatus()).isEqualTo("Trading");
    assertThat(btcusdt.getBaseCurrency()).isEqualTo("BTC");
    assertThat(btcusdt.getQuoteCurrency()).isEqualTo("USDT");
    assertThat(btcusdt.getPriceScale()).isEqualTo(2);
    assertThat(btcusdt.getTakerFee()).isEqualTo(new BigDecimal("0.0006"));
    assertThat(btcusdt.getMakerFee()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(btcusdt.getFundingInterval()).isEqualTo(480);
    assertThat(btcusdt.getLeverageFilter().getMinLeverage()).isEqualTo(1);
    assertThat(btcusdt.getLeverageFilter().getMaxLeverage()).isEqualTo(100);
    assertThat(btcusdt.getLeverageFilter().getLeverageStep()).isEqualTo(new BigDecimal("0.01"));
    assertThat(btcusdt.getPriceFilter().getMinPrice()).isEqualTo(new BigDecimal("0.5"));
    assertThat(btcusdt.getPriceFilter().getMaxPrice()).isEqualTo(new BigDecimal("999999"));
    assertThat(btcusdt.getPriceFilter().getTickSize()).isEqualTo(new BigDecimal("0.5"));
    assertThat(btcusdt.getLotSizeFilter().getMaxTradingQty()).isEqualTo(new BigDecimal("20"));
    assertThat(btcusdt.getLotSizeFilter().getMinTradingQty()).isEqualTo(new BigDecimal("0.001"));
    assertThat(btcusdt.getLotSizeFilter().getQtyStep()).isEqualTo(new BigDecimal("0.001"));
    assertThat(btcusdt.getLotSizeFilter().getPostOnlyMaxTradingQty()).isEqualTo(new BigDecimal("100"));

  }

}