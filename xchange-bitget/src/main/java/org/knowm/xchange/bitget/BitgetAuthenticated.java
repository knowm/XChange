package org.knowm.xchange.bitget;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.BitgetResponse;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;
import org.knowm.xchange.bitget.dto.account.BitgetMainSubTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto;
import org.knowm.xchange.bitget.dto.trade.BitgetFillDto;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto;
import org.knowm.xchange.bitget.dto.trade.BitgetPlaceOrderDto;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BitgetAuthenticated {

  @GET
  @Path("api/v2/spot/account/assets")
  BitgetResponse<List<BitgetBalanceDto>> balances(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp)
      throws IOException, BitgetException;


  @GET
  @Path("api/v2/spot/trade/orderInfo")
  BitgetResponse<List<BitgetOrderInfoDto>> orderInfo(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("orderId") String orderId)
      throws IOException, BitgetException;


  @POST
  @Path("api/v2/spot/trade/place-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetResponse<BitgetOrderInfoDto> createOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      BitgetPlaceOrderDto bitgetPlaceOrderDto)
      throws IOException, BitgetException;


  @GET
  @Path("api/v2/spot/trade/fills")
  BitgetResponse<List<BitgetFillDto>> fills(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit, @QueryParam("orderId") String orderId,
      @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;


  @GET
  @Path("api/v2/spot/account/transferRecords")
  BitgetResponse<List<BitgetTransferRecordDto>> transferRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency, @QueryParam("limit") Integer limit, @QueryParam("clientOid") String clientOid,
      @QueryParam("fromType") String fromType,
      @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;


  @GET
  @Path("api/v2/spot/account/sub-main-trans-record")
  BitgetResponse<List<BitgetMainSubTransferRecordDto>> mainSubTransferRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency, @QueryParam("limit") Integer limit, @QueryParam("clientOid") String clientOid,
      @QueryParam("role") String role, @QueryParam("subUid") String subAccountUid,
      @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;


}
