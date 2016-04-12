package org.knowm.xchange.lakebtc.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCAccountJsonTest {

  @Test
  public void testDeserializeAccount() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCAccountJsonTest.class.getResourceAsStream("/account/example-account-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCAccount account = mapper.readValue(is, LakeBTCAccount.class);

    LakeBTCProfile profile = account.getProfile();
    LakeBTCBalance balance = account.getBalance();

    assertThat(profile.getBtcDepositAddres()).isEqualTo("16pVfBxNog3pxtbaEBVDEVRZJhJ8PZoxnX");
    assertThat(profile.getEmail()).isEqualTo("foo@bar.com");
    assertThat(profile.getId()).isEqualTo("U826459145");

    assertThat(balance.getBTC().toString()).isEqualTo("20.8613");
    assertThat(balance.getCNY().toString()).isEqualTo("1008215.55");
    assertThat(balance.getUSD().toString()).isEqualTo("4829.33");
  }

}
