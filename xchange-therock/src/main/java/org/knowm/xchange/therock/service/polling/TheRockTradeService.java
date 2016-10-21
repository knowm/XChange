package org.knowm.xchange.therock.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;
import org.knowm.xchange.therock.TheRockAdapters;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;

/**
 * @author Matija Mazi
 * @author Pnk
 */
public class TheRockTradeService extends TheRockTradeServiceRaw implements PollingTradeService {

  public TheRockTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, ExchangeException {
    final TheRockOrder placedOrder = placeTheRockOrder(order.getCurrencyPair(), order.getTradableAmount(), null,
        TheRockAdapters.adaptSide(order.getType()), TheRockOrder.Type.market);
    return placedOrder.getId().toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, ExchangeException {
    final TheRockOrder placedOrder = placeTheRockOrder(order.getCurrencyPair(), order.getTradableAmount(), order.getLimitPrice(),
        TheRockAdapters.adaptSide(order.getType()), TheRockOrder.Type.limit);
    return placedOrder.getId().toString();
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to return open orders
   */
  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException {
    throw new NotAvailableFromExchangeException();
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to cancel an order
   */
  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  /**
   * Not available from exchange since TheRock needs currency pair in order to return/show the order
   */
  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }
}
