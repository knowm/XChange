package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Object contains position data
 *
 * @author Andrea Fossi.
 */
public class CexioPosition {

  private final String id; // 	position id
  private final Long timestamp; // 	timestamp the position was opened at
  private final String psymbol; // 	currency, in which the position was opened (product)
  private final String
      msymbol; //	currency, in which user is going to gain profit, can be one of the currencies,
  // presented in the pair
  private final String
      lsymbol; //	currency of borrowed funds, can be one of the currencies, presented in the pair
  private final String pair; // 	trading pair as a string like "XXX:XXX"
  private final BigDecimal pamount; // total position amount, presented in "psymbol"
  private final BigDecimal
      omamount; //	string	("open money amount') user's amount used in the position, presented in
  // 'msymbol"
  private final BigDecimal
      lamount; //	amount of borrowed funds in the position, presented in "lsymbol"
  private final BigDecimal
      openPrice; //	price the position was opened at, calculated as average of underlying executed
  // orders
  private final CexioPositionType
      type; //	position type. long - buying product, profitable if product price grows; short -
  // selling product, profitable if product price falls;
  private final BigDecimal
      stopLossPrice; //	price, near which your position will be closed automatically in case of
  // unfavorable market conditions
  private final BigDecimal
      pfee; //	estimated fee (in %) from user's amount, that will be charged for position rollover
  // for the next 4 hours
  private final BigDecimal
      cfee; //	fee (in %) from user's amount, that will be charged for position closing
  private final BigDecimal
      tfeeAmount; //	total fees paid by user, it is equal to opening fee amount, when position has
  // been just opened
  private final String user; // 	User ID
  private final BigDecimal amount; // 	total amount of "product" in the position
  private final String symbol; // 	total amount of product in the position
  private final BigDecimal
      slamount; //	(stop-loss amount) amount that will be returned, including user`s and borrowed
  // funds
  private final Integer leverage; // 	leverage, with which the position was opened
  private final String dfl; // 	(TECH) desired fast liquidation price
  private final String flPrice; // 	(TECH) estimated price of total loss
  private final String
      ofee; //	fee (in %) from user's amount, that was charged for position opening
  private final String rinterval; // 	rollover interval in miliseconds
  private final String okind; // 	how the position was opened
  //  private final String  a:BTC:c	;//	(TECH) credit in psymbol
  //  private final String  a:BTC:s	;//	(TECH) saldo in psymbol
  private final String oorder; // 	underlying order id for position opening
  private final String lremains; // 	(TECH) amount of borrowed funds to be returned by user
  private final String slremains; // 	(TECH) remains of slamount to return
  private final String status; // 	position's current status (e.g. a for active)
  //  public final String  a:USD:cds	;//	(TECH) equation c==d, s==0

  public CexioPosition(
      @JsonProperty("id") String id,
      @JsonProperty("otime") Long timestamp,
      @JsonProperty("psymbol") String psymbol,
      @JsonProperty("msymbol") String msymbol,
      @JsonProperty("lsymbol") String lsymbol,
      @JsonProperty("pair") String pair,
      @JsonProperty("pamount") BigDecimal pamount,
      @JsonProperty("omamount") BigDecimal omamount,
      @JsonProperty("lamount") BigDecimal lamount,
      @JsonProperty("oprice") BigDecimal openPrice,
      @JsonProperty("ptype") CexioPositionType type,
      @JsonProperty("stopLossPrice") BigDecimal stopLossPrice,
      @JsonProperty("pfee") BigDecimal pfee,
      @JsonProperty("cfee") BigDecimal cfee,
      @JsonProperty("tfeeAmount") BigDecimal tfeeAmount,
      @JsonProperty("user") String user,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("slamount") BigDecimal slamount,
      @JsonProperty("leverage") Integer leverage,
      @JsonProperty("dfl") String dfl,
      @JsonProperty("flPrice") String flPrice,
      @JsonProperty("ofee") String ofee,
      @JsonProperty("rinterval") String rinterval,
      @JsonProperty("okind") String okind,
      @JsonProperty("oorder") String oorder,
      @JsonProperty("lremains") String lremains,
      @JsonProperty("slremains") String slremains,
      @JsonProperty("status") String status) {
    this.id = id;
    this.timestamp = timestamp;
    this.psymbol = psymbol;
    this.msymbol = msymbol;
    this.lsymbol = lsymbol;
    this.pair = pair;
    this.pamount = pamount;
    this.omamount = omamount;
    this.lamount = lamount;
    this.openPrice = openPrice;
    this.type = type;
    this.stopLossPrice = stopLossPrice;
    this.pfee = pfee;
    this.cfee = cfee;
    this.tfeeAmount = tfeeAmount;
    this.user = user;
    this.amount = amount;
    this.symbol = symbol;
    this.slamount = slamount;
    this.leverage = leverage;
    this.dfl = dfl;
    this.flPrice = flPrice;
    this.ofee = ofee;
    this.rinterval = rinterval;
    this.okind = okind;
    this.oorder = oorder;
    this.lremains = lremains;
    this.slremains = slremains;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getPsymbol() {
    return psymbol;
  }

  public String getMsymbol() {
    return msymbol;
  }

  public String getLsymbol() {
    return lsymbol;
  }

  public String getPair() {
    return pair;
  }

  public BigDecimal getPamount() {
    return pamount;
  }

  public BigDecimal getOmamount() {
    return omamount;
  }

  public BigDecimal getLamount() {
    return lamount;
  }

  public BigDecimal getOpenPrice() {
    return openPrice;
  }

  public CexioPositionType getType() {
    return type;
  }

  public BigDecimal getStopLossPrice() {
    return stopLossPrice;
  }

  public BigDecimal getPfee() {
    return pfee;
  }

  public BigDecimal getCfee() {
    return cfee;
  }

  public BigDecimal getTfeeAmount() {
    return tfeeAmount;
  }

  public String getUser() {
    return user;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getSlamount() {
    return slamount;
  }

  public Integer getLeverage() {
    return leverage;
  }

  public String getDfl() {
    return dfl;
  }

  public String getFlPrice() {
    return flPrice;
  }

  public String getOfee() {
    return ofee;
  }

  public String getRinterval() {
    return rinterval;
  }

  public String getOkind() {
    return okind;
  }

  public String getOorder() {
    return oorder;
  }

  public String getLremains() {
    return lremains;
  }

  public String getSlremains() {
    return slremains;
  }

  public String getStatus() {
    return status;
  }
}
