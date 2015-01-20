package com.xeiam.xchange.hitbtc.service.polling;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class HitbtcAccountServiceTest {

  @Test public void testGetMetadata() throws Exception {
    // Read in the JSON from the example resources
    InputStream is = getClass().getClassLoader().getResourceAsStream("marketdata/example-symbols-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    HitbtcSymbols hitBtcSymbols = mapper.readValue(is, HitbtcSymbols.class);
    Map<CurrencyPair, ? extends TradeServiceHelper> metaMap = HitbtcAdapters.adaptSymbolsToMetadata(hitBtcSymbols);

    TradeServiceHelper eur = metaMap.get(CurrencyPair.BTC_EUR);

    assertThat(eur.getAmountMinimum()).isEqualTo(".01");
    assertThat(eur.getAmountStep()).isEqualTo(".01");
  }
}
