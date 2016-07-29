package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcSymbolsJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcSymbolsJsonTest.class.getResourceAsStream("/marketdata/example-symbols-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcSymbols hitbtcSymbols = mapper.readValue(is, HitbtcSymbols.class);

    List<HitbtcSymbol> symbols = hitbtcSymbols.getHitbtcSymbols();
    assertThat(symbols).hasSize(15);
    HitbtcSymbol symbol = symbols.get(0);
    assertThat(symbol.getCommodity()).isEqualTo("BCN");
    assertThat(symbol.getCurrency()).isEqualTo("BTC");
    assertThat(symbol.getStep()).isEqualTo("0.000000001");
    assertThat(symbol.getLot()).isEqualTo("100");
    assertThat(symbol.getTakeLiquidityRate()).isEqualTo("0.001");
    assertThat(symbol.getProvideLiquidityRate()).isEqualTo("-0.0001");
  }
}
