package org.knowm.xchange.bitcoincore.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoincore.BitcoinCore;
import org.knowm.xchange.bitcoincore.dto.BitcoinCoreException;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceRequest;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreUnconfirmedBalanceRequest;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.RestProxyFactory;

public class BitcoinCoreAccountServiceRaw extends BaseExchangeService {

  private final BitcoinCore bitcoinCore;

  private final BitcoinCoreBalanceRequest balanceRequest = new BitcoinCoreBalanceRequest();
  private final BitcoinCoreUnconfirmedBalanceRequest unconfirmedBalanceRequest =
      new BitcoinCoreUnconfirmedBalanceRequest();

  protected BitcoinCoreAccountServiceRaw(Exchange exchange) {
    super(exchange);

    ExchangeSpecification specification = exchange.getExchangeSpecification();

    ClientConfig config = getClientConfig();
    String user = specification.getUserName();
    ClientConfigUtil.addBasicAuthCredentials(
        config, user == null ? "" : user, specification.getPassword());

    bitcoinCore =
        RestProxyFactory.createProxy(BitcoinCore.class, specification.getPlainTextUri(), config);
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
