package com.xeiam.xchange.cryptonit.v2;

import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitCoins;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitFunds;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrder;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.trade.CancelOrderRequest;
import com.xeiam.xchange.cryptonit.v2.dto.trade.NewOrderRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yar.kh on 02/10/14.
 */
@Path("apiv2/rest")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptonitAuth extends Cryptonit {

    @GET
    @Path("developer/funds")
    public CryptonitFunds getAccountFunds(@HeaderParam("Authorization") String token) throws IOException;

    @GET
    @Path("developer/ccaccount")
    public CryptonitCoins getAccountСoins(@HeaderParam("Authorization") String token) throws IOException;

    @GET
    @Path("developer/ccorder")
    public CryptonitOrders getOrders(@HeaderParam("Authorization") String token,
                                     @QueryParam("type") CryptonitOrder.OrderType type,
                                     @QueryParam("my_orders") Boolean my_orders,
                                     @QueryParam("limit") Long limit) throws IOException;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("developer/ccorder")
    public CryptonitOrders placeOrder(@HeaderParam("Authorization") String token, NewOrderRequest newOrderRequest) throws IOException;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("developer/ccorder")
    public List<Boolean> cancelOrder(@HeaderParam("Authorization") String token, CancelOrderRequest cancelOrderRequest) throws IOException;
}
