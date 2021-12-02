package org.knowm.xchange.dto.account;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenPositionTest {

  @Test
  public void testSerializeDeserialize() throws IOException {

    OpenPosition position =
        new OpenPosition.Builder()
            .instrument(new FuturesContract(CurrencyPair.BTC_USD, null))
            .type(OpenPosition.Type.LONG)
            .size(new BigDecimal("0.17"))
            .price(new BigDecimal("54321"))
            .markPrice(null)
            .leverage(new BigDecimal("3"))
            .marginRatio(new BigDecimal("0.004"))
            .build();

    String json = ObjectMapperHelper.toCompactJSON(position);

    assertThat(json).contains("\"instrument\":\"BTC/USD/perpetual\"");

    OpenPosition jsonCopy = ObjectMapperHelper.readValueStrict(json, OpenPosition.class);
    assertThat(jsonCopy).isEqualToComparingFieldByField(position);
  }
}
