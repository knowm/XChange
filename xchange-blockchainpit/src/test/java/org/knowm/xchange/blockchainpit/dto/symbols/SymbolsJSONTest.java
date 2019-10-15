package org.knowm.xchange.blockchainpit.dto.symbols;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.blockchainpit.dto.PitChannel;
import org.knowm.xchange.blockchainpit.dto.PitEventType;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/** Test PitSymbols JSON parsing */
public class SymbolsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = SymbolsJSONTest.class.getResourceAsStream(
            "/org.knowm.xchange.blockchainpit.dto.symbols/symbols.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PitSymbols symbolMesssage = mapper.readValue(is, PitSymbols.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(symbolMesssage.getSeqnum()).isEqualTo(1);
    assertThat(symbolMesssage.getEvent()).isEqualTo(PitEventType.SNAPSHOT);
    assertThat(symbolMesssage.getChannel()).isEqualTo(PitChannel.SYMBOLS);

    Map<String, PitSymbol> symbols = symbolMesssage.getSymbols();

    assertThat(symbols.get("BTC-PAX").getBaseCurrency()).isEqualTo("BTC");
    assertThat(symbols.get("BTC-PAX").getBaseCurrencyScale()).isEqualTo(8);
    assertThat(symbols.get("BTC-PAX").getCounterCurrency()).isEqualTo("PAX");
    assertThat(symbols.get("BTC-PAX").getCounterCurrencyScale()).isEqualTo(8);
    assertThat(symbols.get("BTC-PAX").getMinPriceIncrement()).isEqualTo(10000000);
    assertThat(symbols.get("BTC-PAX").getMinPriceIncrementScale()).isEqualTo(8);
    assertThat(symbols.get("BTC-PAX").getMinOrderSize()).isEqualTo(50000);
    assertThat(symbols.get("BTC-PAX").getMinOrderSizeScale()).isEqualTo(8);
    assertThat(symbols.get("BTC-PAX").getMaxOrderSize()).isEqualTo(0);
    assertThat(symbols.get("BTC-PAX").getMaxOrderSizeScale()).isEqualTo(8);
    assertThat(symbols.get("BTC-PAX").getLotSize()).isEqualTo(1);
    assertThat(symbols.get("BTC-PAX").getLotSizeScale()).isEqualTo(8);
    assertThat(symbols.get("BTC-PAX").getStatus()).isEqualTo("open");
    assertThat(symbols.get("BTC-PAX").getId()).isEqualTo(16);
    assertThat(symbols.get("BTC-PAX").getAuctionPrice()).isEqualTo(new BigDecimal("0.0"));
    assertThat(symbols.get("BTC-PAX").getAuctionSize()).isEqualTo(new BigDecimal("0.0"));
    assertThat(symbols.get("BTC-PAX").getAuctionTime()).isEqualTo("");
    assertThat(symbols.get("BTC-PAX").getImbalance()).isEqualTo(new BigDecimal("0.0"));

    Set<String> expectedSymbols = new HashSet<>(
      Arrays.asList(
        "BTC-PAX",
        "LTC-BTC",
        "ETH-PAX",
        "XLM-EUR",
        "BCH-USDT",
        "XLM-BTC",
        "BCH-USD",
        "PAX-USD",
        "LTC-USDT",
        "BTC-EUR",
        "USDT-EUR",
        "BCH-PAX",
        "ETH-USD",
        "LTC-EUR",
        "XLM-PAX",
        "XLM-USD",
        "ETH-BTC",
        "LTC-PAX",
        "PAX-EUR",
        "BTC-USDT",
        "BCH-EUR",
        "BCH-BTC",
        "BTC-USD",
        "BCH-ETH",
        "USDT-USD",
        "ETH-USDT",
        "LTC-USD",
        "ETH-EUR",
        "XLM-ETH")
    );

    assertThat(symbols.keySet()).isEqualTo(expectedSymbols);
  }
}



