package org.knowm.xchange.ascendex.MarketData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexBarHistDto;
import org.knowm.xchange.ascendex.service.AscendexMarketDataService;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class AscendexMarketDataJSONTest {

  @Test
  public void ascendexAssetDtoTest() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
            org.knowm.xchange.ascendex.AscendexMarketDataJSONTest.class.getResourceAsStream(
                    "/org/knowm/xchange/ascendex/MarketData/ascendexAssetsResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<List<AscendexAssetDto>> ascendexAssets =
            mapper.readValue(is, new TypeReference<AscendexResponse<List<AscendexAssetDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().size()).isEqualTo(2);
    assertThat(ascendexAssets.getData().get(0).getStatus())
            .isEqualTo(AscendexAssetDto.AscendexAssetStatus.Normal);
    assertThat(ascendexAssets.getData().get(0).getWithdrawFee())
            .isEqualTo(new BigDecimal("30.0"));
  }
}
