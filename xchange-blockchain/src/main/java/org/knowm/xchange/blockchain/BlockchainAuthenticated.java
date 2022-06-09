package org.knowm.xchange.blockchain;

import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.blockchain.params.BlockchainWithdrawalParams;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @see <a href="https://api.blockchain.info/v3/#/">Swagger</a>
 */
@Path("v3/exchange")
@Produces(MediaType.APPLICATION_JSON)
public interface BlockchainAuthenticated extends Blockchain {

    /**
     * Receive current account balances
     *
     * @return This returns a map where the key {@link String} represents the primary data and the value which is an
     * instance of type {@link BlockchainAccountInformation} is a list of account balances
     */
    @Path("/accounts")
    @GET
    Map<String, List<BlockchainAccountInformation>> getAccountInformation();

    /**
     * Request a withdrawal
     * Call GET /whitelist first to retrieve the ID of the beneficiary.
     *
     * @return the withdrawal object created
     * @throws IOException
     * @throws BlockchainException
     */
    @Path("/withdrawals")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    BlockchainWithdrawal postWithdrawFunds(BlockchainWithdrawalParams blockchainWithdrawalRequest) throws IOException, BlockchainException;

    /**
     * Get a list of withdrawals
     *
     * @param startTime
     * @param endTime
     * @return list of withdrawals object
     */
    @Path("/withdrawals")
    @GET
    List<BlockchainWithdrawal> getWithdrawFunds(@QueryParam("from") Long startTime,
                                                @QueryParam("to") Long endTime);

    /**
     * Get a deposit address. Currently, only crypto currencies are supported
     *
     * @param symbol
     * @return the deposit object according to the currency
     * @throws IOException
     * @throws BlockchainException
     */
    @Path("/deposits/{symbol}")
    @POST
    BlockchainDeposit getDepositAddress(@PathParam("symbol") @FormParam("symbol") String symbol) throws IOException, BlockchainException;

    /**
     * Get current fee level
     *
     * @return the fees of all currency pairs
     */
    @Path("/fees")
    @GET
    BlockchainFees getFees();

    /**
     * Get a list of deposits
     *
     * @param startTime
     * @param endTime
     * @return list of deposits object
     */
    @Path("/deposits")
    @GET
    List<BlockchainDeposits> depositHistory(@QueryParam("from") Long startTime,
                                            @QueryParam("to") Long endTime);
}
