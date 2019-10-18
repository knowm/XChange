package org.knowm.xchange.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class TickerTest {

  @Test
  public void testSerializeDeserialize() throws IOException {
    Ticker ticker =
        new Ticker.Builder()
            .ask(new BigDecimal("0.12"))
            .askSize(new BigDecimal("0.13"))
            .bid(new BigDecimal("0.14"))
            .bidSize(new BigDecimal("0.14"))
            .currencyPair(CurrencyPair.ADA_BNB)
            .high(new BigDecimal("0.15"))
            .last(new BigDecimal("0.16"))
            .low(new BigDecimal("0.17"))
            .open(new BigDecimal("0.18"))
            .quoteVolume(new BigDecimal("0.19"))
            .timestamp(new Date())
            .volume(new BigDecimal("100000"))
            .vwap(new BigDecimal("0.2"))
            .build();
    Ticker copy = ObjectMapperHelper.viaJSON(ticker);
    assertThat(copy).isEqualToComparingFieldByField(ticker);
  }
}
