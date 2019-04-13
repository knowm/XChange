package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitInstrumentTest {

    @Test
    public void deserializeInstrumentTest() throws Exception {

        // given
        InputStream is =
                DeribitInstrument.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v1/dto/marketdata/example-instrument.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitInstrument instrument = mapper.readValue(is, DeribitInstrument.class);

        // then
        assertThat(instrument).isNotNull();

        assertThat(instrument.getKind()).isEqualTo("future");
        assertThat(instrument.getBaseCurrency()).isEqualTo(Currency.ETH.getSymbol());
        assertThat(instrument.getCurrency()).isEqualTo(Currency.USD.getSymbol());
        assertThat(instrument.getMinTradeSize()).isEqualTo(1);
        assertThat(instrument.getInstrumentName()).isEqualTo("ETH-PERPETUAL");
        assertThat(instrument.isActive()).isTrue();
        assertThat(instrument.getSettlement()).isEqualTo("perpetual");
        assertThat(instrument.getCreated()).isEqualTo("2019-01-16 14:02:07 GMT");
        assertThat(instrument.getTickSize()).isEqualTo(0.01);
        assertThat(instrument.getPricePrecision()).isEqualTo(2);
        assertThat(instrument.getSettlement()).isEqualTo("perpetual");
        assertThat(instrument.getExpiration()).isEqualTo("3000-01-01 15:00:00 GMT");

    }
}