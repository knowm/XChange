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
package com.xeiam.xchange.examples.mtgox.v1;

import java.math.BigDecimal;

import org.joda.money.BigMoney;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.LimitOrder;
import com.xeiam.xchange.service.trade.MarketOrder;
import com.xeiam.xchange.service.trade.OpenOrders;
import com.xeiam.xchange.service.trade.Order.OrderType;
import com.xeiam.xchange.service.trade.TradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Mt Gox BTC exchange with authentication</li>
 * <li>Retrieving account info data</li>
 * </ul>
 */
public class MtGoxTradeDemo {

  public static void main(String[] args) {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification("com.xeiam.xchange.mtgox.v1.MtGoxExchange");
    exchangeSpecification.setApiKey("150c6db9-e5ab-47ac-83d6-4440d1b9ce49");
    exchangeSpecification.setSecretKey("olHM/yl3CAuKMXFS2+xlP/MC0Hs1M9snHpaHwg0UZW52Ni0Tf4FhGFELO9cHcDNGKvFrj8CgyQUA4VsMTZ6dXg==");
    exchangeSpecification.setUri("https://mtgox.com");
    exchangeSpecification.setVersion("1");
    Exchange mtgox = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = mtgox.getTradeService();

    // Get the account information
    AccountInfo accountInfo = tradeService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    // Get the open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());

    // place a market order
    MarketOrder marketOrder = new MarketOrder();
    marketOrder.setType(OrderType.BID);
    marketOrder.setTradableIdentifier("BTC");
    marketOrder.setTradableAmount(new BigDecimal(1)); // 1 BTC
    marketOrder.setTransactionCurrency("USD");
    boolean marketOrderSuccess = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order Successful: " + marketOrderSuccess);

    // place a limit order
    LimitOrder limitOrder = new LimitOrder();
    limitOrder.setType(OrderType.BID);
    limitOrder.setTradableIdentifier("BTC");
    limitOrder.setTradableAmount(new BigDecimal(1)); // 1 BTC
    BigMoney limitPrice = BigMoney.parse("USD 1.25");
    limitOrder.setLimitPrice(limitPrice);
    boolean limitOrderSuccess = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order Successful: " + limitOrderSuccess);

  }
}
