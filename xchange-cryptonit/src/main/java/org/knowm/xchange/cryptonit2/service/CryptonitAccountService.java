package org.knowm.xchange.cryptonit2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAdapters;
import org.knowm.xchange.cryptonit2.CryptonitUtils;
import org.knowm.xchange.cryptonit2.dto.account.CryptoWithdrawParams;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitDepositAddress;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitWithdrawal;
import org.knowm.xchange.cryptonit2.dto.account.SepaWithdrawParams;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

/** @author Matija Mazi */
public class CryptonitAccountService extends CryptonitAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return CryptonitAdapters.adaptAccountInfo(
        getCryptonitBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new CryptoWithdrawParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    CryptonitWithdrawal response;
    if (params instanceof CryptoWithdrawParams) {
      CryptoWithdrawParams cryptoParams = (CryptoWithdrawParams) params;
      response =
          withdrawCrypto(
              cryptoParams.getAmount(), cryptoParams.address, cryptoParams.getCurrency());
    } else if (params instanceof SepaWithdrawParams) {
      SepaWithdrawParams sepaParams = (SepaWithdrawParams) params;
      response = withdrawSepa(sepaParams);
    } else {
      throw new IllegalStateException("Don't know how to withdraw: " + params);
    }
    if (response.error != null) {
      throw new ExchangeException("Failed to withdraw: " + response.error);
    }
    return String.valueOf(response.getSuccess());
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie.
   * repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    CryptonitDepositAddress response = null;

    throw new NotYetImplementedForExchangeException();
    // return response.getDepositAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CryptonitTradeHistoryParams(null, CryptonitUtils.MAX_TRANSACTIONS_PER_QUERY);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    Long limit = null;
    CurrencyPair currencyPair = null;
    Long offset = null;
    TradeHistoryParamsSorted.Order sort = null;
    if (params instanceof TradeHistoryParamPaging) {
      limit = Long.valueOf(((TradeHistoryParamPaging) params).getPageLength());
    }
    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    CryptonitUserTransaction[] txs =
        getCryptonitUserTransactions(limit, offset, sort == null ? null : sort.toString());
    return CryptonitAdapters.adaptFundingHistory(Arrays.asList(txs));
  }
}
