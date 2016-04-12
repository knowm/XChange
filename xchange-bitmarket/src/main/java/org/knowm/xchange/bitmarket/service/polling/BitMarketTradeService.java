package org.knowm.xchange.bitmarket.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketAdapters;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import org.knowm.xchange.bitmarket.service.polling.params.BitMarketHistoryParams;
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

/**
 * @author kfonal
 */
public class BitMarketTradeService extends BitMarketTradeServiceRaw implements PollingTradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitMarketTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitMarketOrdersResponse response = getBitMarketOpenOrders();
    return BitMarketAdapters.adaptOpenOrders(response.getData());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitMarketTradeResponse response = placeBitMarketOrder(limitOrder);
    return String.valueOf(response.getData().getId());
  }

  @Override
  public boolean cancelOrder(String id)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    cancelBitMarketOrder(id);
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) throws IOException {

    BitMarketHistoryTradesResponse response = getBitMarketTradeHistory(tradeHistoryParams);
    BitMarketHistoryOperationsResponse response2 = getBitMarketOperationHistory(tradeHistoryParams);
    return BitMarketAdapters.adaptTradeHistory(response.getData(), response2.getData());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitMarketHistoryParams();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
