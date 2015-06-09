package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.ripple.RippleAdapters;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class RippleTradeService extends RippleTradeServiceRaw implements PollingTradeService {

  private static final boolean VALIDATE_ALL_REQUESTS = true;

  public RippleTradeService(final Exchange exchange) {
    super(exchange);
  }

  /**
   * The additional data map of an order will be populated with {@link RippleExchange.DATA_BASE_COUNTERPARTY} if the base currency is not XRP,
   * similarly if the counter currency is not XRP then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} will be populated.
   */
  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return RippleAdapters.adaptOpenOrders(openAccountOrders());
  }

  @Override
  public String placeMarketOrder(final MarketOrder order) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * If the base currency is not XRP then the order's additional data map must contain a value for {@link RippleExchange.DATA_BASE_COUNTERPARTY},
   * similarly if the counter currency is not XRP then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} must be populated.
   */
  @Override
  public String placeLimitOrder(final LimitOrder order) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    return placeOrder(order, VALIDATE_ALL_REQUESTS);
  }

  @Override
  public boolean cancelOrder(final String orderId) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    return cancelOrder(orderId, VALIDATE_ALL_REQUESTS);
  }

  @Override
  public UserTrades getTradeHistory(final Object... arguments) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(final TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }

}
