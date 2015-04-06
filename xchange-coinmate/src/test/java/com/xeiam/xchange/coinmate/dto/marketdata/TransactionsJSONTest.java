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
public class TransactionsJSONTest {

        @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = TransactionsJSONTest.class.getResourceAsStream("/marketdata/example-transactions.json");

        ObjectMapper mapper = new ObjectMapper();
        CoinmateTransactions coinmateTransactions = mapper.readValue(is, CoinmateTransactions.class);

        // Verify that the example data was unmarshalled correctly
        assertThat(coinmateTransactions.getData().get(0).getTimestamp()).isEqualTo(1428330164181L);
        assertThat(coinmateTransactions.getData().get(0).getTransactionId()).isEqualTo("33737");
        assertThat(coinmateTransactions.getData().get(0).getPrice()).isEqualTo(new BigDecimal("256.51"));
        assertThat(coinmateTransactions.getData().get(0).getAmount()).isEqualTo(new BigDecimal("0.20128269"));
        assertThat(coinmateTransactions.getData().get(0).getCurrencyPair()).isEqualTo("BTC_USD");
    }
}
