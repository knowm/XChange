package com.xeiam.xchange.cexio.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cexio.CexIOAdapters;
import com.xeiam.xchange.cexio.CexIOAuthenticated;
import com.xeiam.xchange.cexio.CexIOUtils;
import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.service.CexIODigest;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingAccountService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Author: brox
 * Since:  2/6/14
 */

public class CexIOAccountService extends BasePollingExchangeService implements PollingAccountService {

  private final CexIOAuthenticated exchange;
  private ParamsDigest signatureCreator;


    /**
   * Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOAccountService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.exchange = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = CexIODigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CexIOBalanceInfo info = exchange.getBalance(exchangeSpecification.getApiKey(), signatureCreator, CexIOUtils.nextNonce());
    if (info.getError() != null) {
      throw new ExchangeException("Error getting balance. " + info.getError());
    }

    return CexIOAdapters.adaptAccountInfo(info, exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestBitcoinDepositAddress(String... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

}
