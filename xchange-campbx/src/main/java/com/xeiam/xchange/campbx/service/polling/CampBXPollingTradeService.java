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
package com.xeiam.xchange.campbx.service.polling;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXOrder;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;

/**
 * @author Matija Mazi
 */
public class CampBXPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private final Logger logger = LoggerFactory.getLogger(CampBXPollingTradeService.class);

  private static final MessageFormat ID_FORMAT = new MessageFormat("{0}-{1}");

  private final CampBX campbx;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CampBXPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campbx = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  @Override
  public OpenOrders getOpenOrders() {

    MyOpenOrders myOpenOrders = campbx.getOpenOrders(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    logger.debug("myOpenOrders = {}", myOpenOrders);

    if (!myOpenOrders.isError()) {

      List<LimitOrder> orders = new ArrayList<LimitOrder>();
      for (CampBXOrder cbo : myOpenOrders.getBuy()) {
        if (cbo.isError() || cbo.isInfo()) {
          logger.debug("Skipping non-order in Buy: " + cbo);
        }
        else {
          orders.add(new LimitOrder(Order.OrderType.BID, cbo.getQuantity(), "BTC", "USD", composeOrderId(CampBX.OrderType.Buy, cbo.getOrderID()), BigMoney.of(CurrencyUnit.USD, cbo.getPrice())));
        }
      }
      for (CampBXOrder cbo : myOpenOrders.getSell()) {
        if (cbo.isError() || cbo.isInfo()) {
          logger.debug("Skipping non-order in Sell: " + cbo);
        }
        else {
          orders.add(new LimitOrder(Order.OrderType.ASK, cbo.getQuantity(), "BTC", "USD", composeOrderId(CampBX.OrderType.Sell, cbo.getOrderID()), BigMoney.of(CurrencyUnit.USD, cbo.getPrice())));
        }
      }
      return new OpenOrders(orders);
    }
    else {
      throw new ExchangeException("Error calling getOpenOrders(): " + myOpenOrders.getError());
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    CampBX.AdvTradeMode mode = marketOrder.getType() == Order.OrderType.ASK ? CampBX.AdvTradeMode.AdvancedSell : CampBX.AdvTradeMode.AdvancedBuy;
    CampBXResponse campBXResponse =
        campbx.tradeAdvancedMarketEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, marketOrder.getTradableAmount(), CampBX.MarketPrice.Market, null, null, null);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return composeOrderId(campBXResponse.getSuccess(), marketOrder.getType());
    }
    else {
      throw new ExchangeException("Error calling placeMarketOrder(): " + campBXResponse.getError());
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    CampBX.TradeMode mode = limitOrder.getType() == Order.OrderType.ASK ? CampBX.TradeMode.QuickSell : CampBX.TradeMode.QuickBuy;
    CampBXResponse campBXResponse =
        campbx.tradeEnter(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), mode, limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return composeOrderId(campBXResponse.getSuccess(), limitOrder.getType());
    }
    else {
      throw new ExchangeException("Error calling placeLimitOrder(): " + campBXResponse.getError());
    }
  }

  @Override
  public boolean cancelOrder(String orderId) {

    ParsedId parsedId = parseOrderId(orderId);
    CampBXResponse campBXResponse = campbx.tradeCancel(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), parsedId.type, Long.parseLong(parsedId.id));
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.isSuccess();
    }
    else {
      throw new ExchangeException("Error calling cancelOrder(): " + campBXResponse.getError());
    }
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

  @Override
  public Trades getTradeHistory(Long numberOfTransactions, String tradableIdentifier, String transactionCurrency) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException {

    throw new NotYetImplementedForExchangeException();
  }

}
