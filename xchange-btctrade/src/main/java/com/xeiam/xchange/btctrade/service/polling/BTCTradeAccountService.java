package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class BTCTradeAccountService extends BTCTradeAccountServiceRaw implements PollingAccountService {

  /**
   * @param exchangeSpecification
   */
  public BTCTradeAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BTCTradeAdapters.adaptAccountInfo(getBTCTradeBalance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    return BTCTradeAdapters.adaptDepositAddress(getBTCTradeWallet());
  }

}
