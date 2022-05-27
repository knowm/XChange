package org.knowm.xchange.blockchain;

import org.knowm.xchange.blockchain.dto.account.BlockchainSymbols;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/** @author scuevas*/
@Path("v3/exchange")
@Produces(MediaType.APPLICATION_JSON)
public interface Blockchain {

  /**
   * Get a list of symbols
   * @return
   */
  @Path("/symbols")
  @GET
  Map<String, BlockchainSymbols> getSymbols();
}
