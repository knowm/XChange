package org.knowm.xchange.kraken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;

public class KrakenAssetPairsJSONTest {

  private KrakenAssetPair expectedAssetPairInfo;

  @Before
  public void before() {

    List<KrakenFee> fees = new ArrayList<>();
    fees.add(new KrakenFee(new BigDecimal("0"), new BigDecimal("0.26")));
    List<KrakenFee> fees_maker = new ArrayList<>();
    fees.add(new KrakenFee(new BigDecimal("0"), new BigDecimal("0.1")));
    List<String> leverage_buy = Arrays.asList("2", "3", "4", "5");
    List<String> leverage_sell = Arrays.asList("2", "3", "4", "5");
    expectedAssetPairInfo =
        new KrakenAssetPair(
            "XBTUSD",
            "currency",
            "XXBT",
            "currency",
            "ZUSD",
            "unit",
            3,
            8,
            new BigDecimal(1),
            fees,
            fees_maker,
            "ZUSD",
            new BigDecimal(80),
            new BigDecimal(40),
            leverage_buy,
            leverage_sell);
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenAssetPairsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/marketdata/example-assetpairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenAssetPairsResult krakenAssetPairs = mapper.readValue(is, KrakenAssetPairsResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenAssetPairs.getResult().get("XXBTZEUR")).isNotNull();
    assertThat(krakenAssetPairs.getResult().get("XBTCEUR")).isNull();

    KrakenAssetPair krakenAssetPairInfo = krakenAssetPairs.getResult().get("XXBTZUSD");
    assertThat(krakenAssetPairInfo.getAltName()).isEqualTo(expectedAssetPairInfo.getAltName());
    assertThat(krakenAssetPairInfo.getBase()).isEqualTo(expectedAssetPairInfo.getBase());
    assertThat(krakenAssetPairInfo.getClassBase()).isEqualTo(expectedAssetPairInfo.getClassBase());
    assertThat(krakenAssetPairInfo.getClassQuote())
        .isEqualTo(expectedAssetPairInfo.getClassQuote());
    assertThat(krakenAssetPairInfo.getFeeVolumeCurrency())
        .isEqualTo(expectedAssetPairInfo.getFeeVolumeCurrency());
    assertThat(krakenAssetPairInfo.getLeverage_buy())
        .isEqualTo(expectedAssetPairInfo.getLeverage_buy());
    assertThat(krakenAssetPairInfo.getLeverage_sell())
        .isEqualTo(expectedAssetPairInfo.getLeverage_sell());
    assertThat(krakenAssetPairInfo.getQuote()).isEqualTo(expectedAssetPairInfo.getQuote());
    assertThat(krakenAssetPairInfo.getVolumeLotSize())
        .isEqualTo(expectedAssetPairInfo.getVolumeLotSize());
    assertThat(krakenAssetPairInfo.getPairScale()).isEqualTo(expectedAssetPairInfo.getPairScale());
    assertThat(krakenAssetPairInfo.getVolumeLotScale())
        .isEqualTo(expectedAssetPairInfo.getVolumeLotScale());
    assertThat(krakenAssetPairInfo.getMarginCall())
        .isEqualTo(expectedAssetPairInfo.getMarginCall());
    assertThat(krakenAssetPairInfo.getMarginStop())
        .isEqualTo(expectedAssetPairInfo.getMarginStop());
    assertThat(krakenAssetPairInfo.getVolumeMultiplier())
        .isEqualTo(expectedAssetPairInfo.getVolumeMultiplier());
    assertThat(krakenAssetPairInfo.getFees().size()).isEqualTo(9);

    KrakenFee deserializedFee = krakenAssetPairInfo.getFees().get(0);
    KrakenFee expectedFee = expectedAssetPairInfo.getFees().get(0);
    assertThat(deserializedFee.getPercentFee()).isEqualTo(expectedFee.getPercentFee());
    assertThat(deserializedFee.getVolume()).isEqualTo(expectedFee.getVolume());
  }
}
