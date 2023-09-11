package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bybit.BybitExchangeWiremock;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload.Category;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo.LotSizeFilter;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo.MarginTrading;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo.PriceFilter;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo.Status;
import org.knowm.xchange.bybit.dto.marketdata.BybitServerTime;
import org.knowm.xchange.currency.Currency;

public class BybitMarketDataServiceRawTest extends BybitExchangeWiremock {

  BybitMarketDataServiceRaw bybitMarketDataServiceRaw = (BybitMarketDataServiceRaw) exchange.getMarketDataService();

  @Test
  void server_time() throws IOException {
    BybitServerTime actual = bybitMarketDataServiceRaw.getServerTime();

    BybitServerTime expected = BybitServerTime.builder()
        .timestamp(Instant.ofEpochSecond(1694037473L))
        .timestampNano(Instant.ofEpochSecond(0L, 1694037473517512495L))
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void instruments_info() throws IOException {
    BybitCategorizedPayload<BybitInstrumentInfo> actual = bybitMarketDataServiceRaw.getInstrumentsInfo();

    BybitInstrumentInfo expected = BybitInstrumentInfo.builder()
        .symbol("BTCUSDT")
        .base(Currency.BTC)
        .counter(Currency.USDT)
        .innovation(false)
        .status(Status.TRADING)
        .marginTrading(MarginTrading.BOTH)
        .lotSizeFilter(LotSizeFilter.builder()
            .basePrecision(new BigDecimal("0.000001"))
            .quotePrecision(new BigDecimal("0.00000001"))
            .minOrderQty(new BigDecimal("0.000048"))
            .maxOrderQty(new BigDecimal("71.73956243"))
            .minOrderAmt(new BigDecimal("1"))
            .maxOrderAmt(new BigDecimal("2000000"))
            .build())
        .priceFilter(PriceFilter.builder()
            .tickSize(new BigDecimal("0.01"))
            .build())
        .build();

    assertThat(actual.getCategory()).isEqualTo(Category.SPOT);
    assertThat(actual.getList()).hasSize(2);

    assertThat(actual.getList()).first().usingRecursiveComparison().isEqualTo(expected);

  }

}