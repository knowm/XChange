package org.knowm.xchange.globitex.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexMarketDataDtoJSONTest;

public class GlobitexTradeDtoJSONTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void globitexUserTradesJsonTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/trade/globitex-usertrades-example.json");

    GlobitexUserTrades usertrades = mapper.readValue(is, GlobitexUserTrades.class);

    assertThat(usertrades.getUserTrades().size()).isEqualTo(2);
    assertThat(usertrades.getUserTrades().get(0).getTradeId()).isEqualTo(39);
    assertThat(usertrades.getUserTrades().get(0).getSymbol()).isEqualTo("XBTEUR");
    assertThat(usertrades.getUserTrades().get(0).getSide()).isEqualTo("sell");
    assertThat(usertrades.getUserTrades().get(0).getOriginalOrderId()).isEqualTo("114");
    assertThat(usertrades.getUserTrades().get(0).getClientOrderId()).isEqualTo("FTO18jd4ou41--25");
    assertThat(usertrades.getUserTrades().get(0).getQuantity()).isEqualTo("10");
    assertThat(usertrades.getUserTrades().get(0).getPrice()).isEqualTo("150");
    assertThat(usertrades.getUserTrades().get(0).getTimestamp())
        .isEqualTo(new Date(1395231854030L));
    assertThat(usertrades.getUserTrades().get(0).getFee()).isEqualTo("0.03");
    assertThat(usertrades.getUserTrades().get(0).isLiqProvided()).isEqualTo(false);
    assertThat(usertrades.getUserTrades().get(0).getFeeCurrency()).isEqualTo("EUR");
    assertThat(usertrades.getUserTrades().get(0).getAccount()).isEqualTo("ADE922A21");
  }
}
