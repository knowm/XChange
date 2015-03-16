package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams.CryptoTradeQueryParamsBuilder;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrdering;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class CryptoTradeTradeService extends CryptoTradeTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoTradeTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    CryptoTradeOrders orderHistory = super.getCryptoTradeOrderHistory();

    return CryptoTradeAdapters.adaptOpenOrders(orderHistory);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return String.valueOf(placeCryptoTradeLimitOrder(limitOrder).getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    CryptoTradeCancelOrderReturn cancelOrderReturn = super.cancelCryptoTradeOrder(Long.valueOf(orderId));

    return cancelOrderReturn.getStatus().equalsIgnoreCase("success");
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    CryptoTradeQueryParamsBuilder paramsBuilder = CryptoTradeHistoryQueryParams.getQueryParamsBuilder();
    if (args != null) {
      switch (args.length) {
      case 7:
        if (args[6] != null && args[6] instanceof CurrencyPair) {
          paramsBuilder.withCurrencyPair((CurrencyPair) args[6]);
        }
      case 6:
        if (args[5] != null && args[5] instanceof CryptoTradeOrdering) {
          paramsBuilder.withOrdering((CryptoTradeOrdering) args[5]);
        }
      case 5:
        if (args[4] != null && args[4] instanceof Integer) {
          paramsBuilder.withCount((Integer) args[4]);
        }
      case 4:
        if (args[3] != null && args[3] instanceof Long) {
          paramsBuilder.withEndDate((Long) args[3]);
        }
      case 3:
        if (args[2] != null && args[2] instanceof Long) {
          paramsBuilder.withStartDate((Long) args[2]);
        }
      case 2:
        if (args[1] != null && args[1] instanceof Long) {
          paramsBuilder.withEndId((Long) args[1]);
        }
      case 1:
        if (args[0] != null && args[0] instanceof Long) {
          paramsBuilder.withStartId((Long) args[0]);
        }
      }
    }
    CryptoTradeTrades tradeHistory = super.getCryptoTradeTradeHistory(paramsBuilder.build());

    return CryptoTradeAdapters.adaptTrades(tradeHistory);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    throw new NotYetImplementedForExchangeException();
  }

}
