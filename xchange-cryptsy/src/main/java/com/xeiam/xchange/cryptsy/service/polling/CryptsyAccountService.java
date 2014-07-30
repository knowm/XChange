package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author ObsessiveOrange
 */
public class CryptsyAccountService extends CryptsyAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptsyAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException, ExchangeException {

    return CryptsyAdapters.adaptAccountInfo(getCryptsyAccountInfo());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException, ExchangeException {

    return makeCryptsyWithdrawal(address, amount).getReturnValue();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException, ExchangeException {

    return generateNewCryptsyDepositAddress(null, currency).getReturnValue().getAddress();
  }
}
