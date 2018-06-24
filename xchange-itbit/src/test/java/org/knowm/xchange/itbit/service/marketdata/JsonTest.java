package org.knowm.xchange.itbit.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitTradeHistory;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitUserTrade;

/** Test Transaction[] JSON parsing */
public class JsonTest {

  @Test
  public void testTradeHistory() throws IOException {
    InputStream is =
        JsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/itbit/service/marketdata/ItBitTradeHistory.json");

    ObjectMapper mapper = new ObjectMapper();
    ItBitTradeHistory tradeHistory = mapper.readValue(is, ItBitTradeHistory.class);

    assertThat(tradeHistory.getCurrentPageNumber()).isEqualTo(1);
    assertThat(tradeHistory.getTotalNumberOfRecords()).isEqualTo(2);
    assertThat(tradeHistory.getLatestExecutionId()).isEqualTo("332");
    assertThat(tradeHistory.getRecordsPerPage()).isEqualTo(50);

    assertThat(tradeHistory.getTradingHistory()).hasSize(2);

    ItBitUserTrade userTrade = tradeHistory.getTradingHistory().get(0);

    assertThat(userTrade.getDirection()).isEqualTo(ItBitUserTrade.Direction.buy);
    assertThat(userTrade.getCurrency1()).isEqualTo("XBT");
    assertThat(userTrade.getCurrency1Amount()).isEqualByComparingTo(new BigDecimal("0.0001"));
    assertThat(userTrade.getRebatesApplied()).isEqualTo(new BigDecimal("-0.000125265"));
    assertThat(userTrade.getTimestamp())
        .withDateFormat(new ISO8601DateFormat())
        .isEqualTo("2015-05-11T14:48:01.987Z");
  }
}
