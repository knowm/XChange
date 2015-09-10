package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterAdapters;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.coinsetter.dto.order.request.CoinsetterOrderRequest;
import com.xeiam.xchange.coinsetter.dto.order.response.CoinsetterOrder;
import com.xeiam.xchange.coinsetter.dto.order.response.CoinsetterOrderList;
import com.xeiam.xchange.coinsetter.dto.order.response.CoinsetterOrderResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * Trade service.
 */
public class CoinsetterTradeService extends CoinsetterOrderServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterTradeService(Exchange exchange) {

    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OpenOrders getOpenOrders() throws IOException {

    CoinsetterClientSession session = getSession();
    CoinsetterOrderList orderList = list(session.getUuid(), getAccountUuid(), "OPEN");
    return CoinsetterAdapters.adaptOpenOrders(orderList);
  }

  /**
   * Method returns CoinsetterOrder type giving full order execution state details. Method getOpenOrders() do not provide information of
   * filledQuantity, average execution price etc.
   *
   * @return
   * @throws IOException
   */
  public List<CoinsetterOrder> getCoinsetterOpenOrders() throws IOException {
    CoinsetterClientSession session = getSession();
    CoinsetterOrderList orderList = list(session.getUuid(), getAccountUuid(), "OPEN");
    return new ArrayList<CoinsetterOrder>(Arrays.asList(orderList.getOrderList()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return add(marketOrder.getCurrencyPair(), marketOrder.getType(), marketOrder.getTradableAmount(), null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return add(limitOrder.getCurrencyPair(), limitOrder.getType(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
  }

  private String add(CurrencyPair currencyPair, OrderType orderType, BigDecimal quantity, BigDecimal price) throws IOException {

    CoinsetterClientSession session = getSession();
    CoinsetterOrderRequest request = new CoinsetterOrderRequest(session.getCustomerUuid(), getAccountUuid(),
        CoinsetterAdapters.adaptSymbol(currencyPair), CoinsetterAdapters.adaptSide(orderType), price == null ? "MARKET" : "LIMIT", quantity, 2,
        price);
    return add(getSession().getUuid(), request).getUuid().toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    CoinsetterOrderResponse response = cancel(getSession().getUuid(), UUID.fromString(orderId));
    return "SUCCESS".equals(response.getRequestStatus());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

}
