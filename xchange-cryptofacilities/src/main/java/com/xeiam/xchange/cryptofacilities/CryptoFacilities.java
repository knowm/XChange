package com.xeiam.xchange.cryptofacilities;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesContracts;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesIndex;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesVolatility;

/**
 * @author Jean-Christophe Laruelle
 */

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilities {

  @GET
  @Path("/ticker")
  CryptoFacilitiesTicker getTicker(@QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit);

  @GET
  @Path("/contracts")
  CryptoFacilitiesContracts getContracts();

  @GET
  @Path("/cumulativebidask")
  CryptoFacilitiesCumulativeBidAsk getCumulativeBidAsk(@QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit);

  @GET
  @Path("/cfbpi")
  CryptoFacilitiesIndex getIndex();

  @GET
  @Path("/volatility")
  CryptoFacilitiesVolatility getVolatility();

}
