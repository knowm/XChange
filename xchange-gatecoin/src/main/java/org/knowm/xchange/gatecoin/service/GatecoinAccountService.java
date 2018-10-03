package org.knowm.xchange.gatecoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.gatecoin.GatecoinAdapters;
import org.knowm.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Sumedha */
public class GatecoinAccountService extends GatecoinAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        GatecoinAdapters.adaptWallet(getGatecoinBalance().getBalances()));
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    GatecoinDepositAddressResult result = getGatecoinDepositAddress();
    if (result.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      GatecoinDepositAddress[] addresses = result.getAddresses();
      if (addresses.length > 0) return addresses[0].getAddress();
      else return null;
    }
    return null;
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

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {

    GatecoinWithdrawResult result = withdrawGatecoinFunds(currency.toString(), amount, address);
    if (result.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return "Ok";
    } else {
      return result.getResponseStatus().getMessage();
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
