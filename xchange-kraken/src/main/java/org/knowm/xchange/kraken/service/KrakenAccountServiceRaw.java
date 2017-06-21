package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.account.DepostitStatus;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;
import org.knowm.xchange.kraken.dto.account.KrakenDepositMethods;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.account.KrakenTradeBalanceInfo;
import org.knowm.xchange.kraken.dto.account.KrakenTradeVolume;
import org.knowm.xchange.kraken.dto.account.LedgerType;
import org.knowm.xchange.kraken.dto.account.Withdraw;
import org.knowm.xchange.kraken.dto.account.WithdrawInfo;
import org.knowm.xchange.kraken.dto.account.WithdrawStatus;
import org.knowm.xchange.kraken.dto.account.results.DepositStatusResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenBalanceResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenDepositAddressResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenDepositMethodsResults;
import org.knowm.xchange.kraken.dto.account.results.KrakenLedgerResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenQueryLedgerResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeBalanceInfoResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;
import org.knowm.xchange.kraken.dto.account.results.WithdrawInfoResult;
import org.knowm.xchange.kraken.dto.account.results.WithdrawResult;
import org.knowm.xchange.kraken.dto.account.results.WithdrawStatusResult;

/**
 * @author jamespedwards42
 */
public class KrakenAccountServiceRaw extends KrakenBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * To avoid having to map to Kraken currency codes (e.g., ZUSD) use {@link KrakenAccountService#getAccountInfo} instead.
   *
   * @return Map of Kraken Assets to account balance
   * @throws IOException
   */
  public Map<String, BigDecimal> getKrakenBalance() throws IOException {

    KrakenBalanceResult balanceResult = kraken.balance(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return checkResult(balanceResult);
  }

  public KrakenDepositAddress[] getDepositAddresses(String currency, String method, boolean newAddress) throws IOException {
    KrakenDepositAddressResult depositAddressesResult = kraken.getDepositAddresses(null, currency, method,
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return checkResult(depositAddressesResult);
  }

  public KrakenDepositMethods[] getDepositMethods(String assetPairs, String assets) throws IOException {
    KrakenDepositMethodsResults depositMethods = kraken.getDepositMethods(assetPairs, assets, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(depositMethods);
  }

  public WithdrawInfo getWithdrawInfo(String assetPairs, String assets, String key, BigDecimal amount) throws IOException {
    WithdrawInfoResult withdrawInfoResult = kraken.getWithdrawInfo(assetPairs, assets, key, amount, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(withdrawInfoResult);
  }

  public Withdraw withdraw(String assetPairs, String assets, String key, BigDecimal amount) throws IOException {
    WithdrawResult withdrawResult = kraken.withdraw(assetPairs, assets, key, amount, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(withdrawResult);
  }

  public List<DepostitStatus> getDepositStatus(String assetPairs, String assets, String method) throws IOException {
    DepositStatusResult result = kraken.getDepositStatus(assetPairs, assets, method, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(result);
  }

  public List<WithdrawStatus> getWithdrawStatus(String assetPairs, String assets, String method) throws IOException {
    WithdrawStatusResult result = kraken.getWithdrawStatus(assetPairs, assets, method, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(result);
  }

  /**
   * @param valuationCurrency - Base asset used to determine balance (can be null, defaults to USD). The asset should be provided in the form of a
   * standard currency code, i.e., EUR. It will be converted to the appropriate Kraken Asset code.
   * @return KrakenTradeBalanceInfo
   * @throws IOException
   */
  public KrakenTradeBalanceInfo getKrakenTradeBalance(Currency valuationCurrency) throws IOException {

    String valuationCurrencyCode = null;

    if (valuationCurrency != null) {
      valuationCurrencyCode = KrakenUtils.getKrakenCurrencyCode(valuationCurrency);
    }

    KrakenTradeBalanceInfoResult balanceResult = kraken.tradeBalance(null, valuationCurrencyCode, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(balanceResult);
  }

  /**
   * Retrieves the user's trade balance using the default currency ZUSD to determine the balance.
   *
   * @return KrakenTradeBalanceInfo
   * @throws IOException
   */
  public KrakenTradeBalanceInfo getKrakenTradeBalance() throws IOException {

    return getKrakenTradeBalance(null);
  }

  /**
   * Retrieves the full account Ledger which represents all account asset activity.
   *
   * @return
   * @throws IOException
   */
  public Map<String, KrakenLedger> getKrakenLedgerInfo() throws IOException {

    return getKrakenLedgerInfo(null, null, null, null);
  }

  /**
   * Retrieves the Ledger which represents all account asset activity.
   *
   * @param assets - Set of assets to restrict output to (can be null, defaults to all)
   * @param ledgerType - {@link LedgerType} to retrieve (can be null, defaults to all types)
   * @param start - starting unix timestamp or ledger id of results (can be null)
   * @param end - ending unix timestamp or ledger id of results (can be null)
   * @param offset - result offset (can be null)
   * @return
   * @throws IOException
   */
  public Map<String, KrakenLedger> getKrakenLedgerInfo(LedgerType ledgerType, String start, String end, String offset,
      Currency... assets) throws IOException {

    String ledgerTypeString = (ledgerType == null) ? "all" : ledgerType.toString().toLowerCase();

    KrakenLedgerResult ledgerResult = kraken.ledgers(null, delimitAssets(assets), ledgerTypeString, start, end, offset,
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return checkResult(ledgerResult).getLedgerMap();
  }

  public Map<String, KrakenLedger> queryKrakenLedger(String... ledgerIds) throws IOException {

    KrakenQueryLedgerResult ledgerResult = kraken.queryLedgers(createDelimitedString(ledgerIds), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());

    return checkResult(ledgerResult);
  }

  public KrakenTradeVolume getTradeVolume(CurrencyPair... currencyPairs) throws IOException {
    KrakenTradeVolumeResult result = kraken.tradeVolume(delimitAssetPairs(currencyPairs), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return checkResult(result);
  }
}
