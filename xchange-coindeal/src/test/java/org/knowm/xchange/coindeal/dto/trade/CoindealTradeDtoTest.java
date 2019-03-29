package org.knowm.xchange.coindeal.dto.trade;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.BigDecimalAssert;
import org.junit.Test;

public class CoindealTradeDtoTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testUnmarshall() throws IOException {

    InputStream is =
        CoindealTradeDtoTest.class.getResourceAsStream(
                "/org/knowm/xchange/coindeal/dto/trade/example-tradehistory.json");

    List<CoindealTradeHistory> coindealTradeHistory =
            mapper.readValue(is, new TypeReference<List<CoindealTradeHistory>>(){});

    // verify that the example data was unmarshalled correctly
    assertThat(coindealTradeHistory.size()).isEqualTo(2);
    assertThat(coindealTradeHistory.get(0).getId()).isEqualTo("id");
    assertThat(coindealTradeHistory.get(0).getClientOrderId()).isEqualTo("clientOrderId");
    assertThat(coindealTradeHistory.get(0).getOrderId()).isEqualTo("orderId");
    assertThat(coindealTradeHistory.get(0).getSymbol()).isEqualTo("BTCEUR");
    assertThat(coindealTradeHistory.get(0).getSide()).isEqualTo("BUY");
    assertThat(coindealTradeHistory.get(0).getQuantity()).isEqualTo(new BigDecimal("10.00"));
    assertThat(coindealTradeHistory.get(0).getFee()).isEqualTo(new BigDecimal("0.1"));
    assertThat(coindealTradeHistory.get(0).getPrice()).isEqualTo(new BigDecimal("10.00"));
    assertThat(coindealTradeHistory.get(0).getTimestamp()).isEqualTo("2019-01-30 18:13:36");
  }
}
