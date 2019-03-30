package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitCurrencyTest {

    @Test
    public void deserializeCurrencyTest() throws Exception {

        // given
        InputStream is =
                DeribitCurrency.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v1/dto/marketdata/example-currency.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitCurrency instrument = mapper.readValue(is, DeribitCurrency.class);

        // then
        assertThat(instrument).isNotNull();

        assertThat(instrument.getTxFee()).isEqualTo(0.0001f);
        assertThat(instrument.getMinConfirmation()).isEqualTo(1);
        assertThat(instrument.getActive()).isTrue();
        assertThat(instrument.getCurrencyLong()).isEqualTo(Currency.BTC.getDisplayName());
        assertThat(instrument.getCurrency()).isEqualTo(Currency.BTC.getSymbol());
        assertThat(instrument.getCoinType()).isEqualTo("BITCOIN");
        assertThat(instrument.getBaseAddress()).isNull();
    }
}