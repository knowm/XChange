package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bittrex.v1.BittrexAdapters;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class BittrexTradeService extends BittrexTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    String id = placeBittrexMarketOrder(marketOrder);

    return id;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String id = placeBittrexLimitOrder(limitOrder);

    return id;
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return new OpenOrders(BittrexAdapters.adaptOpenOrders(getBittrexOpenOrders()));
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException {

    return cancelBittrexLimitOrder(orderId);
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    return new UserTrades(BittrexAdapters.adaptUserTrades(getBittrexTradeHistory()), TradeSortType.SortByTimestamp);
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