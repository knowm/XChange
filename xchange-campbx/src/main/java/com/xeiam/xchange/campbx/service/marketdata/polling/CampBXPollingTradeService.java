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
package com.xeiam.xchange.campbx.service.marketdata.polling;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class CampBXPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private static final Logger log = LoggerFactory.getLogger(CampBXPollingTradeService.class);
  private static final MessageFormat ID_FORMAT = new MessageFormat("{0}-{1}");

  private final CampBX campbx;

  /**
   * Constructor
   *
   * @param exchangeSpecification
   */
  public CampBXPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campbx = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getUri());
  }

  @Override
  public OpenOrders getOpenOrders() {

    Object openOrders = campbx.getOpenOrders(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    log.debug("openOrders = {}", openOrders);
    // todo: compose id, include type (buy/sell)
    // todo!
    return new OpenOrders(new ArrayList<LimitOrder>());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    CampBX.AdvTradeMode mode = marketOrder.getType() == Order.OrderType.ASK ? CampBX.AdvTradeMode.AdvancedSell : CampBX.AdvTradeMode.AdvancedBuy;
    Object result = campbx.tradeAdvancedMarketEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, marketOrder.getTradableAmount(), CampBX.MarketPrice.Market, null, null, null);
    log.debug("result = {}", result);
    // todo: compose id, include type (buy/sell)
    // todo!
    return result.toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    CampBX.TradeMode mode = limitOrder.getType() == Order.OrderType.ASK ? CampBX.TradeMode.QuickSell : CampBX.TradeMode.QuickBuy;
    Object result = campbx.tradeEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    log.debug("result = {}", result);
    // todo: compose id, include type (buy/sell)
    // todo!
    return result.toString();
  }

  @Override
  public boolean cancelOrder(String orderId) {

    ParsedId parsedId = parseOrderId(orderId);
    campbx.tradeCancel(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), parsedId.type, Long.parseLong(parsedId.id));
    // todo!
    return false;
  }

  static String composeOrderId(CampBX.OrderType type, String id) {

    return ID_FORMAT.format(new Object[]{type, id});
  }

  static ParsedId parseOrderId(String compositeId) {

    try {
      Object[] parts = ID_FORMAT.parse(compositeId);
      return new ParsedId(CampBX.OrderType.valueOf(parts[0].toString()), parts[1].toString());
    } catch (ParseException e) {
      throw new IllegalArgumentException("Can't parse order id: " + compositeId);
    }
  }

  static class ParsedId {

    final CampBX.OrderType type;
    final String id;

    private ParsedId(CampBX.OrderType type, String id) {

      this.type = type;
      this.id = id;
    }
  }
}
