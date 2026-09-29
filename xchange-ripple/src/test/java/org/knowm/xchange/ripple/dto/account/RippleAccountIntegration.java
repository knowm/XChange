package org.knowm.xchange.ripple.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.service.RippleAccountServiceRaw;

class RippleAccountIntegration {

  @Test
  void accountSettingsTest() throws Exception {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(RippleExchange.class);
    final RippleAccountServiceRaw accountService =
        (RippleAccountServiceRaw) exchange.getAccountService();
    final RippleSettings settings =
        accountService.getRippleAccountSettings("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B").getSettings();

    assertThat(settings.getAccount()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(settings.getDomain()).isEqualTo("bitstamp.net");
  }
}
