package org.knowm.xchange.cryptsy;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyDepositAddressReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyNewAddressReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyTransfersReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyTxnHistoryReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyWithdrawalReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCalculatedFeesReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCancelMultipleOrdersReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCancelOrderReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyPlaceOrderReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author ObsessiveOrange
 */
@Path("/api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CryptsyAuthenticated {

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyAccountInfoReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyAccountInfoReturn getinfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyTxnHistoryReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTxnHistoryReturn mytransactions(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param currencyID
   * @param currencyCode
   * @return CryptsyNewAddressReturn DTO
   * @throws IOException
   */

  @POST
  @FormParam("method")
  CryptsyNewAddressReturn generatenewaddress(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyid") @Nullable Integer currencyID,
      @FormParam("currencycode") @Nullable String currencyCode) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyDepositAddressReturn DTO
   * @throws IOException
   */

  @POST
  @FormParam("method")
  CryptsyDepositAddressReturn getmydepositaddresses(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param address Pre-Approved address to make this request to
   * @param amount Amount to withdraw to address (Currency determined by withdrawal address
   * @return CryptsyWithdrawalReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyWithdrawalReturn makewithdrawal(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("address") String address, @FormParam("amount") BigDecimal amount)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyTransfersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTransfersReturn mytransfers(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request orderbook data for
   * @return CryptsyOrderBookReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyOrderBookReturn marketorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("marketid") int marketID) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request trades data for
   * @return CryptsyMarketTradesReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyMarketTradesReturn markettrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("marketid") int marketID) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyGetMarketsReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyGetMarketsReturn getmarkets(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request trades data for
   * @param resultCount Number of results to display (default: 1000)
   * @return CryptsyTradeHistoryReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTradeHistoryReturn mytrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("marketid") int marketID, @FormParam("limit") int resultCount)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param startDate Start date to display record from
   * @param endDate Display record until this date
   * @return CryptsyTradeHistoryReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTradeHistoryReturn allmytrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("startdate") String startDate, @FormParam("enddate") String endDate)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request openorders data for
   * @return CryptsyOpenOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyOpenOrdersReturn myorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("marketid") int marketID) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyOpenOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyOpenOrdersReturn allmyorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID in which to create orders
   * @param orderType OrderType (Buy/Sell)
   * @param quantity quantity to trade(BigDecimal)
   * @param price Price to be sold at (BigDecimal)
   * @return CryptsyPlaceOrderReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyPlaceOrderReturn createorder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("marketid") int marketID, @FormParam("ordertype") String orderType,
      @FormParam("quantity") BigDecimal quantity, @FormParam("price") BigDecimal price) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param orderID ID of order to cancel
   * @return CryptsyCancelOrderReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCancelOrderReturn cancelorder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("orderid") int orderID) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID in which to cancel orders
   * @return CryptsyCancelMultipleOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCancelMultipleOrdersReturn cancelmarketorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("marketid") int marketID) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyCancelMultipleOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCancelMultipleOrdersReturn cancelallorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param orderType OrderType (Buy/Sell)
   * @param quantity quantity to trade(BigDecimal)
   * @param price Price to be sold at (BigDecimal)
   * @return CryptsyCalculatedFeesReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCalculatedFeesReturn calculatefees(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("ordertype") String orderType, @FormParam("quantity") BigDecimal quantity,
      @FormParam("price") BigDecimal price) throws IOException;

  /**
   * Stub method - to be added at later date
   *
   * @param apiKey API key as given in {@link org.knowm.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyGenericReturn<String> getwalletstatus(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  enum SortOrder {
    ASC, DESC
  }
}
