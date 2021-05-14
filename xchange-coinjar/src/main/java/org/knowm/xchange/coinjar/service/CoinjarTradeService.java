package org.knowm.xchange.coinjar.service;

import static org.knowm.xchange.coinjar.CoinjarAdapters.currencyPairToProduct;
import static org.knowm.xchange.coinjar.CoinjarAdapters.orderTypeToBuySell;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinjar.CoinjarAdapters;
import org.knowm.xchange.coinjar.CoinjarErrorAdapter;
import org.knowm.xchange.coinjar.CoinjarException;
import org.knowm.xchange.coinjar.CoinjarExchange;
import org.knowm.xchange.coinjar.dto.CoinjarOrder;
import org.knowm.xchange.coinjar.dto.trading.CoinjarOrderRequest;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class CoinjarTradeService extends CoinjarTradeServiceRaw implements TradeService {

  public CoinjarTradeService(CoinjarExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      CoinjarOrderRequest request =
          new CoinjarOrderRequest(
              currencyPairToProduct(limitOrder.getCurrencyPair()),
              "LMT",
              orderTypeToBuySell(limitOrder.getType()),
              limitOrder.getLimitPrice().stripTrailingZeros().toPlainString(),
              limitOrder.getOriginalAmount().stripTrailingZeros().toPlainString(),
              "GTC");
      CoinjarOrder coinjarOrder = placeOrder(request);
      return coinjarOrder.oid.toString();
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    Integer cursor = null;
    if (params instanceof TradeHistoryParamPaging) {
      cursor = ((TradeHistoryParamPaging) params).getPageNumber();
    }
    List<LimitOrder> limitOrders =
        super.getOpenOrders(cursor).stream()
            .map(CoinjarAdapters::adaptOrderToLimitOrder)
            .collect(Collectors.toList());
    return new OpenOrders(limitOrders);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new CoinjarOpenOrdersParams();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    try {
      String orderId = orderIds[0];
      CoinjarOrder coinjarOrder = getOrder(orderId);
      return Collections.singletonList(CoinjarAdapters.adaptOrderToLimitOrder(coinjarOrder));
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer page = 0;
    if (params instanceof CoinjarTradeHistoryParams) {
      page = ((CoinjarTradeHistoryParams) params).pageNumber;
    }
    try {
      List<UserTrade> trades =
          getFills(page, null, null).stream()
              .map(CoinjarAdapters::adaptFillToUserTrade)
              .collect(Collectors.toList());
      return new UserTrades(trades, UserTrades.TradeSortType.SortByID);
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinjarTradeHistoryParams();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try {
      if (orderParams instanceof CancelOrderByIdParams) {
        CoinjarOrder cancelledOrder =
            cancelOrderById(((CancelOrderByIdParams) orderParams).getOrderId());
        return cancelledOrder.status == "cancelled";
      } else {
        throw new IllegalArgumentException(
            "Unable to extract id from CancelOrderParams" + orderParams);
      }
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  private static class CoinjarTradeHistoryParams
      implements TradeHistoryParams, TradeHistoryParamPaging {
    private Integer pageNumber;

    @Override
    public Integer getPageLength() {
      return null;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      throw new NotAvailableFromExchangeException();
    }

    @Override
    public Integer getPageNumber() {
      return pageNumber;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      this.pageNumber = pageNumber;
    }
  }

  private static class CoinjarOpenOrdersParams implements OpenOrdersParams, OpenOrdersParamOffset {
    private Integer offset;

    @Override
    public Integer getOffset() {
      return this.offset;
    }

    @Override
    public void setOffset(Integer offset) {
      this.offset = offset;
    }
  }
}
