/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.kraken.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPair;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenFee;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;

public class KrakenAssetPairsJSONTest {

  private KrakenAssetPair expectedAssetPairInfo;

  @Before
  public void before() {

    List<KrakenFee> fees = new ArrayList<KrakenFee>();
    fees.add(new KrakenFee(new BigDecimal("0"), new BigDecimal("0.3")));
    expectedAssetPairInfo =
        new KrakenAssetPair("XBTUSD", "currency", "XXBT", "currency", "ZUSD", "unit", 5, 8, new BigDecimal(1), new ArrayList<String>(), fees, "ZUSD", new BigDecimal(80), new BigDecimal(40));
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAssetPairsJSONTest.class.getResourceAsStream("/marketdata/example-assetpairs-data.json");

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
    assertThat(krakenAssetPairInfo.getClassQuote()).isEqualTo(expectedAssetPairInfo.getClassQuote());
    assertThat(krakenAssetPairInfo.getFeeVolumeCurrency()).isEqualTo(expectedAssetPairInfo.getFeeVolumeCurrency());
    assertThat(krakenAssetPairInfo.getLeverage()).isEqualTo(expectedAssetPairInfo.getLeverage());
    assertThat(krakenAssetPairInfo.getQuote()).isEqualTo(expectedAssetPairInfo.getQuote());
    assertThat(krakenAssetPairInfo.getVolumeLotSize()).isEqualTo(expectedAssetPairInfo.getVolumeLotSize());
    assertThat(krakenAssetPairInfo.getPairScale()).isEqualTo(expectedAssetPairInfo.getPairScale());
    assertThat(krakenAssetPairInfo.getVolumeLotScale()).isEqualTo(expectedAssetPairInfo.getVolumeLotScale());
    assertThat(krakenAssetPairInfo.getMarginCall()).isEqualTo(expectedAssetPairInfo.getMarginCall());
    assertThat(krakenAssetPairInfo.getMarginStop()).isEqualTo(expectedAssetPairInfo.getMarginStop());
    assertThat(krakenAssetPairInfo.getVolumeMultiplier()).isEqualTo(expectedAssetPairInfo.getVolumeMultiplier());
    assertThat(krakenAssetPairInfo.getFees().size()).isEqualTo(26);

    KrakenFee deserializedFee = krakenAssetPairInfo.getFees().get(0);
    KrakenFee expectedFee = expectedAssetPairInfo.getFees().get(0);
    assertThat(deserializedFee.getPercentFee()).isEqualTo(expectedFee.getPercentFee());
    assertThat(deserializedFee.getVolume()).isEqualTo(expectedFee.getVolume());
  }
}
