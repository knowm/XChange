package org.knowm.xchange.coindirect;

import org.knowm.xchange.coindirect.dto.CoindirectException;
import org.knowm.xchange.coindirect.dto.account.CoindirectWallet;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrder;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface CoindirectAuthenticated extends Coindirect {
    public static final String AUTHORIZATION = "Authorization";

    @GET
    @Path("/api/wallet")
    List<CoindirectWallet> listWallets(@QueryParam("max") long max, @HeaderParam("Authorization") ParamsDigest signer) throws IOException, CoindirectException;

    @GET
    @Path("/api/v1/exchange/order")
    List<CoindirectOrder> listExchangeOrders(@QueryParam("symbol") String symbol, @QueryParam("completed") boolean completed, @QueryParam("offset") long offset, @QueryParam("max") long max, @HeaderParam("Authorization") ParamsDigest signer)  throws IOException, CoindirectException;
}
