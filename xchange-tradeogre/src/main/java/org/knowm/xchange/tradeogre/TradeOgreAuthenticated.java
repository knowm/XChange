package org.knowm.xchange.tradeogre;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalances;

@Path("")
public interface TradeOgreAuthenticated extends TradeOgre {

  @GET
  @Path("account/balances")
  TradeOgreBalances getBalances(@HeaderParam("Authorization") String base64UserPwd)
      throws IOException;

  @POST
  @Path("account/balance")
  TradeOgreBalance getBalance(
      @HeaderParam("Authorization") String base64UserPwd, @FormParam("currency") String currency)
      throws IOException;
}
