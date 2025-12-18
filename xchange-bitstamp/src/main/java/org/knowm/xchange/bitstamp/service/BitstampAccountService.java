package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.BitstampUtils;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnSettingRequest;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnTerm;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnTransaction;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnType;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author Matija Mazi
 */
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
      throws ExchangeException,
          NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException,
          IOException {

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
    } else if (currency.equals(Currency.USDT)) {
      return getBitstampUsdtDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.XLM)) {
      return getBitstampXlmDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.DOGE)) {
      return getBitstampDogeDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("RLUSD"))) {
      return getBitstampRlusdDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("EURC"))) {
      return getBitstampEurcDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("SUSHI"))) {
      return getBitstampSushiDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("SHIB"))) {
      return getBitstampShibDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("BONK"))) {
      return getBitstampBonkDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.ETC)) {
      return getBitstampEtcDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("PENGU"))) {
      return getBitstampPenguDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("YFI"))) {
      return getBitstampYfiDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("SUI"))) {
      return getBitstampSuiDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.LINK)) {
      return getBitstampLinkDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("LDO"))) {
      return getBitstampLdoDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("FARTCOIN"))) {
      return getBitstampFartcoinDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.getInstance("PEPE"))) {
      return getBitstampPepeDepositAddress().getDepositAddress();
    } else if (currency.equals(Currency.USDC)) {
      return getBitstampUsdcDepositAddress().getDepositAddress();
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
    } else if (currency.equals(Currency.USDT)) {
      return getBitstampUsdtDepositAddress();
    } else if (currency.equals(Currency.XLM)) {
      return getBitstampXlmDepositAddress();
    } else if (currency.equals(Currency.DOGE)) {
      return getBitstampDogeDepositAddress();
    } else if (currency.equals(Currency.getInstance("RLUSD"))) {
      return getBitstampRlusdDepositAddress();
    } else if (currency.equals(Currency.getInstance("EURC"))) {
      return getBitstampEurcDepositAddress();
    } else if (currency.equals(Currency.getInstance("SUSHI"))) {
      return getBitstampSushiDepositAddress();
    } else if (currency.equals(Currency.getInstance("SHIB"))) {
      return getBitstampShibDepositAddress();
    } else if (currency.equals(Currency.getInstance("BONK"))) {
      return getBitstampBonkDepositAddress();
    } else if (currency.equals(Currency.ETC)) {
      return getBitstampEtcDepositAddress();
    } else if (currency.equals(Currency.getInstance("PENGU"))) {
      return getBitstampPenguDepositAddress();
    } else if (currency.equals(Currency.getInstance("YFI"))) {
      return getBitstampYfiDepositAddress();
    } else if (currency.equals(Currency.getInstance("SUI"))) {
      return getBitstampSuiDepositAddress();
    } else if (currency.equals(Currency.LINK)) {
      return getBitstampLinkDepositAddress();
    } else if (currency.equals(Currency.getInstance("LDO"))) {
      return getBitstampLdoDepositAddress();
    } else if (currency.equals(Currency.getInstance("FARTCOIN"))) {
      return getBitstampFartcoinDepositAddress();
    } else if (currency.equals(Currency.getInstance("PEPE"))) {
      return getBitstampPepeDepositAddress();
    } else if (currency.equals(Currency.USDC)) {
      return getBitstampUsdcDepositAddress();
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

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException {
    return BitstampAdapters.adaptTradingFees(getTradingFees());
  }

  /**
   * Get Earn transaction history.
   *
   * <p><strong>Note:</strong> Query parameters (limit, offset, currency, quoteCurrency) are
   * currently not supported due to Bitstamp API signature validation requirements for authenticated
   * GET requests. All parameters are ignored and the endpoint is called without query parameters.
   * For filtering and pagination, use {@link #getEarnTransactions()} and filter the results
   * client-side.
   *
   * @param limit Maximum number of transactions to return (ignored - not supported)
   * @param offset Number of transactions to skip (ignored - not supported)
   * @param currency Optional currency filter (ignored - not supported)
   * @param quoteCurrency Optional quote currency for value calculation (ignored - not supported)
   * @return List of Earn transactions
   * @throws IOException if the request fails
   */
  public List<BitstampEarnTransaction> getEarnTransactions(
      Integer limit, Integer offset, Currency currency, Currency quoteCurrency) throws IOException {
    String currencyCode = currency != null ? currency.getCurrencyCode() : null;
    String quoteCurrencyCode = quoteCurrency != null ? quoteCurrency.getCurrencyCode() : null;
    return getEarnTransactions(limit, offset, currencyCode, quoteCurrencyCode);
  }

  /**
   * Get Earn transaction history.
   *
   * @return List of Earn transactions
   * @throws IOException if the request fails
   */
  public List<BitstampEarnTransaction> getEarnTransactions() throws IOException {
    return getEarnTransactions(null, null, (Currency) null, (Currency) null);
  }

  /**
   * Subscribe to an Earn product.
   *
   * @param currency The currency to subscribe to
   * @param earnType The earn type: "STAKING" or "LENDING"
   * @param earnTerm The earn term: "FLEXIBLE" or "FIXED"
   * @param amount The amount to subscribe
   * @throws IOException if the request fails
   */
  public void subscribeToEarn(
      Currency currency, BitstampEarnType earnType, BitstampEarnTerm earnTerm, BigDecimal amount)
      throws IOException {
    super.subscribeToEarn(currency.getCurrencyCode(), earnType, earnTerm, amount);
  }

  /**
   * Unsubscribe from an Earn product.
   *
   * @param currency The currency to unsubscribe from
   * @param earnType The earn type: "STAKING" or "LENDING"
   * @param earnTerm The earn term: "FLEXIBLE" or "FIXED"
   * @param amount The amount to unsubscribe
   * @throws IOException if the request fails
   */
  public void unsubscribeFromEarn(
      Currency currency, BitstampEarnType earnType, BitstampEarnTerm earnTerm, BigDecimal amount)
      throws IOException {
    super.unsubscribeFromEarn(currency.getCurrencyCode(), earnType, earnTerm, amount);
  }

  /**
   * Manage Earn subscription settings (opt in/opt out).
   *
   * @param setting The setting: "OPT_IN" or "OPT_OUT"
   * @param currency The currency
   * @param earnType The earn type: "STAKING" or "LENDING"
   * @throws IOException if the request fails
   */
  public void manageEarnSubscriptionSetting(
      BitstampEarnSettingRequest.Setting setting, Currency currency, BitstampEarnType earnType)
      throws IOException {
    super.manageEarnSubscriptionSetting(setting, currency.getCurrencyCode(), earnType);
  }
}
