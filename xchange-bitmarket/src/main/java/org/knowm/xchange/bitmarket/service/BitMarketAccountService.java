package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketAdapters;
import org.knowm.xchange.bitmarket.dto.account.BitMarketAccountInfo;
import org.knowm.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author kfonal
 */
public class BitMarketAccountService extends BitMarketAccountServiceRaw implements AccountService {

  public BitMarketAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitMarketAccountInfo accountInfo = getBitMarketAccountInfo().getData();
    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), BitMarketAdapters.adaptWallet(accountInfo.getBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitMarketWithdrawResponse response = withdrawFromBitMarket(currency.toString(), amount, address);
    return response.getData();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... strings)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitMarketDepositResponse response = depositToBitMarket(currency.toString());
    return response.getData();
  }
}
