package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: bryant_harris
 */

public class AbucoinsAccountService extends AbucoinsAccountServiceRaw implements AccountService {
  private static Logger logger = LoggerFactory.getLogger(AbucoinsAccountService.class);
        
  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    AbucoinsAccount[] accounts = getAbucoinsAccounts();
    return AbucoinsAdapters.adaptAccountInfo(accounts);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    String currencyStr = currency.getCurrencyCode();
    String method = null;
    AbucoinsPaymentMethod[] paymentMethods = getPaymentMethods();
    for ( AbucoinsPaymentMethod apm : paymentMethods ) {
      if ( apm.getCurrency().equals(currencyStr)) {
        method = apm.getType();
        break;
      }
    }
          
    if ( method == null )
      logger.warn("Unable to determine the payment method suitable for " + currency + " this will likely lead to an error");

    AbucoinsCryptoDeposit cryptoDeposit = getAbucoinsCryptoDeposit(new AbucoinsCryptoDepositRequest(currencyStr, method));
    if ( cryptoDeposit.getMessage() != null )
      throw new IOException(cryptoDeposit.getMessage());
          
    return cryptoDeposit.getAddress();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();

  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(
      TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
