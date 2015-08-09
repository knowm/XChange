package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransaction;
import com.xeiam.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransactionList;
import com.xeiam.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Financial transaction raw service.
 */
public class CoinsetterFinancialTransactionServiceRaw extends BaseExchangeService {

  private final com.xeiam.xchange.coinsetter.rs.CoinsetterFinancialTransaction financialTransaction;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterFinancialTransactionServiceRaw(Exchange exchange) {

    super(exchange);
    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    financialTransaction = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterFinancialTransaction.class, baseUrl);
  }

  public CoinsetterFinancialTransaction get(UUID clientSessionId, UUID financialTransactionUuid) throws CoinsetterException, IOException {

    return financialTransaction.get(clientSessionId, financialTransactionUuid);
  }

  public CoinsetterFinancialTransactionList list(UUID clientSessionId, UUID accountUuid) throws CoinsetterException, IOException {

    return financialTransaction.list(clientSessionId, accountUuid);
  }

}
