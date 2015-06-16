package com.xeiam.xchange.bitcurex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexResponse;
import com.xeiam.xchange.bitcurex.dto.marketdata.trade.BitcurexOrder;
import com.xeiam.xchange.bitcurex.dto.marketdata.trade.BitcurexOrderResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.bitcurex.dto.marketdata.account.BitcurexFunds;

@Path("v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface BitcurexAuthenticated {

  @GET
  @Path("balance/{api_key}/hash/")
  public BitcurexResponse<BitcurexFunds> getFunds(@PathParam("api_key") String apiKey,
                                @PathParam("hash") ParamsDigest hash,
                                @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;


//    GET /offers/market/api_key/hash/?nonce

//    {"status": "ok", "data": {"pln": "999555.1225", "usd": "1248705.0000", "btc": "75.00000000", "eur":"1246843.0000"}}

    @GET
    @Path("offers/{market}/{api_key}/hash/")
    public BitcurexFunds getUserOffers(@PathParam("market") String market,
                                       @PathParam("api_key") String apiKey,
                                       @PathParam("hash") ParamsDigest hash,
                                       @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

//     GET /all/offers/market/api_key/hash/?nonce

    @GET
    @Path("offers/{market}/{api_key}/hash/")
    public BitcurexResponse<List<BitcurexOrder>> getAllOffers(@PathParam("market") String market,
                                                              @PathParam("api_key") String apiKey,
                                                              @PathParam("hash") ParamsDigest hash,
                                                              @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;





//    key=secret,message="limit=200&market=pln&nonce=1416526815&offer_type=bid &volume=2",sha512) market = pln / eur / usd nonce = timestamp limit = price (integer or float) volume = how many btc user want to buy/sell offer_type = 'ask' / 'buy'

//    URL: POST /offer/market/api_key/hash

    @GET
    @Path("offer/{market}/{api_key}/hash/")
    public BitcurexResponse<BitcurexOrderResponse> createOffer(@PathParam("api_key") String apiKey,
                                                               @PathParam("hash") ParamsDigest hash,
                                                               @PathParam("market") String market,
                                                               @FormParam("limit") BigDecimal limit,
                                                               @FormParam("offer_type") String offerType,
                                                               @FormParam("volume") BigDecimal volume,
                                                               @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

}
