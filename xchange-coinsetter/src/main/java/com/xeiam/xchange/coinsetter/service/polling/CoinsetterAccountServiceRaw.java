package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccount;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccountList;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;

public class CoinsetterAccountServiceRaw extends CoinsetterBasePollingService {

  private final com.xeiam.xchange.coinsetter.CoinsetterAccount account;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    String baseUrl = exchangeSpecification.getSslUri();
    account = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.CoinsetterAccount.class, baseUrl);
  }

  public CoinsetterAccount get(CoinsetterClientSession session, UUID accountUuid) throws CoinsetterException, IOException {

    return account.get(session.getUuid(), accountUuid);
  }

  public CoinsetterAccountList list(CoinsetterClientSession session) throws CoinsetterException, IOException {

    return account.list(session.getUuid());
  }

}
