package com.xeiam.xchange.bitcurex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.trade.BitcurexOrderResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;

public class BitcurexTradeService extends BitcurexTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexTradeService(Exchange exchange) {

    super(exchange);
  }


  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    getBitcurexOrders("pln");
    return null;
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String market = limitOrder.getCurrencyPair().counterSymbol.toLowerCase();
    String type = limitOrder.getType() == Order.OrderType.BID ?  "buy" : "ask";
    BitcurexOrderResponse bitcurexOffer = createBitcurexOffer(market, limitOrder.getLimitPrice(), limitOrder.getTradableAmount(), type);

    return null;
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return false;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return null;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return null;
  }
}
