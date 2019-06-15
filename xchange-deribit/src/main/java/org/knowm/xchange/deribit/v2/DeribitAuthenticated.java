package org.knowm.xchange.deribit.v2;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;
import si.mazi.rescu.ParamsDigest;

@Path("/api/v2/private")
@Produces(MediaType.APPLICATION_JSON)
public interface DeribitAuthenticated {

  /**
   * Retrieves user account summary.
   *
   * @param currency required, The currency symbol, BTC or ETH
   * @param extended Include additional fields
   * @return
   * @throws DeribitException
   * @throws IOException
   */
  @GET
  @Path("get_account_summary")
  DeribitResponse<AccountSummary> getAccountSummary(
      @QueryParam("currency") String currency,
      @QueryParam("extended") Boolean extended,
      @HeaderParam("Authorization") ParamsDigest auth)
      throws DeribitException, IOException;
}
