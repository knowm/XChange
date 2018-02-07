package org.knowm.xchange.bitmex.dto.account;

import com.google.gson.Gson;
import org.junit.Test;
import org.knowm.xchange.bitmex.dto.BitmexMargin;
import org.knowm.xchange.bitmex.util.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexMarginAccountJSONTest {
    @Test
    public void testUnmarshal() throws IOException {


        BitmexMargin bitmexMarginAccount;
        try (InputStream is = BitmexAccountJSONTest.class.getResourceAsStream("/account/example-margin-account.json");
             InputStreamReader reader = new InputStreamReader(is);) {
            Gson jsonReader = new JSON().getGson();
            bitmexMarginAccount = jsonReader.fromJson(reader, BitmexMargin.class);
        }

        // Verify that the example data was unmarshalled correctly
        BigDecimal account = bitmexMarginAccount.getAccount();
        assertThat(account).isEqualTo(BigDecimal.ZERO);
        String currency = bitmexMarginAccount.getCurrency();
        assertThat(currency).isEqualTo("string");
        BigDecimal amount = bitmexMarginAccount.getAmount();
        assertThat(amount).isEqualTo(BigDecimal.ZERO);
        BigDecimal availableMargin = bitmexMarginAccount.getAvailableMargin();
        assertThat(availableMargin).isEqualTo(BigDecimal.ZERO);
        BigDecimal maintMargin = bitmexMarginAccount.getMaintMargin();
        assertThat(maintMargin).isEqualTo(BigDecimal.ZERO);
        BigDecimal marginBalance = bitmexMarginAccount.getMarginBalance();
        assertThat(marginBalance).isEqualTo(BigDecimal.ZERO);
        Double marginLeverage = bitmexMarginAccount.getMarginLeverage();
        assertThat(marginLeverage).isEqualTo(0);
        BigDecimal taxableMargin = bitmexMarginAccount.getTaxableMargin();
        assertThat(taxableMargin).isEqualTo(BigDecimal.ZERO);
    }

}