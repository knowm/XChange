package org.knowm.xchange.bitcoincore.dto.account;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class BitcoinCoreErrorTest {

  @Test
  void unmarshal() throws Exception {
    InputStream is = getClass().getResourceAsStream("/account/example-error.json");
    ObjectMapper mapper = new ObjectMapper();
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(() -> mapper.readValue(is, BitcoinCoreBalanceResponse.class));
  }
}
