package org.knowm.xchange.deribit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class DeribitCurrencyTest {

  @Test
  public void deserializeCurrencyTest() throws Exception {

    // given
    InputStream is =
        DeribitCurrency.class.getResourceAsStream(
            "/org/knowm/xchange/deribit/v2/dto/marketdata/example-currency.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    DeribitCurrency currency = mapper.readValue(is, DeribitCurrency.class);

    // then
    assertThat(currency).isNotNull();

    assertThat(currency.getCoinType()).isEqualTo("BITCOIN");
    assertThat(currency.getCurrency()).isEqualTo("BTC");
    assertThat(currency.getCurrencyLong()).isEqualTo("Bitcoin");
    assertThat(currency.isDisabledDepositAddressCreation()).isFalse();
    assertThat(currency.getFeePrecision()).isEqualTo(4);
    assertThat(currency.getMinConfirmations()).isEqualTo(1);
    assertThat(currency.getMinWithdrawalFee()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(currency.getWithdrawalFee()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(currency.getWithdrawalPriorities()).isNotEmpty();
  }
}
