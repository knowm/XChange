package com.xeiam.xchange.cryptofacilities;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesContracts;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesIndex;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesInstruments;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTickers;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesVolatility;

/**
 * @author Jean-Christophe Laruelle
 */

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilities {

  @GET
  @Path("/ticker")
  @Deprecated
  CryptoFacilitiesTicker getTicker(@QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit);

  @GET
  @Path("/v2/tickers")
  CryptoFacilitiesTickers getTickers();

  @GET
  @Path("/contracts")
  @Deprecated
  CryptoFacilitiesContracts getContracts();

  @GET
  @Path("/cumulativebidask")
  CryptoFacilitiesCumulativeBidAsk getCumulativeBidAsk(@QueryParam("tradeable") String tradeable, @QueryParam("unit") String unit);

  @GET
  @Path("/cfbpi")
  @Deprecated
  CryptoFacilitiesIndex getIndex();

  @GET
  @Path("/volatility")
  @Deprecated
  CryptoFacilitiesVolatility getVolatility();

  @GET
  @Path("/v2/instruments")
  CryptoFacilitiesInstruments getInstruments();

}
