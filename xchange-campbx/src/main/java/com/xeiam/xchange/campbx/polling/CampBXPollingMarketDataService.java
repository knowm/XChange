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
package com.xeiam.xchange.campbx.polling;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.api.CampBX;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.proxy.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matija Mazi <br/>
 */
public class CampBXPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private static final CurrencyUnit BTC = CurrencyUnit.of("BTC");
  private static final CurrencyUnit USD = CurrencyUnit.of("USD");
  private static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(CurrencyPair.BTC_USD);

  private final CampBX campbx;

  public CampBXPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campbx = RestProxyFactory.createProxy(CampBX.class, httpTemplate, exchangeSpecification, mapper);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    checkArgument(tradableIdentifier.equals(BTC.getCode()));
    checkArgument(currency.equals(USD.getCode()));
    com.xeiam.xchange.campbx.api.model.Ticker tck = campbx.getTicker();
    return new TickerBuilder().withAsk(BigMoney.of(USD, tck.getAsk())).withBid(BigMoney.of(USD, tck.getBid()))
        .withLast(BigMoney.of(USD, tck.getLast())).build();
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    return getFullOrderBook(tradableIdentifier, currency);
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    com.xeiam.xchange.campbx.api.model.OrderBook orderBook = campbx.getOrderBook();
    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, orderBook.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, orderBook.getBids());
    return new OrderBook(asks, bids);
  }

  private List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<List<Double>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<Double> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(tradableIdentifier, currency, ask, orderType));
    }
    return limitOrders;
  }

  private LimitOrder createOrder(String tradableIdentifier, String currency, List<Double> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, new BigDecimal(priceAndAmount.get(1)), tradableIdentifier, currency, BigMoney.of(CurrencyUnit.USD, priceAndAmount.get(0)));
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    throw new UnsupportedOperationException("getTrades not supported.");
  }

  private static void checkArgument(boolean argument) {

    checkArgument(argument, "Illegal argument.");
  }

  private static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }
}
