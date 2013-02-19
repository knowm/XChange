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
package com.xeiam.xchange.campbx;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.istack.internal.NotNull;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.account.MyFunds;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;

/** @author Matija Mazi */
@Path("api")
@Produces("application/json")
public interface CampBX {

  @POST
  @Path("xdepth.php")
  @Produces("application/json")
  public CampBXOrderBook getOrderBook();

  @POST
  @Path("xticker.php")
  @Produces("application/json")
  public CampBXTicker getTicker();

  /**
   * An API call to obtain list pending orders for an account can be made in following format:
   * https://CampBX.com/api/myorders.php POST: user=USERNAME pass=PASSWORD Result is a JSON object with two arrays: Buy
   * and Sell. Each array includes a sub-array of open Buy or Sell orders.
   */
  @POST
  @Path("myorders.php")
  public MyOpenOrders getOpenOrders(
      @FormParam("user") String user, @FormParam("pass") String password);


  /**
   * An API call to obtain latest account balances can be made in following format: https://CampBX.com/api/myfunds.php
   * POST: user=USERNAME pass=PASSWORD Result is a JSON object with six members. First two members show Total USD and
   * BTC balances for a given account, provided the login credentials are correct. Next two members show Liquid USD and
   * BTC balances not tied up in open orders on the order book. Last two members show Margin Account USD and BTC
   * balances. When you open or close a margin position, CampBX transparently moves funds in and out of the Margin
   * Account; hence, no explicit funds-transfer is required.
   */
  @POST
  @Path("myfunds.php")
  public MyFunds getMyFunds(
      @FormParam("user") String user, @FormParam("pass") String password);

  /**
   * An API call to placing an order can be made in following format:
   * <p/>
   * https://CampBX.com/api/tradeenter.php POST: user=USERNAME pass=PASSWORD TradeMode=QuickBuy Quantity=DECIMAL
   * Price=DECIMAL
   * <p/>
   * OR
   * <p/>
   * https://CampBX.com/api/tradeenter.php POST: user=USERNAME pass=PASSWORD TradeMode=QuickSell Quantity=DECIMAL
   * Price=DECIMAL Please note that the parameters listed below are case-sensitive. The TradeMode parameter refers to
   * the type of the order, and permitted values are QuickBuy or QuickSell. Quick orders are described in more detail on
   * the FAQ page; essentially they are limit-price orders that stay open on CampBX order book for up to 31 days.
   * Quantity and Price are decimal values that must follow all rules / limits set by CampBX. Minimum quantity to place
   * an order is 0.1 Bitcoins.
   * <p/>
   * Return Values: If your entire order is executed immediately, the return value will be '0' in JSON format:
   * {"Success":"0"}
   * <p/>
   * In case the order was not filled entirely, the return value would be corresponding Order ID in our Order Book.
   */
  @POST
  @Path("tradeenter.php")
  public CampBXResponse tradeEnter(
      @FormParam("user") String user,
      @FormParam("pass") String password,
      @FormParam("TradeMode") TradeMode mode,
      @FormParam("Quantity") BigDecimal quantity,
      @FormParam("Price") BigDecimal price);

  /**
   * An API call to place an advanced order can be made in following format:
   * <p/>
   * https://CampBX.com/api/tradeadv.php
   * <p/>
   * POST Mandatory fields: user=USERNAME pass=PASSWORD TradeMode=AdvancedBuy OR TradeMode=AdvancedSell Price=DECIMAL OR
   * Price=Market Quantity=DECIMAL
   * <p/>
   * Optional Fields: FillType=Incremental OR FillType=AON OR FillType=FOK (If omitted, default Fill Type is
   * Incremental)
   * <p/>
   * DarkPool=No OR DarkPool=Yes (Default is No Darkpool)
   * <p/>
   * Expiry=YYYY/MM/DD (Allowed range is 1 Hour through 31 Days) (Many additional formats are supported as well,
   * including relative values! Please contact our helpdesk if you would like additional information about time/date
   * formats.)
   * <p/>
   * Please note that all parameters are case-sensitive. We highly recommend executing small trades and experimenting
   * with all of the possible parameter values before implementing them in your strategy. Expiry date field allows using
   * many relative and absolute values and offers a lot of flexibility. If this is something that you rely on heavily in
   * your strategy, please contact us for details about additional formats. Return Values: If your entire order is
   * executed immediately, the return value will be '0' in JSON format: {"Success":"0"}
   * <p/>
   * In case the order was not filled entirely, the return value would be corresponding Order ID in our Order Book.
   */
  @POST
  @Path("tradeadv.php")
  public CampBXResponse tradeAdvancedEnter(
      @NotNull @FormParam("user") String user,
      @NotNull @FormParam("pass") String password,
      @NotNull @FormParam("TradeMode") AdvTradeMode mode,
      @NotNull @FormParam("Quantity") BigDecimal quantity,
      @NotNull @FormParam("Price") BigDecimal price,
      @FormParam("FillType") FillType fillType,
      @FormParam("DarkPool") DarkPool darkPool,
      @FormParam("Expiry") String expiry
      // todo: Date
  );

