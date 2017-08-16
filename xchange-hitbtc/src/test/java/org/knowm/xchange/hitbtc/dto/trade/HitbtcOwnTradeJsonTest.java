package org.knowm.xchange.hitbtc.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.hitbtc.dto.general.HitbtcSide;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTradesJsonTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcOwnTradeJsonTest {

  private static SimpleDateFormat SIMPLE_DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  @BeforeClass
  public static void setUpClass() {
    SIMPLE_DATE_FORMATER.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  @Test
  public void testUnmarshal() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcTradesJsonTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    List<HitbtcOwnTrade> trades = mapper.readValue(is, new TypeReference<List<HitbtcOwnTrade>>() { });

    assertThat(trades).hasSize(10);
    HitbtcOwnTrade trade = trades.get(0);
    assertThat(trade.getPrice()).isEqualTo("4110.55");
    assertThat(trade.getQuantity()).isEqualTo("0.15");
    assertThat(trade.getId()).isEqualTo(17556218L);
    assertThat(trade.getSide()).isEqualTo(HitbtcSide.BUY);
    assertThat(trade.getTimestamp()).isEqualTo(SIMPLE_DATE_FORMATER.parse("2017-08-15T18:52:26.381Z"));

  }
}
