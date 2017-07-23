package org.knowm.xchange.anx.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.ANXUtils;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.anx.v2.dto.account.ANXAccountInfo;
import org.knowm.xchange.anx.v2.dto.account.ANXAccountInfoWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXBitcoinDepositAddress;
import org.knowm.xchange.anx.v2.dto.account.ANXBitcoinDepositAddressWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistory;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryEntry;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponse;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponseWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.utils.Assert;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.knowm.xchange.utils.DateUtils.toMillisNullSafe;

public class ANXAccountServiceRaw extends ANXBaseService {

  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;

  /**
   * Constructor
   */
  protected ANXAccountServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public ANXAccountInfo getANXAccountInfo() throws IOException {

    try {
      ANXAccountInfoWrapper anxAccountInfoWrapper = anxV2.getAccountInfo(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory());
      return anxAccountInfoWrapper.getANXAccountInfo();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXWithdrawalResponseWrapper anxWithdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    try {
      ANXWithdrawalResponseWrapper anxWithdrawalResponseWrapper = anxV2.withdrawBtc(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), currency, address,
          amount.multiply(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR_2)).intValue(), 1, false, false);
      return anxWithdrawalResponseWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXBitcoinDepositAddress anxRequestDepositAddress(String currency) throws IOException {

    try {
      ANXBitcoinDepositAddressWrapper anxBitcoinDepositAddressWrapper = anxV2.requestDepositAddress(exchange.getExchangeSpecification().getApiKey(),
          signatureCreator, exchange.getNonceFactory(), currency);
      return anxBitcoinDepositAddressWrapper.getAnxBitcoinDepositAddress();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public List<ANXWalletHistoryEntry> getWalletHistory(TradeHistoryParams params) throws IOException {
    String currencyCode = null;
    if (params instanceof TradeHistoryParamCurrency) {
      Currency currency = ((TradeHistoryParamCurrency) params).getCurrency();
      currencyCode = currency == null ? null : currency.getCurrencyCode();
    }

    Integer pageNumber = null;
    if (params instanceof TradeHistoryParamPaging) {
      pageNumber = ((TradeHistoryParamPaging) params).getPageNumber();
    }
    boolean userSpecifiedPageNumber = pageNumber != null;

    Date from = null;
    Date to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      from = tradeHistoryParamsTimeSpan.getStartTime();
      to = tradeHistoryParamsTimeSpan.getEndTime();
    }

    List<ANXWalletHistoryEntry> all = new ArrayList<>();

    ANXWalletHistory walletHistory = getWalletHistory(currencyCode, pageNumber, from, to);

    all.addAll(Arrays.asList(walletHistory.getANXWalletHistoryEntries()));

    //if there are more results (and the user didn't specify a specific page) keep loading
    while (walletHistory.getRecords() == walletHistory.getMaxResults() && !userSpecifiedPageNumber) {
      pageNumber = walletHistory.getCurrentPage() + 1;

      walletHistory = getWalletHistory(currencyCode, pageNumber, from, to);

      all.addAll(Arrays.asList(walletHistory.getANXWalletHistoryEntries()));
    }

    return all;
  }

  public ANXWalletHistory getWalletHistory(String currency, Integer page, Date from, Date to) throws IOException {
    try {
      ANXWalletHistoryWrapper walletHistory = anxV2.getWalletHistory(exchange.getExchangeSpecification().getApiKey(),
          signatureCreator, exchange.getNonceFactory(), currency, page, toMillisNullSafe(from), toMillisNullSafe(to));
      return walletHistory.getANXWalletHistory();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }
}
