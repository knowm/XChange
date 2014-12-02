package com.xeiam.xchange.mercadobitcoin.service.polling.trade;

import com.sun.istack.internal.NotNull;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAuthenticated;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import com.xeiam.xchange.mercadobitcoin.service.MercadoBitcoinDigest;
import com.xeiam.xchange.mercadobitcoin.service.polling.MercadoBitcoinBasePollingService;
import si.mazi.rescu.RestProxyFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinTradeServiceRaw extends MercadoBitcoinBasePollingService {

  private static final String GET_ORDER_LIST = "OrderList";
  private static final String TRADE = "Trade";
  private static final String CANCEL_ORDER = "CancelOrder";

  private final MercadoBitcoinAuthenticated mercadoBitcoinAuthenticated;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public MercadoBitcoinTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.mercadoBitcoinAuthenticated = RestProxyFactory.createProxy(MercadoBitcoinAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> getMercadoBitcoinUserOrders(@NotNull String pair, @Nullable String type, @Nullable String status, @Nullable String fromId,
      @Nullable String endId, @Nullable Long since, @Nullable Long end) throws IOException {

    String method = GET_ORDER_LIST;
    String tonce = MercadoBitcoinUtils.getTonce();

    MercadoBitcoinDigest signatureCreator = MercadoBitcoinDigest.createInstance(method, exchangeSpecification.getPassword(), exchangeSpecification.getSecretKey(), tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> userOrders =
        mercadoBitcoinAuthenticated.getOrderList(exchangeSpecification.getApiKey(), signatureCreator, method, tonce, pair, type, status, fromId, endId, since, end);

    if (userOrders.getSuccess() == 0) {
      throw new ExchangeException("Error getting user orders: " + userOrders.getError());
    }

    return userOrders;
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> mercadoBitcoinPlaceLimitOrder(@NotNull String pair, @NotNull String type, @NotNull BigDecimal volume,
      @NotNull BigDecimal price) throws IOException {

    String method = TRADE;
    String tonce = MercadoBitcoinUtils.getTonce();

    MercadoBitcoinDigest signatureCreator = MercadoBitcoinDigest.createInstance(method, exchangeSpecification.getPassword(), exchangeSpecification.getSecretKey(), tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> newOrder =
        mercadoBitcoinAuthenticated.placeLimitOrder(exchangeSpecification.getApiKey(), signatureCreator, method, tonce, pair, type, volume, price);

    if (newOrder.getSuccess() == 0) {
      throw new ExchangeException("Error creating a new order: " + newOrder.getError());
    }

    return newOrder;
  }

  /**
   * @param pair btc_brl or ltc_brl
   * @param orderId the order ID
   * @return See {@link com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult}.
   * @throws IOException
   */
  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> mercadoBitcoinCancelOrder(@NotNull String pair, @NotNull String orderId) throws IOException {

    String method = CANCEL_ORDER;
    String tonce = MercadoBitcoinUtils.getTonce();

    MercadoBitcoinDigest signatureCreator = MercadoBitcoinDigest.createInstance(method, exchangeSpecification.getPassword(), exchangeSpecification.getSecretKey(), tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> result =
        mercadoBitcoinAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, method, tonce, pair, orderId);

    if (result.getSuccess() == 0) {
      throw new ExchangeException("Error canceling a new order: " + result.getError());
    }

    return result;
  }

}
