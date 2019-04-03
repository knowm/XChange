package org.knowm.xchange.dragonex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.dragonex.dto.DragonResult;
import org.knowm.xchange.dragonex.dto.DragonexException;
import org.knowm.xchange.dragonex.dto.marketdata.Coin;
import org.knowm.xchange.dragonex.dto.marketdata.Depth;
import org.knowm.xchange.dragonex.dto.marketdata.Order;
import org.knowm.xchange.dragonex.dto.marketdata.Symbol;
import org.knowm.xchange.dragonex.dto.marketdata.Ticker;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Dragonex {

  /** Query all coins in DragonEx Platform */
  @GET
  @Path("coin/all/")
  DragonResult<List<Coin>> coinAll() throws DragonexException, IOException;

  /** Query all exchange pairs */
  @GET
  @Path("symbol/all/")
  DragonResult<List<Symbol>> symbolAll() throws DragonexException, IOException;

  /** Query K line @TODO */
  /*@GET
  @Path("market/kline/")
  DragonResult<KLine> kLine() throws DragonexException, IOException;*/

  /** Query buy orders quotes */
  @GET
  @Path("market/buy/")
  DragonResult<List<Order>> marketBuyOrders(@QueryParam("symbol_id") long symbolId)
      throws DragonexException, IOException;

  /** Query sell orders quotes */
  @GET
  @Path("market/sell/")
  DragonResult<List<Order>> marketSellOrders(@QueryParam("symbol_id") long symbolId)
      throws DragonexException, IOException;

  /** Query orders quotes */
  @GET
  @Path("market/depth/")
  DragonResult<Depth> marketDepth(
      @QueryParam("symbol_id") long symbolId, @QueryParam("count") int count)
      throws DragonexException, IOException;

  /** Query real time quotes */
  @GET
  @Path("market/real/")
  DragonResult<List<Ticker>> ticker(@QueryParam("symbol_id") long symbolId)
      throws DragonexException, IOException;
}
