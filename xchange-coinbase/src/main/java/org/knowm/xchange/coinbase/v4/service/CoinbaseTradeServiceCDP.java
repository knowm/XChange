package org.knowm.xchange.coinbase.v4.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySellResponse;
import org.knowm.xchange.coinbase.v2.service.CoinbaseTradeHistoryParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;

public final class CoinbaseTradeServiceCDP extends CoinbaseTradeServiceRawCDP implements TradeService {

  public CoinbaseTradeServiceCDP(Exchange exchange) {
    super(exchange);
  }

  /**
   * ********************************************************************************************************************************************************
   */
  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException, IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinbaseTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  /**
   * The Coinbase is not typical exchange. It has splitted buys and sells into wallets (accounts).
   * To get it is necessary to know the accountId (wallet ID) see {@link AccountInfo#getWallets()}
   */
  public UserTrades getBuyTradeHistory(CoinbaseTradeHistoryParams params, String accountId)
      throws IOException {
    final CoinbaseBuySellResponse buys =
        coinbase.getBuys(
            signatureCreator2,
            accountId,
            params.getLimit(),
            params.getStartId());
    return CoinbaseAdapters.adaptTrades(buys.getData(), Order.OrderType.BID);
  }

  /**
   * The Coinbase is not typical exchange. It has splitted buys and sells into wallets (accounts).
   * To get it is necessary to know the accountId (wallet ID) from {@link AccountInfo#getWallets()}
   */
  public UserTrades getSellTradeHistory(CoinbaseTradeHistoryParams params, String accountId)
      throws IOException {
    final CoinbaseBuySellResponse sells =
        coinbase.getSells(
            signatureCreator2,
            accountId,
            params.getLimit(),
            params.getStartId());
    return CoinbaseAdapters.adaptTrades(sells.getData(), Order.OrderType.ASK);
  }
}
