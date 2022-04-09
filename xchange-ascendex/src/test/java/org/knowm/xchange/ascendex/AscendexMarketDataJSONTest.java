package org.knowm.xchange.ascendex;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexOrderbookDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexProductDto;

public class AscendexMarketDataJSONTest {

  @Test
  public void ascendexAssetDtoTest() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        AscendexMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/ascendex/ascendexAssetsResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<List<AscendexAssetDto>> ascendexAssets =
        mapper.readValue(is, new TypeReference<AscendexResponse<List<AscendexAssetDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().size()).isEqualTo(1);
    assertThat(ascendexAssets.getData().get(0).getStatus())
        .isEqualTo(AscendexAssetDto.AscendexAssetStatus.Normal);
  }

  @Test
  public void ascendexProductDtoTest() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        AscendexMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/ascendex/ascendexProductsResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<List<AscendexProductDto>> ascendexAssets =
        mapper.readValue(is, new TypeReference<AscendexResponse<List<AscendexProductDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().size()).isEqualTo(1);
    assertThat(ascendexAssets.getData().get(0).getStatus())
        .isEqualTo(AscendexAssetDto.AscendexAssetStatus.Normal);
  }

  @Test
  public void ascendexOrderbookDtoTest() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        AscendexMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/ascendex/ascendexOrderbookResponseExample.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    AscendexResponse<AscendexOrderbookDto> ascendexAssets =
        mapper.readValue(is, new TypeReference<AscendexResponse<AscendexOrderbookDto>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ascendexAssets.getData().getData().getSeqnum()).isEqualTo(5068757);
    assertThat(ascendexAssets.getData().getM()).isEqualTo("depth-snapshot");
    assertThat(ascendexAssets.getData().getData().getAsks().get(0).getPrice())
        .isEqualByComparingTo(BigDecimal.valueOf(0.06848));
    assertThat(ascendexAssets.getData().getData().getAsks().get(0).getVolume())
        .isEqualByComparingTo(BigDecimal.valueOf(4084.2));
  }
}
