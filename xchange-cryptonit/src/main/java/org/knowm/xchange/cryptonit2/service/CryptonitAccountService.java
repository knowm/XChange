package org.knowm.xchange.cryptonit2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAdapters;
import org.knowm.xchange.cryptonit2.CryptonitUtils;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitDepositAddress;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitWithdrawal;
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
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleWithdrawFundsParams = (RippleWithdrawFundsParams) params;

      CryptonitWithdrawal response =
          withdrawRippleFunds(
              rippleWithdrawFundsParams.getAmount(),
              rippleWithdrawFundsParams.getAddress(),
              rippleWithdrawFundsParams.getTag());

      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }

      if (response.getId() == null) {
        return null;
      }

      return Integer.toString(response.getId());
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;

      CryptonitWithdrawal response;
      if (defaultParams.getCurrency().equals(Currency.LTC)) {
        response = withdrawLtcFunds(defaultParams.getAmount(), defaultParams.getAddress());
      } else if (defaultParams.getCurrency().equals(Currency.ETH)) {
        response = withdrawEthFunds(defaultParams.getAmount(), defaultParams.getAddress());
      } else if (defaultParams.getCurrency().equals(Currency.BTC)) {
        response = withdrawBtcFunds(defaultParams.getAmount(), defaultParams.getAddress());
      } else if (defaultParams.getCurrency().equals(Currency.BCH)) {
        response = withdrawBchFunds(defaultParams.getAmount(), defaultParams.getAddress());
      } else {
        throw new IllegalStateException("Cannot withdraw " + defaultParams.getCurrency());
      }

      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }

      if (response.getId() == null) {
        return null;
      }

      return Integer.toString(response.getId());
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie.
   * repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    CryptonitDepositAddress response = null;

    if (currency.equals(Currency.BTC)) {
      response = getCryptonitBitcoinDepositAddress();
    } else if (currency.equals(Currency.LTC)) {
      response = getCryptonitLitecoinDepositAddress();
    } else if (currency.equals(Currency.ETH)) {
      response = getCryptonitEthereumDepositAddress();
    } else {
      throw new IllegalStateException("Unsupported currency " + currency);
    }

    return response.getDepositAddress();
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
