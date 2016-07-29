package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.pricealert.request.CoinsetterPriceAlertRequest;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertList;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertResponse;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterRemovePriceAlertResponse;

/**
 * RESTful/JSON API: Price Alert.
 */
@Path("/pricealert")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinsetterPriceAlert {

  @POST
  CoinsetterPriceAlertResponse add(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId, CoinsetterPriceAlertRequest request)
      throws CoinsetterException, IOException;

  @GET
  CoinsetterPriceAlertList list(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId) throws CoinsetterException, IOException;

  @DELETE
  @Path("{priceAlertId}")
  CoinsetterRemovePriceAlertResponse remove(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId,
      @PathParam("priceAlertId") UUID priceAlertId) throws CoinsetterException, IOException;

}
