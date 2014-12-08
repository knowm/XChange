package com.xeiam.xchange.bitstamp.dto.account;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.xeiam.xchange.bitstamp.util.WithdrawalRequestStatusDeserializer;
import com.xeiam.xchange.bitstamp.util.WithdrawalRequestTypeDeserializer;
import com.xeiam.xchange.utils.jackson.EnumIntDeserializerOneBased;
import com.xeiam.xchange.utils.jackson.SqlTimeDeserializer;

public class WithdrawalRequest {

  private Long id;

  @JsonDeserialize(using = SqlTimeDeserializer.class)
  private Date datetime;

  @JsonDeserialize(using = WithdrawalRequestTypeDeserializer.class)
  private Type type;

  private BigDecimal amount;

  @JsonDeserialize(using = WithdrawalRequestStatusDeserializer.class)
  private Status status;

//  private String data; // additional withdrawal request data

  ////////////////////

  public Long getId() {
    return id;
  }

  public Date getDatetime() {
    return datetime;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return String.format("WithdrawalRequest{id=%d, datetime=%s, type=%s, amount=%s, status=%s}",
        id, datetime, type, amount, status);
  }

  public static enum Type {
    SEPA, bitcoin, wire, type3, type4, type5, rippleUSD, rippleBTC
  }

  public static enum Status {
    open, in_process, finished, canceled, failed
  }
}
