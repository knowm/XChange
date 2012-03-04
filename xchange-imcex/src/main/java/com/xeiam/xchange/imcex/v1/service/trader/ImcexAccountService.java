/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.imcex.v1.service.trader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.LimitOrder;
import com.xeiam.xchange.service.trade.MarketOrder;
import com.xeiam.xchange.service.trade.OpenOrders;
import com.xeiam.xchange.service.trade.TradeService;

public class ImcexAccountService extends BaseExchangeService implements TradeService {

  /**
   * Provides logging for this class
   */
  private final Logger log = LoggerFactory.getLogger(ImcexAccountService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase = String.format("%s/api/%s/", apiURI, apiVersion);

  /**
   * Initialise common properties from the exchange specification
   * 
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  public ImcexAccountService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() {

    return null;
  }

  @Override
  public OpenOrders getOpenOrders() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean placeMarketOrder(MarketOrder marketOrder) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean placeLimitOrder(LimitOrder limitOrder) {
    // TODO Auto-generated method stub
    return false;
  }

}
