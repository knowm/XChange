package org.knowm.xchange.coingi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.CoingiErrorAdapter;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.account.CoingiBalances;
import org.knowm.xchange.coingi.dto.account.TransactionList;
import org.knowm.xchange.coingi.dto.request.TransactionHistoryRequest;
import org.knowm.xchange.coingi.dto.request.WithdrawalRequest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CoingiAccountService extends CoingiAccountServiceRaw implements AccountService {
  public CoingiAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    CoingiBalances coingiBalances;
    try {
      coingiBalances = getCoingiBalance();
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
    
    return CoingiAdapters.adaptAccountInfo(
        coingiBalances, exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    String result;
    try {
      result = withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }

    return result;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams p)
      throws IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    if (p instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams params = (DefaultWithdrawFundsParams) p;
      WithdrawalRequest request =
          new WithdrawalRequest()
              .setAddress(params.address)
              .setAmount(params.amount)
              .setCurrency(params.currency.getCurrencyCode().toUpperCase());

      return withdraw(request).toString();
    }

    throw new NotYetImplementedForExchangeException();
  }

  /**
   * This returns the current deposit address.
   * It does not generate a new one!
   * Repeated calls will return the same.
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CoingiTradeHistoryParams(null, 1, 30, null, null);
  }

  public TransactionList getTransactions(TradeHistoryParams p) throws IOException {
    CoingiTradeHistoryParams params = (CoingiTradeHistoryParams) p;

    TransactionHistoryRequest request = new TransactionHistoryRequest();
    request.setPageNumber(params.getPageNumber());
    request.setCurrencyPair(params.getCurrencyPair());
    request.setPageSize(params.getPageSize());
    request.setType(params.getType());
    request.setStatus(params.getStatus());
    TransactionList transactions;
    try {
      transactions = getTransactions(request);
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }

    return transactions;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
      throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }
}
