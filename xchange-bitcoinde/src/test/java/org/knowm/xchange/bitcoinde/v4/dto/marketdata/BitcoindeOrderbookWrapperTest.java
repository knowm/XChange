package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTrustLevel;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;

public class BitcoindeOrderbookWrapperTest {

  @Test
  public void testBitcoindeOrderbookWrapper()
      throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeOrderbookWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeOrderbookWrapper.class);
    final BitcoindeOrder[] orders = bitcoindeOrderBook.getBitcoindeOrders();

    assertThat(orders.length).isEqualTo(1);
    assertThat(orders[0].getOrderId()).isEqualTo("A1B2D3");
    assertThat(orders[0].getTradingPair()).isEqualByComparingTo(CurrencyPair.BTC_EUR);
    assertThat(orders[0].getType()).isEqualByComparingTo(BitcoindeType.BUY);
    assertThat(orders[0].getExternalWalletOrder()).isFalse();
    assertThat(orders[0].getMinAmount()).isEqualByComparingTo("0.1");
    assertThat(orders[0].getMaxAmount()).isEqualByComparingTo("0.5");
    assertThat(orders[0].getPrice()).isEqualByComparingTo("230.55");
    assertThat(orders[0].getMinVolume()).isEqualByComparingTo("23.06");
    assertThat(orders[0].getMaxVolume()).isEqualByComparingTo("115.28");
    assertThat(orders[0].getRequirementsFullfilled()).isTrue();
    assertThat(orders[0].getTradingPartnerInformation()).isNotNull();
    assertThat(orders[0].getTradingPartnerInformation().getUserName()).isEqualTo("bla");
    assertThat(orders[0].getTradingPartnerInformation().getTrustLevel())
        .isEqualByComparingTo(BitcoindeTrustLevel.GOLD);
    assertThat(orders[0].getTradingPartnerInformation().getRating()).isEqualTo(99);
    assertThat(orders[0].getTradingPartnerInformation().getKycFull()).isTrue();
    assertThat(orders[0].getTradingPartnerInformation().getBankName()).isEqualTo("Sparkasse");
    assertThat(orders[0].getTradingPartnerInformation().getBic()).isEqualTo("HASPDEHHXXX");
    assertThat(orders[0].getTradingPartnerInformation().getSeatOfBank()).isEqualTo("DE");
    assertThat(orders[0].getTradingPartnerInformation().getAmountTrades()).isEqualTo(52);
    assertThat(orders[0].getOrderRequirements()).isNotNull();
    assertThat(orders[0].getOrderRequirements().getMinTrustLevel())
        .isEqualByComparingTo(BitcoindeTrustLevel.GOLD);
    assertThat(orders[0].getOrderRequirements().getOnlyKycFull()).isTrue();
    assertThat(orders[0].getOrderRequirements().getPaymentOption())
        .isEqualByComparingTo(BitcoindePaymentOption.EXPRESS_ONLY);
    assertThat(orders[0].getOrderRequirements().getSeatOfBank()).containsExactly("DE", "NL");

    assertThat(bitcoindeOrderBook.getCredits()).isEqualTo(12);
    assertThat(bitcoindeOrderBook.getErrors()).isEmpty();
    assertThat(bitcoindeOrderBook.getMaintenance()).isNull();
    assertThat(bitcoindeOrderBook.getNonce()).isNull();
  }
}
