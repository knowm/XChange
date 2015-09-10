package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.account.KrakenDepositAddress;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class KrakenAccountService extends KrakenAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return KrakenAdapters.adaptBalance(getKrakenBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
    return withdraw(null, currency, address, amount).getRefid();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {
    KrakenDepositAddress[] depositAddresses = getDepositAddresses(currency, "Bitcoin", false);
    return KrakenAdapters.adaptKrakenDepositAddress(depositAddresses);
  }

  // public WithdrawInfo[] getWithdrawInfo(String assetPairs, String assets,
  // String key, BigDecimal amount)
  // throws IOException {
  // return getWithdrawInfo(assetPairs, assets, key, amount);
  // }
}
