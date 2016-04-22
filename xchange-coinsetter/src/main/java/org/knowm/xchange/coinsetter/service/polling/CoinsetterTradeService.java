package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterAdapters;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import org.knowm.xchange.coinsetter.dto.order.request.CoinsetterOrderRequest;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrder;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderList;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

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

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public org.knowm.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
