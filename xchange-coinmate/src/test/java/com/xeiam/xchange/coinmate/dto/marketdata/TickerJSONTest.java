package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Martin Stachon
 */
public class TickerJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker.json");

        ObjectMapper mapper = new ObjectMapper();
        CoinmateTicker coinmateTicker = mapper.readValue(is, CoinmateTicker.class);

        // Verify that the example data was unmarshalled correctly
        assertThat(coinmateTicker.getData().getLast()).isEqualTo(new BigDecimal("254.08"));
        assertThat(coinmateTicker.getData().getHigh()).isEqualTo(new BigDecimal("255.43"));
        assertThat(coinmateTicker.getData().getLow()).isEqualTo(new BigDecimal("250.02"));
        assertThat(coinmateTicker.getData().getAmount()).isEqualTo(new BigDecimal("42.78294066"));
        assertThat(coinmateTicker.getData().getBid()).isEqualTo(new BigDecimal("252.93"));
        assertThat(coinmateTicker.getData().getAsk()).isEqualTo(new BigDecimal("254.08"));
    }
}
