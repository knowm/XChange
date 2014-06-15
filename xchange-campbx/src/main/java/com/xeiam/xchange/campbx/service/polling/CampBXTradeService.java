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
package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXOrder;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class CampBXTradeService extends CampBXTradeServiceRaw implements PollingTradeService {

  private final Logger logger = LoggerFactory.getLogger(CampBXTradeService.class);

  private static final MessageFormat ID_FORMAT = new MessageFormat("{0}-{1}");

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CampBXTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    MyOpenOrders myOpenOrders = getCampBXOpenOrders();
    logger.debug("myOpenOrders = {}", myOpenOrders);

    if (!myOpenOrders.isError()) {

      // TODO move to adapter class
      List<LimitOrder> orders = new ArrayList<LimitOrder>();
      for (CampBXOrder cbo : myOpenOrders.getBuy()) {
        if (cbo.isError() || cbo.isInfo()) {
          logger.debug("Skipping non-order in Buy: " + cbo);
        }
        else {
          String id = composeOrderId(CampBX.OrderType.Buy, cbo.getOrderID());
          BigDecimal price = cbo.getPrice();
          orders.add(new LimitOrder(Order.OrderType.BID, cbo.getQuantity(), CurrencyPair.BTC_USD, id, cbo.getOrderEntered(), price));
        }
      }
      for (CampBXOrder cbo : myOpenOrders.getSell()) {
        if (cbo.isError() || cbo.isInfo()) {
          logger.debug("Skipping non-order in Sell: " + cbo);
        }
        else {

          String id = composeOrderId(CampBX.OrderType.Sell, cbo.getOrderID());
          BigDecimal price = cbo.getPrice();
          orders.add(new LimitOrder(Order.OrderType.ASK, cbo.getQuantity(), CurrencyPair.BTC_USD, id, cbo.getOrderEntered(), price));
        }
      }
      return new OpenOrders(orders);
    }
    else {
      throw new ExchangeException("Error calling getOpenOrders(): " + myOpenOrders.getError());
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    CampBXResponse campBXResponse = placeCampBXMarketOrder(marketOrder);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return composeOrderId(campBXResponse.getSuccess(), marketOrder.getType());
    }
    else {
      throw new ExchangeException("Error calling placeMarketOrder(): " + campBXResponse.getError());
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    CampBXResponse campBXResponse = placeCampBXLimitOrder(limitOrder);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return composeOrderId(campBXResponse.getSuccess(), limitOrder.getType());
    }
    else {
      throw new ExchangeException("Error calling placeLimitOrder(): " + campBXResponse.getError());
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    CampBXResponse campBXResponse = cancelCampBXOrder(orderId);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.isSuccess();
    }
    else {
      throw new ExchangeException("Error calling cancelOrder(): " + campBXResponse.getError());
    }
  }

  private String composeOrderId(String id, Order.OrderType orderType) {

    CampBX.OrderType type = orderType == Order.OrderType.ASK ? CampBX.OrderType.Sell : CampBX.OrderType.Buy;
    return composeOrderId(type, id);
  }

  private String composeOrderId(CampBX.OrderType type, String id) {

    return ID_FORMAT.format(new Object[] { type, id });
  }

  @Override
  public Trades getTradeHistory(Object... args) {

    throw new NotYetImplementedForExchangeException();
  }

}
