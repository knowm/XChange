package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class BTCETradeService extends BTCETradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public BTCETradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    Map<Long, BTCEOrder> orders = getBTCEActiveOrders(null);
    return BTCEAdapters.adaptOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BTCEOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTCEOrder.Type.buy : BTCEOrder.Type.sell;

    String pair = com.xeiam.xchange.btce.v3.BTCEUtils.getPair(limitOrder.getCurrencyPair());

    BTCEOrder btceOrder = new BTCEOrder(0, null, limitOrder.getLimitPrice(), limitOrder.getTradableAmount(), type, pair);

    BTCEPlaceOrderResult result = placeBTCEOrder(btceOrder);
    return Long.toString(result.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCECancelOrderResult ret = cancelBTCEOrder(Long.parseLong(orderId));
    return (ret != null);
  }

  /**
   * @param arguments Vararg list of optional (nullable) arguments:
   *          (Long) arguments[0] Number of transactions to return
   *          (String) arguments[1] TradableIdentifier
   *          (String) arguments[2] TransactionCurrency
   *          (Long) arguments[3] Starting ID
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTradeHistory(final Object... arguments) throws IOException {

    Long numberOfTransactions = Long.MAX_VALUE;
    String tradableIdentifier = "";
    String transactionCurrency = "";
    Long id = null;
    try {
      numberOfTransactions = (Long) arguments[0];
      tradableIdentifier = (String) arguments[1];
      transactionCurrency = (String) arguments[2];
      id = (Long) arguments[3];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    String pair = null;
    if (!tradableIdentifier.equals("") && !transactionCurrency.equals("")) {
      pair = String.format("%s_%s", tradableIdentifier, transactionCurrency).toLowerCase();
    }
    Map<Long, BTCETradeHistoryResult> resultMap = getBTCETradeHistory(null, numberOfTransactions, id, id, BTCEAuthenticated.SortOrder.DESC, null, null, pair);
    return BTCEAdapters.adaptTradeHistory(resultMap);
  }

}
