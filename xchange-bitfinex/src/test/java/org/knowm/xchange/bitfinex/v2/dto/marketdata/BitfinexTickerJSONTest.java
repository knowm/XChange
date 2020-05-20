package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;

public class BitfinexTickerJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitfinexTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v2/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CollectionType constructCollectionType =
        mapper.getTypeFactory().constructCollectionType(List.class, ArrayNode.class);

    List<ArrayNode> tickers0 = mapper.readValue(is, constructCollectionType);
    BitfinexTicker[] tickers = BitfinexAdapters.adoptBitfinexTickers(tickers0);

    // Verify that the example data was unmarshalled correctly
    // funding currency:
    BitfinexTickerFundingCurrency bitfinexTicker = (BitfinexTickerFundingCurrency) tickers[0];
    assertThat(bitfinexTicker.getSymbol()).isEqualTo("fLEO");
    assertThat(bitfinexTicker.getFrr()).isEqualTo("1.0958904109589042e-08");
    assertThat(bitfinexTicker.getBid()).isEqualTo("0");
    assertThat(bitfinexTicker.getBidPeriod()).isEqualTo("0");
    assertThat(bitfinexTicker.getBidSize()).isEqualTo("0");
    assertThat(bitfinexTicker.getAsk()).isEqualByComparingTo("1e-08");
    assertThat(bitfinexTicker.getAskPeriod()).isEqualTo("2");
    assertThat(bitfinexTicker.getAskSize()).isEqualTo("2663861.8810786298");
    assertThat(bitfinexTicker.getDailyChange()).isEqualTo("0");
    assertThat(bitfinexTicker.getDailyChangePerc()).isEqualTo("0");
    assertThat(bitfinexTicker.getLastPrice()).isEqualByComparingTo("1e-08");
    assertThat(bitfinexTicker.getVolume()).isEqualTo("664.1085");
    assertThat(bitfinexTicker.getHigh()).isEqualByComparingTo("1e-08");
    assertThat(bitfinexTicker.getLow()).isEqualByComparingTo("1e-08");
    assertThat(bitfinexTicker.getPlaceHolder0()).isNull();
    assertThat(bitfinexTicker.getPlaceHolder1()).isNull();
    assertThat(bitfinexTicker.getFrrAmountAvailable()).isEqualTo("2594257.74114297");

    // traiding pair:
    BitfinexTickerTraidingPair bitfinexTicker2 = (BitfinexTickerTraidingPair) tickers[1];
    assertThat(bitfinexTicker2.getSymbol()).isEqualTo("tBTCUSD");
    assertThat(bitfinexTicker2.getBid()).isEqualTo("7381.6");
    assertThat(bitfinexTicker2.getBidSize()).isEqualTo("38.644979070000005");
    assertThat(bitfinexTicker2.getAsk()).isEqualTo("7381.7");
    assertThat(bitfinexTicker2.getAskSize()).isEqualByComparingTo("32.145906579999995");
    assertThat(bitfinexTicker2.getDailyChange()).isEqualTo("126.6");
    assertThat(bitfinexTicker2.getDailyChangePerc()).isEqualTo("0.0175");
    assertThat(bitfinexTicker2.getLastPrice()).isEqualByComparingTo("7381.2");
    assertThat(bitfinexTicker2.getVolume()).isEqualTo("1982.88275223");
    assertThat(bitfinexTicker2.getHigh()).isEqualByComparingTo("7390");
    assertThat(bitfinexTicker2.getLow()).isEqualByComparingTo("7228.1");
  }
}
