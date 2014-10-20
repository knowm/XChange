package com.xeiam.xchange.bitbay;

import com.xeiam.xchange.bitbay.dto.BitbayBaseResponse;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.bitbay.dto.trade.BitbayOrder;
import com.xeiam.xchange.bitbay.dto.trade.BitbayTradeResponse;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yarkh
 */
@Path("/API/Trading/tradingApi.php")
@Produces(MediaType.APPLICATION_JSON)
public interface BitbayAuthentiacated extends Bitbay {

  @POST
  @FormParam("method")
  public BitbayAccount info(@HeaderParam("API-Key") String key,
                           @HeaderParam("API-Hash") ParamsDigest sign,
                           @FormParam("moment") Long timestamp,
                           @FormParam("currency") String currency) throws IOException;

    @POST
    @FormParam("method")
    public List<BitbayOrder> orders(@HeaderParam("API-Key") String key,
                              @HeaderParam("API-Hash") ParamsDigest sign,
                              @FormParam("moment") Long timestamp) throws IOException;

    @POST
    @FormParam("method")
    public BitbayTradeResponse trade(@HeaderParam("API-Key") String key,
                        @HeaderParam("API-Hash") ParamsDigest sign,
                        @FormParam("moment") Long timestamp,
                        @FormParam("type") String type,
                        @FormParam("currency") String currency,
                        @FormParam("payment_currency") String payment_currency,
                        @FormParam("amount") BigDecimal amount,
                        @FormParam("price") BigDecimal price,
                        @FormParam("rate") BigDecimal rate) throws IOException;

    @POST
    @FormParam("method")
    public BitbayBaseResponse cancel(@HeaderParam("API-Key") String key,
                         @HeaderParam("API-Hash") ParamsDigest sign,
                         @FormParam("moment") Long timestamp,
                         @FormParam("id") String id) throws IOException;

}
