package org.knowm.xchange.ascendex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexOrderbookDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexProductDto;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AscendexMarketDataJSONTest {

    @Test
    public void bitmaxAssetDtoTest() throws IOException {

        // Read in the JSON from the example resources
        InputStream is =
                AscendexMarketDataJSONTest.class.getResourceAsStream(
                        "/org/knowm/xchange/ascendex/ascendexAssetsResponseExample.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        AscendexResponse<List<AscendexAssetDto>> bitmaxAssets = mapper.readValue(is, new TypeReference<AscendexResponse<List<AscendexAssetDto>>>(){});

        // Verify that the example data was unmarshalled correctly
        assertThat(bitmaxAssets.getData().size()).isEqualTo(1);
        assertThat(bitmaxAssets.getData().get(0).getStatus()).isEqualTo(AscendexAssetDto.BitmaxAssetStatus.Normal);

    }

    @Test
    public void bitmaxProductDtoTest() throws IOException {

        // Read in the JSON from the example resources
        InputStream is =
                AscendexMarketDataJSONTest.class.getResourceAsStream(
                        "/org/knowm/xchange/ascendex/ascendexProductsResponseExample.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        AscendexResponse<List<AscendexProductDto>> bitmaxAssets = mapper.readValue(is, new TypeReference<AscendexResponse<List<AscendexProductDto>>>(){});

        // Verify that the example data was unmarshalled correctly
        assertThat(bitmaxAssets.getData().size()).isEqualTo(1);
        assertThat(bitmaxAssets.getData().get(0).getStatus()).isEqualTo(AscendexAssetDto.BitmaxAssetStatus.Normal);

    }

    @Test
    public void bitmaxOrderbookDtoTest() throws IOException {

        // Read in the JSON from the example resources
        InputStream is =
                AscendexMarketDataJSONTest.class.getResourceAsStream(
                        "/org/knowm/xchange/ascendex/ascendexOrderbookResponseExample.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        AscendexResponse<AscendexOrderbookDto> bitmaxAssets = mapper.readValue(is, new TypeReference<AscendexResponse<AscendexOrderbookDto>>(){});

        // Verify that the example data was unmarshalled correctly
        assertThat(bitmaxAssets.getData().getData().getSeqnum()).isEqualTo(5068757);
        assertThat(bitmaxAssets.getData().getM()).isEqualTo("depth-snapshot");
        assertThat(bitmaxAssets.getData().getData().getAsks().get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.06848));
        assertThat(bitmaxAssets.getData().getData().getAsks().get(0).getVolume()).isEqualByComparingTo(BigDecimal.valueOf(4084.2));

    }
}
