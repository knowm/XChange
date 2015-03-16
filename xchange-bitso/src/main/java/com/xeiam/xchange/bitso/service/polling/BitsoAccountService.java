package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.BitsoAdapters;
import com.xeiam.xchange.bitso.dto.account.BitsoDepositAddress;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class BitsoAccountService extends BitsoAccountServiceRaw implements PollingAccountService {

  public BitsoAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BitsoAdapters.adaptAccountInfo(getBitsoBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    return withdrawBitsoFunds(amount, address);
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    final BitsoDepositAddress response = getBitsoBitcoinDepositAddress();
    return response.getDepositAddress();

  }
}
