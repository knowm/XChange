package com.xeiam.xchange.independentreserve;

import com.xeiam.xchange.independentreserve.dto.IndependentReserveHttpStatusException;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveBalance;
import com.xeiam.xchange.independentreserve.dto.auth.AuthAggregate;
import com.xeiam.xchange.independentreserve.dto.trade.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Author: Kamil Zbikowski
 * Date: 4/10/15
 */
@Path("Private")
@Produces(MediaType.APPLICATION_JSON)
public interface IndependentReserveAuthenticated {

    @POST
    @Path("GetAccounts")
    @Consumes(MediaType.APPLICATION_JSON)
    public IndependentReserveBalance getBalance(AuthAggregate authAggregate) throws IndependentReserveHttpStatusException, IOException;

    @POST
    @Path("GetOpenOrders")
    @Consumes(MediaType.APPLICATION_JSON)
    public IndependentReserveOpenOrdersResponse getOpenOrders(IndependentReserveOpenOrderRequest independentReserveOpenOrderRequest) throws IndependentReserveHttpStatusException, IOException;

    @POST
    @Path("GetTrades")
    @Consumes(MediaType.APPLICATION_JSON)
    public IndependentReserveTradeHistoryResponse getTradeHistory(IndependentReserveTradeHistoryRequest independentReserveTradeHistoryRequest) throws IndependentReserveHttpStatusException, IOException;

    @POST
    @Path("PlaceLimitOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public IndependentReservePlaceLimitOrderResponse placeLimitOrder(IndependentReservePlaceLimitOrderRequest independentReservePlaceLimitOrderRequest) throws IndependentReserveHttpStatusException, IOException;

    @POST
    @Path("CancelOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public IndependentReserveCancelOrderResponse cancelOrder(IndependentReserveCancelOrderRequest independentReserveCancelOrderRequest) throws IndependentReserveHttpStatusException, IOException;
}
