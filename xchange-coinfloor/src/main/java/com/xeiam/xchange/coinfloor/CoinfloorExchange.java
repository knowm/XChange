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
package com.xeiam.xchange.coinfloor;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorStreamingConfiguration;
import com.xeiam.xchange.coinfloor.streaming.CoinfloorStreamingExchangeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * @author obsessiveOrange
 */
public class CoinfloorExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUriStreaming("ws://api.coinfloor.co.uk");
    exchangeSpecification.setSslUriStreaming("wss://api.coinfloor.co.uk");
    exchangeSpecification.setHost("coinfloor.co.uk");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coinfloor");
    exchangeSpecification.setExchangeDescription("Coinfloor is a company registered in England and Wales registration number 08493818. " + "Coinfloor allows users to trade Bitcoin. "
        + "Coinfloor LTD is registered at 200 Aldergate C/O Buckworth Solicitors EC1A 4HD London, United Kingdom.");

    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration exchangeStreamingConfiguration) {

    return new CoinfloorStreamingExchangeService(getExchangeSpecification(), (CoinfloorStreamingConfiguration) exchangeStreamingConfiguration);
  }

  public StreamingExchangeService getStreamingExchangeService() {

    return getStreamingExchangeService(new CoinfloorStreamingConfiguration(10, 10000, 60000, false, true, false));
  }
}
