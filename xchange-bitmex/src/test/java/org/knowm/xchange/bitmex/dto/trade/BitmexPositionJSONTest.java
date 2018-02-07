package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Test;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.dto.BitmexPosition;
import org.knowm.xchange.bitmex.dto.BitmexTrade;
import org.knowm.xchange.bitmex.dto.account.BitmexAccountJSONTest;
import org.knowm.xchange.bitmex.util.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BitmexPositionJSONTest {
    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
//        InputStream is = BitmexTradesJSONTest.class.getResourceAsStream("/trade/example-position.json");

        BitmexPosition position;
        try (InputStream is = BitmexAccountJSONTest.class.getResourceAsStream("/trade/example-position.json")   ) {
            position = new JSON().getGson().fromJson( new InputStreamReader(is), BitmexPosition .class);
        }
         assertThat(position.getSymbol()).isEqualTo("string");
        BigDecimal account = position.getAccount();
        assertThat(account).isEqualTo(BigDecimal.ZERO);
        assertThat(position.getCurrency()).isEqualTo("string");
        assertThat(position.getUnderlying()).isEqualTo("string");
        assertThat(position.getQuoteCurrency()).isEqualTo("string");

    }
}