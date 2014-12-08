package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransaction;
import com.xeiam.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransactionList;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * Financial transaction raw service.
 */
public class CoinsetterFinancialTransactionServiceRaw extends BaseExchangeService {

  private final com.xeiam.xchange.coinsetter.rs.CoinsetterFinancialTransaction financialTransaction;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterFinancialTransactionServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    String baseUrl = exchangeSpecification.getSslUri();
    financialTransaction = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterFinancialTransaction.class, baseUrl);
  }

  public CoinsetterFinancialTransaction get(UUID clientSessionId, UUID financialTransactionUuid) throws CoinsetterException, IOException {

    return financialTransaction.get(clientSessionId, financialTransactionUuid);
  }

  public CoinsetterFinancialTransactionList list(UUID clientSessionId, UUID accountUuid) throws CoinsetterException, IOException {

    return financialTransaction.list(clientSessionId, accountUuid);
  }

}
