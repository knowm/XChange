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
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CambBXUtils;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXOrder;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
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
    this.campbx = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  @Override
  public OpenOrders getOpenOrders() {

    MyOpenOrders openOrders = campbx.getOpenOrders(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    log.debug("openOrders = {}", openOrders);
    CambBXUtils.handleError(openOrders);
    List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (CampBXOrder cbo : openOrders.getBuy()) {
      if (cbo.isError() || cbo.isInfo()) {
        log.debug("Skipping non-order in Buy: " + cbo);
      } else {
        orders.add(new LimitOrder(Order.OrderType.BID, cbo.getQuantity(), "BTC", "USD", composeOrderId(CampBX.OrderType.Buy, cbo.getOrderID()), BigMoney.of(CurrencyUnit.USD, cbo.getPrice())));
      }
    }
    for (CampBXOrder cbo : openOrders.getSell()) {
      if (cbo.isError() || cbo.isInfo()) {
        log.debug("Skipping non-order in Sell: " + cbo);
      } else {
        orders.add(new LimitOrder(Order.OrderType.ASK, cbo.getQuantity(), "BTC", "USD", composeOrderId(CampBX.OrderType.Sell, cbo.getOrderID()), BigMoney.of(CurrencyUnit.USD, cbo.getPrice())));
      }
    }
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    CampBX.AdvTradeMode mode = marketOrder.getType() == Order.OrderType.ASK ? CampBX.AdvTradeMode.AdvancedSell : CampBX.AdvTradeMode.AdvancedBuy;
    CampBXResponse result = campbx.tradeAdvancedMarketEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, marketOrder.getTradableAmount(), CampBX.MarketPrice.Market,
        null, null, null);
    log.debug("result = {}", result);
    CambBXUtils.handleError(result);
    return composeOrderId(result.getSuccess(), marketOrder.getType());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    CampBX.TradeMode mode = limitOrder.getType() == Order.OrderType.ASK ? CampBX.TradeMode.QuickSell : CampBX.TradeMode.QuickBuy;
    CampBXResponse result = campbx.tradeEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    log.debug("result = {}", result);
    CambBXUtils.handleError(result);
    return composeOrderId(result.getSuccess(), limitOrder.getType());
  }

  @Override
  public boolean cancelOrder(String orderId) {

    ParsedId parsedId = parseOrderId(orderId);
    CampBXResponse response = campbx.tradeCancel(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), parsedId.type, Long.parseLong(parsedId.id));
    CambBXUtils.handleError(response);
    return response.isSuccess();
  }

  static String composeOrderId(String id, Order.OrderType orderType) {

    CampBX.OrderType type = orderType == Order.OrderType.ASK ? CampBX.OrderType.Sell : CampBX.OrderType.Buy;
    return composeOrderId(type, id);
  }

  static String composeOrderId(CampBX.OrderType type, String id) {

    return ID_FORMAT.format(new Object[] { type, id });
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
