package org.knowm.xchange.cryptofacilities;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesInstruments;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTickers;

/**
 * @author Jean-Christophe Laruelle
 */

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilities {

  @GET
  @Path("/v2/tickers")
  CryptoFacilitiesTickers getTickers();

  @GET
  @Path("/cumulativebidask")
  CryptoFacilitiesCumulativeBidAsk getCumulativeBidAsk(@QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit);

  @GET
  @Path("/v2/instruments")
  CryptoFacilitiesInstruments getInstruments();

}
