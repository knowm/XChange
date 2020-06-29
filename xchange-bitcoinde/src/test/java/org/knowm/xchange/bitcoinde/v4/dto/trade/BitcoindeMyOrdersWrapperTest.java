package org.knowm.xchange.bitcoinde.v4.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePage;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTrustLevel;
import org.knowm.xchange.currency.CurrencyPair;

public class BitcoindeMyOrdersWrapperTest {

  @Test
  public void testBitcoindeMyOrdersWrapper()
      throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeMyOrdersWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/my_orders.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    BitcoindeMyOrdersWrapper bitcoindeOpenOrdersWrapper =
        mapper.readValue(is, BitcoindeMyOrdersWrapper.class);

    // Make sure trade values are correct
    assertThat(bitcoindeOpenOrdersWrapper.getOrders().size()).isEqualTo(1);

    final BitcoindeMyOrder order = bitcoindeOpenOrdersWrapper.getOrders().get(0);
    assertThat(order.getOrderId()).isEqualTo("VNSP86");
    assertThat(order.getTradingPair()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(order.getExternalWalletOrder()).isFalse();
    assertThat(order.getMaxAmount()).isEqualByComparingTo("0.01");
    assertThat(order.getMinAmount()).isEqualByComparingTo("0.01");
    assertThat(order.getPrice()).isEqualByComparingTo("6000");
    assertThat(order.getMaxVolume()).isEqualByComparingTo("60");
    assertThat(order.getMinVolume()).isEqualByComparingTo("60");

    assertThat(order.getOrderRequirements()).isNotNull();
    assertThat(order.getOrderRequirements().getMinTrustLevel())
        .isEqualTo(BitcoindeTrustLevel.SILVER);
    assertThat(order.getOrderRequirements().getOnlyKycFull()).isFalse();
    assertThat(order.getOrderRequirements().getSeatOfBank())
        .containsExactly(
            "AT", "BE", "BG", "CH", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FR", "GB", "GR",
            "HR", "HU", "IE", "IS", "IT", "LI", "LT", "LU", "LV", "MQ", "MT", "NL", "NO", "PL",
            "PT", "RO", "SE", "SI", "SK");
    assertThat(order.getOrderRequirements().getPaymentOption())
        .isEqualTo(BitcoindePaymentOption.EXPRESS_SEPA);

    assertThat(order.getNewOrderForRemainingAmount()).isFalse();
    assertThat(order.getState()).isEqualTo(BitcoindeOrderState.PENDING);
    assertThat(order.getEndDatetime()).isEqualTo("2018-01-30T23:45:00+01:00");
    assertThat(order.getCreatedAt()).isEqualTo("2018-01-25T17:35:19+01:00");

    assertThat(bitcoindeOpenOrdersWrapper.getPage()).isEqualTo(new BitcoindePage(1, 1));
  }
}
