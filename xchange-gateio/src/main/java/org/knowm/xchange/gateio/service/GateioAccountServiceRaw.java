package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioFunds;

public class GateioAccountServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GateioFunds getGateioAccountInfo() throws IOException {

    GateioFunds gateioFunds =
        bter.getFunds(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    return handleResponse(gateioFunds);
  }

  public GateioDepositAddress getGateioDepositAddress(Currency currency) throws IOException {
    GateioDepositAddress depositAddress =
        bter.getDepositAddress(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            currency.getCurrencyCode());
    return depositAddress;
  }

  public GateioBaseResponse withdraw(
      Currency currency, BigDecimal amount, String baseAddress, String addressTag)
      throws IOException {
    String withdrawAddress = baseAddress;
    if (addressTag != null && addressTag.length() > 0) {
      withdrawAddress = withdrawAddress + "/" + addressTag;
    }

    return bter.withdraw(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        currency.getCurrencyCode(),
        amount.toPlainString(),
        withdrawAddress);
  }
}
