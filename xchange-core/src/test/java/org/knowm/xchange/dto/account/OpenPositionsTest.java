package org.knowm.xchange.dto.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OpenPositionsTest {

  @Test
  public void openPositionJsonMarchallTest() throws JsonProcessingException {

    List<OpenPosition> openPositionList = new ArrayList<>();
    openPositionList.add(
        new OpenPosition(
            CurrencyPair.BTC_USD,
            OpenPosition.Type.LONG,
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.TEN));
    openPositionList.add(new OpenPosition.Builder().instrument(CurrencyPair.BTC_USD).build());
    openPositionList.add(
        new OpenPosition(
            CurrencyPair.BTC_USD,
            OpenPosition.Type.LONG,
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.TEN));
    OpenPositions openPositions = new OpenPositions(openPositionList);

    String json = new ObjectMapper().writeValueAsString(openPositions);

    OpenPositions result = new ObjectMapper().readValue(json, OpenPositions.class);

    assertThat(result).isInstanceOf(OpenPositions.class);
    assertThat(result.getOpenPositions().get(0)).isInstanceOf(OpenPosition.class);
    System.out.println(result.getOpenPositions());
  }
}
