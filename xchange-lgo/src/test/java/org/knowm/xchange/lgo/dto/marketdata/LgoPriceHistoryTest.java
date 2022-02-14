package org.knowm.xchange.lgo.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.lgo.dto.product.LgoProductsTest;

public class LgoPriceHistoryTest {

  private SimpleDateFormat dateFormat;

  @Before
  public void setUp() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Test
  public void itCanReadJson() throws IOException, ParseException {
    InputStream is =
        LgoProductsTest.class.getResourceAsStream(
            "/org/knowm/xchange/lgo/marketdata/example-pricehistory-data.json");
    ObjectMapper mapper = new ObjectMapper();

    LgoPriceHistory response = mapper.readValue(is, LgoPriceHistory.class);

    assertThat(response).isNotNull();
    assertThat(response.getPrices()).hasSize(2);
    LgoCandlestick candlestick = response.getPrices().get(0);
    assertThat(candlestick.getTime()).isEqualTo(dateFormat.parse("2019-12-20T15:00:00Z"));
    assertThat(candlestick.getLow()).isEqualTo(new BigDecimal("4396.7000"));
    assertThat(candlestick.getHigh()).isEqualTo(new BigDecimal("4654.4000"));
    assertThat(candlestick.getOpen()).isEqualTo(new BigDecimal("4592.6000"));
    assertThat(candlestick.getClose()).isEqualTo(new BigDecimal("4531.6000"));
    assertThat(candlestick.getVolume()).isEqualTo(new BigDecimal("485.35050000"));
  }
}
