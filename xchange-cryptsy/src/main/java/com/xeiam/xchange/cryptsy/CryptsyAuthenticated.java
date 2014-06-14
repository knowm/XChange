/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.cryptsy;

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

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.cryptsy.dto.CryptsyGenericReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyDepositAddressReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyNewAddressReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyTransfersReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyTxnHistoryReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyWithdrawalReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCalculatedFeesReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCancelMultipleOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCancelOrderReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyPlaceOrderReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;

/**
 * @author ObsessiveOrange
 */
@Path("/api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CryptsyAuthenticated extends Cryptsy {

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyAccountInfoReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyAccountInfoReturn getinfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyTxnHistoryReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTxnHistoryReturn mytransactions(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param currencyID
   * @param currencyCode
   * @return CryptsyNewAddressReturn DTO
   * @throws IOException
   */

  @POST
  @FormParam("method")
  CryptsyNewAddressReturn generatenewaddress(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce,
      @FormParam("currencyid") @Nullable Integer currencyID, @FormParam("currencycode") @Nullable String currencyCode) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyDepositAddressReturn DTO
   * @throws IOException
   */

  @POST
  @FormParam("method")
  CryptsyDepositAddressReturn getmydepositaddresses(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param address Pre-Approved address to make this request to
   * @param amount Amount to withdraw to address (Currency determined by withdrawal address
   * @return CryptsyWithdrawalReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyWithdrawalReturn makewithdrawal(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("address") String address,
      @FormParam("amount") BigDecimal amount) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyTransfersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTransfersReturn mytransfers(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request orderbook data for
   * @return CryptsyOrderBookReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyOrderBookReturn marketorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("marketid") int marketID)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request trades data for
   * @return CryptsyMarketTradesReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyMarketTradesReturn markettrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("marketid") int marketID)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyGetMarketsReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyGetMarketsReturn getmarkets(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request trades data for
   * @param resultCount Number of results to display (default: 1000)
   * @return CryptsyTradeHistoryReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTradeHistoryReturn mytrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("marketid") int marketID,
      @FormParam("limit") int resultCount) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param startDate Start date to display record from
   * @param endDate Display record until this date
   * @return CryptsyTradeHistoryReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyTradeHistoryReturn allmytrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("startdate") String startDate,
      @FormParam("enddate") String endDate) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID to request openorders data for
   * @return CryptsyOpenOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyOpenOrdersReturn myorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("marketid") int marketID) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyOpenOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyOpenOrdersReturn allmyorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
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
  CryptsyPlaceOrderReturn createorder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("marketid") int marketID,
      @FormParam("ordertype") String orderType, @FormParam("quantity") BigDecimal quantity, @FormParam("price") BigDecimal price) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param orderID ID of order to cancel
   * @return CryptsyCancelOrderReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCancelOrderReturn cancelorder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("orderid") int orderID)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @param marketID marketID in which to cancel orders
   * @return CryptsyCancelMultipleOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCancelMultipleOrdersReturn cancelmarketorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("marketid") int marketID)
      throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return CryptsyCancelMultipleOrdersReturn DTO
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyCancelMultipleOrdersReturn cancelallorders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  /**
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
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
  CryptsyCalculatedFeesReturn calculatefees(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("ordertype") String orderType,
      @FormParam("quantity") BigDecimal quantity, @FormParam("price") BigDecimal price) throws IOException;

  /**
   * Stub method - to be added at later date
   * 
   * @param apiKey API key as given in {@link com.xeiam.xchange.ExchangeSpecification}
   * @param signer POST Body Digest instance to use when signing POST request
   * @param nonce Nonce to use
   * @return
   * @throws IOException
   */
  @POST
  @FormParam("method")
  CryptsyGenericReturn<String> getwalletstatus(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce) throws IOException;

  enum SortOrder {
    ASC, DESC
  }
}
