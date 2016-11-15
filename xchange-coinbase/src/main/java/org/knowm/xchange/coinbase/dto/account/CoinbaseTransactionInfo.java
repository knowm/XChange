package org.knowm.xchange.coinbase.dto.account;

import java.util.Date;

import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseTransactionStatus;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jamespedwards42
 */
public interface CoinbaseTransactionInfo {

  @JsonIgnore
  public String getId();

  @JsonIgnore
  public Date getCreatedAt();

  @JsonIgnore
  public CoinbaseMoney getAmount();

  @JsonIgnore
  public boolean isRequest();

  @JsonIgnore
  public CoinbaseTransactionStatus getStatus();

  @JsonIgnore
  public CoinbaseUser getSender();

  @JsonIgnore
  public CoinbaseUser getRecipient();

  @JsonIgnore
  public String getRecipientAddress();

  public String getNotes();

  @JsonIgnore
  public String getTransactionHash();

  @JsonIgnore
  public String getIdempotencyKey();
};
