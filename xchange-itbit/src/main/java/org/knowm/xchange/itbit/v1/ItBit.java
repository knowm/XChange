package org.knowm.xchange.itbit.v1;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.itbit.v1.dto.ItBitException;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTrades;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface ItBit {

  @GET
  @Path("/markets/{ident}{currency}/order_book")
  ItBitDepth getDepth(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException, ItBitException;

  @GET
  @Path("/markets/{ident}{currency}/trades")
  ItBitTrades getTrades(
      @PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency,
      @QueryParam("since") long sinceId)
      throws IOException, ItBitException;
}
