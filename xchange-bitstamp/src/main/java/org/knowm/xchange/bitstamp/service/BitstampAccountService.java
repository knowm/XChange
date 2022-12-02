package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.BitstampUtils;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.utils.DateUtils;

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

  public String withdrawFunds(
      Currency currency, BigDecimal amount, String address, String addressTag) throws IOException {
    return withdrawFunds(
        new DefaultWithdrawFundsParams(address, addressTag, currency, amount, null));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {

    BitstampWithdrawal response;

    // XRP, XLM and HBAR add extra param to transaction address which will be the addressTag
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      response =
          withdrawBitstampFunds(
              defaultParams.getCurrency(),
              defaultParams.getAmount(),
              defaultParams.getAddress(),
              defaultParams.getAddressTag());
      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }
    } else {
      throw new IllegalStateException("Unsupported WithdrawFundsParams sub class");
    }

    if (response.getId() == null) {
      return null;
    }

    return Long.toString(response.getId());
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie.
   * repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    if (currency.equals(Currency.BTC)) {
      return getBitstampBitcoinDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.LTC)) {
      return getBitstampLitecoinDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.XRP)) {
      return getXRPDepositAddress().getAddressAndDt();
    } else if (currency.equals(Currency.BCH)) {
      return getBitstampBitcoinCashDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.ETH)) {
      return getBitstampEthereumDepositAddress().getDepositAddress();
    } else {
      throw new IllegalStateException("Unsupported currency " + currency);
    }
  }

  public BitstampDepositAddress requestDepositAddressObject(Currency currency, String... arguments)
      throws IOException {
    if (currency.equals(Currency.BTC)) {
      return getBitstampBitcoinDepositAddress();
    } else if (currency.equals(Currency.LTC)) {
      return getBitstampLitecoinDepositAddress();
    } else if (currency.equals(Currency.XRP)) {
      return getXRPDepositAddress();
    } else if (currency.equals(Currency.BCH)) {
      return getBitstampBitcoinCashDepositAddress();
    } else if (currency.equals(Currency.ETH)) {
      return getBitstampEthereumDepositAddress();
    } else {
      throw new IllegalStateException("Unsupported currency " + currency);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitstampTradeHistoryParams(null, BitstampUtils.MAX_TRANSACTIONS_PER_QUERY);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Long limit = null;
    Long offset = null;
    TradeHistoryParamsSorted.Order sort = null;
    Long sinceTimestamp = null;
    String sinceId = null;
    if (params instanceof TradeHistoryParamPaging) {
      limit = Long.valueOf(((TradeHistoryParamPaging) params).getPageLength());
    }
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    if (params instanceof TradeHistoryParamsTimeSpan) {
      sinceTimestamp =
          DateUtils.toUnixTimeNullSafe(((TradeHistoryParamsTimeSpan) params).getStartTime());
    }
    if (params instanceof TradeHistoryParamsIdSpan) {
      sinceId = Optional.ofNullable(((TradeHistoryParamsIdSpan) params).getStartId()).orElse(null);
    }
    BitstampUserTransaction[] txs =
        getBitstampUserTransactions(
            limit, offset, sort == null ? null : sort.toString(), sinceTimestamp, sinceId);
    return BitstampAdapters.adaptFundingHistory(Arrays.asList(txs));
  }
}
