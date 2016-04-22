package org.knowm.xchange.quoine.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.quoine.QuoineAdapters;
import org.knowm.xchange.quoine.dto.account.QuoineAccountInfo;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * <p>
 * XChange service to provide the following to {@link org.knowm.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>ANX specific methods to handle account-related operations</li>
 * </ul>
 */
public class QuoineAccountService extends QuoineAccountServiceRaw implements PollingAccountService {

  private final boolean useMargin;

  /**
   * Constructor
   */
  public QuoineAccountService(BaseExchange baseExchange, boolean useMargin) {

    super(baseExchange);

    this.useMargin = useMargin;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    if (useMargin) {
      QuoineTradingAccountInfo[] quoineTradingAccountInfo = getQuoineTradingAccountInfo();
      return new AccountInfo(QuoineAdapters.adaptTradingWallet(quoineTradingAccountInfo));

    } else {
      QuoineAccountInfo quoineAccountInfo = getQuoineAccountInfo();
      return new AccountInfo(QuoineAdapters.adaptWallet(quoineAccountInfo));
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
