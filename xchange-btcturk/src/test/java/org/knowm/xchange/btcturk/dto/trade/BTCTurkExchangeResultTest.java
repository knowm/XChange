package org.knowm.xchange.btcturk.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTickerTest;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author mertguner
 */
class BTCTurkExchangeResultTest {
  @Test
  void withStaticData() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/trade/example-exchange-result-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkExchangeResult btcTurkExchangeResult = mapper.readValue(is, BTCTurkExchangeResult.class);

    assertThat(btcTurkExchangeResult.getId()).isEqualTo("24502215");
    assertThat(DateUtils.toUTCString(btcTurkExchangeResult.getDatetime()))
        .isEqualTo("2019-01-14 17:55:31 GMT");
    assertThat(btcTurkExchangeResult.getType()).isEqualTo(BTCTurkOrderTypes.Sell);
    assertThat(btcTurkExchangeResult.getPrice()).isEqualTo(new BigDecimal("900"));
    assertThat(btcTurkExchangeResult.getAmount()).isEqualTo(new BigDecimal("0.0123"));
  }
}
