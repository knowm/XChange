package org.knowm.xchange.ripple.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

public class RippleAccountTest {

  @Test
  public void unmarshalBalancesTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/account/example-account-balances.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountBalances account = mapper.readValue(is, RippleAccountBalances.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(account.getLedger()).isEqualTo(13895887);
    assertThat(account.isValidated()).isEqualTo(true);
    assertThat(account.isSuccess()).isEqualTo(true);

    // check balances are correct
    final List<RippleBalance> balances = account.getBalances();
    assertThat(balances).hasSize(3);

    final Iterator<RippleBalance> iterator = balances.iterator();

    final RippleBalance balance1 = iterator.next();
    assertThat(balance1.getValue()).isEqualTo("861.401578");
    assertThat(balance1.getCurrency()).isEqualTo("XRP");
    assertThat(balance1.getCounterparty()).isEqualTo("");

    final RippleBalance balance2 = iterator.next();
    assertThat(balance2.getValue()).isEqualTo("0.038777349225374");
    assertThat(balance2.getCurrency()).isEqualTo("BTC");
    assertThat(balance2.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    final RippleBalance balance3 = iterator.next();
    assertThat(balance3.getValue()).isEqualTo("10");
    assertThat(balance3.getCurrency()).isEqualTo("USD");
    assertThat(balance3.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
  }

  @Test
  public void unmarshalSettingsTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/account/example-account-settings-rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleSettings settings = mapper.readValue(is, RippleAccountSettings.class).getSettings();

    // Verify that the example data was unmarshalled correctly
    assertThat(settings.getAccount()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(settings.getTransferRate()).isEqualTo(1002000000);
    assertThat(settings.getTransferFeeRate()).isEqualTo("0.002");
    assertThat(settings.isPasswordSpent()).isFalse();
    assertThat(settings.isRequireDestinationTag()).isTrue();
    assertThat(settings.isRequireAuthorization()).isFalse();
    assertThat(settings.isDisallowXRP()).isFalse();
    assertThat(settings.isDisableMaster()).isFalse();
    assertThat(settings.isNoFreeze()).isFalse();
    assertThat(settings.isGlobalFreeze()).isFalse();
    assertThat(settings.isDefaultRipple()).isTrue();
    assertThat(settings.getTransactionSequence()).isEqualTo("2279");
    assertThat(settings.getEmailHash()).isEqualTo("5B33B93C7FFE384D53450FC666BB11FB");
    assertThat(settings.getWalletLocator()).isEqualTo("");
    assertThat(settings.getWalletSize()).isEqualTo("");
    assertThat(settings.getMessageKey()).isEqualTo("");
    assertThat(settings.getDomain()).isEqualTo("bitstamp.net");
    assertThat(settings.getSigners()).isEqualTo("");
  }
}
