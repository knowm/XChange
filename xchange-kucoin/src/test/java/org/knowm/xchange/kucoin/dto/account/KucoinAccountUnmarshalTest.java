
package org.knowm.xchange.kucoin.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.dto.account.FundingRecord.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Account JSON parsing
 */
public class KucoinAccountUnmarshalTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBalanceUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KucoinAccountUnmarshalTest.class.getResourceAsStream("/account/example-balance.json");
    KucoinCoinBalance balance = mapper.readValue(is, KucoinCoinBalance.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(balance.getCoinType()).isEqualTo("XRB");
    assertThat(balance.getBalance()).isEqualTo(BigDecimal.valueOf(0.06870691));
    assertThat(balance.getFreezeBalance()).isEqualTo(BigDecimal.valueOf(0d));
  }

  @Test
  public void testFundingRecordUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KucoinAccountUnmarshalTest.class.getResourceAsStream("/account/example-wallet-record.json");
    KucoinWalletRecord balance = mapper.readValue(is, KucoinWalletRecord.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(balance.getCoinType()).isEqualTo("XRB");
    assertThat(balance.getCreatedAt()).isEqualTo(1516831164000L);
    assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(0.9));
    assertThat(balance.getAddress()).isEqualTo("xrb_1wgznwiqs5nthtr4qgrx4q1qchnj4nqi3cw8t6jgoh5cicx8qcc4fhebw63y");
    assertThat(balance.getFee()).isEqualTo(BigDecimal.valueOf(0.0));
    assertThat(balance.getOuterWalletTxid()).isEqualTo("9BBC27DB999B414212CB823C453F31C05488FD523625CAD5E06A24487DC7A62F");
    assertThat(balance.getOid()).isEqualTo("5a6901bc76088742e6dab974");
    assertThat(balance.getType()).isEqualTo(KucoinWalletOperation.DEPOSIT);
    assertThat(balance.getStatus()).isEqualTo(KucoinWalletOperationStatus.SUCCESS);
    assertThat(balance.getUpdatedAt()).isEqualTo(1516831164000L);
  }

}