package org.knowm.xchange.quadrigacx.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.quadrigacx.QuadrigaCxAdapters;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxBalance;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class QuadrigaCxAccountService extends QuadrigaCxAccountServiceRaw
    implements AccountService {

  public QuadrigaCxAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    QuadrigaCxBalance quadrigaCxBalance = getQuadrigaCxBalance();
    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        quadrigaCxBalance.getFee(),
        QuadrigaCxAdapters.adaptWallet(quadrigaCxBalance));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {

    if (currency.equals(Currency.BTC)) return withdrawBitcoin(amount, address);
    else if (currency.equals(Currency.ETH)) return withdrawEther(amount, address);
    else throw new IllegalStateException("unsupported ccy " + currency);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie.
   * repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    if (currency.equals(Currency.BTC))
      return getQuadrigaCxBitcoinDepositAddress().getDepositAddress();
    else if (currency.equals(Currency.ETH))
      return getQuadrigaCxEtherDepositAddress().getDepositAddress();
    else throw new IllegalStateException("unsupported ccy " + currency);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
