package org.knowm.xchange.coingi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.dto.request.WithdrawalRequest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
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
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoingiAccountService(Exchange exchange) {
    super(exchange);
  }

  /** @throws IOException */
  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return CoingiAdapters.adaptAccountInfo(
        getCoingiBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams p)
      throws ExchangeException, IOException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException {
    if (p instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams params = (DefaultWithdrawFundsParams) p;
      WithdrawalRequest request =
          new WithdrawalRequest()
              .setAddress(params.address)
              .setAmount(params.amount.doubleValue())
              .setCurrency(params.currency.getCurrencyCode().toUpperCase());

      return withdraw(request).toString();
    }

    throw new NotYetImplementedForExchangeException();
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie.
   * repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CoingiTradeHistoryParams(null, 1, 30, null, null);
  }

  /*
  public TransactionList getTransactions(TradeHistoryParams p) throws IOException {
      CoingiTradeHistoryParams params = (CoingiTradeHistoryParams) p;

      TransactionHistoryRequest request = new TransactionHistoryRequest();
      request.setPageNumber(params.getPageNumber());
      request.setCurrencyPair(Optional.of(params.getCurrencyPair()));
      request.setPageSize(params.getPageSize());
      request.setType(Optional.of(params.getType()));
      request.setStatus(Optional.of(params.getStatus()));
      return getTransactions(request);
  }*/

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
