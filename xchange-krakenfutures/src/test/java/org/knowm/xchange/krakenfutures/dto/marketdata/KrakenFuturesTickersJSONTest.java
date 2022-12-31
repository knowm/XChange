package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.krakenfutures.Util;

/** @author Neil Panchen */
public class KrakenFuturesTickersJSONTest {

  @Test
  public void testUnmarshal1() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesTickersJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-tickers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesTickers cryptoFacilitiesTickers =
        mapper.readValue(is, KrakenFuturesTickers.class);

    // Verify that the example data was unmarshalled correctly for fwd contract
    assertThat(cryptoFacilitiesTickers.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getAskSize())
        .isEqualTo(new BigDecimal("2"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getLast())
        .isEqualTo(new BigDecimal("424.85"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getLow24H())
        .isEqualTo(new BigDecimal("422.9"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getBidSize())
        .isEqualTo(new BigDecimal("2"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getOpen24H())
        .isEqualTo(new BigDecimal("422.9"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getHigh24H())
        .isEqualTo(new BigDecimal("424.85"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getMarkPrice())
        .isEqualTo(new BigDecimal("421.7"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getAsk())
        .isEqualTo(new BigDecimal("427.81"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getBid())
        .isEqualTo(new BigDecimal("426.75"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getLastSize())
        .isEqualTo(new BigDecimal("4"));
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getVol24H())
        .isEqualTo(new BigDecimal("5"));

    // 2016-04-04 18:19:56 UTC

    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5").getLastTime())
        .isEqualTo(Util.parseDate("2016-04-04T18:19:56.000Z"));

    // Verify that the example data was unmarshalled correctly for vol index contract
    assertThat(cryptoFacilitiesTickers.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesTickers.getTicker("CF-Bpi-V").getLast())
        .isEqualTo(new BigDecimal("29.31"));

    // 2016-04-05 08:49:41.116 UTC
    assertThat(cryptoFacilitiesTickers.getTicker("CF-Bpi-V").getLastTime())
        .isEqualTo(Util.parseDate("2016-04-05T08:49:41.116Z"));
  }

  @Test
  public void testUnmarshal2() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesTickersJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-ticker-data-2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesTickers cryptoFacilitiesTickers =
        mapper.readValue(is, KrakenFuturesTickers.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesTickers.isSuccess()).isFalse();
    assertThat(cryptoFacilitiesTickers.getTicker("F-XBT:USD-Apr16-W5")).isNull();
  }
}
