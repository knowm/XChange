/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.dto.account.KrakenLedger;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeBalanceInfo;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeVolume;
import com.xeiam.xchange.kraken.dto.account.LedgerType;
import com.xeiam.xchange.kraken.dto.account.results.KrakenBalanceResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenLedgerResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenQueryLedgerResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeBalanceInfoResult;
import com.xeiam.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;

/**
 * @author jamespedwards42
 */
public class KrakenAccountServiceRaw extends KrakenBasePollingService<KrakenAuthenticated> {

  public KrakenAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(KrakenAuthenticated.class, exchangeSpecification);
  }

  /**
   * To avoid having to map to Kraken currency codes (e.g., ZUSD) use {@link KrakenAccountService#getAccountInfo} instead.
   * 
   * @return Map of Kraken Assets to account balance
   * @throws IOException
   */
  public Map<String, BigDecimal> getKrakenBalance() throws IOException {

    KrakenBalanceResult balanceResult = kraken.balance(exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
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
      valuationCurrency = getKrakenCurrencyCode(valuationCurrency);

    KrakenTradeBalanceInfoResult balanceResult = kraken.tradeBalance(null, valuationCurrency, exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
    return checkResult(balanceResult);
  }

  /**
   * Retrieves the user's trade balance using the default currency ZUSD to
   * determine the balance.
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
  public Map<String, KrakenLedger> getKrakenLedgerInfo(LedgerType ledgerType, String start, String end, String offset, String... assets) throws IOException {

    String ledgerTypeString = (ledgerType == null) ? "all" : ledgerType.toString().toLowerCase();

    KrakenLedgerResult ledgerResult = kraken.ledgers(null, delimitAssets(assets), ledgerTypeString, start, end, offset, exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
    return checkResult(ledgerResult).getLedgerMap();
  }

  public Map<String, KrakenLedger> queryKrakenLedger(String... ledgerIds) throws IOException {

    KrakenQueryLedgerResult ledgerResult = kraken.queryLedgers(createDelimitedString(ledgerIds), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(ledgerResult);
  }

  public KrakenTradeVolume getTradeVolume(CurrencyPair... currencyPairs) throws IOException {

    KrakenTradeVolumeResult result = kraken.tradeVolume(delimitAssetPairs(currencyPairs), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result);
  }
}
