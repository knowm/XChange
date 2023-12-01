package org.knowm.xchange.btcturk.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTickerTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author mertguner
 */
public class BTCTurkOpenOrdersTest {

  @Test
  public void testWithStaticData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/trade/example-openorders-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkOpenOrders[] btcTurkOpenOrders = mapper.readValue(is, BTCTurkOpenOrders[].class);

    assertThat(btcTurkOpenOrders.length).isEqualTo(2);
    assertThat(btcTurkOpenOrders[0].getId()).isEqualTo("23674042");
    assertThat(DateUtils.toUTCString(btcTurkOpenOrders[0].getDatetime()))
        .isEqualTo("2019-01-04 11:46:49 GMT");
    assertThat(btcTurkOpenOrders[0].getType()).isEqualTo("SellBtc");
    assertThat(btcTurkOpenOrders[0].getPrice()).isEqualTo(new BigDecimal("1956.0000000000000000"));
    assertThat(btcTurkOpenOrders[0].getAmount()).isEqualTo(new BigDecimal("0.6316092000000000"));
    assertThat(btcTurkOpenOrders[0].getPairsymbol().pair).isEqualTo(CurrencyPair.ETH_TRY);
  }
}
