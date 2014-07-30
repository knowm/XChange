package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;
import java.util.ArrayList;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.v2.BTCEAdapters;
import com.xeiam.xchange.btce.v2.BTCEAuthenticated;
import com.xeiam.xchange.btce.v2.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryReturn;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/** @author Matija Mazi */
@Deprecated
public class BTCETradeService extends BTCEBasePollingService implements PollingTradeService {

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

    BTCEOpenOrdersReturn orders = btce.ActiveOrders(apiKey, signatureCreator, nextNonce(), null);
    if ("no orders".equals(orders.getError())) {
      return new OpenOrders(new ArrayList<LimitOrder>());
    }
    checkResult(orders);
    return BTCEAdapters.adaptOrders(orders.getReturnValue());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = String.format("%s_%s", limitOrder.getCurrencyPair().baseSymbol, limitOrder.getCurrencyPair().counterSymbol).toLowerCase();
    BTCEOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTCEOrder.Type.buy : BTCEOrder.Type.sell;
    BTCEPlaceOrderReturn ret = btce.Trade(apiKey, signatureCreator, nextNonce(), pair, type, limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
    checkResult(ret);
    return Long.toString(ret.getReturnValue().getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCECancelOrderReturn ret = btce.CancelOrder(apiKey, signatureCreator, nextNonce(), Long.parseLong(orderId));
    checkResult(ret);
    return ret.isSuccess();
  }

  @Override
  public Trades getTradeHistory(final Object... arguments) throws IOException {

    Long numberOfTransactions = Long.MAX_VALUE;
    String tradableIdentifier = "";
    String transactionCurrency = "";
    try {
      numberOfTransactions = (Long) arguments[0];
      tradableIdentifier = (String) arguments[1];
      transactionCurrency = (String) arguments[2];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }

    String pair = String.format("%s_%s", tradableIdentifier, transactionCurrency).toLowerCase();
    BTCETradeHistoryReturn btceTradeHistory = btce.TradeHistory(apiKey, signatureCreator, nextNonce(), null, numberOfTransactions, null, null, BTCEAuthenticated.SortOrder.DESC, null, null, pair);
    checkResult(btceTradeHistory);
    return BTCEAdapters.adaptTradeHistory(btceTradeHistory.getReturnValue());
  }

}
