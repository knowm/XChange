package org.knowm.xchange.coinbaseex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbaseex.CoinbaseExAdapters;
import org.knowm.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExSendMoneyResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class CoinbaseExAccountService extends CoinbaseExAccountServiceRaw implements PollingAccountService {

  public CoinbaseExAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return new AccountInfo(CoinbaseExAdapters.adaptAccountInfo(getCoinbaseExAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinbaseExAccount[] accounts = getCoinbaseExAccountInfo();
    String accountId = null;
    for (CoinbaseExAccount account : accounts) {
      if (currency.getCurrencyCode().equals(account.getCurrency())) {
        accountId = account.getId();
      }
    }
    if (accountId == null) {
      throw new ExchangeException("Cannot determine account id for currency " + currency.getCurrencyCode());
    }
    CoinbaseExSendMoneyResponse response = sendMoney(accountId, address, amount, currency);
    if (response.getData() != null) {
      return response.getData().getId();
    }
    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
