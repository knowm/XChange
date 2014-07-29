package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * <p>
 * XChange service to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>ANX specific methods to handle account-related operations</li>
 * </ul>
 */
public class ANXAccountService extends ANXAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public ANXAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return ANXAdapters.adaptAccountInfo(getANXAccountInfo());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    if (amount.scale() > ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE) {
      throw new IllegalArgumentException("Amount scale exceed " + ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE);
    }

    if (address == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }

    anxWithdrawFunds(currency, amount, address);
    return "success";
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    return anxRequestDepositAddress(currency).getAddress();
  }

}
