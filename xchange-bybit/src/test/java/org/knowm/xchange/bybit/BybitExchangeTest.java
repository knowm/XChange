package org.knowm.xchange.bybit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BybitExchangeTest extends BybitExchangeWiremock {

  @Test
  void instruments_initialized() {
     Map<Instrument, InstrumentMetaData> actual = exchange.getExchangeMetaData().getInstruments();

     InstrumentMetaData expected = new InstrumentMetaData.Builder()
         .minimumAmount(new BigDecimal("0.000048"))
         .maximumAmount(new BigDecimal("71.73956243"))
         .counterMinimumAmount(new BigDecimal("1"))
         .counterMaximumAmount(new BigDecimal("2000000"))
         .priceScale(2)
         .volumeScale(6)
         .amountStepSize(new BigDecimal("0.000001"))
         .priceStepSize(new BigDecimal("0.01"))
         .build();

    assertThat(actual.keySet()).hasSize(2);

    assertThat(actual.get(CurrencyPair.BTC_USDT)).usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void currency_pair_by_symbol_initialized() {
    Map<String, CurrencyPair> actual = ((BybitExchangeMetadata) exchange.getExchangeMetaData()).getCurrencyPairBySymbol();

    Map<String, CurrencyPair> expected = new HashMap<>();
    expected.put("BTCUSDT", CurrencyPair.BTC_USDT);
    expected.put("ETHUSDT", CurrencyPair.ETH_USDT);

    assertThat(actual).isEqualTo(expected);
  }

}