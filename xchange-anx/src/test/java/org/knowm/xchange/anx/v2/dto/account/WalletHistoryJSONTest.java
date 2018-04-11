package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

// import static org.assertj.core.api.Assertions.assertThat;

/** Test ANXWalletHistory JSON parsing */
public class WalletHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WalletHistoryJSONTest.class.getResourceAsStream(
            "/v2/account/example-wallethistory-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ANXWalletHistoryWrapper anxWalletHistoryWrapper =
        mapper.readValue(is, ANXWalletHistoryWrapper.class);

    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory() != null);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getRecords() == 104);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getCurrentPage() == 1);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getMaxPage() == 3);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getMaxResults() == 50);
    Assert.assertTrue(
        anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries().length == 50);

    Assert.assertEquals(
        "BTC bought: [tid:264f7b7f-f70c-4fc6-ba27-2bcf33b6cd51] 10.00000000 BTC at 280.65500 HKD",
        anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getInfo());

    Assert.assertEquals(
        104,
        anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getIndex());

    Assert.assertEquals(
        "1394594770000",
        anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getDate());

    Assert.assertEquals(
        "fee",
        anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getType());

    Assert.assertEquals(
        "BTC",
        anxWalletHistoryWrapper
            .getANXWalletHistory()
            .getANXWalletHistoryEntries()[0]
            .getValue()
            .getCurrency());

    // Assert.assertEquals(new BigDecimal(0),
    // anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getValue().getValue());

    Assert.assertEquals(
        new BigDecimal("103168.75400000"),
        anxWalletHistoryWrapper
            .getANXWalletHistory()
            .getANXWalletHistoryEntries()[0]
            .getBalance()
            .getValue());

    Assert.assertEquals(
        "cc496636-4849-4acf-a390-e4091a5009c3",
        anxWalletHistoryWrapper
            .getANXWalletHistory()
            .getANXWalletHistoryEntries()[0]
            .getTrade()
            .getOid());

    Assert.assertEquals(
        "264f7b7f-f70c-4fc6-ba27-2bcf33b6cd51",
        anxWalletHistoryWrapper
            .getANXWalletHistory()
            .getANXWalletHistoryEntries()[0]
            .getTrade()
            .getTid());

    Assert.assertEquals(
        new BigDecimal("10.00000000"),
        anxWalletHistoryWrapper
            .getANXWalletHistory()
            .getANXWalletHistoryEntries()[0]
            .getTrade()
            .getAmount()
            .getValue());

    Assert.assertEquals(
        "market",
        anxWalletHistoryWrapper
            .getANXWalletHistory()
            .getANXWalletHistoryEntries()[0]
            .getTrade()
            .getProperties());
  }
}
