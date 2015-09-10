package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccount;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccountList;
import com.xeiam.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Account raw service.
 */
public class CoinsetterAccountServiceRaw extends BaseExchangeService {

  private final com.xeiam.xchange.coinsetter.rs.CoinsetterAccount account;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterAccountServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    account = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterAccount.class, baseUrl);
  }

  public CoinsetterAccount get(UUID clientSessionId, UUID accountUuid) throws CoinsetterException, IOException {

    return account.get(clientSessionId, accountUuid);
  }

  public CoinsetterAccountList list(UUID clientSessionId) throws CoinsetterException, IOException {

    return account.list(clientSessionId);
  }

}
