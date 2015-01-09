package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitvc.BitVcAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class HuobiAccountService extends HuobiAccountServiceRaw implements PollingAccountService {

  public HuobiAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitVcAdapters.adaptHuobiAccountInfo(getHuobiAccountInfo());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) {
    throw new NotAvailableFromExchangeException();
  }

}
