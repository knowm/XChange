package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexWalletJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = BitmexWalletJSONTest.class.getResourceAsStream("/account/example-wallet.json");

        ObjectMapper mapper = new ObjectMapper();
        BitmexWallet bitmexWallet = mapper.readValue(is, BitmexWallet.class);

        // Verify that the example data was unmarshalled correctly
        assertThat(bitmexWallet.getAccount()).isEqualTo(0);
        assertThat(bitmexWallet.getAddr()).isEqualTo("string");
        assertThat(bitmexWallet.getCurrency()).isEqualTo("string");
        assertThat(bitmexWallet.getAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(bitmexWallet.getDeltaAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(bitmexWallet.getPrevAmount()).isEqualTo(BigDecimal.ZERO);
    }

}