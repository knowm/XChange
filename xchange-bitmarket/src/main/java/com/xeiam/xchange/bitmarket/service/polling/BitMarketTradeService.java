package com.xeiam.xchange.bitmarket.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketAdapters;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import com.xeiam.xchange.bitmarket.service.polling.params.BitMarketHistoryParams;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

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
  public UserTrades getTradeHistory(Object... objects)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitMarketHistoryParams params = new BitMarketHistoryParams();

    try {
      params.setCurrencyPair((CurrencyPair) objects[0]);
      params.setCount((Integer) objects[1]);
      params.setOffset((Long) objects[2]);
    } catch (Exception e) {
    } //ignore, wrong or missed params just will be default

    return getTradeHistory(params);
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
}
