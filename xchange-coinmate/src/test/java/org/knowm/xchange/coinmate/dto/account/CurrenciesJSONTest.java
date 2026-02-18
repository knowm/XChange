package org.knowm.xchange.coinmate.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class CurrenciesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is =
        CurrenciesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/account/example-currencies.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinmateCurrencies currencies = mapper.readValue(is, CoinmateCurrencies.class);

    assertThat(currencies.isError()).isFalse();
    assertThat(currencies.getData()).hasSize(2);

    // Test BTC currency
    CoinmateCurrencyInfo btc = currencies.getData().get(0);
    assertThat(btc.getCurrency()).isEqualTo("BTC");
    assertThat(btc.getCurrencyName()).isEqualTo("Bitcoin");
    assertThat(btc.isDepositEnabled()).isTrue();
    assertThat(btc.isWithdrawEnabled()).isTrue();
    assertThat(btc.getPrecision()).isEqualTo(8);
    assertThat(btc.getNetworks()).hasSize(2);

    // Test BTC main network
    CoinmateNetworkInfo btcMainNet = btc.getNetworks().get(0);
    assertThat(btcMainNet.getNetwork()).isEqualTo("BTC");
    assertThat(btcMainNet.getDeposit().isEnabled()).isTrue();
    assertThat(btcMainNet.getDeposit().getFixFee()).isEqualTo(BigDecimal.ZERO);
    assertThat(btcMainNet.getDeposit().getMinAmount()).isEqualByComparingTo("0.0001");
    assertThat(btcMainNet.getDeposit().getMinConfirmations()).isEqualTo(2);

    assertThat(btcMainNet.getWithdraw().isEnabled()).isTrue();
    assertThat(btcMainNet.getWithdraw().isRequiresTag()).isFalse();
    assertThat(btcMainNet.getWithdraw().getFee()).hasSize(2);
    assertThat(btcMainNet.getWithdraw().getFee().get(0).getVariant()).isEqualTo("HIGH");
    assertThat(btcMainNet.getWithdraw().getFee().get(0).getFixFee())
        .isEqualByComparingTo("0.00002");
    assertThat(btcMainNet.getWithdraw().getMinAmount()).isEqualByComparingTo("0.00001");
    assertThat(btcMainNet.getWithdraw().getMax24hLimit()).isEqualByComparingTo("100");

    // Test XRP currency
    CoinmateCurrencyInfo xrp = currencies.getData().get(1);
    assertThat(xrp.getCurrency()).isEqualTo("XRP");
    assertThat(xrp.getCurrencyName()).isEqualTo("Ripple");
    assertThat(xrp.getNetworks()).hasSize(1);

    // Test XRP network with requiresTag
    CoinmateNetworkInfo xrpNet = xrp.getNetworks().get(0);
    assertThat(xrpNet.getWithdraw().isRequiresTag()).isTrue();
    assertThat(xrpNet.getWithdraw().getFee()).hasSize(1);
    assertThat(xrpNet.getWithdraw().getFee().get(0).getFixFee()).isEqualByComparingTo("0.02");
  }
}
