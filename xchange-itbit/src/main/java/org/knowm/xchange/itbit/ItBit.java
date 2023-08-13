package org.knowm.xchange.itbit;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.itbit.dto.ItBitException;
import org.knowm.xchange.itbit.dto.marketdata.ItBitDepth;
import org.knowm.xchange.itbit.dto.marketdata.ItBitTrades;

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