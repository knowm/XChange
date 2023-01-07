package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.trade.BinanceListenKey;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceFutureAuthenticated extends BinanceFutures {

  String SIGNATURE = "signature";
  String X_MBX_APIKEY = "X-MBX-APIKEY";

  /**
   * Get current account information. User in single-asset/ multi-assets mode will see different
   * value, see comments in response section for detail
   *
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v2/account")
  BinanceAccountInformation account(
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Keeps the authenticated websocket session alive.
   *
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @PUT
  @Path("/fapi/v1/listenKey")
  void keepAliveUserDataStream() throws IOException, BinanceException;

  /**
   * Returns a listen key for websocket login.
   *
   * @param apiKey the api key
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @POST
  @Path("/fapi/v1/listenKey")
  BinanceListenKey startUserDataStream(@HeaderParam(X_MBX_APIKEY) String apiKey)
      throws IOException, BinanceException;

  /**
   * Closes the websocket authenticated connection.
   *
   * @throws BinanceException
   * @throws IOException
   */
  @DELETE
  @Path("/fapi/v1/listenKey")
  void closeUserDataStream() throws IOException, BinanceException;
}
