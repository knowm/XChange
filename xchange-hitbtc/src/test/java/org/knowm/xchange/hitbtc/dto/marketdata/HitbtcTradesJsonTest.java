package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcTradesJsonTest {

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

    List<HitbtcTrade> trades = mapper.readValue(is, new TypeReference<List<HitbtcTrade>>() { });

    assertThat(trades).hasSize(10);
    HitbtcTrade trade = trades.get(0);
    assertThat(trade.getPrice()).isEqualTo("4110.55");
    assertThat(trade.getQuantity()).isEqualTo("0.15");
    assertThat(trade.getId()).isEqualTo("17556218");
    assertThat(trade.getSide()).isEqualTo(HitbtcTrade.HitbtcTradeSide.BUY);
    assertThat(trade.getTimestamp()).isEqualTo(SIMPLE_DATE_FORMATER.parse("2017-08-15T18:52:26.381Z"));

  }
}
