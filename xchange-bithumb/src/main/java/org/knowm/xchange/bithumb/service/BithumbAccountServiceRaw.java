package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbWalletAddress;
import org.knowm.xchange.currency.Currency;

public class BithumbAccountServiceRaw extends BithumbBaseService {

  protected BithumbAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BithumbBalance getBithumbBalance() throws IOException {
    final BithumbResponse<BithumbBalance> balance =
        bithumbAuthenticated.getBalance(
            apiKey, signatureCreator, exchange.getNonceFactory(), "2", endpointGenerator, "ALL");
    return balance.getData();
  }

  public BithumbAccount getBithumbAddress() throws IOException {
    final BithumbResponse<BithumbAccount> account =
        bithumbAuthenticated.getAccount(
            apiKey, signatureCreator, exchange.getNonceFactory(), "2", endpointGenerator, "BTC");
    return account.getData();
  }

  public Optional<BithumbWalletAddress> getBithumbWalletAddress(Currency currency)
      throws IOException {
    return Optional.ofNullable(
            bithumbAuthenticated.getWalletAddress(
                apiKey,
                signatureCreator,
                exchange.getNonceFactory(),
                "2",
                endpointGenerator,
                currency.getCurrencyCode()))
        .map(BithumbResponse::getData);
  }
}
