package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Test;
import org.knowm.xchange.bitmex.dto.BitmexTrade;
import org.knowm.xchange.bitmex.dto.BitmexWallet;
import org.knowm.xchange.bitmex.util.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexWalletJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        List<BitmexTrade> bitmexTrades;
        BitmexWallet bitmexWallet;
        try (InputStream is = BitmexAccountJSONTest.class.getResourceAsStream("/account/example-wallet.json");
             InputStreamReader reader = new InputStreamReader(is);) {
            Gson jsonReader =new JSON().getGson();
            bitmexWallet = jsonReader.fromJson(reader, BitmexWallet.class);
        }


        // Verify that the example data was unmarshalled correctly
        assertThat(bitmexWallet.getAccount()).isEqualTo(BigDecimal.ZERO);
        assertThat(bitmexWallet.getAddr()).isEqualTo("string");
        assertThat(bitmexWallet.getCurrency()).isEqualTo("string");
        assertThat(bitmexWallet.getAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(bitmexWallet.getDeltaAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(bitmexWallet.getPrevAmount()).isEqualTo(BigDecimal.ZERO);
    }

}