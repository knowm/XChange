package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;

public class RippleAccountServiceRaw extends RippleBasePollingService {

  public RippleAccountServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public RippleAccount getRippleAccount() throws IOException {
    final RippleAccount rippleAccount = ripplePublic.getAccounts(exchange.getExchangeSpecification().getApiKey());
    return rippleAccount;
  }
}
