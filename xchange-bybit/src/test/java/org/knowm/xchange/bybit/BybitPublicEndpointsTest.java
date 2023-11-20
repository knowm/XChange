package org.knowm.xchange.bybit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitPublicEndpointsTest {

  private static final Exchange exchange = BybitExchangeInit.getBybitExchange();
  private final Logger LOG = LoggerFactory.getLogger(BybitPublicEndpointsTest.class);
  @Test
  public void checkInstrumentMetaData() {
    exchange
        .getExchangeMetaData()
        .getInstruments()
        .forEach(
            (instrument1, instrumentMetaData) -> {
              LOG.debug(instrument1 + "||" + instrumentMetaData);
              assertThat(instrumentMetaData.getMinimumAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(instrumentMetaData.getMaximumAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(instrumentMetaData.getAmountStepSize()).isGreaterThan(BigDecimal.ZERO);
              assertThat(instrumentMetaData.getPriceStepSize()).isGreaterThan(BigDecimal.ZERO);
              assertThat(instrumentMetaData.getPriceScale()).isNotNegative();
              assertThat(instrumentMetaData.getVolumeScale()).isNotNegative();
            });
  }

  @Test
  public void checkCurrenciesMetaData() {
    Map<Currency, CurrencyMetaData> currencyMetaDataMap = exchange.getExchangeMetaData().getCurrencies();
    Map<Currency, Integer> numberOfOccurrences = new HashMap<>();

    currencyMetaDataMap.forEach((currency,currencyMetaData) -> {
      assertThat(currency).isNotNull();
      assertThat(currencyMetaData.getScale()).isNotNegative();

      if(numberOfOccurrences.containsKey(currency)){
        numberOfOccurrences.put(currency, numberOfOccurrences.get(currency) + 1);
      } else {
        numberOfOccurrences.put(currency, 1);
      }
    });

    numberOfOccurrences.forEach((currency,integer) -> assertThat(integer).isEqualTo(1));
  }
}
