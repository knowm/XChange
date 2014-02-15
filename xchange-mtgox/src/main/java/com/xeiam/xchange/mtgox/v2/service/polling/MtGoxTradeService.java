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
package com.xeiam.xchange.mtgox.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class MtGoxTradeService extends MtGoxTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public MtGoxTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return new OpenOrders(MtGoxAdapters.adaptOrders(getMtGoxOpenOrders()));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    verify(marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency());

    return placeMtGoxMarketOrder(marketOrder).getDataString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    verify(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency());

    Assert.notNull(limitOrder.getLimitPrice().getAmount(), "getLimitPrice().getAmount() cannot be null");
    Assert.notNull(limitOrder.getLimitPrice().getCurrencyUnit(), "getLimitPrice().getCurrencyUnit() cannot be null");

    String tradableIdentifier = limitOrder.getTradableIdentifier();
    String currency = limitOrder.getLimitPrice().getCurrencyUnit().toString();
    String type = limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask";
    // need to convert to MtGox "amount"
    BigDecimal amount = limitOrder.getTradableAmount().multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    // need to convert to MtGox "Price"
    String price = MtGoxUtils.getPriceString(limitOrder.getLimitPrice());

    return placeMtGoxLimitOrder(tradableIdentifier, currency, type, amount, price).getDataString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    Assert.notNull(orderId, "orderId cannot be null");

    return cancelMtGoxOrder(orderId).getResult().equals("success");
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }
}
