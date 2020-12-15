package org.knowm.xchange.ftx;

import org.knowm.xchange.ftx.dto.account.*;
import org.knowm.xchange.ftx.dto.trade.FtxOrderRequestPOJO;
import org.knowm.xchange.ftx.dto.trade.FtxOrderResponse;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface FtxAuthenticated extends Ftx {

    @GET
    @Path("/account")
    FtxAccountResponse getAccount(
        @HeaderParam("FTX-KEY") String apiKey,
        @HeaderParam("FTX-TS") Long nonce,
        @HeaderParam("FTX-SIGN") ParamsDigest signature,
        @HeaderParam("FTX-SUBACCOUNT") String subaccount
    ) throws IOException,FtxException;


    @POST
    @Path("/subaccounts")
    FtxSubAccountResponse createSubAccount(
        @HeaderParam("FTX-KEY") String apiKey,
        @HeaderParam("FTX-TS") Long nonce,
        @HeaderParam("FTX-SIGN") ParamsDigest signature,
        @HeaderParam("FTX-SUBACCOUNT") String subaccount,
        FtxSubAccountRequestPOJO payload
    )throws IOException,FtxException;

    @GET
    @Path("/subaccounts/{nickname}/balances")
    FtxSubAccountBalanceResponse getSubAccountBalances(
        @HeaderParam("FTX-KEY") String apiKey,
        @HeaderParam("FTX-TS") Long nonce,
        @HeaderParam("FTX-SIGN") ParamsDigest signature,
        @HeaderParam("FTX-SUBACCOUNT") String subaccount,
        @PathParam("nickname") String nickname
    ) throws IOException,FtxException;

    @POST
    @Path("/subaccounts/transfer")
    FtxSubAccountTransferResponse transferBetweenSubAccounts(
        @HeaderParam("FTX-KEY") String apiKey,
        @HeaderParam("FTX-TS") Long nonce,
        @HeaderParam("FTX-SIGN") ParamsDigest signature,
        @HeaderParam("FTX-SUBACCOUNT") String subaccount,
        FtxSubAccountTransferPOJO payload
    ) throws IOException,FtxException;

    @POST
    @Path("/orders")
    FtxOrderResponse placeNewOrder(
        @HeaderParam("FTX-KEY") String apiKey,
        @HeaderParam("FTX-TS") Long nonce,
        @HeaderParam("FTX-SIGN") ParamsDigest signature,
        @HeaderParam("FTX-SUBACCOUNT") String subaccount,
        FtxOrderRequestPOJO payload
    )throws IOException,FtxException;

    @POST
    @Path("/account/leverage")
    FtxLeverageResponse changeLeverage(
        @HeaderParam("FTX-KEY") String apiKey,
        @HeaderParam("FTX-TS") Long nonce,
        @HeaderParam("FTX-SIGN") ParamsDigest signature,
        @HeaderParam("FTX-SUBACCOUNT") String subaccount,
        FtxLeverageDto leverage
    ) throws IOException,FtxException;
}
