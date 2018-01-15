package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoWithdrawal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * Author: bryant_harris
 */
public class AbucoinsAccountService extends AbucoinsAccountServiceRaw implements AccountService {        
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
    String method = abucoinsPaymentMethodForCurrency(currency.getCurrencyCode());
    AbucoinsCryptoDeposit cryptoDeposit = abucoinsCryptoDeposit(new AbucoinsCryptoDepositRequest(currency.getCurrencyCode(), method));
    if ( cryptoDeposit.getMessage() != null )
      throw new IOException(cryptoDeposit.getMessage());
          
    return cryptoDeposit.getAddress();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if ( params instanceof DefaultWithdrawFundsParams ) {
      DefaultWithdrawFundsParams defParams = (DefaultWithdrawFundsParams) params;
                 
      String method = abucoinsPaymentMethodForCurrency(defParams.currency.getCurrencyCode());
      AbucoinsCryptoWithdrawal withdrawal = abucoinsWithdraw( new AbucoinsCryptoWithdrawalRequest(defParams.amount,
                                                                                                  defParams.currency.getCurrencyCode(),
                                                                                                  method,
                                                                                                  defParams.address,
                                                                                                  null));
      return withdrawal.getPayoutId();
    }
         
    if ( params == null )
      throw new IllegalArgumentException("Requires a DefaultWithdrawFundsParams object to describe the withdrawal");
         
    throw new IOException("Abucoins only understands DefaultWithdrawFundsParams not " + params.getClass().getName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
