package org.knowm.xchange.gateio;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

public class GateioExchangeTest extends GateioExchangeWiremock {

  @Test
  public void metadata_present() {
    InstrumentMetaData expected = new InstrumentMetaData.Builder()
        .tradingFee(new BigDecimal("0.2"))
        .minimumAmount(new BigDecimal("0.0001"))
        .priceScale(1)
        .marketOrderEnabled(false)
        .build();

    InstrumentMetaData actual = exchange.getExchangeMetaData().getInstruments().get(CurrencyPair.BTC_USDT);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

}