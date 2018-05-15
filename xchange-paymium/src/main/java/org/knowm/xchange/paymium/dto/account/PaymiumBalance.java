package org.knowm.xchange.paymium.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PaymiumBalance {

  @JsonProperty("lockedBtc")
  private BigDecimal lockedBtc;

  @JsonProperty("lockedEur")
  private BigDecimal lockedEur;

  @JsonProperty("balanceBtc")
  private BigDecimal balanceBtc;

  @JsonProperty("balanceEur")
  private BigDecimal balanceEur;

  @JsonProperty("metaState")
  private String metaState;

  @JsonProperty("name")
  private String name;

  @JsonProperty("locale")
  private String locale;

  @JsonProperty("channelId")
  private String channelId;

  @JsonProperty("email")
  private String email;

  public PaymiumBalance(
      @JsonProperty("lockedBtc") BigDecimal lockedBtc,
      @JsonProperty("lockedEur") BigDecimal lockedEur,
      @JsonProperty("balanceBtc") BigDecimal balanceBtc,
      @JsonProperty("balanceEur") BigDecimal balanceEur,
      @JsonProperty("metaState") String metaState,
      @JsonProperty("name") String name,
      @JsonProperty("locale") String locale,
      @JsonProperty("channelId") String channelId,
      @JsonProperty("email") String email) {

    super();
    this.lockedBtc = lockedBtc;
    this.lockedEur = lockedEur;
    this.balanceBtc = balanceBtc;
    this.balanceEur = balanceEur;
    this.metaState = metaState;
    this.name = name;
    this.locale = locale;
    this.channelId = channelId;
    this.email = email;
  }

  public BigDecimal getLockedBtc() {
    return lockedBtc;
  }

  public void setLockedBtc(BigDecimal lockedBtc) {
    this.lockedBtc = lockedBtc;
  }

  public BigDecimal getLockedEur() {
    return lockedEur;
  }

  public void setLockedEur(BigDecimal lockedEur) {
    this.lockedEur = lockedEur;
  }

  public BigDecimal getBalanceBtc() {
    return balanceBtc;
  }

  public void setBalanceBtc(BigDecimal balanceBtc) {
    this.balanceBtc = balanceBtc;
  }

  public BigDecimal getBalanceEur() {
    return balanceEur;
  }

  public void setBalanceEur(BigDecimal balanceEur) {
    this.balanceEur = balanceEur;
  }

  public String getMetaState() {
    return metaState;
  }

  public void setMetaState(String metaState) {
    this.metaState = metaState;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {

    return "PaymiumBalance [lockedBtc="
        + lockedBtc
        + ", lockedEur="
        + lockedEur
        + ", balanceBtc="
        + balanceBtc
        + ", balanceEur="
        + balanceEur
        + ", metaState="
        + metaState
        + ", name="
        + name
        + ", locale="
        + locale
        + ", channelId="
        + channelId
        + ", email="
        + email
        + "]";
  }
}
