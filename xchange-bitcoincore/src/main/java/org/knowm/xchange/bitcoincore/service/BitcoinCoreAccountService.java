package org.knowm.xchange.bitcoincore.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoincore.BitcoinCoreAdapters;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BitcoinCoreAccountService extends BitcoinCoreAccountServiceRaw
    implements AccountService {

  public BitcoinCoreAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    final BitcoinCoreBalanceResponse balance = getBalance();
    final BitcoinCoreBalanceResponse unconfirmed = getUnconfirmedBalance();
    return BitcoinCoreAdapters.adaptAccountInfo(balance, unconfirmed);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
