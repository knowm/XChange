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
public class OrderBookJSONTest {
    
    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = OrderBookJSONTest.class.getResourceAsStream("/marketdata/example-orderbook.json");

        ObjectMapper mapper = new ObjectMapper();
        CoinmateOrderBook coinmateOrderbook = mapper.readValue(is, CoinmateOrderBook.class);

        // Verify that the example data was unmarshalled correctly
        assertThat(coinmateOrderbook.getData().getAsks().get(0).getAmount()).isEqualTo(new BigDecimal("0.057877"));
        assertThat(coinmateOrderbook.getData().getAsks().get(0).getPrice()).isEqualTo(new BigDecimal("259"));
        
        assertThat(coinmateOrderbook.getData().getBids().get(1).getAmount()).isEqualTo(new BigDecimal("21.21730895"));
        assertThat(coinmateOrderbook.getData().getBids().get(1).getPrice()).isEqualTo(new BigDecimal("254.6"));
    }

}
