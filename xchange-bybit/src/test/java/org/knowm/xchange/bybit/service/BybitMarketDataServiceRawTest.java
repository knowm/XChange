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
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfos;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInstrumentInfo.ContractType;

public class BybitMarketDataServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetLinearInstrumentsInfo() throws Exception {
    Exchange bybitExchange = createExchange();
    BybitMarketDataServiceRaw marketDataServiceRaw =
        (BybitMarketDataServiceRaw) bybitExchange.getMarketDataService();

    stubFor(
        get(urlPathEqualTo("/v5/market/instruments-info"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        IOUtils.resourceToString(
                            "/getInstrumentLinear.json5", StandardCharsets.UTF_8))));

    BybitInstrumentInfos<BybitInstrumentInfo> instrumentsInfo =
        marketDataServiceRaw.getInstrumentsInfo(BybitCategory.LINEAR).getResult();

    assertThat(instrumentsInfo.getList()).hasSize(1);

    BybitLinearInstrumentInfo actualInstrumentInfo =
        (BybitLinearInstrumentInfo) instrumentsInfo.getList().get(0);

    assertThat(actualInstrumentInfo.getSymbol()).isEqualTo("BTCUSDT");
    assertThat(actualInstrumentInfo.getContractType()).isEqualTo(ContractType.LINEAR_PERPETUAL);
    assertThat(actualInstrumentInfo.getStatus().name())
        .isEqualToIgnoringCase("Trading"); // Assuming InstrumentStatus is a string enum or constant
    assertThat(actualInstrumentInfo.getBaseCoin()).isEqualTo("BTC");
    assertThat(actualInstrumentInfo.getQuoteCoin()).isEqualTo("USDT");
    assertThat(actualInstrumentInfo.getLaunchTime()).isEqualTo(new Date(1585526400000L));
    assertThat(actualInstrumentInfo.getDeliveryTime()).isEqualTo(new Date(0L));
    assertThat(actualInstrumentInfo.getDeliveryFeeRate())
        .isNull(); // Since it's an empty string in JSON
    assertThat(actualInstrumentInfo.getPriceScale()).isEqualTo(2);
    assertThat(actualInstrumentInfo.getLeverageFilter().getMinLeverage()).isEqualTo(1);
    assertThat(actualInstrumentInfo.getLeverageFilter().getMaxLeverage())
        .isEqualTo(new BigDecimal("100.00"));
    assertThat(actualInstrumentInfo.getLeverageFilter().getLeverageStep())
        .isEqualTo(new BigDecimal("0.01"));
    assertThat(actualInstrumentInfo.getPriceFilter().getTickSize())
        .isEqualTo(new BigDecimal("0.50"));
    assertThat(actualInstrumentInfo.getPriceFilter().getMinPrice())
        .isEqualTo(new BigDecimal("0.50"));
    assertThat(actualInstrumentInfo.getPriceFilter().getMaxPrice())
        .isEqualTo(new BigDecimal("999999.00"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .isEqualTo(new BigDecimal("100.000"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
        .isEqualTo(new BigDecimal("0.001"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getQtyStep())
        .isEqualTo(new BigDecimal("0.001"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getPostOnlyMaxOrderQty())
        .isEqualTo(new BigDecimal("1000.000"));
    assertThat(actualInstrumentInfo.isUnifiedMarginTrade()).isTrue();
    assertThat(actualInstrumentInfo.getFundingInterval()).isEqualTo(480);
    assertThat(actualInstrumentInfo.getSettleCoin()).isEqualTo("USDT");
    assertThat(actualInstrumentInfo.getCopyTrading()).isNull();
  }
}
