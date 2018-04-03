package org.knowm.xchange.ripple.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.service.RippleAccountServiceRaw;

public class RippleAccountIntegration {

  @Test
  public void accountSettingsTest() throws IOException {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    final RippleAccountServiceRaw accountService =
        (RippleAccountServiceRaw) exchange.getAccountService();
    final RippleSettings settings =
        accountService.getRippleAccountSettings("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B").getSettings();

    assertThat(settings.getAccount()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(settings.getDomain()).isEqualTo("bitstamp.net");
  }
}
