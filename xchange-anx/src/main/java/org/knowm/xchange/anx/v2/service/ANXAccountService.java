package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.anx.ANXUtils;
import org.knowm.xchange.anx.v2.ANXAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * <p>
 * XChange service to provide the following to {@link org.knowm.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>ANX specific methods to handle account-related operations</li>
 * </ul>
 */
public class ANXAccountService extends ANXAccountServiceRaw implements AccountService {

  /**
   * Constructor
   */
  public ANXAccountService(BaseExchange baseExchange) {

    super(baseExchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return ANXAdapters.adaptAccountInfo(getANXAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    if (amount.scale() > ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE) {
      throw new IllegalArgumentException("Amount scale exceed " + ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE);
    }

    if (address == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }

    anxWithdrawFunds(currency.toString(), amount, address);
    return "success";
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return anxRequestDepositAddress(currency.toString()).getAddress();
  }

}
