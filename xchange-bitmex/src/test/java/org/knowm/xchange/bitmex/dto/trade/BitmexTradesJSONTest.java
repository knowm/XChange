package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Test;
import org.knowm.xchange.bitmex.dto.BitmexTrade;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexAccountJSONTest;
import org.knowm.xchange.bitmex.util.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexTradesJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources

         List<BitmexTrade> bitmexTrades;
        try (InputStream is = BitmexAccountJSONTest.class.getResourceAsStream("/trade/example-trades.json"); InputStreamReader reader = new InputStreamReader(is) ) {
            bitmexTrades =  JSON.streamToArray(is,  BitmexTrade[].class);
        }

        // Verify that the example data was unmarshalled correctly
        assertThat(bitmexTrades.size()).isEqualTo(23);
        BitmexTrade bitmexTrade = bitmexTrades.get(0);
        assertThat(bitmexTrade.getSymbol()).isEqualTo(".XBTUSDPI");
        assertThat(bitmexTrade.getPrice()).isEqualTo(new BigDecimal("0.002924"));
        assertThat(bitmexTrade.getSide()).isEqualToIgnoringCase( BitmexSide.BUY.name());
        assertThat(bitmexTrade.getSize()).isEqualTo(BigDecimal.ZERO);
        assertThat(bitmexTrade.getForeignNotional()).isNull();
        assertThat(bitmexTrade.getGrossValue()).isNull();
        assertThat(bitmexTrade.getHomeNotional()).isNull();
        assertThat(bitmexTrade.getTickDirection()).isEqualToIgnoringCase(BitmexTickDirection.PLUSTICK.name());
    }

}