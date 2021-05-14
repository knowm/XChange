package org.knowm.xchange.bx;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bx.dto.marketdata.results.BxAssetPairsResult;
import org.knowm.xchange.bx.dto.marketdata.results.BxHistoryTradeResult;
import org.knowm.xchange.bx.dto.marketdata.results.BxTickerResult;

@Path("/api/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bx {

  @GET
  @Path("pairing/")
  BxAssetPairsResult getAssetPairs() throws IOException;

  @GET
  @Path("")
  BxTickerResult getTicker() throws IOException;

  @GET
  @Path("tradehistory/?pairing={pairing_id}&date={date}")
  BxHistoryTradeResult getHistoryTrade(
      @PathParam("pairing_id") String pairingId, @PathParam("date") String date) throws IOException;
}
