package org.knowm.xchange.binance;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.meta.BinanceSystemStatus;
import org.knowm.xchange.binance.dto.meta.BinanceTime;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceFutureExchangeInfo;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceFuture {

  /**
   * @param symbol
   * @param limit optional, Default 500; Valid limits:[5, 10, 20, 50, 100, 500, 1000]
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v1/depth")
  BinanceOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, BinanceException;

  /**
   * Test connectivity to the Rest API and get the current server time.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("fapi/v1/time")
  BinanceTime time() throws IOException;

  /**
   * Test connectivity to the Rest API.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("fapi/v1/ping")
  Object ping() throws IOException;

  /**
   * Fetch system status which is normal or system maintenance.
   *
   * @throws IOException
   */
  @GET
  @Path("sapi/v1/system/status")
  BinanceSystemStatus systemStatus() throws IOException;

  /**
   * Current exchange trading rules and symbol information.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("fapi/v1/exchangeInfo")
  BinanceFutureExchangeInfo exchangeInfo() throws IOException;
}
