package org.knowm.xchange.cryptocompare;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareHistory;

/**
 * Interface routines to Cryptocompare
 * 
 * @author Ian Worthington
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Cryptocompare {
// eg https://min-api.cryptocompare.com/data/histohour?fsym=ETH&tsym=BTC&limit=1&aggregate=1&toTs=1452680400&e=CCCAGG

  @GET
  @Path("data/histohour")
  CryptocompareHistory getHourHistory(
		  @QueryParam("e") String exchange, 
		  @QueryParam("fsym") String fromSymbol,
		  @QueryParam("tsym") String toSymbol,
		  @QueryParam("limit") int itemLimit,
		  @QueryParam("toTs") Long toTs,
		  @QueryParam("aggregate") Integer aggregate 
		  ) throws IOException;

}
