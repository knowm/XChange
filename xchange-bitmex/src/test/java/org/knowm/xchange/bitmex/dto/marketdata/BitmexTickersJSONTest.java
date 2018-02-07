package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Test;
import org.knowm.xchange.bitmex.dto.BitmexInstrument;
import org.knowm.xchange.bitmex.dto.BitmexTrade;
import org.knowm.xchange.bitmex.dto.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexAccountJSONTest;
import org.knowm.xchange.bitmex.util.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexTickersJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        List<BitmexInstrument> bitmexTickers = JSON.streamToArray(BitmexAccountJSONTest.class.getResourceAsStream("/example-tickers.json"), BitmexInstrument[].class);
        // Verify that the example data was unmarshalled correctly
        assertThat(bitmexTickers.size()).isEqualTo(2);
        assertThat(bitmexTickers.get(0).getSymbol()).isEqualTo("XBTZ14");
        assertThat(bitmexTickers.get(0).getReferenceSymbol()).isEqualTo(".XBT2H");
        assertThat(bitmexTickers.get(0).getRootSymbol()).isEqualTo("XBT");
        assertThat(bitmexTickers.get(0).getUnderlying()).isEqualTo("XBT");
        assertThat(bitmexTickers.get(0).getUnderlyingSymbol()).isEqualTo("XBT=");
    }
}