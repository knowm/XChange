package org.knowm.xchange.anx.v2.dto.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.anx.v2.dto.meta.ANXMetaData;
import org.knowm.xchange.currency.Currency;

/** Test BitStamp Full Depth JSON parsing */
public class WalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WalletJSONTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ANXAccountInfo anxAccountInfo = mapper.readValue(is, ANXAccountInfo.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(anxAccountInfo.getLogin()).isEqualTo("test@anxpro.com");

    // Get Balance
    assertThat(anxAccountInfo.getWallets().get("BTC").getBalance().getValue())
        .isEqualTo(new BigDecimal("100000.01988000"));
    assertThat(anxAccountInfo.getWallets().get("USD").getBalance().getValue())
        .isEqualTo(new BigDecimal("100000.00000"));
    assertThat(anxAccountInfo.getWallets().get("HKD").getBalance().getValue())
        .isEqualTo(new BigDecimal("99863.07000"));

    assertThat(anxAccountInfo.getWallets().get("LTC").getBalance().getValue())
        .isEqualTo(new BigDecimal("100000.00000000"));
    assertThat(anxAccountInfo.getWallets().get("DOGE").getBalance().getValue())
        .isEqualTo(new BigDecimal("9999781.09457936"));

    // Get Other Balance
    assertThat(anxAccountInfo.getWallets().get("BTC").getMaxWithdraw().getValue())
        .isEqualTo(new BigDecimal("20.00000000"));
    assertThat(anxAccountInfo.getWallets().get("BTC").getDailyWithdrawLimit().getValue())
        .isEqualTo(new BigDecimal("20.00000000"));
  }

  @Test
  public void testCurrencies() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        WalletJSONTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ANXAccountInfo anxAccountInfo = mapper.readValue(is, ANXAccountInfo.class);

    Map<String, ANXWallet> wallets = anxAccountInfo.getWallets();

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
    ANXMetaData anxMetaData = ((ANXExchange) exchange).getANXMetaData();

    Set<String> metadataCurrencyStrings = new TreeSet<>();
    for (Currency currency : anxMetaData.getCurrencies().keySet())
      metadataCurrencyStrings.add(currency.toString());

    assertEquals(new TreeSet<>(wallets.keySet()), metadataCurrencyStrings);
  }
}
