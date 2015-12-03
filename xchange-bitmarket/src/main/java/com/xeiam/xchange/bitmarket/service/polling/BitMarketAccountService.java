package com.xeiam.xchange.bitmarket.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketAdapters;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfo;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author kfonal
 */
public class BitMarketAccountService extends BitMarketAccountServiceRaw implements PollingAccountService {

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
