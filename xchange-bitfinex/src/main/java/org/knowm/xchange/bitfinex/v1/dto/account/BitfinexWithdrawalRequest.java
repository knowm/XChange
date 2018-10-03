package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import java.math.BigDecimal;

public class BitfinexWithdrawalRequest {

  @JsonProperty("withdraw_type")
  private final String withdrawType;

  @JsonProperty("walletselected")
  private final String walletSelected;

  @JsonProperty("amount")
  private final String amount;

  @JsonProperty("address")
  private final String address;

  @JsonProperty("payment_id")
  private final String paymentId;

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("options")
  @JsonRawValue
  protected String options;

  /**
   * Constructor
   *
   * @param nonce
   * @param withdrawType
   * @param walletSelected
   * @param amount
   * @param address
   */
  public BitfinexWithdrawalRequest(
      String nonce,
      String withdrawType,
      String walletSelected,
      BigDecimal amount,
      String address,
      String paymentId) {

    this.request = "/v1/withdraw";
    this.nonce = String.valueOf(nonce);
    this.options = "[]";
    this.withdrawType = withdrawType;
    this.walletSelected = walletSelected;
    this.amount = amount.toString();
    this.address = address;
    this.paymentId = paymentId;
  }

  public String getRequest() {

    return request;
  }

  public void setRequest(String request) {

    this.request = request;
  }

  public String getNonce() {

    return nonce;
  }

  public void setNonce(String nonce) {

    this.nonce = nonce;
  }

  public String getOptions() {

    return options;
  }

  public void setOptions(String options) {

    this.options = options;
  }

  public String getWithdrawType() {
    return withdrawType;
  }

  public String getWalletSelected() {
    return walletSelected;
  }

  public String getAmount() {
    return amount;
  }

  public String getAddress() {
    return address;
  }

  public String getPaymentId() {
    return paymentId;
  }
}
