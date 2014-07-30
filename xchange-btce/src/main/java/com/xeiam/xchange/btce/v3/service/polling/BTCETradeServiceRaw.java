package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;

/**
 * Author: brox
 * Since: 2014-02-13
 */

public class BTCETradeServiceRaw extends BTCEBasePollingService<BTCEAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BTCETradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCEAuthenticated.class, exchangeSpecification);
  }

  /**
   * @param pair The pair to display the orders e.g. btc_usd (null: all pairs)
   * @return Active orders map
   * @throws IOException
   */
  public Map<Long, BTCEOrder> getBTCEActiveOrders(String pair) throws IOException {

    BTCEOpenOrdersReturn orders = btce.ActiveOrders(apiKey, signatureCreator, nextNonce(), pair);
    if ("no orders".equals(orders.getError())) {
      return new HashMap<Long, BTCEOrder>();
    }
    checkResult(orders);
    return orders.getReturnValue();
  }

  /**
   * @param order BTCEOrder object
   * @return BTCEPlaceOrderResult object
   * @throws IOException
   */
  public BTCEPlaceOrderResult placeBTCEOrder(BTCEOrder order) throws IOException {

    String pair = order.getPair().toLowerCase();
    BTCEPlaceOrderReturn ret = btce.Trade(apiKey, signatureCreator, nextNonce(), pair, order.getType(), order.getRate(), order.getAmount());
    checkResult(ret);
    return ret.getReturnValue();
  }

  public BTCECancelOrderResult cancelBTCEOrder(long orderId) throws IOException {

    BTCECancelOrderReturn ret = btce.CancelOrder(apiKey, signatureCreator, nextNonce(), orderId);
    checkResult(ret);
    return ret.getReturnValue();
  }

  /**
   * All parameters are nullable
   * 
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default +inf
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default +inf
   * @param pair The pair to show the transaction; example btc_usd; all pairs
   * @return {success=1, return={tradeId={pair=btc_usd, type=sell, amount=1, rate=1, orderId=1234, timestamp=1234}}}
   */
  public Map<Long, BTCETradeHistoryResult> getBTCETradeHistory(Long from, Long count, Long fromId, Long endId, BTCEAuthenticated.SortOrder order, Long since, Long end, String pair) throws IOException {

    BTCETradeHistoryReturn btceTradeHistory = btce.TradeHistory(apiKey, signatureCreator, nextNonce(), from, count, fromId, endId, order, since, end, pair);
    checkResult(btceTradeHistory);
    return btceTradeHistory.getReturnValue();
  }

}
