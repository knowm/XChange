package org.knowm.xchange.independentreserve.dto.trade;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;

public class IndependentReserveOrderDetailsResponseTest {

  @Test
  public void testUnmarshall() throws IOException {
    InputStream is =
        IndependentReserveOrderDetailsResponseTest.class.getResourceAsStream(
            "/org/knowm/xchange/independentreserve/dto/trade/GetOrderDetailsResponse.json");

    ObjectMapper mapper = new ObjectMapper();
    IndependentReserveOrderDetailsResponse response =
        mapper.readValue(is, IndependentReserveOrderDetailsResponse.class);
    assertThat(response.getOrderGuid()).isEqualTo("c7347e4c-b865-4c94-8f74-d934d4b0b177");
    assertThat(response.getCreatedTimestamp())
        .isEqualTo(Date.from(ZonedDateTime.of(2014, 9, 23, 12, 39, 34, 0, UTC).toInstant()));
    assertThat(response.getOrderType()).isEqualTo("MarketBid");
    assertThat(response.getVolumeOrdered()).isEqualByComparingTo(new BigDecimal(5));
    assertThat(response.getVolumeFilled()).isEqualByComparingTo(new BigDecimal(5));
    assertThat(response.getPrice()).isNull();
    assertThat(response.getAvgPrice()).isEqualByComparingTo(new BigDecimal(100));
    assertThat(response.getReservedAmount()).isEqualByComparingTo(new BigDecimal(0));
    assertThat(response.getPrimaryCurrencyCode()).isEqualTo("Xbt");
    assertThat(response.getSecondaryCurrencyCode()).isEqualTo("Usd");
  }
}
