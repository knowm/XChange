package com.xeiam.xchange.ripple.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RippleAccountTest {

  @Test
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = RippleAccount.class.getResourceAsStream("/account/example-account.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccount account = mapper.readValue(is, RippleAccount.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(account.getLedger()).isEqualTo("13895887");
    assertThat(account.getValidated()).isEqualTo(true);
    assertThat(account.isSuccess()).isEqualTo(true);

    // check balances are correct
    final List<RippleBalance> balances = account.getBalances();
    assertThat(balances).hasSize(3);
    
    final Iterator<RippleBalance> iterator = balances.iterator();
    
    final RippleBalance balance1 = iterator.next();
    assertThat(balance1.getValue()).isEqualTo("861.401578");
    assertThat(balance1.getCurrency()).isEqualTo("XRP");
    assertThat(balance1.getCounterparty()).isEqualTo("");

    final RippleBalance balance2 = iterator.next();
    assertThat(balance2.getValue()).isEqualTo("0.038777349225374");
    assertThat(balance2.getCurrency()).isEqualTo("BTC");
    assertThat(balance2.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    final RippleBalance balance3 = iterator.next();
    assertThat(balance3.getValue()).isEqualTo("10");
    assertThat(balance3.getCurrency()).isEqualTo("USD");
    assertThat(balance3.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
  }
}
