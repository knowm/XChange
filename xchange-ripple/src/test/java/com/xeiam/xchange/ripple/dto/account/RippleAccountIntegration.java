package com.xeiam.xchange.ripple.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.ripple.service.polling.RippleAccountServiceRaw;

public class RippleAccountIntegration {

  @Test
  public void accountSettingsTest() throws IOException {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    final RippleAccountServiceRaw accountService = (RippleAccountServiceRaw) exchange.getPollingAccountService();
    final RippleSettings settings = accountService.getRippleAccountSettings("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B").getSettings();

    assertThat(settings.getAccount()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(settings.getDomain()).isEqualTo("bitstamp.net");
  }
}
