package org.knowm.xchange.ftx.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.trade.FtxOrderSide;
import org.knowm.xchange.ftx.dto.trade.TradeDtosTest;

public class AccountDtosTest {

  @Test
  public void accountDtoUnmarshall() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        TradeDtosTest.class.getResourceAsStream("/responses/example-ftxAccountInfo.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<FtxAccountDto> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<FtxAccountDto>>() {});

    // Verify that the example data was unmarshalled correctly

    assertThat(ftxResponse.getResult().getPositions().size()).isEqualTo(1);
    assertThat(ftxResponse.getResult().getPositions().get(0).getSide())
        .isEqualTo(FtxOrderSide.sell);
    assertThat(ftxResponse.getResult().getUsername()).isEqualTo("user@domain.com");
    assertThat(ftxResponse.getResult().getLeverage()).isEqualByComparingTo(BigDecimal.valueOf(10));
  }
}
