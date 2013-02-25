/*
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
package com.xeiam.xchange.bitfloor.service.trade.polling;

import static com.xeiam.xchange.dto.Order.OrderType.ASK;
import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitfloor.Bitfloor;
import com.xeiam.xchange.bitfloor.dto.Product;
import com.xeiam.xchange.bitfloor.dto.trade.BitfloorOrder;
import com.xeiam.xchange.bitfloor.dto.trade.NewOrderResult;
import com.xeiam.xchange.bitfloor.dto.trade.OrderCancelResult;
import com.xeiam.xchange.bitfloor.service.account.polling.BitfloorPollingService;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/** @author Matija Mazi */
public class BitfloorPollingTradeService extends BitfloorPollingService implements PollingTradeService {

  public BitfloorPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() {

    BitfloorOrder[] openOrders = bitfloor.getOpenOrders(exchangeSpecification.getApiKey(), bodyDigest, exchangeSpecification.getPassword(), Bitfloor.Version.v1, nextNonce());
    List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (BitfloorOrder oo : openOrders) {
      orders.add(new LimitOrder(oo.getSide() == BitfloorOrder.Side.buy ? BID : ASK, oo.getSize(), "BTC", "USD", oo.getId(), BigMoney.of(CurrencyUnit.USD, oo.getPrice())));
    }
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotAvailableFromExchangeException("Placing market orders not supported by Bitfloor API.");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    NewOrderResult ord = bitfloor.newOrder(exchangeSpecification.getApiKey(), bodyDigest, exchangeSpecification.getPassword(), Bitfloor.Version.v1, nextNonce(), limitOrder.getTradableAmount(),
        limitOrder.getLimitPrice().getAmount(), limitOrder.getType() == BID ? BitfloorOrder.Side.buy : BitfloorOrder.Side.sell, Product.BTCUSD);

    return ord.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) {

    OrderCancelResult orderCancelResult = bitfloor.cancelOrder(exchangeSpecification.getApiKey(), bodyDigest, exchangeSpecification.getPassword(), Bitfloor.Version.v1, nextNonce(), orderId,
        Product.BTCUSD);
    return orderCancelResult.getOrderId() != null;
  }

}
