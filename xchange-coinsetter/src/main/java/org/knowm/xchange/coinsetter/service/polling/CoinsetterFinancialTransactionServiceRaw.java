package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransaction;
import org.knowm.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransactionList;
import org.knowm.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Financial transaction raw service.
 */
public class CoinsetterFinancialTransactionServiceRaw extends BaseExchangeService {

  private final org.knowm.xchange.coinsetter.rs.CoinsetterFinancialTransaction financialTransaction;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterFinancialTransactionServiceRaw(Exchange exchange) {

    super(exchange);
    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    financialTransaction = RestProxyFactory.createProxy(org.knowm.xchange.coinsetter.rs.CoinsetterFinancialTransaction.class, baseUrl);
  }

  public CoinsetterFinancialTransaction get(UUID clientSessionId, UUID financialTransactionUuid) throws CoinsetterException, IOException {

    return financialTransaction.get(clientSessionId, financialTransactionUuid);
  }

  public CoinsetterFinancialTransactionList list(UUID clientSessionId, UUID accountUuid) throws CoinsetterException, IOException {

    return financialTransaction.list(clientSessionId, accountUuid);
  }

}
