package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class GDAXAccountService extends GDAXAccountServiceRaw implements AccountService {

  public GDAXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return new AccountInfo(GDAXAdapters.adaptAccountInfo(getCoinbaseExAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      GDAXWithdrawCryptoResponse response = withdrawCrypto(defaultParams.address, defaultParams.amount, defaultParams.currency);
      return response.id;
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  public String moveFunds(Currency currency, String address, BigDecimal amount) throws IOException {
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
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public static class GDAXMoveFundsParams implements WithdrawFundsParams {
    public final Currency currency;
    public final BigDecimal amount;
    public final String address;

    public GDAXMoveFundsParams(Currency currency, BigDecimal amount, String address) {
      this.currency = currency;
      this.amount = amount;
      this.address = address;
    }
  }
}
