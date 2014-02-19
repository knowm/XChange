package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public final class CoinbaseTradeService extends CoinbaseTradeServiceRaw implements PollingTradeService {

  public CoinbaseTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, IOException {

    final CoinbaseTransfer transfer = marketOrder.getType().equals(OrderType.BID) ? super.buy(marketOrder.getTradableAmount()) : super.sell(marketOrder.getTradableAmount());
    return transfer.getTransactionId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, IOException {

    Integer page = null;
    Integer limit = null;
    if (arguments != null && arguments.length > 0) {
      if (!(arguments[0] instanceof Integer))
        throw new ExchangeException("args[0] must be of type Integer.");
      page = (Integer) arguments[0];

      if (arguments.length > 1) {
        if (!(arguments[1] instanceof Integer))
          throw new ExchangeException("args[1] must be of type Integer.");
        limit = (Integer) arguments[1];
      }
    }

    final CoinbaseTransfers transfers = super.getCoinbaseTransfers(page, limit);
    return CoinbaseAdapters.adaptTrades(transfers);
  }

}
