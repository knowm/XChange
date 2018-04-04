package org.knowm.xchange.mercadobitcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinAuthenticated;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import si.mazi.rescu.RestProxyFactory;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinTradeServiceRaw extends MercadoBitcoinBaseService {

  private static final String GET_ORDER_LIST = "OrderList";
  private static final String TRADE = "Trade";
  private static final String CANCEL_ORDER = "CancelOrder";

  private final MercadoBitcoinAuthenticated mercadoBitcoinAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.mercadoBitcoinAuthenticated =
        RestProxyFactory.createProxy(
            MercadoBitcoinAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> getMercadoBitcoinUserOrders(
      @Nonnull String pair,
      @Nullable String type,
      @Nullable String status,
      @Nullable String fromId,
      @Nullable String endId,
      @Nullable Long since,
      @Nullable Long end)
      throws IOException {

    String method = GET_ORDER_LIST;
    long tonce = exchange.getNonceFactory().createValue();

    MercadoBitcoinDigest signatureCreator =
        MercadoBitcoinDigest.createInstance(
            method,
            exchange.getExchangeSpecification().getPassword(),
            exchange.getExchangeSpecification().getSecretKey(),
            tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> userOrders =
        mercadoBitcoinAuthenticated.getOrderList(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            method,
            tonce,
            pair,
            type,
            status,
            fromId,
            endId,
            since,
            end);

    if (userOrders.getSuccess() == 0) {
      throw new ExchangeException("Error getting user orders: " + userOrders.getError());
    }

    return userOrders;
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult>
      mercadoBitcoinPlaceLimitOrder(
          @Nonnull String pair,
          @Nonnull String type,
          @Nonnull BigDecimal volume,
          @Nonnull BigDecimal price)
          throws IOException {

    String method = TRADE;
    long tonce = exchange.getNonceFactory().createValue();

    MercadoBitcoinDigest signatureCreator =
        MercadoBitcoinDigest.createInstance(
            method,
            exchange.getExchangeSpecification().getPassword(),
            exchange.getExchangeSpecification().getSecretKey(),
            tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> newOrder =
        mercadoBitcoinAuthenticated.placeLimitOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            method,
            tonce,
            pair,
            type,
            volume,
            price);

    if (newOrder.getSuccess() == 0) {
      throw new ExchangeException("Error creating a new order: " + newOrder.getError());
    }

    return newOrder;
  }

  /**
   * @param pair btc_brl or ltc_brl
   * @param orderId the order ID
   * @return See {@link org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult}
   *     .
   * @throws IOException
   */
  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult>
      mercadoBitcoinCancelOrder(@Nonnull String pair, @Nonnull String orderId) throws IOException {

    String method = CANCEL_ORDER;
    long tonce = exchange.getNonceFactory().createValue();

    MercadoBitcoinDigest signatureCreator =
        MercadoBitcoinDigest.createInstance(
            method,
            exchange.getExchangeSpecification().getPassword(),
            exchange.getExchangeSpecification().getSecretKey(),
            tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> result =
        mercadoBitcoinAuthenticated.cancelOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            method,
            tonce,
            pair,
            orderId);

    if (result.getSuccess() == 0) {
      throw new ExchangeException("Error canceling a new order: " + result.getError());
    }

    return result;
  }
}
