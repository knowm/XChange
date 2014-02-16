package com.xeiam.xchange.coinbase.dto.trade;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransaction.CoinbaseTransactionStatus;

public interface CoinbaseTransactionInfo {

  @JsonIgnore
  public String getId();

  @JsonIgnore
  public Date getCreatedAt();

  @JsonIgnore
  public CoinbaseAmount getAmount();

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
