package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.v3.BitbayAdapters;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTrades;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTradesQuery;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author walec51 */
public class BitbayTradeService extends BitbayTradeServiceRaw implements TradeService {

  public BitbayTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    BitbayUserTradesQuery query = new BitbayUserTradesQuery();
    if (params instanceof TradeHistoryParamNextPageCursor) {
      query.setNextPageCursor(((TradeHistoryParamNextPageCursor) params).getNextPageCursor());
    }
    if (params instanceof TradeHistoryParamLimit) {
      Integer limit = ((TradeHistoryParamLimit) params).getLimit();
      query.setLimit(limit != null ? limit.toString() : null);
    }
    BitbayUserTrades response = getBitbayTransactions(query);
    return BitbayAdapters.adaptUserTrades(response);
  }

}
