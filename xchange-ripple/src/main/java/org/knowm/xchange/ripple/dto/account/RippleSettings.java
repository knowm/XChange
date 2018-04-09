package org.knowm.xchange.ripple.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class RippleSettings {

  private static final BigDecimal TRANSFER_RATE_DENOMINATOR = BigDecimal.valueOf(1000000000);

  private String account;

  @JsonProperty("transfer_rate")
  private int transferRate;

  @JsonProperty("password_spent")
  private boolean passwordSpent;

  @JsonProperty("require_destination_tag")
  private boolean requireDestinationTag;

  @JsonProperty("require_authorization")
  private boolean requireAuthorization;

  @JsonProperty("disallow_xrp")
  private boolean disallowXRP;

  @JsonProperty("disable_master")
  private boolean disableMaster;

  @JsonProperty("no_freeze")
  private boolean noFreeze;

  @JsonProperty("global_freeze")
  private boolean globalFreeze;

  @JsonProperty("default_ripple")
  private boolean defaultRipple;

  @JsonProperty("transaction_sequence")
  private String transactionSequence;

  @JsonProperty("email_hash")
  private String emailHash;

  @JsonProperty("wallet_locator")
  private String walletLocator;

  @JsonProperty("wallet_size")
  private String walletSize;

  @JsonProperty("message_key")
  private String messageKey;

  private String domain;

  private String signers;

  public String getAccount() {
    return account;
  }

  public void setAccount(final String account) {
    this.account = account;
  }

  /**
   * The raw transfer rate is represented as an integer, the amount that must be sent in order for 1
   * billion units to arrive. For example, a 20% transfer fee is represented as the value 120000000.
   * The value cannot be less than 1000000000. Less than that would indicate giving away money for
   * sending transactions, which is exploitable. You can specify 0 as a shortcut for 1000000000,
   * meaning no fee.
   *
   * @return percentage transfer rate charge
   */
  public BigDecimal getTransferFeeRate() {
    if (transferRate == 0) {
      return BigDecimal.ZERO;
    } else {
      return BigDecimal.valueOf(transferRate)
          .divide(TRANSFER_RATE_DENOMINATOR)
          .subtract(BigDecimal.ONE);
    }
  }

  public int getTransferRate() {
    return transferRate;
  }

  public void setTransferRate(final int transferRate) {
    this.transferRate = transferRate;
  }

  public boolean isPasswordSpent() {
    return passwordSpent;
  }

  public void setPasswordSpent(final boolean passwordSpent) {
    this.passwordSpent = passwordSpent;
  }

  public boolean isRequireDestinationTag() {
    return requireDestinationTag;
  }

  public void setRequireDestinationTag(final boolean requireDestinationTag) {
    this.requireDestinationTag = requireDestinationTag;
  }

  public boolean isRequireAuthorization() {
    return requireAuthorization;
  }

  public void setRequireAuthorization(final boolean requireAuthorization) {
    this.requireAuthorization = requireAuthorization;
  }

  public boolean isDisallowXRP() {
    return disallowXRP;
  }

  public void setDisallowXRP(final boolean disallowXRP) {
    this.disallowXRP = disallowXRP;
  }

  public boolean isDisableMaster() {
    return disableMaster;
  }

  public void setDisableMaster(final boolean disallowMaster) {
    this.disableMaster = disallowMaster;
  }

  public boolean isNoFreeze() {
    return noFreeze;
  }

  public void setNoFreeze(final boolean noFreeze) {
    this.noFreeze = noFreeze;
  }

  public boolean isGlobalFreeze() {
    return globalFreeze;
  }

  public void setGlobalFreeze(final boolean globalFreeze) {
    this.globalFreeze = globalFreeze;
  }

  public boolean isDefaultRipple() {
    return defaultRipple;
  }

  public void setDefaultRipple(final boolean defaultRipple) {
    this.defaultRipple = defaultRipple;
  }

  public String getTransactionSequence() {
    return transactionSequence;
  }

  public void setTransactionSequence(final String transactionSequence) {
    this.transactionSequence = transactionSequence;
  }

  public String getEmailHash() {
    return emailHash;
  }

  public void setEmailHash(final String emailHash) {
    this.emailHash = emailHash;
  }

  public String getWalletLocator() {
    return walletLocator;
  }

  public void setWalletLocator(final String walletLocator) {
    this.walletLocator = walletLocator;
  }

  public String getWalletSize() {
    return walletSize;
  }

  public void setWalletSize(final String walletSize) {
    this.walletSize = walletSize;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(final String messageKey) {
    this.messageKey = messageKey;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public String getSigners() {
    return signers;
  }

  public void setSigners(final String signers) {
    this.signers = signers;
  }

  @Override
  public String toString() {
    return String.format("%s [account=%s]", getClass().getSimpleName(), account);
  }
}
