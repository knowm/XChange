package com.xeiam.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
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
import com.xeiam.xchange.vaultoro.VaultoroAdapters;
import com.xeiam.xchange.vaultoro.dto.trade.VaultoroCancelOrderResponse;
import com.xeiam.xchange.vaultoro.dto.trade.VaultoroNewOrderResponse;

public class VaultoroTradeService extends VaultoroTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public boolean cancelOrder(String arg0) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    try {
      VaultoroCancelOrderResponse response = super.cancelVaultoroOrder(arg0);
      if (response.getStatus().equals("success")) {
        return true;
      }
      else {
        return false;
      }
    } catch (ExchangeException e) {
      return false;
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return VaultoroAdapters.adaptVaultoroOpenOrders(getVaultoroOrders());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams arg0) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder arg0) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    VaultoroNewOrderResponse response = super.placeLimitOrder(arg0.getCurrencyPair(), arg0.getType(), arg0.getTradableAmount(), arg0.getLimitPrice());
    return response.getData().getOrderID();

  }

  @Override
  public String placeMarketOrder(MarketOrder arg0) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    VaultoroNewOrderResponse response = super.placeMarketOrder(arg0.getCurrencyPair(), arg0.getType(), arg0.getTradableAmount());
    return response.getData().getOrderID();

  }

  @Override
  public Collection<Order> getOrder(String... arg0) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();

  }

}