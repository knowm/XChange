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
package com.xeiam.xchange.mintpal.service.polling;

import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.mintpal.MintPal;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataServiceRaw extends MintPalBasePollingService<MintPal> {

  public MintPalMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(MintPal.class, exchangeSpecification);
  }
  
  public List<MintPalTicker> getAllMintPalTickers() {
    
    return handleRespone(mintPal.getAllTickers());
  }
  
  public MintPalTicker getMintPalTicker(final CurrencyPair currencyPair) {
    
    return handleRespone(mintPal.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
  
  public List<MintPalPublicOrders> getMintPalOrders(final CurrencyPair currencyPair) {
    
    return handleRespone(mintPal.getOrders(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
  
  public List<MintPalPublicTrade> getMintPalTrades(final CurrencyPair currencyPair) {
    
    return handleRespone(mintPal.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
}
