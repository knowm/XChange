package org.knowm.xchange.blockchain;

import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("v3/exchange")
@Produces(MediaType.APPLICATION_JSON)
public interface BlockchainAuthenticated extends Blockchain{

    /**
     * Receive current account balances
     * @return
     */
    @Path("/accounts")
    @GET
    Map<String, List<BlockchainAccountInformation>> getAccountInformation();

    /**
     * Request a withdrawal
     * Call GET /whitelist first to retrieve the ID of the beneficiary.
     * @return
     * @throws IOException
     * @throws BlockchainException
     */
    @Path("/withdrawals")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    BlockchainWithdrawal postWithdrawFunds(BlockchainWithdrawalRequest blockchainWithdrawalRequest) throws IOException, BlockchainException;

    /**
     * Get a list of withdrawals
     * @param startTime
     * @param endTime
     * @return
     */
    @Path("/withdrawals")
    @GET
    List<BlockchainWithdrawal> getWithdrawFunds(@QueryParam("from") Long startTime,
                                                @QueryParam("to") Long endTime);
    /**
     * Get a deposit address. Currently, only crypto currencies are supported
     * @param symbol
     * @return
     */
    @Path("/deposits/{symbol}")
    @POST
    //TODO: @FormParam is being used as a workaround to avoid http status error 411.
    //TODO: To solved that, we need exposed a get endpoint in the API
    BlockchainDeposit getDepositAddress(@PathParam("symbol") @FormParam("symbol") String symbol);

    /**
     * Get current fee level
     * @return
     */
    @Path("/fees")
    @GET
    BlockchainFees getFees();

    /**
     * Get a list of deposits
     * @param startTime
     * @param endTime
     * @return
     */
    @Path("/deposits")
    @GET
    List<BlockchainDeposits> depositHistory(@QueryParam("from") Long startTime,
                                            @QueryParam("to") Long endTime);
}
