package org.knowm.xchange.quadrigacx.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.quadrigacx.QuadrigaCxAdapters;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxBalance;
import org.knowm.xchange.service.account.AccountService;

public class QuadrigaCxAccountService extends QuadrigaCxAccountServiceRaw implements AccountService {

  public QuadrigaCxAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    QuadrigaCxBalance quadrigaCxBalance = getQuadrigaCxBalance();
    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), quadrigaCxBalance.getFee(),
        QuadrigaCxAdapters.adaptWallet(quadrigaCxBalance));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return (currency.equals(Currency.BTC) ? withdrawBitcoin(amount, address) : withdrawEther(amount, address));
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    return currency.equals(Currency.BTC) ? getQuadrigaCxBitcoinDepositAddress().getDepositAddress()
        : getQuadrigaCxEtherDepositAddress().getDepositAddress();
  }
}
