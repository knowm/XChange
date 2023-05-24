package org.knowm.xchange.krakenfutures.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesCancel;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesCancelAllOrdersAfter;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesFills;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOpenOrders;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesOrder;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.krakenfutures.dto.trade.*;
import org.knowm.xchange.utils.DateUtils;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesTradeServiceRaw extends KrakenFuturesBaseService {

  /**
   * Constructor
   *
   * @param exchange of KrakenFutures
   */
  public KrakenFuturesTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public KrakenFuturesOrder placeKrakenFuturesLimitOrder(LimitOrder order) throws IOException {

    KrakenFuturesOrder ord =
        krakenFuturesAuthenticated.sendOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            order.hasFlag(KrakenFuturesOrderFlags.POST_ONLY) ? KrakenFuturesOrderType.post.name() : KrakenFuturesOrderType.lmt.name(),
            KrakenFuturesAdapters.adaptKrakenFuturesSymbol(order.getInstrument()),
            order.getType().equals(OrderType.ASK) ? KrakenFuturesOrderSide.sell.name() : KrakenFuturesOrderSide.buy.name(),
            order.getOriginalAmount(),
            order.getLimitPrice(),
            order.getUserReference(),
            order.hasFlag(KrakenFuturesOrderFlags.REDUCE_ONLY),
                null,
                null
        );

    if (ord.isSuccess() && ord.getOrderStatus().getStatus().equals("placed")) {
      return ord;
    } else {
      String errorMessage = (ord.getError() == null) ? ord.getOrderStatus().getStatus() : ord.getError();
      throw new ExchangeException("Error sending CF limit order: " + errorMessage);
    }
  }

  public KrakenFuturesOrder placeKrakenFuturesMarketOrder(MarketOrder order) throws IOException {

    KrakenFuturesOrder ord =
            krakenFuturesAuthenticated.sendOrder(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    exchange.getNonceFactory(),
                    KrakenFuturesOrderType.mkt.name(),
                    KrakenFuturesAdapters.adaptKrakenFuturesSymbol(order.getInstrument()),
                    order.getType().equals(OrderType.ASK) ? KrakenFuturesOrderSide.sell.name() : KrakenFuturesOrderSide.buy.name(),
                    order.getOriginalAmount(),
                    null,
                    order.getUserReference(),
                    order.hasFlag(KrakenFuturesOrderFlags.REDUCE_ONLY),
                    null,
                    null
            );

    if (ord.isSuccess() && ord.getOrderStatus().getStatus().equals("placed")) {
      return ord;
    } else {
      String errorMessage = (ord.getError() == null) ? ord.getOrderStatus().getStatus() : ord.getError();
      throw new ExchangeException("Error sending CF limit order: " + errorMessage);
    }
  }

  public KrakenFuturesOrder placeKrakenFuturesStopOrder(StopOrder order) throws IOException {

    KrakenFuturesOrder ord =
            krakenFuturesAuthenticated.sendOrder(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    exchange.getNonceFactory(),
                    (order.getIntention().equals(StopOrder.Intention.STOP_LOSS)) ? KrakenFuturesOrderType.stp.name() : KrakenFuturesOrderType.take_profit.name(),
                    KrakenFuturesAdapters.adaptKrakenFuturesSymbol(order.getInstrument()),
                    order.getType().equals(OrderType.ASK) ? KrakenFuturesOrderSide.sell.name() : KrakenFuturesOrderSide.buy.name(),
                    order.getOriginalAmount(),
                    (order.getLimitPrice() != null) ? order.getLimitPrice() : null,
                    order.getUserReference(),
                    order.hasFlag(KrakenFuturesOrderFlags.REDUCE_ONLY),
                    order.getStopPrice(),
                    "mark"
            );

    if (ord.isSuccess() && ord.getOrderStatus().getStatus().equals("placed")) {
      return ord;
    } else {
      String errorMessage = (ord.getError() == null) ? ord.getOrderStatus().getStatus() : ord.getError();
      throw new ExchangeException("Error sending CF limit order: " + errorMessage);
    }
  }

  public String changeKrakenFuturesOrder(LimitOrder limitOrder) throws IOException {

    KrakenFuturesEditOrderResponse ord =
            krakenFuturesAuthenticated.changeOrder(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    exchange.getNonceFactory(),
                    limitOrder.getUserReference(),
                    limitOrder.getLimitPrice(),
                    (limitOrder.getUserReference() != null) ? limitOrder.getId() : null,
                    limitOrder.getOriginalAmount(),
                    null
            );

    if (ord.isSuccess()) {
      return ord.getEditStatus().getOrderId();
    } else {
      throw new ExchangeException("Error sending CF limit order: " + ord.getError());
    }
  }

  public BatchOrderResult sendKrakenFuturesBatchOrder(List<OrderCommand> commands)
      throws IOException {
    BatchOrderResult ord =
        krakenFuturesAuthenticated.batchOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            new BatchOrder(commands));

    if (ord.isSuccess()) {
      return ord;
    } else {
      throw new ExchangeException("Error sending CF batch order: " + ord.getError());
    }
  }

  public KrakenFuturesCancel cancelKrakenFuturesOrder(String uid) throws IOException {
    KrakenFuturesCancel res =
        krakenFuturesAuthenticated.cancelOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            uid);

    if (res.isSuccess()) {
      return res;
    } else {
      throw new ExchangeException("Error cancelling CF order: " + res.getError());
    }
  }

  public KrakenFuturesOpenOrders getKrakenFuturesOpenOrders() throws IOException {
    KrakenFuturesOpenOrders openOrders =
        krakenFuturesAuthenticated.openOrders(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    if (openOrders.isSuccess()) {
      return openOrders;
    } else {
      throw new ExchangeException("Error getting CF open orders: " + openOrders.getError());
    }
  }

  public KrakenFuturesFills getKrakenFuturesFills() throws IOException {
    return getKrakenFuturesFills(null);
  }

  public KrakenFuturesFills getKrakenFuturesFills(Date lastFillTime) throws IOException {
    KrakenFuturesFills fills =
        krakenFuturesAuthenticated.fills(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
                (lastFillTime != null) ? DateUtils.toUTCISODateString(lastFillTime) : null);

    if (fills.isSuccess()) {
      return fills;
    } else {
      throw new ExchangeException("Error getting CF fills: " + fills.getError());
    }
  }

  public KrakenFuturesCancelAllOrdersAfter cancelAllOrdersAfter(long timeoutSeconds)
      throws IOException {
    KrakenFuturesCancelAllOrdersAfter cancelallordersafter =
        krakenFuturesAuthenticated.cancelAllOrdersAfter(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            timeoutSeconds);

    if (cancelallordersafter.isSuccess()) {
      return cancelallordersafter;
    } else {
      throw new ExchangeException(
          "Error cancelling all CF orders after: " + cancelallordersafter.getError());
    }
  }

  public KrakenFuturesCancelAllOrders cancelAllOrdersByInstrument(Instrument instrument)
          throws IOException {
    KrakenFuturesCancelAllOrders cancelAllOrdersByInstrument =
            krakenFuturesAuthenticated.cancelAllOrdersByInstrument(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    exchange.getNonceFactory(),
                    KrakenFuturesAdapters.adaptKrakenFuturesSymbol(instrument));

    if (cancelAllOrdersByInstrument.isSuccess()) {
      return cancelAllOrdersByInstrument;
    } else {
      throw new ExchangeException(
              "Error cancelling all CF orders after: " + cancelAllOrdersByInstrument.getError());
    }
  }

  public KrakenFuturesOrdersStatusesResponse getKrakenFuturesOrdersStatuses(String... orderIds)
          throws IOException {
    KrakenFuturesOrdersStatusesResponse cancelAllOrdersByInstrument =
            krakenFuturesAuthenticated.getOrdersStatuses(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    exchange.getNonceFactory(),
                    new KrakenFuturesOrderStatusRequest(orderIds));

    if (cancelAllOrdersByInstrument.isSuccess()) {
      return cancelAllOrdersByInstrument;
    } else {
      throw new ExchangeException(
              "Error cancelling all CF orders after: " + cancelAllOrdersByInstrument.getError());
    }
  }
}
