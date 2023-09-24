package org.knowm.xchange.bybit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitPublicEndpointsTest {

  Exchange exchange = BybitExchangeInit.getBybitExchange();
  private final Logger LOG = LoggerFactory.getLogger(BybitPublicEndpointsTest.class);
  @Test
  public void checkInstrumentMetaData() {
    exchange.getExchangeMetaData().getInstruments().forEach((instrument1, instrumentMetaData) -> {
      LOG.debug(instrument1+"||"+instrumentMetaData);
      assertThat(instrumentMetaData.getMinimumAmount()).isGreaterThan(BigDecimal.ZERO);
      assertThat(instrumentMetaData.getMaximumAmount()).isGreaterThan(BigDecimal.ZERO);
      assertThat(instrumentMetaData.getAmountStepSize()).isGreaterThan(BigDecimal.ZERO);
      assertThat(instrumentMetaData.getPriceStepSize()).isGreaterThan(BigDecimal.ZERO);
      assertThat(instrumentMetaData.getPriceScale()).isGreaterThanOrEqualTo(0);
      assertThat(instrumentMetaData.getVolumeScale()).isGreaterThanOrEqualTo(0);
    });
  }
}
