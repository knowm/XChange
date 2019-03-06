package org.knowm.xchange.lykke;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAsset;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAssetPair;

public class LykkeAssetsTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testUnmarshalLykkeAssetPairs() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        LykkeAssetsTest.class.getResourceAsStream(
            "/org/knowm/xchange/lykke/example-lykkeAssetPair.json");
    LykkeAssetPair[] assetPairs = mapper.readValue(is, LykkeAssetPair[].class);

    assertThat(assetPairs[0].getId()).isEqualTo("AEBTC");
    assertThat(assetPairs[0].getName()).isEqualTo("AE/BTC");
    assertThat(assetPairs[0].getAccuracy()).isEqualTo(6);
    assertThat(assetPairs[0].getInvertedAccuracy()).isEqualTo(8);
    assertThat(assetPairs[0].getBaseAssetId()).isEqualTo("6f75280b-a005-4016-a3d8-03dc644e8912");
    assertThat(assetPairs[0].getQuotingAssetId()).isEqualTo("BTC");
    assertThat(assetPairs[0].getMinVolume()).isEqualTo(0.4);
    assertThat(assetPairs[0].getMinInvertedVolume()).isEqualTo(0.0001);
  }

  @Test
  public void testUnmarschalLykkeAssets() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        LykkeAssetsTest.class.getResourceAsStream(
            "/org/knowm/xchange/lykke/example-lykkeAssets.json");
    LykkeAsset[] assetPairs = mapper.readValue(is, LykkeAsset[].class);

    assertThat(assetPairs[0].getId()).isEqualTo("03b97387-f34a-4131-9fc1-8ee6e9b00baa");
    assertThat(assetPairs[0].getName())
        .isEqualTo("Erc20 token Friendz Coin (0x23352036e911a22cfc692b5e2e196692658aded9)");
    assertThat(assetPairs[0].getDisplayId()).isEqualTo("FDZ");
    assertThat(assetPairs[0].getBitcoinAssetId())
        .isEqualTo("0x23352036e911a22cfc692b5e2e196692658aded9");
    assertThat(assetPairs[0].getBitcoinAssetAddress()).isEqualTo("");
    assertThat(assetPairs[0].getSymbol()).isEqualTo("FDZ");
    assertThat(assetPairs[0].getAccuracy()).isEqualTo(6);
  }
}
