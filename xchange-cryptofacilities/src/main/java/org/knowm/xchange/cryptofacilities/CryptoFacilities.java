package org.knowm.xchange.cryptofacilities;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesInstruments;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrderBook;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesPublicFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTickers;

/** @author Jean-Christophe Laruelle */
@Path("/api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilities {

  @GET
  @Path("/tickers")
  CryptoFacilitiesTickers getTickers() throws IOException;

  @GET
  @Path("/orderbook")
  CryptoFacilitiesOrderBook getOrderBook(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("/instruments")
  CryptoFacilitiesInstruments getInstruments() throws IOException;

  @GET
  @Path("/history")
  CryptoFacilitiesPublicFills getHistory(@QueryParam("symbol") String symbol) throws IOException;
}
