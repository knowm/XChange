package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.account.KrakenLedger;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeBalanceInfo;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeVolume;
import com.xeiam.xchange.kraken.dto.account.LedgerType;
import com.xeiam.xchange.kraken.dto.account.results.KrakenBalanceResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenLedgerResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenQueryLedgerResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeBalanceInfoResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;
import com.xeiam.xchange.kraken.service.KrakenDigest;

/**
 * @author jamespedwards42
 */
public class KrakenAccountServiceRaw extends BaseKrakenService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  /**
   * To avoid having to map to Kraken currency codes (e.g., ZUSD) use {@link KrakenAccountService#getAccountInfo} instead.
   * 
   * @return Map of Kraken Assets to account balance
   * @throws IOException
   */
  public Map<String, BigDecimal> getKrakenBalance() throws IOException {

    KrakenBalanceResult balanceResult = krakenAuthenticated.balance(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());
    return checkResult(balanceResult);
  }

  /**
   * @param valuationCurrency - Base asset used to determine balance (can be null, defaults to USD).
   *          The asset should be provided in the form of a standard currency code, i.e., EUR. It will be converted
   *          to the appropriate Kraken Asset code.
   * @return KrakenTradeBalanceInfo
   * @throws IOException
   */
  public KrakenTradeBalanceInfo getKrakenTradeBalance(String valuationCurrency) throws IOException {

    if (valuationCurrency != null)
      valuationCurrency = KrakenUtils.getKrakenCurrencyCode(valuationCurrency);

    KrakenTradeBalanceInfoResult balanceResult = krakenAuthenticated.tradeBalance(null, valuationCurrency, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());
    return checkResult(balanceResult);
  }

  /**
   * Retrieves the user's trade balance using the default currency ZUSD to
   * determine the balance.
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
  public Map<String, KrakenLedger> getKrakenLedgerInfo(LedgerType ledgerType, String start, String end, String offset, String... assets) throws IOException {

    String ledgerTypeString = (ledgerType == null) ? "all" : ledgerType.toString().toLowerCase();

    KrakenLedgerResult ledgerResult =
        krakenAuthenticated.ledgers(null, delimitAssets(assets), ledgerTypeString, start, end, offset, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());
    return checkResult(ledgerResult).getLedgerMap();
  }

  public Map<String, KrakenLedger> queryKrakenLedger(String... ledgerIds) throws IOException {

    KrakenQueryLedgerResult ledgerResult = krakenAuthenticated.queryLedgers(createDelimitedString(ledgerIds), exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(ledgerResult);
  }

  public KrakenTradeVolume getTradeVolume(CurrencyPair... currencyPairs) throws IOException {

    KrakenTradeVolumeResult result = krakenAuthenticated.tradeVolume(delimitAssetPairs(currencyPairs), exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result);
  }
}
