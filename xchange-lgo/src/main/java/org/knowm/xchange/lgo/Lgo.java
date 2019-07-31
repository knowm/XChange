package org.knowm.xchange.lgo;

import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.lgo.dto.WithCursor;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.product.LgoProducts;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;

@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public interface Lgo {

  String X_LGO_DATE = "X-LGO-DATE";
  String AUTHORIZATION = "Authorization";
  String PRODUCT_ID = "product_id";
  String MAX_RESULTS = "max_results";
  String PAGE = "page";
  String SORT = "sort";

  @GET
  @Path("products")
  LgoProducts getProducts(
      @HeaderParam(X_LGO_DATE) long timestamp, @HeaderParam(AUTHORIZATION) ParamsDigest signature)
      throws HttpStatusIOException;

  @GET
  @Path("currencies")
  LgoCurrencies getCurrencies(
      @HeaderParam(X_LGO_DATE) long timestamp, @HeaderParam(AUTHORIZATION) ParamsDigest signature)
      throws HttpStatusIOException;

  @GET
  @Path("trades")
  WithCursor<LgoUserTrades> getLastTrades(
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature,
      @QueryParam(PRODUCT_ID) String productId,
      @QueryParam(MAX_RESULTS) int maxResults,
      @QueryParam(PAGE) String page,
      @QueryParam(SORT) String sort)
      throws HttpStatusIOException;
}
