/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.kraken.dto.marketdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Raphael Voellmy
 */
public class KrakenTrades {

  private final Map<String, String[][]> tradesPerCurrencyPair = new HashMap<String, String[][]>();
  private final long last;

  /**
   * Constructor
   * 
   * @param xxbtzusd
   * @param xxbtzeur
   * @param xltczeur
   * @param xxbtxltc
   * @param xltczusd
   * @param last
   */
  public KrakenTrades(@JsonProperty("XXBTZUSD") String[][] xxbtzusd, @JsonProperty("XXBTZEUR") String[][] xxbtzeur, @JsonProperty("XLTCZEUR") String[][] xltczeur,
      @JsonProperty("XXBTXLTC") String[][] xxbtxltc, @JsonProperty("XLTCZUSD") String[][] xltczusd, @JsonProperty("XLTCXXRP") String[][] xltcxxrp, @JsonProperty("XLTCZKRW") String[][] xltczkrw,
      @JsonProperty("XNMCXXRP") String[][] xnmcxxrp, @JsonProperty("XNMCZEUR") String[][] xnmczeur, @JsonProperty("XNMCZKRW") String[][] xnmczkrw, @JsonProperty("XNMCZUSD") String[][] xnmczusd,
      @JsonProperty("XXBTXNMC") String[][] xxbtxnmc, @JsonProperty("XXBTXXRP") String[][] xxbtxxrp, @JsonProperty("XXBTXXVN") String[][] xxbtxxvn, @JsonProperty("XXBTZKRW") String[][] xxbtzkrw,
      @JsonProperty("XXVNXXRP") String[][] xxvnxxrp, @JsonProperty("ZEURXXRP") String[][] zeurxxrp, @JsonProperty("ZEURXXVN") String[][] zeurxxvn, @JsonProperty("ZKRWXXRP") String[][] zkrwxxrp,
      @JsonProperty("ZUSDXXRP") String[][] zusdxxrp, @JsonProperty("ZUSDXXVN") String[][] zusdxxvn, @JsonProperty("last") long last) {

    tradesPerCurrencyPair.put("XXBTZUSD", xxbtzusd);
    tradesPerCurrencyPair.put("XXBTZEUR", xxbtzeur);
    tradesPerCurrencyPair.put("XLTCZEUR", xltczeur);
    tradesPerCurrencyPair.put("XXBTXLTC", xxbtxltc);
    tradesPerCurrencyPair.put("XLTCZUSD", xltczusd);
    tradesPerCurrencyPair.put("XLTCXXRP", xltcxxrp);
    tradesPerCurrencyPair.put("XLTCZKRW", xltczkrw);
    tradesPerCurrencyPair.put("XNMCXXRP", xnmcxxrp);
    tradesPerCurrencyPair.put("XNMCZEUR", xnmczeur);
    tradesPerCurrencyPair.put("XNMCZKRW", xnmczkrw);
    tradesPerCurrencyPair.put("XNMCZUSD", xnmczusd);
    tradesPerCurrencyPair.put("XXBTXNMC", xxbtxnmc);
    tradesPerCurrencyPair.put("XXBTXXRP", xxbtxxrp);
    tradesPerCurrencyPair.put("XXBTXXVN", xxbtxxvn);
    tradesPerCurrencyPair.put("XXBTZKRW", xxbtzkrw);
    tradesPerCurrencyPair.put("XXVNXXRP", xxvnxxrp);
    tradesPerCurrencyPair.put("ZEURXXRP", zeurxxrp);
    tradesPerCurrencyPair.put("ZEURXXVN", zeurxxvn);
    tradesPerCurrencyPair.put("ZKRWXXRP", zkrwxxrp);
    tradesPerCurrencyPair.put("ZUSDXXRP", zusdxxrp);
    tradesPerCurrencyPair.put("ZUSDXXVN", zusdxxvn);

    this.last = last;

  }

  /**
   * @param krakenCurrencyPair the concatenated currencies in their Kraken symbols e.g. XBTCZEUR
   * @return
   */
  public String[][] getTradesPerCurrencyPair(String krakenCurrencyPair) {

    return tradesPerCurrencyPair.get(krakenCurrencyPair);
  }

  public long getLast() {

    return last;
  }
}
