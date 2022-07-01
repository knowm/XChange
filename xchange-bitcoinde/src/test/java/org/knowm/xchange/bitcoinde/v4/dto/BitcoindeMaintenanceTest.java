package org.knowm.xchange.bitcoinde.v4.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class BitcoindeMaintenanceTest {

  @Test
  public void testBitcoindeMaintenance() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeMaintenanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/maintenance.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeMaintenance bitcoindeMaintenance = mapper.readValue(is, BitcoindeMaintenance.class);
    assertThat(bitcoindeMaintenance.getMessage())
        .isEqualTo(
            "*** Scheduled maintainance on 2018/01/26 at 04:00 am until approx. 06:00 am (CEST)*** Due to network infrastructure maintenance Bitcoin.de will be unavailable between 04:00 am and approx. 06:00 am (CEST). ***");

    assertThat(bitcoindeMaintenance.getStart()).isEqualTo("2018-01-26T04:00:00+01:00");
    assertThat(bitcoindeMaintenance.getEnd()).isEqualTo("2018-01-26T06:00:00+01:00");
  }
}
