package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitOrderTest {

    @Test
    public void deserializeCurrencyTest() throws Exception {

        // given
        InputStream is =
                DeribitOrder.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v1/dto/marketdata/example-order.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitOrder order = mapper.readValue(is, DeribitOrder.class);

        // then
        assertThat(order).isNotNull();

        assertThat(order.getQuantity()).isEqualTo(300);
        assertThat(order.getAmount()).isEqualTo(300);
        assertThat(order.getPrice()).isEqualTo(141.42f);
        assertThat(order.getCm()).isEqualTo(300);
        assertThat(order.getCmAmount()).isEqualTo(300);
    }
}