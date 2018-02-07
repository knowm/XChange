/*
 * BitMEX API
 * ## REST API for the BitMEX Trading Platform  [View Changelog](/app/apiChangelog)  ----  #### Getting Started  Base URI: [https://www.bitmex.com/api/v1](/api/v1)  ##### Fetching Data  All REST endpoints are documented below. You can try out any query right from this interface.  Most table queries accept `count`, `start`, and `reverse` params. Set `reverse=true` to get rows newest-first.  Additional documentation regarding filters, timestamps, and authentication is available in [the main API documentation](/app/restAPI).  *All* table data is available via the [Websocket](/app/wsAPI). We highly recommend using the socket if you want to have the quickest possible data without being subject to ratelimits.  ##### Return Types  By default, all data is returned as JSON. Send `?_format=csv` to get CSV data or `?_format=xml` to get XML data.  ##### Trade Data Queries  *This is only a small subset of what is available, to get you started.*  Fill in the parameters and click the `Try it out!` button to try any of these queries.  * [Pricing Data](#!/Quote/Quote_get)  * [Trade Data](#!/Trade/Trade_get)  * [OrderBook Data](#!/OrderBook/OrderBook_getL2)  * [Settlement Data](#!/Settlement/Settlement_get)  * [Exchange Statistics](#!/Stats/Stats_history)  Every function of the BitMEX.com platform is exposed here and documented. Many more functions are available.  ##### Swagger Specification  [⇩ Download Swagger JSON](swagger.json)  ----  ## All API Endpoints  Click to expand a section. 
 *
 * OpenAPI spec version: 1.2.0
 * Contact: support@bitmex.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.knowm.xchange.bitmex.dto;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * BitmexWallet
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-06T17:10:23.689Z")
public class BitmexWallet {
  @SerializedName("account")
  private BigDecimal account = null;

  @SerializedName("currency")
  private String currency = null;

  @SerializedName("prevDeposited")
  private BigDecimal prevDeposited = null;

  @SerializedName("prevWithdrawn")
  private BigDecimal prevWithdrawn = null;

  @SerializedName("prevTransferIn")
  private BigDecimal prevTransferIn = null;

  @SerializedName("prevTransferOut")
  private BigDecimal prevTransferOut = null;

  @SerializedName("prevAmount")
  private BigDecimal prevAmount = null;

  @SerializedName("prevTimestamp")
  private OffsetDateTime prevTimestamp = null;

  @SerializedName("deltaDeposited")
  private BigDecimal deltaDeposited = null;

  @SerializedName("deltaWithdrawn")
  private BigDecimal deltaWithdrawn = null;

  @SerializedName("deltaTransferIn")
  private BigDecimal deltaTransferIn = null;

  @SerializedName("deltaTransferOut")
  private BigDecimal deltaTransferOut = null;

  @SerializedName("deltaAmount")
  private BigDecimal deltaAmount = null;

  @SerializedName("deposited")
  private BigDecimal deposited = null;

  @SerializedName("withdrawn")
  private BigDecimal withdrawn = null;

  @SerializedName("transferIn")
  private BigDecimal transferIn = null;

  @SerializedName("transferOut")
  private BigDecimal transferOut = null;

  @SerializedName("amount")
  private BigDecimal amount = null;

  @SerializedName("pendingCredit")
  private BigDecimal pendingCredit = null;

  @SerializedName("pendingDebit")
  private BigDecimal pendingDebit = null;

  @SerializedName("confirmedDebit")
  private BigDecimal confirmedDebit = null;

  @SerializedName("timestamp")
  private OffsetDateTime timestamp = null;

  @SerializedName("addr")
  private String addr = null;

  @SerializedName("script")
  private String script = null;

  @SerializedName("withdrawalLock")
  private List<String> withdrawalLock = null;

  public BitmexWallet account(BigDecimal account) {
    this.account = account;
    return this;
  }

   /**
   * Get account
   * @return account
  **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getAccount() {
    return account;
  }

  public void setAccount(BigDecimal account) {
    this.account = account;
  }

  public BitmexWallet currency(String currency) {
    this.currency = currency;
    return this;
  }

   /**
   * Get currency
   * @return currency
  **/
  @ApiModelProperty(required = true, value = "")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BitmexWallet prevDeposited(BigDecimal prevDeposited) {
    this.prevDeposited = prevDeposited;
    return this;
  }

   /**
   * Get prevDeposited
   * @return prevDeposited
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPrevDeposited() {
    return prevDeposited;
  }

  public void setPrevDeposited(BigDecimal prevDeposited) {
    this.prevDeposited = prevDeposited;
  }

  public BitmexWallet prevWithdrawn(BigDecimal prevWithdrawn) {
    this.prevWithdrawn = prevWithdrawn;
    return this;
  }

   /**
   * Get prevWithdrawn
   * @return prevWithdrawn
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPrevWithdrawn() {
    return prevWithdrawn;
  }

  public void setPrevWithdrawn(BigDecimal prevWithdrawn) {
    this.prevWithdrawn = prevWithdrawn;
  }

  public BitmexWallet prevTransferIn(BigDecimal prevTransferIn) {
    this.prevTransferIn = prevTransferIn;
    return this;
  }

   /**
   * Get prevTransferIn
   * @return prevTransferIn
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPrevTransferIn() {
    return prevTransferIn;
  }

  public void setPrevTransferIn(BigDecimal prevTransferIn) {
    this.prevTransferIn = prevTransferIn;
  }

  public BitmexWallet prevTransferOut(BigDecimal prevTransferOut) {
    this.prevTransferOut = prevTransferOut;
    return this;
  }

   /**
   * Get prevTransferOut
   * @return prevTransferOut
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPrevTransferOut() {
    return prevTransferOut;
  }

  public void setPrevTransferOut(BigDecimal prevTransferOut) {
    this.prevTransferOut = prevTransferOut;
  }

  public BitmexWallet prevAmount(BigDecimal prevAmount) {
    this.prevAmount = prevAmount;
    return this;
  }

   /**
   * Get prevAmount
   * @return prevAmount
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPrevAmount() {
    return prevAmount;
  }

  public void setPrevAmount(BigDecimal prevAmount) {
    this.prevAmount = prevAmount;
  }

  public BitmexWallet prevTimestamp(OffsetDateTime prevTimestamp) {
    this.prevTimestamp = prevTimestamp;
    return this;
  }

   /**
   * Get prevTimestamp
   * @return prevTimestamp
  **/
  @ApiModelProperty(value = "")
  public OffsetDateTime getPrevTimestamp() {
    return prevTimestamp;
  }

  public void setPrevTimestamp(OffsetDateTime prevTimestamp) {
    this.prevTimestamp = prevTimestamp;
  }

  public BitmexWallet deltaDeposited(BigDecimal deltaDeposited) {
    this.deltaDeposited = deltaDeposited;
    return this;
  }

   /**
   * Get deltaDeposited
   * @return deltaDeposited
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getDeltaDeposited() {
    return deltaDeposited;
  }

  public void setDeltaDeposited(BigDecimal deltaDeposited) {
    this.deltaDeposited = deltaDeposited;
  }

  public BitmexWallet deltaWithdrawn(BigDecimal deltaWithdrawn) {
    this.deltaWithdrawn = deltaWithdrawn;
    return this;
  }

   /**
   * Get deltaWithdrawn
   * @return deltaWithdrawn
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getDeltaWithdrawn() {
    return deltaWithdrawn;
  }

  public void setDeltaWithdrawn(BigDecimal deltaWithdrawn) {
    this.deltaWithdrawn = deltaWithdrawn;
  }

  public BitmexWallet deltaTransferIn(BigDecimal deltaTransferIn) {
    this.deltaTransferIn = deltaTransferIn;
    return this;
  }

   /**
   * Get deltaTransferIn
   * @return deltaTransferIn
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getDeltaTransferIn() {
    return deltaTransferIn;
  }

  public void setDeltaTransferIn(BigDecimal deltaTransferIn) {
    this.deltaTransferIn = deltaTransferIn;
  }

  public BitmexWallet deltaTransferOut(BigDecimal deltaTransferOut) {
    this.deltaTransferOut = deltaTransferOut;
    return this;
  }

   /**
   * Get deltaTransferOut
   * @return deltaTransferOut
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getDeltaTransferOut() {
    return deltaTransferOut;
  }

  public void setDeltaTransferOut(BigDecimal deltaTransferOut) {
    this.deltaTransferOut = deltaTransferOut;
  }

  public BitmexWallet deltaAmount(BigDecimal deltaAmount) {
    this.deltaAmount = deltaAmount;
    return this;
  }

   /**
   * Get deltaAmount
   * @return deltaAmount
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getDeltaAmount() {
    return deltaAmount;
  }

  public void setDeltaAmount(BigDecimal deltaAmount) {
    this.deltaAmount = deltaAmount;
  }

  public BitmexWallet deposited(BigDecimal deposited) {
    this.deposited = deposited;
    return this;
  }

   /**
   * Get deposited
   * @return deposited
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getDeposited() {
    return deposited;
  }

  public void setDeposited(BigDecimal deposited) {
    this.deposited = deposited;
  }

  public BitmexWallet withdrawn(BigDecimal withdrawn) {
    this.withdrawn = withdrawn;
    return this;
  }

   /**
   * Get withdrawn
   * @return withdrawn
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getWithdrawn() {
    return withdrawn;
  }

  public void setWithdrawn(BigDecimal withdrawn) {
    this.withdrawn = withdrawn;
  }

  public BitmexWallet transferIn(BigDecimal transferIn) {
    this.transferIn = transferIn;
    return this;
  }

   /**
   * Get transferIn
   * @return transferIn
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getTransferIn() {
    return transferIn;
  }

  public void setTransferIn(BigDecimal transferIn) {
    this.transferIn = transferIn;
  }

  public BitmexWallet transferOut(BigDecimal transferOut) {
    this.transferOut = transferOut;
    return this;
  }

   /**
   * Get transferOut
   * @return transferOut
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getTransferOut() {
    return transferOut;
  }

  public void setTransferOut(BigDecimal transferOut) {
    this.transferOut = transferOut;
  }

  public BitmexWallet amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BitmexWallet pendingCredit(BigDecimal pendingCredit) {
    this.pendingCredit = pendingCredit;
    return this;
  }

   /**
   * Get pendingCredit
   * @return pendingCredit
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPendingCredit() {
    return pendingCredit;
  }

  public void setPendingCredit(BigDecimal pendingCredit) {
    this.pendingCredit = pendingCredit;
  }

  public BitmexWallet pendingDebit(BigDecimal pendingDebit) {
    this.pendingDebit = pendingDebit;
    return this;
  }

   /**
   * Get pendingDebit
   * @return pendingDebit
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getPendingDebit() {
    return pendingDebit;
  }

  public void setPendingDebit(BigDecimal pendingDebit) {
    this.pendingDebit = pendingDebit;
  }

  public BitmexWallet confirmedDebit(BigDecimal confirmedDebit) {
    this.confirmedDebit = confirmedDebit;
    return this;
  }

   /**
   * Get confirmedDebit
   * @return confirmedDebit
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getConfirmedDebit() {
    return confirmedDebit;
  }

  public void setConfirmedDebit(BigDecimal confirmedDebit) {
    this.confirmedDebit = confirmedDebit;
  }

  public BitmexWallet timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  @ApiModelProperty(value = "")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public BitmexWallet addr(String addr) {
    this.addr = addr;
    return this;
  }

   /**
   * Get addr
   * @return addr
  **/
  @ApiModelProperty(value = "")
  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public BitmexWallet script(String script) {
    this.script = script;
    return this;
  }

   /**
   * Get script
   * @return script
  **/
  @ApiModelProperty(value = "")
  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public BitmexWallet withdrawalLock(List<String> withdrawalLock) {
    this.withdrawalLock = withdrawalLock;
    return this;
  }

  public BitmexWallet addWithdrawalLockItem(String withdrawalLockItem) {
    if (this.withdrawalLock == null) {
      this.withdrawalLock = new ArrayList<>();
    }
    this.withdrawalLock.add(withdrawalLockItem);
    return this;
  }

   /**
   * Get withdrawalLock
   * @return withdrawalLock
  **/
  @ApiModelProperty(value = "")
  public List<String> getWithdrawalLock() {
    return withdrawalLock;
  }

  public void setWithdrawalLock(List<String> withdrawalLock) {
    this.withdrawalLock = withdrawalLock;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BitmexWallet wallet = (BitmexWallet) o;
    return Objects.equals(this.account, wallet.account) &&
        Objects.equals(this.currency, wallet.currency) &&
        Objects.equals(this.prevDeposited, wallet.prevDeposited) &&
        Objects.equals(this.prevWithdrawn, wallet.prevWithdrawn) &&
        Objects.equals(this.prevTransferIn, wallet.prevTransferIn) &&
        Objects.equals(this.prevTransferOut, wallet.prevTransferOut) &&
        Objects.equals(this.prevAmount, wallet.prevAmount) &&
        Objects.equals(this.prevTimestamp, wallet.prevTimestamp) &&
        Objects.equals(this.deltaDeposited, wallet.deltaDeposited) &&
        Objects.equals(this.deltaWithdrawn, wallet.deltaWithdrawn) &&
        Objects.equals(this.deltaTransferIn, wallet.deltaTransferIn) &&
        Objects.equals(this.deltaTransferOut, wallet.deltaTransferOut) &&
        Objects.equals(this.deltaAmount, wallet.deltaAmount) &&
        Objects.equals(this.deposited, wallet.deposited) &&
        Objects.equals(this.withdrawn, wallet.withdrawn) &&
        Objects.equals(this.transferIn, wallet.transferIn) &&
        Objects.equals(this.transferOut, wallet.transferOut) &&
        Objects.equals(this.amount, wallet.amount) &&
        Objects.equals(this.pendingCredit, wallet.pendingCredit) &&
        Objects.equals(this.pendingDebit, wallet.pendingDebit) &&
        Objects.equals(this.confirmedDebit, wallet.confirmedDebit) &&
        Objects.equals(this.timestamp, wallet.timestamp) &&
        Objects.equals(this.addr, wallet.addr) &&
        Objects.equals(this.script, wallet.script) &&
        Objects.equals(this.withdrawalLock, wallet.withdrawalLock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, currency, prevDeposited, prevWithdrawn, prevTransferIn, prevTransferOut, prevAmount, prevTimestamp, deltaDeposited, deltaWithdrawn, deltaTransferIn, deltaTransferOut, deltaAmount, deposited, withdrawn, transferIn, transferOut, amount, pendingCredit, pendingDebit, confirmedDebit, timestamp, addr, script, withdrawalLock);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BitmexWallet {\n");
    
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    prevDeposited: ").append(toIndentedString(prevDeposited)).append("\n");
    sb.append("    prevWithdrawn: ").append(toIndentedString(prevWithdrawn)).append("\n");
    sb.append("    prevTransferIn: ").append(toIndentedString(prevTransferIn)).append("\n");
    sb.append("    prevTransferOut: ").append(toIndentedString(prevTransferOut)).append("\n");
    sb.append("    prevAmount: ").append(toIndentedString(prevAmount)).append("\n");
    sb.append("    prevTimestamp: ").append(toIndentedString(prevTimestamp)).append("\n");
    sb.append("    deltaDeposited: ").append(toIndentedString(deltaDeposited)).append("\n");
    sb.append("    deltaWithdrawn: ").append(toIndentedString(deltaWithdrawn)).append("\n");
    sb.append("    deltaTransferIn: ").append(toIndentedString(deltaTransferIn)).append("\n");
    sb.append("    deltaTransferOut: ").append(toIndentedString(deltaTransferOut)).append("\n");
    sb.append("    deltaAmount: ").append(toIndentedString(deltaAmount)).append("\n");
    sb.append("    deposited: ").append(toIndentedString(deposited)).append("\n");
    sb.append("    withdrawn: ").append(toIndentedString(withdrawn)).append("\n");
    sb.append("    transferIn: ").append(toIndentedString(transferIn)).append("\n");
    sb.append("    transferOut: ").append(toIndentedString(transferOut)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    pendingCredit: ").append(toIndentedString(pendingCredit)).append("\n");
    sb.append("    pendingDebit: ").append(toIndentedString(pendingDebit)).append("\n");
    sb.append("    confirmedDebit: ").append(toIndentedString(confirmedDebit)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    addr: ").append(toIndentedString(addr)).append("\n");
    sb.append("    script: ").append(toIndentedString(script)).append("\n");
    sb.append("    withdrawalLock: ").append(toIndentedString(withdrawalLock)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

