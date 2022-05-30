package org.knowm.xchange.blockchain;

import org.knowm.xchange.blockchain.dto.account.BlockchainSymbol;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author scuevas
 * @see <a href="https://api.blockchain.info/v3/#/">Swagger</a>
 */
@Path("v3/exchange")
@Produces(MediaType.APPLICATION_JSON)
public interface Blockchain {

    /**
     * Get a list of symbols
     *
     * @return This returns a map where the key {@link String} represents the currency symbol and the value which is an
     * instance of type {@link BlockchainSymbol}
     */
    @Path("/symbols")
    @GET
    Map<String, BlockchainSymbol> getSymbols();
}
