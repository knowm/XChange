package org.knowm.xchange.kraken.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KrakenAssetsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAssetsJSONTest.class.getResourceAsStream("/marketdata/example-assets-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenAssetsResult krakenResult = mapper.readValue(is, KrakenAssetsResult.class);
    Map<String, KrakenAsset> assets = krakenResult.getResult();

    assertThat(assets).hasSize(27);
    KrakenAsset asset = assets.get("XXBT");
    assertThat(asset.getAltName()).isEqualTo("XBT");
    assertThat(asset.getAssetClass()).isEqualTo("currency");
    assertThat(asset.getDisplayScale()).isEqualTo(5);
    assertThat(asset.getScale()).isEqualTo(10);
  }
}
