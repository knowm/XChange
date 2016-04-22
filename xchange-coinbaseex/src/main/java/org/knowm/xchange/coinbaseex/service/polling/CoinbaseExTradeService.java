package org.knowm.xchange.coinbaseex.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbaseex.CoinbaseExAdapters;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExFill;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExIdResponse;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExOrder;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExTradeHistoryParams;
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

public class CoinbaseExTradeService extends CoinbaseExTradeServiceRaw implements PollingTradeService {

  public CoinbaseExTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CoinbaseExOrder[] coinbaseExOpenOrders = getCoinbaseExOpenOrders();

    return CoinbaseExAdapters.adaptOpenOrders(coinbaseExOpenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinbaseExIdResponse response = placeCoinbaseExMarketOrder(marketOrder);

    return response.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CoinbaseExIdResponse response = placeCoinbaseExLimitOrder(limitOrder);

    return response.getId();
  }

  @Override
  public boolean cancelOrder(String orderId)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return cancelCoinbaseExOrder(orderId);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    CoinbaseExFill[] coinbaseExFills = getCoinbaseExFills((CoinbaseExTradeHistoryParams) params);
    return CoinbaseExAdapters.adaptTradeHistory(coinbaseExFills);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
