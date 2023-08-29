package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.response.HistOrdersResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1/hist-orders")
@Produces(MediaType.APPLICATION_JSON)
public interface HistOrdersAPI {

  /**
   * Get a list of recent fills.
   *
   * @param symbol [optional] Limit list of fills to this orderId
   * @param side [optional] buy or sell
   * @param startAt [optional] Start time. unix timestamp calculated in milliseconds, the creation
   *     time queried shall posterior to the start time.
   * @param endAt [optional] End time. unix timestamp calculated in milliseconds, the creation time
   *     queried shall prior to the end time.
   * @param pageSize [optional] The page size.
   * @param currentPage [optional] The page to select.
   * @return Trades.
   */
  @GET
  KucoinResponse<Pagination<HistOrdersResponse>> queryHistOrders(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @HeaderParam(APIConstants.API_HEADER_KEY_VERSION) String apiKeyVersion,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") String side,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("pageSize") Integer pageSize,
      @QueryParam("currentPage") Integer currentPage)
      throws IOException;
}