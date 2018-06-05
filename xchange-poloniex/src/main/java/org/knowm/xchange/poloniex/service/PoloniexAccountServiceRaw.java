package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.account.PoloniexBalance;
import org.knowm.xchange.poloniex.dto.account.PoloniexLoan;
import org.knowm.xchange.poloniex.dto.account.PoloniexWallet;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDepositsWithdrawalsResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexGenerateNewAddressResponse;
import org.knowm.xchange.utils.DateUtils;

/** @author Zach Holmes */
public class PoloniexAccountServiceRaw extends PoloniexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public HashMap<String, PoloniexBalance> getExchangeWallet() throws IOException {
    return poloniexAuthenticated.returnCompleteBalances(
        apiKey, signatureCreator, exchange.getNonceFactory(), null);
  }

  public HashMap<String, PoloniexBalance> getWallets() throws IOException {
    // using account="all" for margin + lending balances
    return poloniexAuthenticated.returnCompleteBalances(
        apiKey, signatureCreator, exchange.getNonceFactory(), "all");
  }

  public HashMap<String, PoloniexLoan[]> getLoanInfo() throws IOException {
    return poloniexAuthenticated.returnActiveLoans(
        apiKey, signatureCreator, exchange.getNonceFactory());
  }

  public String getDepositAddress(String currency) throws IOException {

    HashMap<String, String> response =
        poloniexAuthenticated.returnDepositAddresses(
            apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.containsKey("error")) {
      throw new PoloniexException(response.get("error"));
    }
    if (response.containsKey(currency)) {
      return response.get(currency);
    } else {
      PoloniexGenerateNewAddressResponse newAddressResponse =
          poloniexAuthenticated.generateNewAddress(
              apiKey, signatureCreator, exchange.getNonceFactory(), currency);
      if (newAddressResponse.success()) {
        return newAddressResponse.getAddress();
      } else {
        throw new PoloniexException("Failed to get Poloniex deposit address for " + currency);
      }
    }
  }

  /** @param paymentId For XMR withdrawals, you may optionally specify "paymentId". */
  public String withdraw(
      Currency currency, BigDecimal amount, String address, @Nullable String paymentId)
      throws IOException {
    return poloniexAuthenticated
        .withdraw(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            currency.getCurrencyCode(),
            amount,
            address,
            paymentId)
        .getResponse();
  }

  public PoloniexDepositsWithdrawalsResponse returnDepositsWithdrawals(Date start, Date end)
      throws IOException {
    return poloniexAuthenticated.returnDepositsWithdrawals(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        DateUtils.toUnixTimeNullSafe(start),
        DateUtils.toUnixTimeNullSafe(end));
  }

  public String transfer(
      Currency currency, BigDecimal amount, PoloniexWallet fromWallet, PoloniexWallet toWallet)
      throws IOException {
    return poloniexAuthenticated
        .transferBalance(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            currency.getCurrencyCode(),
            amount,
            fromWallet.name().toLowerCase(),
            toWallet.name().toLowerCase())
        .getMessage();
  }
}
