package org.knowm.xchange.ascendex;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexOpenOrdersResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexOrderResponse;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

class AscendexTradeDataJSONTest {

  @Test
  void ascendexPlaceOrderResponseTest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        AscendexTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/ascendex/ascendexPlaceOrderResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<AscendexOrderResponse> ascendexAssets =
        mapper.readValue(is, new TypeReference<AscendexResponse<AscendexOrderResponse>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().getAc()).isEqualTo("MARGIN");
    assertThat(ascendexAssets.getData().getInfo().getOrderId())
        .isEqualTo("16e85b4d9b9a8bXHbAwwoqDoc3d66830");
  }

  @Test
  void ascendexOpenOrdersResponseTest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        AscendexTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/ascendex/ascendexOpenOrdersResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<List<AscendexOpenOrdersResponse>> ascendexAssets =
        mapper.readValue(
            is, new TypeReference<AscendexResponse<List<AscendexOpenOrdersResponse>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().size()).isEqualTo(1);
    assertThat(ascendexAssets.getData().get(0).getAvgPx()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(ascendexAssets.getData().get(0).getSymbol())
        .isEqualTo(CurrencyPairDeserializer.getCurrencyPairFromString("BTC/USDT").toString());
  }

  @Test
  void ascendexOrderHistoryResponseTest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        AscendexTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/ascendex/ascendexOrderHistoryResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<List<AscendexOpenOrdersResponse>> ascendexAssets =
        mapper.readValue(
            is, new TypeReference<AscendexResponse<List<AscendexOpenOrdersResponse>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().size()).isEqualTo(1);
    assertThat(ascendexAssets.getData().get(0).getAvgPx())
        .isEqualByComparingTo(BigDecimal.valueOf(7243.34));
    assertThat(ascendexAssets.getData().get(0).getSymbol())
        .isEqualTo(CurrencyPairDeserializer.getCurrencyPairFromString("BTC/USDT").toString());
  }
}
