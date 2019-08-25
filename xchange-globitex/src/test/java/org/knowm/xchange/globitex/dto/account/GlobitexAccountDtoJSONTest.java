package org.knowm.xchange.globitex.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class GlobitexAccountDtoJSONTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void globitexAccountJsonTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexAccountDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/account/globitex-accounts-example.json");

    GlobitexAccounts accounts = mapper.readValue(is, GlobitexAccounts.class);

    Assert.assertEquals(2, accounts.getAccounts().size());
    assertThat(accounts.getAccounts().get(0).getAccount()).isEqualTo("AFN561A01");
    assertThat(accounts.getAccounts().get(0).isMain()).isEqualTo(true);
    assertThat(accounts.getAccounts().get(0).getBalance().get(0).getCurrency()).isEqualTo("EUR");
    assertThat(accounts.getAccounts().get(0).getBalance().get(0).getAvailable()).isEqualTo("100.0");
    assertThat(accounts.getAccounts().get(0).getBalance().get(0).getReserved()).isEqualTo("0.0");
  }
}
