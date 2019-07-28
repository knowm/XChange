package org.knowm.xchange.coindeal.dto.account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

public class CoindealAccountDtoTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void getAccountBalanceTest() throws IOException {
    InputStream is =
        ClassLoader.getSystemClassLoader()
            .getResourceAsStream(
                "org/knowm/xchange/coindeal/dto/account/example-coindealBalances.json");

    List<CoindealBalance> coindealBalances =
        mapper.readValue(is, new TypeReference<List<CoindealBalance>>() {});

    // verify that the example data was unmarshalled correctly
    assertThat(coindealBalances.get(0).getCurrency()).isEqualTo("Bitcoin");
    assertThat(coindealBalances.get(0).getAvailable()).isEqualTo(new BigDecimal("10.00"));
    assertThat(coindealBalances.get(0).getReserved()).isEqualTo(new BigDecimal("11.00"));
  }
}
