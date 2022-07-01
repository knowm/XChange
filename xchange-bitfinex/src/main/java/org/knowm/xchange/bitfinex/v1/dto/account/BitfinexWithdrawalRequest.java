package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BitfinexWithdrawalRequest {

  @JsonProperty("withdraw_type")
  private final String withdrawType;

  @JsonProperty("walletselected")
  private final String walletSelected;

  private final String amount;

  private final String address;

  @JsonProperty("payment_id")
  private final String paymentId;

  protected String request;

  protected String nonce;

  @JsonRawValue protected String options;

  private String currency;

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
}
