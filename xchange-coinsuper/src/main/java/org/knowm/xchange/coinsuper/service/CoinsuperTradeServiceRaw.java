package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsuper.CoinsuperAuthenticated;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.trade.CoinsuperCancelOrder;
import org.knowm.xchange.coinsuper.dto.trade.CoinsuperOrder;
import org.knowm.xchange.coinsuper.dto.trade.OrderDetail;
import org.knowm.xchange.coinsuper.dto.trade.OrderList;
import org.knowm.xchange.coinsuper.utils.RestApiRequestHandler;
import org.knowm.xchange.coinsuper.utils.RestRequestParam;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsuperTradeServiceRaw extends CoinsuperBaseService {
  private final CoinsuperAuthenticated coinsuper;

  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public CoinsuperTradeServiceRaw(Exchange exchange) {

    super(exchange);

    this.coinsuper =
        RestProxyFactory.createProxy(
            CoinsuperAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * @param order
   * @return
   * @throws IOException
   */
  public CoinsuperResponse<CoinsuperOrder> createOrder(Map<String, String> order)
      throws IOException {
    RestRequestParam parameters =
        parameters = RestApiRequestHandler.generateRequestParam(order, this.apiKey, this.secretKey);
    if (order.get("side") == "BID") {
      return coinsuper.createBuyOrder(parameters);
    } else if (order.get("side") == "ASK") {
      return coinsuper.createSellOrder(parameters);
    } else {
      return null;
    }
  }

  /**
   * @return Object
   * @throws IOException
   */
  public boolean cancelCoinsuperOrder(Map<String, String> data) throws IOException {

    RestRequestParam parameters =
        RestApiRequestHandler.generateRequestParam(data, this.apiKey, this.secretKey);

    CoinsuperResponse<CoinsuperCancelOrder> coinsuperCancelOrder =
        coinsuper.cancelOrder(parameters);
    if (coinsuperCancelOrder.getData().getResult().getOperate().equals("success")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<List<OrderList>> orderList(Map<String, String> parameters)
      throws IOException {

    RestRequestParam restParameters =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.orderList(restParameters);
  }

  /**
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<List<OrderDetail>> orderDetails(Map<String, String> parameters)
      throws IOException {

    RestRequestParam restParameters =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.orderDetails(restParameters);
  }

  /**
   * @return Object
   * @throws IOException
   */
  public CoinsuperResponse<List<String>> orderOpenList(Map<String, String> parameters)
      throws IOException {

    RestRequestParam restParameters =
        RestApiRequestHandler.generateRequestParam(parameters, this.apiKey, this.secretKey);

    return coinsuper.orderOpenList(restParameters);
  }
}
