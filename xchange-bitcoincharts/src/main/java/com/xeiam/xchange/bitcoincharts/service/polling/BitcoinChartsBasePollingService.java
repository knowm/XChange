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
package com.xeiam.xchange.bitcoincharts.service.polling;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitcoinChartsBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair(Currencies.BTC, "thAUD"),

  new CurrencyPair(Currencies.BTC, "bitmarketAUD"),

  new CurrencyPair(Currencies.BTC, "mtgoxAUD"),

  new CurrencyPair(Currencies.BTC, "ruxumAUD"),

  new CurrencyPair(Currencies.BTC, "weexAUD"),

  new CurrencyPair(Currencies.BTC, "wbxAUD"),

  new CurrencyPair(Currencies.BTC, "cryptoxAUD"),

  new CurrencyPair(Currencies.BTC, "mrcdBRL"),

  new CurrencyPair(Currencies.BTC, "bbmBRL"),

  new CurrencyPair(Currencies.BTC, "mtgoxCAD"),

  new CurrencyPair(Currencies.BTC, "virtexCAD"),

  new CurrencyPair(Currencies.BTC, "weexCAD"),

  new CurrencyPair(Currencies.BTC, "mtgoxCHF"),

  new CurrencyPair(Currencies.BTC, "ruxumCHF"),

  new CurrencyPair(Currencies.BTC, "thCLP"),

  new CurrencyPair(Currencies.BTC, "btcnCNY"),

  new CurrencyPair(Currencies.BTC, "mtgoxCNY"),

  new CurrencyPair(Currencies.BTC, "mtgoxDKK"),

  new CurrencyPair(Currencies.BTC, "bcEUR"),

  new CurrencyPair(Currencies.BTC, "btcexEUR"),

  new CurrencyPair(Currencies.BTC, "thEUR"),

  new CurrencyPair(Currencies.BTC, "rockEUR"),

  new CurrencyPair(Currencies.BTC, "b7EUR"),

  new CurrencyPair(Currencies.BTC, "mtgoxEUR"),

  new CurrencyPair(Currencies.BTC, "aqoinEUR"),

  new CurrencyPair(Currencies.BTC, "ruxumEUR"),

  new CurrencyPair(Currencies.BTC, "intrsngEUR"),

  new CurrencyPair(Currencies.BTC, "btcdeEUR"),

  new CurrencyPair(Currencies.BTC, "globalEUR"),

  new CurrencyPair(Currencies.BTC, "vcxEUR"),

  new CurrencyPair(Currencies.BTC, "imcexEUR"),

  new CurrencyPair(Currencies.BTC, "btc24EUR"),

  new CurrencyPair(Currencies.BTC, "bitcurexEUR"),

  new CurrencyPair(Currencies.BTC, "btceEUR"),

  new CurrencyPair(Currencies.BTC, "bcmPXGAU"),

  new CurrencyPair(Currencies.BTC, "bcmBMGAU"),

  new CurrencyPair(Currencies.BTC, "britcoinGBP"),

  new CurrencyPair(Currencies.BTC, "bitmarketGBP"),

  new CurrencyPair(Currencies.BTC, "bcGBP"),

  new CurrencyPair(Currencies.BTC, "bcGBP"),

  new CurrencyPair(Currencies.BTC, "intrsngGBP"),

  new CurrencyPair(Currencies.BTC, "ruxumGBP"),

  new CurrencyPair(Currencies.BTC, "globalGBP"),

  new CurrencyPair(Currencies.BTC, "mtgoxHKD"),

  new CurrencyPair(Currencies.BTC, "ruxumHKD"),

  new CurrencyPair(Currencies.BTC, "ruxumHUF"),

  new CurrencyPair(Currencies.BTC, "thINR"),

  new CurrencyPair(Currencies.BTC, "btcexJPY"),

  new CurrencyPair(Currencies.BTC, "ruxumJPY"),

  new CurrencyPair(Currencies.BTC, "mtgoxJPY"),

  new CurrencyPair(Currencies.BTC, "bitnzNZD"),

  new CurrencyPair(Currencies.BTC, "mtgoxNZD"),

  new CurrencyPair(Currencies.BTC, "bitomatPLN"),

  new CurrencyPair(Currencies.BTC, "bitmarketPLN"),

  new CurrencyPair(Currencies.BTC, "bitchangePLN"),

  new CurrencyPair(Currencies.BTC, "mtgoxPLN"),

  new CurrencyPair(Currencies.BTC, "intrsngPLN"),

  new CurrencyPair(Currencies.BTC, "ruxumPLN"),

  new CurrencyPair(Currencies.BTC, "globalPLN"),

  new CurrencyPair(Currencies.BTC, "freshPLN"),

  new CurrencyPair(Currencies.BTC, "bitcurexPLN"),

  new CurrencyPair(Currencies.BTC, "btcexWMR"),

  new CurrencyPair(Currencies.BTC, "btcexRUB"),

  new CurrencyPair(Currencies.BTC, "btcexYAD"),

  new CurrencyPair(Currencies.BTC, "bitmarketRUB"),

  new CurrencyPair(Currencies.BTC, "mtgoxRUB"),

  new CurrencyPair(Currencies.BTC, "ruxumRUB"),

  new CurrencyPair(Currencies.BTC, "btceRUR"),

  new CurrencyPair(Currencies.BTC, "mtgoxSEK"),

  new CurrencyPair(Currencies.BTC, "ruxumSEK"),

  new CurrencyPair(Currencies.BTC, "kptnSEK"),

  new CurrencyPair(Currencies.BTC, "mtgoxSGD"),

  new CurrencyPair(Currencies.BTC, "ruxumSGD"),

  new CurrencyPair(Currencies.BTC, "fybsgSGD"),

  new CurrencyPair(Currencies.BTC, "virwoxSLL"),

  new CurrencyPair(Currencies.BTC, "rockSLL"),

  new CurrencyPair(Currencies.BTC, "ruxumTHB"),

  new CurrencyPair(Currencies.BTC, "mtgoxTHB"),

  new CurrencyPair(Currencies.BTC, "ruxumUAH"),

  new CurrencyPair(Currencies.BTC, "bcmPPUSD"),

  new CurrencyPair(Currencies.BTC, "mtgoxUSD"),

  new CurrencyPair(Currencies.BTC, "bcmLRUSD"),

  new CurrencyPair(Currencies.BTC, "btcexUSD"),

  new CurrencyPair(Currencies.BTC, "btcexWMZ"),

  new CurrencyPair(Currencies.BTC, "bcmBMUSD"),

  new CurrencyPair(Currencies.BTC, "bcmMBUSD"),

  new CurrencyPair(Currencies.BTC, "b2cUSD"),

  new CurrencyPair(Currencies.BTC, "thLRUSD"),

  new CurrencyPair(Currencies.BTC, "thUSD"),

  new CurrencyPair(Currencies.BTC, "vcxUSD"),

  new CurrencyPair(Currencies.BTC, "exchbUSD"),

  new CurrencyPair(Currencies.BTC, "b7USD"),

  new CurrencyPair(Currencies.BTC, "ruxumUSD"),

  new CurrencyPair(Currencies.BTC, "cbxUSD"),

  new CurrencyPair(Currencies.BTC, "btceUSD"),

  new CurrencyPair(Currencies.BTC, "bitstampUSD"),

  new CurrencyPair(Currencies.BTC, "globalUSD"),

  new CurrencyPair(Currencies.BTC, "intrsngUSD"),

  new CurrencyPair(Currencies.BTC, "bitfloorUSD"),

  new CurrencyPair(Currencies.BTC, "rockUSD"),

  new CurrencyPair(Currencies.BTC, "cryptoxUSD"),

  new CurrencyPair(Currencies.BTC, "icbitUSD"),

  new CurrencyPair(Currencies.BTC, "bitmeUSD"),

  new CurrencyPair(Currencies.BTC, "imcexUSD"),

  new CurrencyPair(Currencies.BTC, "btctreeUSD"),

  new CurrencyPair(Currencies.BTC, "btc24USD"),

  new CurrencyPair(Currencies.BTC, "weexUSD"),

  new CurrencyPair(Currencies.BTC, "ruxumZAR")

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcoinChartsBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
