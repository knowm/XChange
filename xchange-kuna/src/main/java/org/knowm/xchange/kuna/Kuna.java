package org.knowm.xchange.kuna;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kuna.dto.KunaAskBid;
import org.knowm.xchange.kuna.dto.KunaDepthDto;
import org.knowm.xchange.kuna.dto.KunaException;
import org.knowm.xchange.kuna.dto.KunaTimeTicker;
import org.knowm.xchange.kuna.dto.KunaTrade;

/**
 * @author Dat Bui
 * @author Roman Dovgan
 */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Kuna {

  /** Returns current server timestamp. */
  @GET
  @Path("timestamp")
  Long getTimestamp() throws IOException;

  /** Returns available tickers. */
  @GET
  @Path("tickers")
  Map<String, KunaTimeTicker> getTickers() throws IOException, KunaException;

  /**
   * Returns ticker for given currency pair.
   *
   * @param pair currency pair
   * @return ticker with timestamp
   */
  @GET
  @Path("tickers/{pair}")
  KunaTimeTicker getTicker(@PathParam("pair") String pair) throws IOException, KunaException;

  /** Returns order list */
  @GET
  @Path("order_book")
  KunaAskBid getOrders(@QueryParam("market") String pair) throws IOException, KunaException;

  /** Returns depth list */
  @GET
  @Path("depth")
  KunaDepthDto getDepth(@QueryParam("market") String pair) throws IOException, KunaException;

  /**
   * Returns trading history
   *
   * @param pair instrument
   * @return trade list
   */
  @GET
  @Path("trades")
  KunaTrade[] getTradesHistory(@QueryParam("market") String pair) throws IOException, KunaException;
}
