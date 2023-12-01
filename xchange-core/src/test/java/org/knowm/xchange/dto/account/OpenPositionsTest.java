package org.knowm.xchange.dto.account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class OpenPositionsTest {

  @Test
  public void openPositionJsonMarchallTest() throws JsonProcessingException {

    List<OpenPosition> openPositionList = new ArrayList<>();
    OpenPosition openPosition =
        new OpenPosition.Builder()
            .instrument(CurrencyPair.BTC_USD)
            .type(OpenPosition.Type.LONG)
            .price(BigDecimal.ONE)
            .size(BigDecimal.ONE)
            .liquidationPrice(BigDecimal.TEN)
            .build();
    openPositionList.add(openPosition);
    openPositionList.add(new OpenPosition.Builder().instrument(CurrencyPair.BTC_USD).build());
    openPositionList.add(openPosition);
    OpenPositions openPositions = new OpenPositions(openPositionList);

    String json = new ObjectMapper().writeValueAsString(openPositions);

    OpenPositions result = new ObjectMapper().readValue(json, OpenPositions.class);

    assertThat(result).isInstanceOf(OpenPositions.class);
    assertThat(result.getOpenPositions().get(0)).isInstanceOf(OpenPosition.class);
    System.out.println(result.getOpenPositions());
  }
}
