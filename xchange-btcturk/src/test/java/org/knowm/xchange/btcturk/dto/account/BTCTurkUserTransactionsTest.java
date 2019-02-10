package org.knowm.xchange.btcturk.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.btcturk.dto.BTCTurkOperations;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTickerTest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.DateUtils;

/** @author mertguner */
public class BTCTurkUserTransactionsTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/account/example-usertransactions-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkUserTransactions[] btcTurkUserTransactions =
        mapper.readValue(is, BTCTurkUserTransactions[].class);

    assertThat(btcTurkUserTransactions.length).isEqualTo(25);
    assertThat(btcTurkUserTransactions[0].getId()).isEqualTo("132599");
    assertThat(DateUtils.toUTCString(btcTurkUserTransactions[0].getDate()))
        .isEqualTo("2018-12-13 23:46:42 GMT");
    assertThat(btcTurkUserTransactions[0].getOperation()).isEqualTo(BTCTurkOperations.trade);
    assertThat(btcTurkUserTransactions[0].getCurrency()).isEqualTo(Currency.ETH);
    assertThat(btcTurkUserTransactions[0].getFunds())
        .isEqualTo(new BigDecimal("23494.6206940035832218"));
    assertThat(btcTurkUserTransactions[0].getAmount())
        .isEqualTo(new BigDecimal("-1.0000000000000000"));
    assertThat(btcTurkUserTransactions[0].getFee())
        .isEqualTo(new BigDecimal("-0.5720337000000000"));
    assertThat(btcTurkUserTransactions[0].getTax())
        .isEqualTo(new BigDecimal("-0.1029660660000000"));
    assertThat(btcTurkUserTransactions[0].getPrice())
        .isEqualTo(new BigDecimal("450.0000000000000000"));
  }
}
