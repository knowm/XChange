package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Object is returned when a position is opened
 *
 * @author Andrea Fossi.
 */
public class CexioOpenPosition {

  private final String id; // 	position id
  private final Long otime; // 	timestamp the position was opened at
  private final String psymbol; // 	currency, in which the position was opened (product)
  private final String
      msymbol; //	currency, in which user is going to gain profit, can be one of the currencies,
  // presented in the pair
  private final String
      lsymbol; //	currency of borrowed funds, can be one of the currencies, presented in the pair
  private final CexioPositionPair pair; // 	trading pair as a string like "XXX:XXX"

  private final BigDecimal pamount; // total position amount, presented in "psymbol"
  private final BigDecimal
      omamount; //	string	("open money amount') user's amount used in the position, presented in
  // 'msymbol"
  private final BigDecimal
      lamount; //	amount of borrowed funds in the position, presented in "lsymbol"
  private final BigDecimal
      oprice; //	price the position was opened at, calculated as average of underlying executed
  // orders
  private final CexioPositionType
      ptype; //	position type. long - buying product, profitable if product price grows; short -
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

  public CexioOpenPosition(
      @JsonProperty("id") String id,
      @JsonProperty("otime") Long otime,
      @JsonProperty("psymbol") String psymbol,
      @JsonProperty("msymbol") String msymbol,
      @JsonProperty("lsymbol") String lsymbol,
      @JsonProperty("pair") CexioPositionPair pair,
      @JsonProperty("pamount") BigDecimal pamount,
      @JsonProperty("omamount") BigDecimal omamount,
      @JsonProperty("lamount") BigDecimal lamount,
      @JsonProperty("oprice") BigDecimal oprice,
      @JsonProperty("ptype") CexioPositionType ptype,
      @JsonProperty("stopLossPrice") BigDecimal stopLossPrice,
      @JsonProperty("pfee") BigDecimal pfee,
      @JsonProperty("cfee") BigDecimal cfee,
      @JsonProperty("tfeeAmount") BigDecimal tfeeAmount) {
    this.id = id;
    this.otime = otime;
    this.psymbol = psymbol;
    this.msymbol = msymbol;
    this.lsymbol = lsymbol;
    this.pair = pair;
    this.pamount = pamount;
    this.omamount = omamount;
    this.lamount = lamount;
    this.oprice = oprice;
    this.ptype = ptype;
    this.stopLossPrice = stopLossPrice;
    this.pfee = pfee;
    this.cfee = cfee;
    this.tfeeAmount = tfeeAmount;
  }

  public String getId() {
    return id;
  }

  public Long getOtime() {
    return otime;
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

  public CexioPositionPair getPair() {
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

  public BigDecimal getOprice() {
    return oprice;
  }

  public CexioPositionType getPtype() {
    return ptype;
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
}
