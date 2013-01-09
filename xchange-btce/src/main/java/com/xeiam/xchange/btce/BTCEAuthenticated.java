/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.xeiam.xchange.btce.dto.marketdata.BTCEAccountInfoResult;
import com.xeiam.xchange.proxy.ParamsDigest;

/** @author Matija Mazi <br/> */
@Path("tapi")
public interface BTCEAuthenticated {
  /**
   * @param from   The ID of the transaction to start displaying with;  default	0
   * @param count  The number of transactions for displaying	          default 1,000
   * @param fromId The ID of the transaction to start displaying with	  default	0
   * @param endId  The ID of the transaction to finish displaying with	default ∞
   * @param order  sorting	ASC or DESC	                                default DESC
   * @param since  When to start displaying?	UNIX time	                default 0
   * @param end    When to finish displaying?	UNIX time	                default ∞
   *
   * @return {success=1, return={funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1, open_orders=0, server_time=1357678428}}
   */
  @POST
  @FormParam("method")
  BTCEAccountInfoResult getInfo(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") Integer nonce,
      @FormParam("from") Long from,
      @FormParam("count") Long count,
      @FormParam("from_id") Long fromId,
      @FormParam("end_id") Long endId,
      @FormParam("order") String order,
      @FormParam("since") Long since,
      @FormParam("end") Long end);



}
