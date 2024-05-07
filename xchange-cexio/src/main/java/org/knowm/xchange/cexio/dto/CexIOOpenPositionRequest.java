package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import org.knowm.xchange.cexio.dto.trade.CexioPositionType;

/** Author: Andrea Fossi Since: 22/06/18 */
public class CexIOOpenPositionRequest extends CexIORequest {
  @JsonProperty("symbol")
  public final String symbol; // currency to buy

  @JsonProperty("amount")
  @JsonSerialize(using = ToStringSerializer.class)
  public final BigDecimal
      amount; // total amount of product to buy using borrowed funds and user's funds

  @JsonProperty("msymbol")
  public final String
      msymbol; // currency of user funds used, may be one of currencies in the pair, default is

  // second currency in the pair

  @JsonProperty("leverage")
  public final Integer
      leverage; // leverage ratio of total funds (user's and borrowed) to user's funds; for example

  // - leverage=3 means - ratio total/user's=3:1, margin=33.(3)%, 1/3 is users, 2/3
  // are borrowed; Note that in UI it will be presented as 1/3

  @JsonProperty("ptype")
  public final CexioPositionType
      ptype; // position type. long - buying product, profitable if product price grows; short -

  // selling product, profitable if product price falls;

  @JsonProperty("anySlippage")
  public final Boolean anySlippage; // allows to open position at changed price

  @JsonProperty("eoprice")
  @JsonSerialize(using = ToStringSerializer.class)
  public final BigDecimal eoprice; // estimated price at which your position will be opened

  @JsonProperty("stopLossPrice")
  @JsonSerialize(using = ToStringSerializer.class)
  public final BigDecimal
      stopLossPrice; // price near which your position will be closed automatically in case of

  // unfavorable market conditions

  public CexIOOpenPositionRequest(
      String symbol,
      BigDecimal amount,
      String msymbol,
      Integer leverage,
      CexioPositionType ptype,
      Boolean anySlippage,
      BigDecimal eoprice,
      BigDecimal stopLossPrice) {
    this.symbol = symbol;
    this.amount = amount;
    this.msymbol = msymbol;
    this.leverage = leverage;
    this.ptype = ptype;
    this.anySlippage = anySlippage;
    this.eoprice = eoprice;
    this.stopLossPrice = stopLossPrice;
  }
}
