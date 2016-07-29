package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccount;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccountList;
import org.knowm.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Account raw service.
 */
public class CoinsetterAccountServiceRaw extends BaseExchangeService {

  private final org.knowm.xchange.coinsetter.rs.CoinsetterAccount account;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterAccountServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    account = RestProxyFactory.createProxy(org.knowm.xchange.coinsetter.rs.CoinsetterAccount.class, baseUrl);
  }

  public CoinsetterAccount get(UUID clientSessionId, UUID accountUuid) throws CoinsetterException, IOException {

    return account.get(clientSessionId, accountUuid);
  }

  public CoinsetterAccountList list(UUID clientSessionId) throws CoinsetterException, IOException {

    return account.list(clientSessionId);
  }

}
