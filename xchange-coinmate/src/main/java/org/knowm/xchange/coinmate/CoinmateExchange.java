/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmate.service.polling.CoinmateAccountService;
import org.knowm.xchange.coinmate.service.polling.CoinmateMarketDataService;
import org.knowm.xchange.coinmate.service.polling.CoinmateTradeService;
import org.knowm.xchange.utils.CertHelper;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Martin Stachon
 */
public class CoinmateExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CoinmateMarketDataService(this);
    this.pollingAccountService = new CoinmateAccountService(this);
    this.pollingTradeService = new CoinmateTradeService(this);

  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
      
       try {
          CertHelper.trustAllCerts(); //TODO FIXME before release!
          System.setProperty("jsse.enableSNIExtension", "false");
      } catch (Exception ex) {
          Logger.getLogger(CoinmateExchange.class.getName()).log(Level.SEVERE, null, ex);
      }

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://coinmate.io");
    //exchangeSpecification.setSslUri("﻿https://104.45.25.164"); //TODO FIXME before release!
    exchangeSpecification.setHost("coinmate.io");
    //exchangeSpecification.setHost("104.45.25.164");
    exchangeSpecification.setPort(80);
    //exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("CoinMate");
    exchangeSpecification.setExchangeDescription("Bitcoin trading made simple.");

    return exchangeSpecification;
  }
}
