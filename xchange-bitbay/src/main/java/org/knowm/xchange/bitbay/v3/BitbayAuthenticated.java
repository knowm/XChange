package org.knowm.xchange.bitbay.v3;

import java.io.IOException;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTrades;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author walec51 */
@SuppressWarnings("rawtypes")
@Path("/rest/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitbayAuthenticated {

  @GET
  @Path("trading/history/transactions")
  BitbayUserTrades getTransactionHistory(
      @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Hash") ParamsDigest sign,
      @HeaderParam("Request-Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("Operation-Id") UUID operationId,
      @QueryParam("query") String query)
      throws IOException;
}
