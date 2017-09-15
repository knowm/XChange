package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.huobi.BitVcFuturesAdapter;
import org.knowm.xchange.huobi.FuturesContract;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPlaceOrderResult;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPositionByContract;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BitVcFuturesTradeService extends BitVcFuturesServiceRaw implements TradeService {
  private final FuturesContract futuresContract;

  private enum TradeTypes {
    BUY(1, 1), SELL(1, 2), CLOSE_BUY(2, 2), CLOSE_SELL(2, 1);

    private int orderType;
    private int tradeType;

    TradeTypes(int orderType, int tradeType) {
      this.orderType = orderType;
      this.tradeType = tradeType;
    }

    public int getOrderType() {
      return orderType;
    }

    public int getTradeType() {
      return tradeType;
    }
  }

  public BitVcFuturesTradeService(final Exchange exchange, final FuturesContract futuresContract) {
    super(exchange);

    this.futuresContract = futuresContract;
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final BitVcFuturesPositionByContract positions = bitvc.positions(accessKey, 1, requestTimestamp(), digest);
    return BitVcFuturesAdapter.adaptOpenOrders(positions);
  }

  @Override
  public OpenOrders getOpenOrders(
      final OpenOrdersParams openOrdersParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return getOpenOrders();
  }

  @Override
  public String placeMarketOrder(
      final MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return null;
  }

  @Override
  public String placeLimitOrder(
      final LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final TradeTypes tradeTypes;
    switch (limitOrder.getType()) {
      case BID:
        tradeTypes = TradeTypes.BUY;
        break;

      case ASK:
        tradeTypes = TradeTypes.SELL;
        break;

      case EXIT_BID:
        tradeTypes = TradeTypes.CLOSE_BUY;
        break;

      case EXIT_ASK:
        tradeTypes = TradeTypes.CLOSE_SELL;
        break;
      default:
        tradeTypes = null;
        break;
    }

    final BitVcFuturesPlaceOrderResult result = bitvc.placeLimitOrder(accessKey, 1, futuresContract.getName(), requestTimestamp(), digest,
        tradeTypes.getOrderType(), tradeTypes.getTradeType(), limitOrder.getLimitPrice().doubleValue(), limitOrder.getTradableAmount().doubleValue());

    return String.valueOf(result.getId());
  }

  @Override
  public boolean cancelOrder(
      final String s) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return false;
  }

  @Override
  public UserTrades getTradeHistory(final TradeHistoryParams tradeHistoryParams) throws IOException {
    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return null;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public void verifyOrder(final LimitOrder limitOrder) {

  }

  @Override
  public void verifyOrder(final MarketOrder marketOrder) {

  }

  @Override
  public Collection<Order> getOrder(
      final String... strings) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return null;
  }
}
