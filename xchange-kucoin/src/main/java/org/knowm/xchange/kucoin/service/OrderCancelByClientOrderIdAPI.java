package org.knowm.xchange.kucoin.service;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.kucoin.dto.KucoinException;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCancelByClientOrderIdResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCancelResponse;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;


@Path("/api/v1/order/client-order")
@Produces(MediaType.APPLICATION_JSON)
public interface OrderCancelByClientOrderIdAPI {

      /**
   * Cancel an order
   *
   * <p>Cancel a previously placed order.
   *
   * @param orderId The order id.
   * @return A response containing the id of the cancelled order.
   */
  @DELETE
  @Path("/{clientOid}")
  KucoinResponse<OrderCancelByClientOrderIdResponse> cancelOrderByClientOrderId(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @HeaderParam(APIConstants.API_HEADER_KEY_VERSION) String apiKeyVersion,
      @PathParam("clientOid") String orderId)
      throws IOException, KucoinException;
}
