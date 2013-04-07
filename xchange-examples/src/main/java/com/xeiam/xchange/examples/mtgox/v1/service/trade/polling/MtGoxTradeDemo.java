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
package com.xeiam.xchange.examples.mtgox.v1.service.trade.polling;

import java.math.BigDecimal;

import org.joda.money.BigMoney;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

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
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(MtGoxExchange.class.getName());
    exchangeSpecification.setApiKey("150c6db9-e5ab-47ac-83d6-4440d1b9ce49");
    exchangeSpecification.setSecretKey("olHM/yl3CAuKMXFS2+xlP/MC0Hs1M9snHpaHwg0UZW52Ni0Tf4FhGFELO9cHcDNGKvFrj8CgyQUA4VsMTZ6dXg==");
    exchangeSpecification.setSslUri("https://mtgox.com");
    Exchange mtgox = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = mtgox.getPollingTradeService();
    PollingAccountService accountService = mtgox.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    // Get the open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());

    // place a market order
    OrderType orderType = (OrderType.BID);
    BigDecimal tradeableAmount = new BigDecimal(1);
    String tradableIdentifier = "BTC";
    String transactionCurrency = "USD";

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, tradableIdentifier, transactionCurrency);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);

    // place a limit order
    orderType = (OrderType.BID);
    tradeableAmount = new BigDecimal(Math.random());
    tradableIdentifier = "BTC";
    transactionCurrency = "USD";
    BigMoney limitPrice = MoneyUtils.parse("USD 1.25");

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, tradableIdentifier, transactionCurrency, limitPrice);
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

  }
}
