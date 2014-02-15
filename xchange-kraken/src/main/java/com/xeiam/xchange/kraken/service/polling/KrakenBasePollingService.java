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

import java.util.Arrays;
import java.util.Set;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.service.KrakenBaseService;
import com.xeiam.xchange.utils.Assert;

public class KrakenBasePollingService extends KrakenBaseService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public KrakenBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");

  }

  protected <T> T checkResult(KrakenResult<T> krakenResult) {

    if (!krakenResult.isSuccess()) {
      throw new ExchangeException(Arrays.toString(krakenResult.getError()));
    }

    return krakenResult.getResult();
  }

  protected String createDelimitedString(String[] items) {

    StringBuilder commaDelimitedString = null;
    if (items != null) {
      for (String item : items) {
        if (commaDelimitedString == null) {
          commaDelimitedString = new StringBuilder(item);
        }
        else {
          commaDelimitedString.append(",").append(item);
        }
      }
    }

    return (commaDelimitedString == null) ? null : commaDelimitedString.toString();
  }

  protected String delimitAssets(String[] assets) {

    StringBuilder commaDelimitedAssets = new StringBuilder();
    if (assets != null && assets.length > 0) {
      boolean started = false;
      for (String asset : assets) {
        commaDelimitedAssets.append((started) ? "," : "").append(KrakenUtils.getKrakenCurrencyCode(asset));
        started = true;
      }

      return commaDelimitedAssets.toString();
    }

    return null;
  }

  protected String delimitAssetPairs(CurrencyPair[] currencyPairs) {

    String assetPairsString = null;
    if (currencyPairs != null && currencyPairs.length > 0) {
      StringBuilder delimitStringBuilder = null;
      for (CurrencyPair currencyPair : currencyPairs) {
        String krakenAssetPair = KrakenUtils.createKrakenCurrencyPair(currencyPair);
        if (delimitStringBuilder == null) {
          delimitStringBuilder = new StringBuilder(krakenAssetPair);
        }
        else {
          delimitStringBuilder.append(",").append(krakenAssetPair);
        }
      }
      assetPairsString = delimitStringBuilder.toString();
    }

    return assetPairsString;
  }

  protected String delimitSet(Set<?> items) {

    String delimitedSetString = null;
    if (items != null && !items.isEmpty()) {
      StringBuilder delimitStringBuilder = null;
      for (Object item : items) {
        if (delimitStringBuilder == null) {
          delimitStringBuilder = new StringBuilder(item.toString());
        }
        else {
          delimitStringBuilder.append(",").append(item.toString());
        }
      }
    }
    return delimitedSetString;
  }

}
