package org.knowm.xchange.tradeogre;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.tradeogre.dto.marketdata.TradeOgreOrderBook;
import org.knowm.xchange.tradeogre.dto.marketdata.TradeOgreTicker;

@Path("")
public interface TradeOgre {

  @GET
  @Path("ticker/{market}")
  TradeOgreTicker getTicker(@PathParam("market") String market) throws IOException;

  @GET
  @Path("markets")
  List<Map<String, TradeOgreTicker>> getTickers() throws IOException;

  @GET
  @Path("orders/{market}")
  TradeOgreOrderBook getOrderBook(@PathParam("market") String market) throws IOException;
}
