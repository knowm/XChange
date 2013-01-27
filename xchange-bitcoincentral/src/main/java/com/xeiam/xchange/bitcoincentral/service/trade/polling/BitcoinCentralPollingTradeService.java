/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.service.trade.polling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitcoincentral.BitcoinCentral;
import com.xeiam.xchange.bitcoincentral.BitcoinCentralAdapters;
import com.xeiam.xchange.bitcoincentral.dto.trade.BitcoinCentralMyOrder;
import com.xeiam.xchange.bitcoincentral.dto.trade.BitcoinCentralTradeBase;
import com.xeiam.xchange.bitcoincentral.dto.trade.BitcoinCentralTradeRequest;
import com.xeiam.xchange.bitcoincentral.dto.trade.TradeOrderRequestWrapper;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.rest.BasicAuthCredentials;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class BitcoinCentralPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private static final Logger log = LoggerFactory.getLogger(BitcoinCentralPollingTradeService.class);

  private BitcoinCentral bitcoincentral;
  private BasicAuthCredentials credentials;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcoinCentralPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoincentral = RestProxyFactory.createProxy(BitcoinCentral.class, exchangeSpecification.getUri());
    this.credentials = new BasicAuthCredentials(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
  }

  @Override
  public OpenOrders getOpenOrders() {

    BitcoinCentralMyOrder[] accountTradeOrders = bitcoincentral.getAccountTradeOrders(credentials, 1, 100);
    return new OpenOrders(BitcoinCentralAdapters.adaptActive(accountTradeOrders));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotYetImplementedForExchangeException("Place market order not supported.");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    BitcoinCentralMyOrder myOrder = bitcoincentral.placeLimitOrder(credentials, new TradeOrderRequestWrapper(limitOrder.getTradableAmount(), getCategory(limitOrder.getType()), limitOrder
        .getTransactionCurrency(), limitOrder.getLimitPrice().getAmount(), BitcoinCentralTradeRequest.Type.limit_order));
    log.debug("myOrder = {}", myOrder);
    return Integer.toString(myOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) {

    String ret = bitcoincentral.cancelOrder(credentials, orderId);
    log.debug("Cancelling returned: {}", ret);
    return true;
  }

  private static BitcoinCentralTradeBase.Category getCategory(Order.OrderType type) {

    return type == Order.OrderType.ASK ? BitcoinCentralTradeBase.Category.sell : BitcoinCentralTradeBase.Category.buy;
  }

}
