package org.knowm.xchange.bitmex.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Test BitstampTicker JSON parsing */
public class BitmexWalletTransactionJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitmexWalletTransactionJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitmex/dto/account/example-wallet-transaction.json");

    ObjectMapper mapper = new ObjectMapper();
    BitmexWalletTransaction[] bitmexWalletTransactions =
        mapper.readValue(is, BitmexWalletTransaction[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitmexWalletTransactions[0].getTransactID())
        .isEqualTo("00000000-0000-0000-0000-000000000000");
    assertThat(bitmexWalletTransactions[0].getAccount()).isEqualTo(8556985);
    assertThat(bitmexWalletTransactions[0].getCurrency()).isEqualTo("XBt");
    assertThat(bitmexWalletTransactions[0].getTransactType()).isEqualTo("UnrealisedPNL");
    assertThat(bitmexWalletTransactions[0].getAmount()).isEqualByComparingTo("-5.17400620");
    assertThat(bitmexWalletTransactions[0].getFee()).isEqualByComparingTo("0.0");
    assertThat(bitmexWalletTransactions[0].getTransactStatus()).isEqualTo("Pending");
    assertThat(bitmexWalletTransactions[0].getAddress()).isEqualTo("ETHUSD");
    assertThat(bitmexWalletTransactions[0].getTx()).isBlank();
    assertThat(bitmexWalletTransactions[0].getText()).isBlank();
    assertThat(bitmexWalletTransactions[0].getTransactTime()).isNull();
    assertThat(bitmexWalletTransactions[0].getWalletBalance()).isEqualByComparingTo("9.24493333");
    assertThat(bitmexWalletTransactions[0].getMarginBalance()).isEqualByComparingTo("4.07092713");
    assertThat(bitmexWalletTransactions[0].getTimestamp()).isNull();

    assertThat(bitmexWalletTransactions[1].getTransactID())
        .isEqualTo("00000000-0000-0000-0000-000000000000");
    assertThat(bitmexWalletTransactions[1].getAccount()).isEqualTo(8556985);
    assertThat(bitmexWalletTransactions[1].getCurrency()).isEqualTo("XBt");
    assertThat(bitmexWalletTransactions[1].getTransactType()).isEqualTo("RealisedPNL");
    assertThat(bitmexWalletTransactions[1].getAmount()).isEqualByComparingTo("0.06574801");
    assertThat(bitmexWalletTransactions[1].getFee()).isEqualByComparingTo("0");
    assertThat(bitmexWalletTransactions[1].getTransactStatus()).isEqualTo("Completed");
    assertThat(bitmexWalletTransactions[1].getAddress()).isEqualTo("ETHUSD");
    assertThat(bitmexWalletTransactions[1].getTx()).isBlank();
    assertThat(bitmexWalletTransactions[1].getText()).isBlank();
    assertThat(bitmexWalletTransactions[1].getTransactTime()).isEqualTo("2020-03-09T12:00:00.000Z");
    assertThat(bitmexWalletTransactions[1].getWalletBalance()).isEqualByComparingTo("9.24493333");
    assertThat(bitmexWalletTransactions[1].getMarginBalance()).isNull();
    assertThat(bitmexWalletTransactions[1].getTimestamp()).isEqualTo("2020-03-09T12:00:00.000Z");

    assertThat(bitmexWalletTransactions[2].getTransactID())
        .isEqualTo("ff12a18b-7879-212a-41cb-688f9c3eff69");
    assertThat(bitmexWalletTransactions[2].getAccount()).isEqualTo(8556985);
    assertThat(bitmexWalletTransactions[2].getCurrency()).isEqualTo("XBt");
    assertThat(bitmexWalletTransactions[2].getTransactType()).isEqualTo("Deposit");
    assertThat(bitmexWalletTransactions[2].getAmount()).isEqualByComparingTo("1.99960000");
    assertThat(bitmexWalletTransactions[2].getFee()).isNull();
    assertThat(bitmexWalletTransactions[2].getTransactStatus()).isEqualTo("Completed");
    assertThat(bitmexWalletTransactions[2].getAddress()).isBlank();
    assertThat(bitmexWalletTransactions[2].getTx()).isBlank();
    assertThat(bitmexWalletTransactions[2].getText()).isBlank();
    assertThat(bitmexWalletTransactions[2].getTransactTime()).isEqualTo("2020-03-05T11:09:46.352Z");
    assertThat(bitmexWalletTransactions[2].getWalletBalance()).isEqualByComparingTo("4.21321128");
    assertThat(bitmexWalletTransactions[2].getMarginBalance()).isNull();
    assertThat(bitmexWalletTransactions[2].getTimestamp()).isEqualTo("2020-03-05T11:09:46.352Z");

    assertThat(bitmexWalletTransactions[3].getTransactID())
        .isEqualTo("de70230c-1f0d-3f9a-b0a2-48658afa3b63");
    assertThat(bitmexWalletTransactions[3].getAccount()).isEqualTo(8556985);
    assertThat(bitmexWalletTransactions[3].getCurrency()).isEqualTo("XBt");
    assertThat(bitmexWalletTransactions[3].getTransactType()).isEqualTo("Withdrawal");
    assertThat(bitmexWalletTransactions[3].getAmount()).isEqualByComparingTo("-1.22279653");
    assertThat(bitmexWalletTransactions[3].getFee()).isEqualByComparingTo("0.00100000");
    assertThat(bitmexWalletTransactions[3].getTransactStatus()).isEqualTo("Completed");
    assertThat(bitmexWalletTransactions[3].getAddress())
        .isEqualTo("13g4J4JVWvMakcBAvfMHAAdt1PfDMF5rU8");
    assertThat(bitmexWalletTransactions[3].getTx()).isEqualTo("2BTEX1RxKBinepEXeHxLzujhPc5ZYqjoUJ");
    assertThat(bitmexWalletTransactions[3].getText()).isBlank();
    assertThat(bitmexWalletTransactions[3].getTransactTime()).isEqualTo("2020-02-11T09:27:57.267Z");
    assertThat(bitmexWalletTransactions[3].getWalletBalance()).isEqualByComparingTo("0.25968238");
    assertThat(bitmexWalletTransactions[3].getMarginBalance()).isNull();
    assertThat(bitmexWalletTransactions[3].getTimestamp()).isEqualTo("2020-02-11T11:19:16.151Z");
  }
}
