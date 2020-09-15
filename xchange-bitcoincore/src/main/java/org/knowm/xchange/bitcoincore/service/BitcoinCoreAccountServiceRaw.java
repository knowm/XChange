package org.knowm.xchange.bitcoincore.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoincore.BitcoinCore;
import org.knowm.xchange.bitcoincore.dto.BitcoinCoreException;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceRequest;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreUnconfirmedBalanceRequest;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import si.mazi.rescu.ClientConfigUtil;

public class BitcoinCoreAccountServiceRaw extends BaseExchangeService {

  private final BitcoinCore bitcoinCore;

  private final BitcoinCoreBalanceRequest balanceRequest = new BitcoinCoreBalanceRequest();
  private final BitcoinCoreUnconfirmedBalanceRequest unconfirmedBalanceRequest =
      new BitcoinCoreUnconfirmedBalanceRequest();

  protected BitcoinCoreAccountServiceRaw(Exchange exchange) {
    super(exchange);

    ExchangeSpecification specification = exchange.getExchangeSpecification();

    String user = specification.getUserName();
    ClientConfigCustomizer clientConfigCustomizer =
        config ->
            ClientConfigUtil.addBasicAuthCredentials(
                config, user == null ? "" : user, specification.getPassword());
    bitcoinCore =
        ExchangeRestProxyBuilder.forInterface(BitcoinCore.class, specification)
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }

  public BitcoinCoreBalanceResponse getBalance() throws IOException {
    try {
      return bitcoinCore.getBalance(balanceRequest);
    } catch (BitcoinCoreException e) {
      throw new ExchangeException(e);
    }
  }

  public BitcoinCoreBalanceResponse getUnconfirmedBalance() throws IOException {
    try {
      return bitcoinCore.getUnconfirmedBalance(unconfirmedBalanceRequest);
    } catch (BitcoinCoreException e) {
      throw new ExchangeException(e);
    }
  }
}
