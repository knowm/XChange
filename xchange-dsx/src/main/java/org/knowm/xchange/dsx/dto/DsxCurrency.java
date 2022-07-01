package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DsxCurrency {

  /** Currency identifier. In the future, the description will simply use the currency */
  private final String id;

  /** Currency full name */
  private final String fullName;

  /** Is currency belongs to blockchain (false for ICO and fiat, like EUR) */
  private final Boolean crypto;

  /** Is allowed for deposit (false for ICO) */
  private final Boolean payinEnabled;

  /** Is required to provide additional information other than the address for deposit */
  private final Boolean payinPaymentId;

  /** Blocks confirmations count for deposit */
  private final Integer payinConfirmations;

  /** Is allowed for withdraw (false for ICO) */
  private final Boolean payoutEnabled;

  /** Is allowed to provide additional information for withdraw */
  private final Boolean payoutIsPaymentId;

  /** Is allowed to transfer between trading and account (may be disabled on maintain) */
  private final Boolean transferEnabled;

  /** True if currency delisted (stopped deposit and trading) */
  private final Boolean delisted;

  /** payoutFee */
  private final BigDecimal payoutFee;

  public DsxCurrency(
      @JsonProperty("id") String id,
      @JsonProperty("fullName") String fullName,
      @JsonProperty("crypto") Boolean crypto,
      @JsonProperty("payinEnabled") Boolean payinEnabled,
      @JsonProperty("payinPaymentId") Boolean payinPaymentId,
      @JsonProperty("payinConfirmations") Integer payinConfirmations,
      @JsonProperty("payoutEnabled") Boolean payoutEnabled,
      @JsonProperty("payoutIsPaymentId") Boolean payoutIsPaymentId,
      @JsonProperty("transferEnabled") Boolean transferEnabled,
      @JsonProperty("delisted") Boolean delisted,
      @JsonProperty("payoutFee") BigDecimal payoutFee) {

    this.id = id;
    this.fullName = fullName;
    this.crypto = crypto;
    this.payinEnabled = payinEnabled;
    this.payinPaymentId = payinPaymentId;
    this.payinConfirmations = payinConfirmations;
    this.payoutEnabled = payoutEnabled;
    this.payoutIsPaymentId = payoutIsPaymentId;
    this.transferEnabled = transferEnabled;
    this.delisted = delisted;
    this.payoutFee = payoutFee;
  }

  public String getId() {
    return id;
  }

  public String getFullName() {
    return fullName;
  }

  public Boolean getCrypto() {
    return crypto;
  }

  public Boolean getPayinEnabled() {
    return payinEnabled;
  }

  public Boolean getPayinPaymentId() {
    return payinPaymentId;
  }

  public Integer getPayinConfirmations() {
    return payinConfirmations;
  }

  public Boolean getPayoutEnabled() {
    return payoutEnabled;
  }

  public Boolean getPayoutIsPaymentId() {
    return payoutIsPaymentId;
  }

  public Boolean getTransferEnabled() {
    return transferEnabled;
  }

  public Boolean getDelisted() {
    return delisted;
  }

  public BigDecimal getPayoutFee() {
    return payoutFee;
  }

  @Override
  public String toString() {
    return "DsxCurrency{"
        + "id='"
        + id
        + '\''
        + ", fullName='"
        + fullName
        + '\''
        + ", crypto="
        + crypto
        + ", payinEnabled="
        + payinEnabled
        + ", payinPaymentId="
        + payinPaymentId
        + ", payinConfirmations="
        + payinConfirmations
        + ", payoutEnabled="
        + payoutEnabled
        + ", payoutIsPaymentId="
        + payoutIsPaymentId
        + ", transferEnabled="
        + transferEnabled
        + ", delisted="
        + delisted
        + ", payoutFee="
        + payoutFee
        + '}';
  }
}
