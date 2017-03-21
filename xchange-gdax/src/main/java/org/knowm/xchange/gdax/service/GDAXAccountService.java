package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
import org.knowm.xchange.service.account.AccountService;

public class GDAXAccountService extends GDAXAccountServiceRaw implements AccountService {

  public GDAXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return new AccountInfo(GDAXAdapters.adaptAccountInfo(getCoinbaseExAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    GDAXAccount[] accounts = getCoinbaseExAccountInfo();
    String accountId = null;
    for (GDAXAccount account : accounts) {
      if (currency.getCurrencyCode().equals(account.getCurrency())) {
        accountId = account.getId();
      }
    }
    if (accountId == null) {
      throw new ExchangeException("Cannot determine account id for currency " + currency.getCurrencyCode());
    }
    GDAXSendMoneyResponse response = sendMoney(accountId, address, amount, currency);
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
