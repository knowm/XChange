package org.knowm.xchange.ripple.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.ripple.RippleAdapters;
import org.knowm.xchange.ripple.dto.account.RippleAccountBalances;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class RippleAccountService extends RippleAccountServiceRaw implements AccountService {

  public RippleAccountService(final Exchange exchange) {
    super(exchange);
  }

  /**
   * A wallet's currency will be prefixed with the issuing counterparty address for all currencies
   * other than XRP.
   */
  @Override
  public AccountInfo getAccountInfo() throws IOException {
    final RippleAccountBalances account = getRippleAccountBalances();
    final String username = exchange.getExchangeSpecification().getApiKey();
    return RippleAdapters.adaptAccountInfo(account, username);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
