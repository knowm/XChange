package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransactionList;

/**
 * RESTful/JSON API: Financial Transaction.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinsetterFinancialTransaction {

  @GET
  @Path("financialTransaction/{financialTransactionUuid}")
  org.knowm.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransaction get(
      @HeaderParam("coinsetter-client-session-id") UUID clientSessionId, @PathParam("financialTransactionUuid") UUID financialTransactionUuid)
      throws CoinsetterException, IOException;

  @GET
  @Path("customer/account/{accountUuid}/financialTransaction")
  CoinsetterFinancialTransactionList list(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId,
      @PathParam("accountUuid") UUID accountUuid) throws CoinsetterException, IOException;

}
