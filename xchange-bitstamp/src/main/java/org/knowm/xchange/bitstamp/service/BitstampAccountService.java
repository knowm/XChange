package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.BitstampUtils;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Matija Mazi */
public class BitstampAccountService extends BitstampAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BitstampAdapters.adaptAccountInfo(
        getBitstampBalance(), exchange.getExchangeSpecification().getUserName());
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

      BitstampWithdrawal response =
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

      BitstampWithdrawal response;
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

    BitstampDepositAddress response = null;

    if (currency.equals(Currency.BTC)) {
      response = getBitstampBitcoinDepositAddress();
    } else if (currency.equals(Currency.LTC)) {
      response = getBitstampLitecoinDepositAddress();
    } else if (currency.equals(Currency.ETH)) {
      response = getBitstampEthereumDepositAddress();
    } else {
      throw new IllegalStateException("Unsupported currency " + currency);
    }

    return response.getDepositAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitstampTradeHistoryParams(null, BitstampUtils.MAX_TRANSACTIONS_PER_QUERY);
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
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    BitstampUserTransaction[] txs =
        getBitstampUserTransactions(limit, offset, sort == null ? null : sort.toString());
    return BitstampAdapters.adaptFundingHistory(Arrays.asList(txs));
  }
}
