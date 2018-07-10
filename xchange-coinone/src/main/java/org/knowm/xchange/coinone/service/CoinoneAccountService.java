package org.knowm.xchange.coinone.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.CoinoneAdapters;
import org.knowm.xchange.coinone.CoinoneExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class CoinoneAccountService extends CoinoneAccountServiceRaw implements AccountService {

  private CoinoneExchange coinoneExchange;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneAccountService(Exchange exchange) {
    super(exchange);
    this.coinoneExchange = (CoinoneExchange) exchange;
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    return new AccountInfo(CoinoneAdapters.adaptWallet(super.getWallet()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams withdrawFundsParams) throws IOException {
    return null;
  }
}
