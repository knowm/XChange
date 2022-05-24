package org.knowm.xchange.blockchain;

import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BlockchainAuthenticated extends Blockchain{

    @Path("v3/exchange/withdrawals")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    BlockchainWithdrawal postWithdrawFunds(BlockchainWithdrawalRequest blockchainWithdrawalRequest) throws IOException, BlockchainException;

    @Path("v3/exchange/withdrawals")
    @GET
    List<BlockchainWithdrawal> getWithdrawFunds(@QueryParam("from") Long startTime,
                                                @QueryParam("to") Long endTime);

    @Path("v3/exchange/deposits/{symbol}")
    @POST
    BlockchainAccount getDepositAddress(@PathParam("symbol") @FormParam("symbol") String symbol);

    @Path("v3/exchange/orders")
    @GET
    List<BlockchainOrder> getOrders();

    @Path("v3/exchange/trades")
    @GET
    List<BlockchainOrder> getTrades();

    @Path("v3/exchange/orders")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    BlockchainOrder postOrder(BlockchainOrder blockchainOrder);

    @Path("v3/exchange/fees")
    @GET
    BlockchainFees getFees();

    @Path("v3/exchange/deposits")
    @GET
    List<BlockchainDeposit> depositHistory(@QueryParam("from") Long startTime,
                                           @QueryParam("to") Long endTime);
}
