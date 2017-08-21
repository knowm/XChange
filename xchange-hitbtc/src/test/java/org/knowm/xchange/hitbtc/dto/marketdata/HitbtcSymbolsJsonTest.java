package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcSymbolsJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcSymbolsJsonTest.class.getResourceAsStream("/marketdata/example-symbols-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<HitbtcSymbol> symbols = mapper.readValue(is, new TypeReference<List<HitbtcSymbol>>() { });

    Map<String, HitbtcSymbol> symbolMap = new HashMap<>();
    for (HitbtcSymbol hitbtcSymbol : symbols) {
      symbolMap.put(hitbtcSymbol.getId(), hitbtcSymbol);
    }

    assertThat(symbols).hasSize(170);
    HitbtcSymbol symbol = symbolMap.get("BCNBTC");

    assertThat(symbol.getId()).isEqualTo("BCNBTC");
    assertThat(symbol.getBaseCurrency()).isEqualTo("BCN");
    assertThat(symbol.getTickSize()).isEqualTo("0.0000000001");
    assertThat(symbol.getTakeLiquidityRate()).isEqualTo("0.001");
    assertThat(symbol.getProvideLiquidityRate()).isEqualTo("-0.0001");
  }
}