  @POST
  @Path("tradeadv.php")
  public CampBXResponse tradeAdvancedMarketEnter(
      @NotNull @FormParam("user") String user,
      @NotNull @FormParam("pass") String password,
      @NotNull @FormParam("TradeMode") AdvTradeMode mode,
      @NotNull @FormParam("Quantity") BigDecimal quantity,
      @NotNull @FormParam("Price") MarketPrice market,
      @FormParam("FillType") FillType fillType,
      @FormParam("DarkPool") DarkPool darkPool,
      @FormParam("Expiry") String expiry
      // todo: Date
  );

  /**
   * An API call to cancel an open order can be made in following format:
   * <p/>
   * https://CampBX.com/api/tradecancel.php POST: user=USERNAME pass=PASSWORD Type=Buy OrderID=NUMERIC_ID
   * <p/>
   * OR
   * <p/>
   * https://CampBX.com/api/tradecancel.php POST: user=USERNAME pass=PASSWORD Type=Sell OrderID=NUMERIC_ID
   * <p/>
   * Please note that the parameters for this call are case-sensitive. Type and OrderID parameters must match the exact
   * information provided by myorders.php call outlined in the previous section. The "Type" parameter refers to the type
   * of order; permitted values are Buy or Sell. "OrderID" must be a numeric value corresponding to the order that you
   * are attempting to cancel.
   */
  @POST
  @Path("tradecancel.php")
  public CampBXResponse tradeCancel(
      @FormParam("user") String user,
      @FormParam("pass") String password,
      @FormParam("Type") OrderType type,
      @FormParam("OrderID") Long orderId);

  /**
   * An API call to get Bitcoin deposit address for your account can be made in following format:
   * <p/>
   * https://CampBX.com/api/getbtcaddr.php POST: user=USERNAME pass=PASSWORD
   * <p/>
   * Please note that the parameters are case-sensitive. API call returns "Success" and the Bitcoin Address if request
   * is successful. An address generated through this method is your dedicated address, and can be used to make deposits
   * as long as you would like.
   */
  @POST
  @Path("getbtcaddr.php")
  public CampBXResponse getDepositAddress(
      @FormParam("user") String user, @FormParam("pass") String password);


  /**
   * An API call to send Bitcoins to an address can be made in following format:
   * <p/>
   * https://CampBX.com/api/sendbtc.php POST: user=USERNAME pass=PASSWORD BTCTo=ADDRESS BTCAmt=DECIMAL
   * <p/>
   * Please note that the parameters listed below are case-sensitive. The BTCTo parameter must be a valid Bitcoin
   * address, while BTCAmt must be a decimal value less than your account balance. API call returns "Success" and the
   * TX_ID if transfer is successful. The default withdrawal limit is 500 Bitcoins per 24 hours, and this limit can be
   * raised by submitting a ticket to the helpdesk.
   */
  @POST
  @Path("sendbtc.php")
  public CampBXResponse withdrawBtc(
      @FormParam("user") String user,
      @FormParam("pass") String password,
      @FormParam("BTCTo") String btcToAddress,
      @FormParam("BTCAmt") BigDecimal amount);

  public static enum TradeMode {QuickBuy, QuickSell}

  public static enum OrderType {Buy, Sell}

  public static enum AdvTradeMode {AdvancedBuy, AdvancedSell}

  public static enum FillType {Incremental, AON, FOK}

  public static enum DarkPool {Yes, No}

  public static enum MarketPrice {Market}

}
